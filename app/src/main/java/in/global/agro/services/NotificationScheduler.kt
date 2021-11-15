package `in`.global.agro.services

import `in`.global.agro.R
import `in`.global.agro.ui.activity.MainActivity
import `in`.global.agro.utils.Constants.NOTIFICATION_CHANNEL
import `in`.global.agro.utils.Constants.NOTIFICATION_CHANNEL_ID
import `in`.global.agro.utils.Constants.NOTIFICATION_ID
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.work.*

class NotificationScheduler(val app: Context, workerParameters: WorkerParameters) :
    Worker(app, workerParameters) {

    @SuppressLint("RestrictedApi")
    override fun doWork(): Result {

        createNotification()
        return Result.Success()


    }


    @SuppressLint("UnspecifiedImmutableFlag")
    private fun createNotification() {

        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(notificationManager)
        }


        val intent = Intent(app, MainActivity::class.java)


        val pendingIntent = PendingIntent.getActivity(
            applicationContext,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )



        val notificationBuilder =
            NotificationCompat.Builder(applicationContext, NOTIFICATION_CHANNEL_ID)
                .setAutoCancel(false)
                .setSmallIcon(R.drawable.icon_home)
                .setContentText("Stay connect with us")
                .setContentIntent(pendingIntent)
                .setContentTitle("AGRO")
                .setPriority(NotificationCompat.PRIORITY_HIGH)

        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())

    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(notificationManager: NotificationManager) {

        val notificationChannel = NotificationChannel(
            NOTIFICATION_CHANNEL,
            NOTIFICATION_CHANNEL_ID,
            NotificationManager.IMPORTANCE_LOW

        )

        notificationManager.createNotificationChannel(notificationChannel)

    }


}