package com.example.myapplication.PresentAPI

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.example.myapplication.ClockService
import com.example.myapplication.MainActivity
import com.example.myapplication.Model.*
import java.util.*


/**
 * Created by 上官轩明 on 2018/1/9.
 */
class PresenterImp(private val view: MainActivity, private val alarm: AlarmManager) : PresenterAPI {

    private val model = ModelImp(this)
    private var cur = Calendar.getInstance()
    private val intentToBroadcast = Intent("android.intent.action.MyService").putExtra("from", "alarm")
    private val pi = PendingIntent.getBroadcast(view, 99, intentToBroadcast, 0)
    var preListenCnt = 0
    var nowListenCnt = 0
    var isOnline = false
    lateinit var auth_token: String
    lateinit var csrf_token: String
    lateinit var articleListBean: ArticleListBean
    lateinit var listenListBean: ListenListBean
    lateinit var freshStBean: FreshStBean
    lateinit var mBinder: ClockService.ViewBinder

    override fun start() {
        if (!isOnline) return
        setAlarm(false)
        mBinder.updateInfo("运行中", getAlarm())
    }

    override fun login() {
        model.login()
    }

    override fun check() {
        Log.d("woggle", "正在查卡")
        model.check(auth_token, this::callbackCheck)
    }

    override fun checkin() {
        Log.d("woggle", "正在打卡")
        model.checkin(auth_token, this::callbackCheckin)
    }

    override fun getArticleList() {
        model.getArticleList(this::callbackGetArtList)
    }

    override fun getListenList() {
        model.getListenList(this::callbackGetListenList)
    }


    override fun getListenStatus() {
        model.getListenStatus(this::callbackGetLisStatus)
    }

    override fun getFreshStBean() {
        model.getFreshStBean(this::callbackGetFreshStBean)
    }

    override fun read() {
        model.read(articleListBean.data.objects[0].id, this::callbackRead)
        model.read(articleListBean.data.objects[1].id, this::callbackRead)
    }

    override fun listen() {
        model.listen(listenListBean.data[0].article_id.toString(), listenListBean.data[0].sentence_id.toString(), this@PresenterImp::callbackGetLisStatus)
    }


    override fun learnSt() {
        model.learnSt(freshStBean.data.reviews[0].id.toString()) {
            val pos =it.indexOf("num_review_status_finished\": ")+"num_review_status_finished\": ".length
            val pos2 = it.indexOf(",",pos-1)
            val finished_num = it.substring(pos..pos2)
            view.showLogs("句子学习完成了："+finished_num)
        }
    }

    ////////////////////////////////////////////////////////设置Alarm//////////////////////////////////////////////////////
    override fun setAlarm(isInit: Boolean) {

        if (isInit) {
            cur = Calendar.getInstance()
            view.showLogs("init" + getAlarm())
            cur.add(Calendar.DAY_OF_YEAR, 1)
            cur.set(cur.get(Calendar.YEAR), cur.get(Calendar.MONTH), cur.get(Calendar.DAY_OF_MONTH), 8, 0)//将时间设置为第当天00：30

            alarm.set(AlarmManager.RTC_WAKEUP, cur.timeInMillis, pi)
            preListenCnt = 0

        } else {
            cur = Calendar.getInstance()
            val today23 = Calendar.getInstance()
            today23.set(cur.get(Calendar.YEAR), cur.get(Calendar.MONTH), cur.get(Calendar.DAY_OF_MONTH), 23, 0)//将时间设置为当天23：00
            val today030 = Calendar.getInstance()
            today030.set(cur.get(Calendar.YEAR), cur.get(Calendar.MONTH), cur.get(Calendar.DAY_OF_MONTH), 0, 30)//将时间设置为第当天00：30

            if (cur.after(today23) || cur.before(today030)) {
                cur.add(Calendar.MINUTE, 1)
                alarm.set(AlarmManager.RTC_WAKEUP, cur.timeInMillis, pi)
            } else {
//                cur.add(Calendar.HOUR_OF_DAY, 1)
//                cur.set(cur.get(Calendar.YEAR), cur.get(Calendar.MONTH), cur.get(Calendar.DAY_OF_MONTH), cur.get(Calendar.HOUR_OF_DAY), 0)
                cur.add(Calendar.MINUTE, 1)
                alarm.set(AlarmManager.RTC_WAKEUP, cur.timeInMillis, pi)
            }
            view.showLogs("设置闹钟" + getAlarm())
        }
    }
////////////////////////////////////////////////////////设置Alarm//////////////////////////////////////////////////////


    override fun showlogs(logs: String) {
        if (logs.contains("SUCCESS")) isOnline = true
        view.showLogs(logs)
        view.showLogs(auth_token)
        view.showLogs(csrf_token)
    }

    override fun setToken(header: String) {
        header.takeIf { it.contains("Set-Cookie") && it.contains("auth") }
                ?.apply {
                    this@PresenterImp.auth_token = header.substring(header.indexOf("auth") + "auth_token=".length, header.indexOf(";"))
                    model.auth_token = auth_token
                }
        //不能从这里修改ui因为拦截log是在io线程调用该函数
        header.takeIf { it.contains("Set-Cookie") && it.contains("csrf") }
                ?.apply {
                    this@PresenterImp.csrf_token = header.substring(header.indexOf("csrf") + "csrftoken=".length, header.indexOf(";"))
                    model.csrftoken = csrf_token
                }


    }

    override fun callbackCheck(checkBean: CheckBean) {
        Log.d("woggle", checkBean.data.checked.toString())
        when (checkBean.data.checked) {
            true -> {
                setAlarm(true)
                view.showLogs("已打卡" + getAlarm())
                mBinder.updateInfo("已打卡", getAlarm())
            }
            false -> {
                if (checkBean.data.finished) {
                    checkin()
                } else {
                    setAlarm(false)
                    Toast.makeText(view, "快去学习！！！", Toast.LENGTH_SHORT).show()
                    view.showLogs("还未学习" + getAlarm())
                    mBinder.updateInfo("还未学习", getAlarm())
                }

            }

        }
    }

    override fun callbackCheckin(checkinBean: CheckinBean) {
        if (checkinBean.msg.equals("SUCCESS")) {
            setAlarm(true)
            view.showLogs("打卡成功" + getAlarm())
            mBinder.updateInfo("打卡成功", getAlarm())
        } else {
            view.showLogs("打卡失败请手动打卡" + getAlarm())
            mBinder.updateInfo("打卡失败", getAlarm())
            setAlarm(false)
            Toast.makeText(view, "请手动打卡！！！", Toast.LENGTH_LONG).show()
        }
    }

    override fun callbackGetArtList(articleListBean: ArticleListBean) {
        this.articleListBean = articleListBean
    }


    override fun callbackGetListenList(listenListBean: ListenListBean) {
        view.showLogs("获取听力列表:" + listenListBean.msg + "数量:" + listenListBean.data.size)
        this.listenListBean = listenListBean
    }

    override fun callbackGetLisStatus(listenStatus: ListenStatus) {
        if (preListenCnt == 0) {
            preListenCnt = listenStatus.data.accumulated_finished_sentences
            view.showLogs("pre" + preListenCnt)
        } else {
            nowListenCnt = listenStatus.data.accumulated_finished_sentences
            view.showLogs("now" + nowListenCnt)
            if (nowListenCnt - preListenCnt >= 5) {
                mBinder.updateInfo("完成听力", getAlarm())
                view.showLogs("完成听力")
            } else {
                mBinder.updateInfo("未完成听力", getAlarm())
                view.showLogs("未完成听力")
            }
        }
    }


    override fun callbackGetFreshStBean(freshStBean: FreshStBean) {
        this.freshStBean = freshStBean
    }

    override fun callbackRead(message: String) {
        Log.d("woggle", message)
        view.showLogs("read：" + message)
    }

    override fun callbackListen(message: String) {
        Log.d("woggle", "gg")
    }


    override fun getAlarm(): String = "AL：" + cur.get(Calendar.YEAR) + "年" + (cur.get(Calendar.MONTH) + 1) + "月" + cur.get(Calendar.DAY_OF_MONTH) + "日" + cur.get(Calendar.HOUR_OF_DAY) + "点" + cur.get(Calendar.MINUTE) + "分"
}