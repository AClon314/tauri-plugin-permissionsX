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

const val DEFAULT_CHANNEL_DESCRIPTION = "My Tauri Foreground Service Channel"

class MyForegroundService : Service() {
  
  private var notifyTitle: String? = null
  private var notifyContent: String? = null
  private var channelDescription: String = DEFAULT_CHANNEL_DESCRIPTION
  private var className: String = "com.tauri.tauri_app.MainActivity"

  companion object {
    private val TAG = MyForegroundService::class.java.simpleName
    const val ACTION_START = "ACTION_START"
    const val ACTION_STOP = "ACTION_STOP"
  }

  fun startService() {
    Log.d(TAG, "startService $notifyTitle $notifyContent")
    var notificationIntent: Intent? = null
    try {
      notificationIntent = Intent(this, Class.forName(className))
      Log.d(TAG, "notificationIntent: $notificationIntent")
    } catch (e: Exception) {
      Log.e(TAG, "startService error: $e")
    }
    val pendingIntent =
        PendingIntent.getActivity(
            this,
            0,
            notificationIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
    val channelId = "MyTauriForegroundServiceChannel"
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
      val channel =
          NotificationChannel(
              channelId,
              channelDescription,
              NotificationManager.IMPORTANCE_DEFAULT
          )
      channel.setShowBadge(false)
      channel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
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
            .setSmallIcon(android.R.drawable.ic_media_play)
            .setContentIntent(pendingIntent)
            .build()
    startForeground(1, notification)
    Log.d(TAG, "startForeground FULLY SUCCESS 🎉")
  }

  fun stopService(): Boolean {
    Log.d(TAG, "stopService")
    notifyTitle = null
    notifyContent = null
    try {
      stopForeground(true)
      stopSelf()
      return true
    } catch (e: Exception) {
      return false
    }
  }

  override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
    Log.d(TAG, "callIntent $intent $flags $startId")
    notifyTitle = intent?.getStringExtra("title")?: "notifyTitle为空"
    notifyContent = intent?.getStringExtra("content")?: "notifyContent为空"
    channelDescription = intent?.getStringExtra("channelDescription") ?: DEFAULT_CHANNEL_DESCRIPTION
    className = intent?.getStringExtra("className") ?: "com.tauri.tauri_app.MainActivity"
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
