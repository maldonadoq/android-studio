package maldonado.sensorlist

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import org.w3c.dom.Text

class SecondActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var textView: TextView
    private lateinit var sensorManager: SensorManager
    private lateinit var sensorSelected: String
    private lateinit var sensorArray: Array<String>

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSensorChanged(event: SensorEvent?) {

        if(sensorSelected == sensorArray[0]){
            textView.text = "X: ${event!!.values[0]}\n\n" +
                            "Y: ${event!!.values[1]}\n\n" +
                            "Z: ${event!!.values[2]}"
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        textView = findViewById(R.id.sensor)
        sensorSelected = intent.getStringExtra("ListViewClickedValue")

        sensorArray = arrayOf("3-axis Accelerometer sensor", "Light sensor", "Proximity sensor")
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensorManager.registerListener(
            this,
            sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
            SensorManager.SENSOR_DELAY_NORMAL
        )

        textView.setText(sensorSelected)
    }
}
