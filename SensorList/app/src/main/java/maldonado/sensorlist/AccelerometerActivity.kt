package maldonado.sensorlist

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_accelerometer.*
import kotlinx.android.synthetic.main.activity_main.*

class AccelerometerActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var textView: TextView
    private lateinit var sensorManager: SensorManager
    private lateinit var sensor: Sensor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accelerometer)
        setSupportActionBar(accelerometer_toolbar)

        textView = findViewById(R.id.accelerometer_text)
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensorManager.registerListener(
            this,
            sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
            SensorManager.SENSOR_DELAY_NORMAL
        )
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    override fun onSensorChanged(event: SensorEvent?) {
        sensor = event!!.sensor
        textView.text = "X: ${event.values[0]}\n\n" +
                    "Y: ${event.values[1]}\n\n" +
                    "Z: ${event.values[2]}"
    }
}
