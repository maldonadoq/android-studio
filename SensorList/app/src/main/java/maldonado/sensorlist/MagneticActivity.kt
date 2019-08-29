package maldonado.sensorlist

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_magnetic.*
import kotlin.math.sqrt
import kotlin.math.pow

class MagneticActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var textView: TextView
    private lateinit var sensorManager: SensorManager
    private lateinit var sensor: Sensor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_magnetic)
        setSupportActionBar(magnetic_toolbar)

        textView = findViewById(R.id.magnetic_text)
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensorManager.registerListener(
            this,
            sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
            SensorManager.SENSOR_DELAY_NORMAL
        )
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    override fun onSensorChanged(event: SensorEvent?) {
        sensor = event!!.sensor
        var mvalue = sqrt(event.values[0].pow(2) + event.values[1].pow(2) + event.values[2].pow(2))

        textView.text = "Microtesla: ${mvalue} mt"
    }
}
