package maldonado.sensorlist

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.media.MediaPlayer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_light.*
import org.w3c.dom.Text
import java.lang.reflect.Field
import kotlin.math.abs

class LightActivity : AppCompatActivity(), SensorEventListener {
    // private lateinit var listView: ListView
    private lateinit var textView: TextView
    private lateinit var sensorManager: SensorManager
    private lateinit var sensor: Sensor
    // private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_light)
        setSupportActionBar(light_toolbar)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensorManager.registerListener(
            this,
            sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT),
            SensorManager.SENSOR_DELAY_NORMAL
        )

        /*val songList = arrayOf("Song 1", "Song 2", "Song 3", "Song 4", "Song 5")
        listView = findViewById(R.id.song_list_view)
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, songList)
        listView.adapter = adapter*/

        textView = findViewById(R.id.light_text)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    override fun onSensorChanged(event: SensorEvent?) {
        sensor = event!!.sensor
        textView.text = "Light: ${event.values[0]}"

        /*var mLight = event.values[0]

        var textLight = "Before Light: ${tLight}\n\n" +
                        "Current Light: ${mLight}\n\n" +
                        "Count: ${tCount}\n\n"

        var lost = tLight/2
        if (abs(mLight-tLight) > lost){
            textLight += "Yes"
            tCount++
        }
        else{
            textLight += "No"
        }

        textView.text = textLight
        tLight = mLight*/
    }
}
