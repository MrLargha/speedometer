package ru.mrlargha.speedometersample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.mrlargha.lib.Speedometer
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var speedometer: Speedometer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        speedometer = findViewById(R.id.speedometer)
        val seek = findViewById<SeekBar>(R.id.seek)

        seek.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                speedometer.setSpeed(progress.toFloat() / 100f, 0)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        GlobalScope.launch {
            var distance = 0f
            while(true) {
                distance += (seek.progress.toFloat() / 100f * 0.27777f) / 5
                runOnUiThread {
                    speedometer.odometerText = "${distance.toInt()} m"
                }
                delay(200)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        speedometer.setSpeed(50f, 1000) {
            speedometer.setSpeed(0f, 1000)
        }
    }
}
