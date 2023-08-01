package com.example.dm_proyecto2_pedidosonline.ui.activities

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.dm_proyecto2_pedidosonline.R
import com.example.dm_proyecto2_pedidosonline.databinding.ActivityNotificationBinding
import com.example.dm_proyecto2_pedidosonline.ui.utilities.BroadcasterNotifications
import java.util.Calendar

class NotificationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNotificationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnNotification.setOnClickListener{
            //Primero se crea el canal
            //Despues de crear el canal se comenta esta linea
//            createNotificationChannel()
            //Luego de crear ir al info de la aplicacion dentro del celular
            //En Notificaciones verificar si esta creado el canal
            //Cuando este creado se envia la notificacion
            sendNotification()
        }
        binding.btnNotificationProgramada.setOnClickListener{
            val calendar=Calendar.getInstance()
            val hora=binding.timePicker.hour
            val minutes=binding.timePicker.minute
            Toast.makeText(
                this,
                "La notificacion se activara a las: $hora:$minutes",
                Toast.LENGTH_SHORT
            ).show()
            calendar.set(Calendar.HOUR,hora)
            calendar.set(Calendar.MINUTE,minutes)
            calendar.set(Calendar.SECOND,0)
            sendNotificationTimePicker(calendar.timeInMillis)
        }
    }

    //Nota: Las fechas siempre se convierten en numeros tipo Long
    private fun sendNotificationTimePicker(time:Long) {
        //Desde cualquier parte de la aplicacion hacia el broadcast
        val myIntent= Intent(applicationContext,BroadcasterNotifications::class.java)
        val myPendingIntent=PendingIntent.getBroadcast(
            applicationContext,
            0,
            myIntent,
            //Estas banderas van a decir que va a pasar cuando se abra el intent
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        //Alarm Manager nos va a ayudar a que el sistema se levante
        val alarmManager=getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExact(AlarmManager.RTC_WAKEUP,time,myPendingIntent)
    }

    val CHANNEL: String ="Notificaciones"
    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Variedades"
            val descriptionText = "Notificaciones simples de variedades"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    @SuppressLint("MissingPermission")
    fun sendNotification(){
        val not = NotificationCompat.Builder(this, CHANNEL)
        not.setContentTitle("Primera notificacion")
        not.setContentText("Tienes una notificacion")
        //El icono que se muestra en la barra de notificaciones
        not.setSmallIcon(R.drawable.baseline_favorite_24)
        //Imagen que aparece dentro de la notificacion
        val bitmap: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.eva)
        not.setLargeIcon(bitmap)
        not.setPriority(NotificationCompat.PRIORITY_MAX)
        not.setStyle(NotificationCompat.BigTextStyle().bigText("Esta es una notificacion para recordar que estamos trabajando en android"))
        with(NotificationManagerCompat.from(this)){
            notify(1,not.build())
        }
    }
}