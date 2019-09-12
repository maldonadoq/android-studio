package maldonado.sensorlist

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_orientation.*
import kotlin.math.round

class OrientationActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var textView: TextView
    private lateinit var imageView: ImageView
    private lateinit var sensorManager: SensorManager
    private lateinit var sensor: Sensor
    //private lateinit var current_degree

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orientation)
        setSupportActionBar(orientation_toolbar)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        textView = findViewById(R.id.orientation_text)
        imageView = findViewById(R.id.compass_back)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    override fun onSensorChanged(event: SensorEvent?) {
        sensor = event!!.sensor

        val degree = round(event.values[0])
        imageView.setRotation(360-degree)

        textView.text = "Degree: ${degree}"
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(
            this,
            sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
            SensorManager.SENSOR_DELAY_NORMAL
        )
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)

    }
}
