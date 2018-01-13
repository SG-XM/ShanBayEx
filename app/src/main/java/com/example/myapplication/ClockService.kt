package com.example.myapplication

import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.support.v7.app.NotificationCompat
import android.util.Log
import android.widget.Toast

/**
 * Created by 上官轩明 on 2018/1/9.
 */
class ClockService : Service() {
    lateinit var serviceintent: Intent
    lateinit var notifyMgr: NotificationManager
    val mbinder = ViewBinder()


    override fun onBind(intent: Intent?): IBinder {
        return mbinder
    }


    override fun onCreate() {
        notifyMgr = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val intent = Intent(this, MainActivity::class.java)
        val pi = PendingIntent.getActivity(this, 0, intent, 0)
        val notification = NotificationCompat
                .Builder(this)
                .setContentTitle("ShanBay")
                .setContentText("运行中")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentIntent(pi)
                .build()
        startForeground(1, notification)
        Toast.makeText(this, "服务已启动", Toast.LENGTH_SHORT).show()
        super.onCreate()
    }


////////////////////////////////////////////获取intent和presenter////////////////////////////////////////////////////

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        this.serviceintent = intent!!
        Log.d("woggle", "start")
        return super.onStartCommand(intent, flags, startId)
    }

////////////////////////////////////////////获取intent和presenter////////////////////////////////////////////////////

////////////////////////////////////////////重启服务////////////////////////////////////////////////////////////

    override fun onDestroy() {
        Toast.makeText(this, "服务被杀掉了", Toast.LENGTH_SHORT).show()
        startService(serviceintent)
        super.onDestroy()
    }
////////////////////////////////////////////重启服务////////////////////////////////////////////////////////////

    inner class ViewBinder : Binder() {
        fun updateInfo(title: String = "运行中", text: String) {
            //一个通知必须要包含以下三个属性，否则或报参数异常
            val intent = Intent(this@ClockService, MainActivity::class.java)//MainActivity设置为singleInstance模式
            val pi = PendingIntent.getActivity(this@ClockService, 0, intent, 0)

            val notification = NotificationCompat
                    .Builder(this@ClockService)
                    .setContentTitle(title)
                    .setContentText(text)
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.mipmap.ic_launcher_round)
                    .setContentIntent(pi)
                    .build()
            notifyMgr.notify(1, notification)

        }

    }

}

/*FLAG_CANCEL_CURRENT:如果当前系统中已经存在一个相同的PendingIntent对象，那么就将先将已有的PendingIntent取消，然后重新生成一个PendingIntent对象。
FLAG_NO_CREATE:如果当前系统中不存在相同的PendingIntent对象，系统将不会创建该PendingIntent对象而是直接返回null。
FLAG_ONE_SHOT:该PendingIntent只作用一次。在该PendingIntent对象通过send()方法触发过后，PendingIntent将自动调用cancel()进行销毁，那么如果你再调用send()方法的话，系统将会返回一个SendIntentException。
FLAG_UPDATE_CURRENT:如果系统中有一个和你描述的PendingIntent对等的PendingInent，那么系统将使用该PendingIntent对象，但是会使用新的Intent来更新之前PendingIntent中的Intent对象数据，例如更新Intent中的Extras。
* */