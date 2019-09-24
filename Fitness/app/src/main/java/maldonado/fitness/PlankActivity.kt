package maldonado.fitness

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

class PlankActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var textViewCount: TextView
    private lateinit var sensorManager: SensorManager
    private lateinit var imageView: ImageView
    private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plank)

        textViewCount = findViewById(R.id.p_count)
        imageView = findViewById(R.id.p_image)
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        textViewCount.text = "$count"
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if(event!!.values[0] < 1){
            count++
            textViewCount.text = "$count"
        }
    }

    override fun onResume() {
        super.onResume()
        var stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)

        if(stepSensor == null){
            Toast.makeText(
                this,
                "Does not exist Proximity Sensor!!",
                Toast.LENGTH_SHORT).show()
        }
        else{
            sensorManager.registerListener(
                this,
                stepSensor,
                SensorManager.SENSOR_DELAY_NORMAL
            )
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)

    }
}
