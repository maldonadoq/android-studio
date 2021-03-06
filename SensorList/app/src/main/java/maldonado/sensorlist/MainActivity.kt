package maldonado.sensorlist

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ListView

import kotlinx.android.synthetic.main.activity_main.*
import android.content.Intent
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.view.View

class MainActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var sensorManager: SensorManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        /*Sensor Init*/
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        /*Get Sensor List*/
        val deviceSensors: List<Sensor> = sensorManager.getSensorList(Sensor.TYPE_ALL)

        /*Find Sensor View on Resources*/
        listView = findViewById(R.id.sensor_list_view)

        // List of all name sensors (string)
        val listSensorItems = arrayOfNulls<String>(deviceSensors.size)

        for (i in 0 until deviceSensors.size) {
            listSensorItems[i] = deviceSensors[i].name
        }

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listSensorItems)
        listView.adapter = adapter

        listView.onItemClickListener = object : OnItemClickListener {

            override fun onItemClick(
                parent: AdapterView<*>, view: View,
                position: Int, id: Long
            ) {
                // Getting listView click value into String variable.
                val clickedValue = listSensorItems[position].toString()

                if(clickedValue == "3-axis Accelerometer sensor"){
                    intent = Intent(this@MainActivity, AccelerometerActivity::class.java)
                    startActivity(intent)
                }
                else if(clickedValue == "Light sensor"){
                    intent = Intent(this@MainActivity, LightActivity::class.java)
                    startActivity(intent)
                }
                else if(clickedValue == "3-axis Magnetic field sensor"){
                    intent = Intent(this@MainActivity, MagneticActivity::class.java)
                    startActivity(intent)
                }
                else if(clickedValue == "Orientation sensor"){
                    intent = Intent(this@MainActivity, OrientationActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
