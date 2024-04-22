package com.plugin.permissionsx

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log

class MyForegroundService : Service() {
  companion object {
    private val TAG = MyForegroundService::class.java.simpleName
    const val ACTION_START = "ACTION_START"
    const val ACTION_STOP = "ACTION_STOP"
  }

  fun startService(
      notifyTitle: String? = "MyForegroundService's notifyTitle",
      notifyContent: String? = "常驻通知以保证后台运行"
  ) {
    Log.d(TAG, "startService $notifyTitle $notifyContent")
    val notificationIntent = Intent(this, MyForegroundService::class.java)
    Log.d(TAG, "notificationIntent: $notificationIntent")
    val pendingIntent =
        PendingIntent.getActivity(
            this,
            0,
            notificationIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
    val channelId = "MyForegroundServiceChannel"
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
      val channel =
          NotificationChannel(
              channelId,
              "My Tauri Foreground Service Channel",
              NotificationManager.IMPORTANCE_DEFAULT
          )
      channel.setShowBadge(false)
      channel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
      val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
      manager.createNotificationChannel(channel)
    }
    val notification: Notification =
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
              Notification.Builder(this, channelId)
            } else {
              Notification.Builder(this)
            }
            .setContentTitle(notifyTitle)
            .setContentText(notifyContent)
            // .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(pendingIntent)
            .build()
    startForeground(1, notification)
  }

  fun stopService(): Boolean {
    Log.d(TAG, "stopService")
    try {
      stopForeground(true)
      stopSelf()
      return true
    } catch (e: Exception) {
      return false
    }
  }

  override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
    Log.d(TAG, "callIntent")
    when (intent?.action) {
      ACTION_START -> startService()
      ACTION_STOP -> stopService()
    }
    return START_STICKY
  }

  override fun onCreate() {
    super.onCreate()
    Log.d(TAG, "onCreate")
  }

  override fun onBind(intent: Intent?): IBinder? {
    Log.d(TAG, "onBind")
    TODO("Not yet implemented")
  }
}
