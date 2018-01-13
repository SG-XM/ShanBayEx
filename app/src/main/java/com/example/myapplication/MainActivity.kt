package com.example.myapplication

import android.app.AlarmManager
import android.content.*
import android.os.Bundle
import android.os.IBinder
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.example.myapplication.PresentAPI.PresenterImp
import com.example.myapplication.View.MainVeiwAPI
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainVeiwAPI {

    lateinit var tv_logs: TextView
    lateinit var button: Button
    lateinit var button_get: Button
    lateinit var mpresenter: PresenterImp
    lateinit var connection: ServiceConnection
    lateinit var alarm: AlarmManager
    val receiver: AlarmReceiver = AlarmReceiver()
    val intentFilter = IntentFilter("android.intent.action.MyService")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    fun init() {
        tv_logs = findViewById<TextView>(R.id.tv_logs)
        //tv_logs.movementMethod = ScrollingMovementMethod.getInstance()
        button = findViewById(R.id.button)
        button_get = findViewById(R.id.button_get)
        button.setOnClickListener({ _ -> mpresenter.start() })
        button_get.setOnClickListener({ _ -> mpresenter.getArticleList() })
        button_getlis.setOnClickListener({ _ -> mpresenter.getListenList() })
        button_read.setOnClickListener({ _ -> mpresenter.read() })
        button_listen.setOnClickListener({ _ -> mpresenter.listen() })
        button_getsts.setOnClickListener { mpresenter.getFreshStBean() }
        button_lnsts.setOnClickListener { mpresenter.learnSt() }
        alarm = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        connection = object : ServiceConnection {
            override fun onServiceDisconnected(name: ComponentName?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                mpresenter.mBinder = service as ClockService.ViewBinder
                showLogs("服务已绑定，可以开始")
            }
        }
        mpresenter = PresenterImp(this@MainActivity, alarm)
        mpresenter.login()
        val intentService = Intent(this, ClockService::class.java)
        startService(intentService)
        bindService(intentService, connection, Context.BIND_AUTO_CREATE)
        Thread {
            Thread.sleep(2000)
            runOnUiThread { mpresenter.start() }
        }.start()


    }

///////////////////////////////////////////////广播接收器////////////////////////////////////////////////

    inner class AlarmReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            when (intent.getStringExtra("from")) {
                "alarm" -> {
                    Log.d("woggle", "来自Alarm")
                    mpresenter.check()
                }
            }

        }
    }

    ///////////////////////////////////////////////广播接收器////////////////////////////////////////////////
    override fun onResume() {
        registerReceiver(receiver, intentFilter)
        super.onResume()
    }

    override fun onDestroy() {
        unregisterReceiver(receiver)
        super.onDestroy()
    }

    override fun showLogs(text: String) {
        val temp: String = tv_logs.text as String
        tv_logs.setText(temp + '\n' + text)
    }
}
