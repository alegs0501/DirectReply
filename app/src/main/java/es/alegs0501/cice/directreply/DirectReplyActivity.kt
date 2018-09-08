package es.alegs0501.cice.directreply

import android.app.*
import android.content.ClipDescription
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Icon
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View

class DirectReplyActivity : AppCompatActivity() {

    private var notificationManager: NotificationManager? = null
    private val chanelID = "es.alegs0501.cice.directreply.noticias"
    private val notificationId = 101
    private val KEY_TEX_REPLY = "key_text_reply"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_direct_reply)

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        createNotificationChanel(chanelID, "Noticias",
                "Las noticias más interesantes")
    }

    private fun createNotificationChanel(id: String, name: String, description: String){
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(id, name, importance)

        channel.enableVibration(true)
        channel.vibrationPattern = longArrayOf(100, 200, 200, 200 ,100, 50 , 50, 100, 100)

        notificationManager?.createNotificationChannel(channel)
    }

    fun sendNotification(view: View){
        val replyLabel = "Por favor introduce tu respuesta"
        val remoteInput = RemoteInput.Builder(KEY_TEX_REPLY).setLabel(replyLabel).build()

        val resultIntent = Intent(this, DirectReplyActivity::class.java)
        val resultPendingIntent = PendingIntent.getActivity(this, 0, resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT)

        val icon = Icon.createWithResource(this, android.R.drawable.ic_dialog_info)
        val replyAction = Notification.Action.Builder(icon, "Responder",
                resultPendingIntent).addRemoteInput(remoteInput).build()

        val newMessageNotification = Notification.Builder(this, chanelID).setColor(
                ContextCompat.getColor(this,R.color.colorPrimary)).setSmallIcon(
                android.R.drawable.ic_dialog_info).setContentTitle("Notification Title").setContentText(
        "este es el mensaje").addAction(replyAction).build()

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(notificationId, newMessageNotification)
    }

}
