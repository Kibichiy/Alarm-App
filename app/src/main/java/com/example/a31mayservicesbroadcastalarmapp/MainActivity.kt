package com.example.a31mayservicesbroadcastalarmapp

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.core.content.getSystemService
import com.example.a31mayservicesbroadcastalarmapp.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpEvents()
    }

    private fun setUpEvents() {
        binding.btnSet.setOnClickListener {
            setCalender()
        }
    }
    private fun setAlarm(timeInMillis:Long){
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this,MyAlarm::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this,0,intent,0)
        alarmManager.setRepeating(AlarmManager.RTC,timeInMillis,AlarmManager.INTERVAL_DAY,pendingIntent)
        Toast.makeText(this, "Alarm is set", Toast.LENGTH_SHORT).show()
    }


    private fun setCalender() {
        val calendar:Calendar = Calendar.getInstance()
        if (Build.VERSION.SDK_INT >= 23 ){
            calendar.set(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                binding.timePicker.hour,
                binding.timePicker.minute,
                0
            )
        }
        setAlarm(calendar.timeInMillis)
    }

    class MyAlarm : BroadcastReceiver(){
        private lateinit var mediaPlayer: MediaPlayer
        override fun onReceive(p0: Context?, p1: Intent?) {
            mediaPlayer = MediaPlayer.create(p0, Settings.System.DEFAULT_RINGTONE_URI)
            mediaPlayer.isLooping = true
            mediaPlayer.start()
            Toast.makeText(p0, "Alarm just fired!!", Toast.LENGTH_SHORT).show()
            Log.i("tag","Alarm just fired!!")
        }

    }
}