package com.ava.ai.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.ava.ai.R
import com.ava.ai.ai.AIManager
import com.ava.ai.ai.voice.VoiceManager
import com.ava.ai.data.datastore.PreferencesManager
import com.ava.ai.presentation.main.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class VoiceAssistantService : Service() {

    private val scope = CoroutineScope(Dispatchers.Main + Job())
    private lateinit var voiceManager: VoiceManager
    private lateinit var preferencesManager: PreferencesManager

    companion object {
        private const val NOTIFICATION_ID = 1001
        private const val CHANNEL_ID = "maya_voice_assistant"
    }

    override fun onCreate() {
        super.onCreate()
        
        preferencesManager = PreferencesManager(this)
        val aiManager = AIManager(this, preferencesManager)
        voiceManager = VoiceManager(this, aiManager, preferencesManager)

        createNotificationChannel()
        startForeground(NOTIFICATION_ID, createNotification())

        // Start wake word detection
        scope.launch {
            val avaActive = preferencesManager.avaActive.first()
            if (avaActive) {
                voiceManager.startWakeWordDetection()
            }
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            "START_LISTENING" -> {
                voiceManager.startListening()
            }
            "STOP_LISTENING" -> {
                voiceManager.stopListening()
            }
            "TOGGLE_AVA" -> {
                scope.launch {
                    val currentState = preferencesManager.avaActive.first()
                    preferencesManager.setMayaActive(!currentState)
                    
                    if (!currentState) {
                        voiceManager.startWakeWordDetection()
                    } else {
                        voiceManager.stopWakeWordDetection()
                    }
                    
                    updateNotification()
                }
            }
        }
        
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        voiceManager.destroy()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "AVA Voice Assistant",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "AVA voice assistant service"
                setShowBadge(false)
            }

            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun createNotification(): Notification {
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        // Toggle action
        val toggleIntent = Intent(this, VoiceAssistantService::class.java).apply {
            action = "TOGGLE_AVA"
        }
        val togglePendingIntent = PendingIntent.getService(
            this,
            1,
            toggleIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("AVA")
            .setContentText("Voice assistant is running")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(pendingIntent)
            .addAction(
                R.drawable.ic_launcher_foreground,
                "Toggle AVA",
                togglePendingIntent
            )
            .setOngoing(true)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()
    }

    private fun updateNotification() {
        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.notify(NOTIFICATION_ID, createNotification())
    }
}
