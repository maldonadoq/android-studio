package maldonado.sensorlist

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.opengl.Matrix
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_orientation.*
import kotlin.math.round
import android.R.attr.pivotY
import android.R.attr.pivotX
import android.R.attr.angle
import android.support.v4.app.SupportActivity
import android.support.v4.app.SupportActivity.ExtraData
import android.support.v4.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T



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
        sensorManager.registerListener(
            this,
            sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
            SensorManager.SENSOR_DELAY_NORMAL
        )

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
}
