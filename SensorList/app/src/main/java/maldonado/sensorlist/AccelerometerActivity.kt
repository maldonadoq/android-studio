package maldonado.sensorlist

import android.content.Context
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_accelerometer.*
import kotlin.math.pow
import kotlin.math.sqrt
import java.util.*
import kotlin.math.abs
import android.content.Intent
import android.media.AudioManager

class AccelerometerActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var textView: TextView
    private lateinit var sensorManager: SensorManager
    private lateinit var sensor: Sensor
    private lateinit var audioManager: AudioManager

    private var mAccelCurrent = 0.0f
    private var mAccelLast = 0.0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accelerometer)
        setSupportActionBar(accelerometer_toolbar)

        textView = findViewById(R.id.accelerometer_text)
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        audioManager = this.getSystemService(Context.AUDIO_SERVICE) as AudioManager

        textView.text = "El Bananero Sabpe!!"
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    override fun onSensorChanged(event: SensorEvent?) {
        sensor = event!!.sensor

        mAccelLast = mAccelCurrent
        mAccelCurrent = sqrt(event.values[0].pow(2) + event.values[1].pow(2) + event.values[2].pow(2))
        val mDelta = mAccelCurrent - mAccelLast

        // textView.text = "${mDelta}"

        if (mDelta > 2.5) {
            val rnd = Random()
            val color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
            textView.setTextColor(color)

            if (audioManager.isMusicActive) {
                val i = Intent("com.android.music.musicservicecommand")
                i.putExtra("command", "pause")
                this@AccelerometerActivity.sendBroadcast(i)
            }
            else{
                val i = Intent("com.android.music.musicservicecommand")
                i.putExtra("command", "play")
                this@AccelerometerActivity.sendBroadcast(i)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(
            this,
            sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
            SensorManager.SENSOR_DELAY_NORMAL
        )
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)

    }
}
