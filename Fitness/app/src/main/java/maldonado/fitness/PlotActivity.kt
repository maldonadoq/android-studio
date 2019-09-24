package maldonado.fitness

import android.content.Context
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

class PlotActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var sensorManager: SensorManager
    private lateinit var lineChart: LineChart
    private var plotData = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plot)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        lineChart = findViewById(R.id.lineChart)

        lineChart.description.isEnabled = false
        lineChart.description.text = "Real Time"
        lineChart.setTouchEnabled(false)
        lineChart.isDragEnabled = false
        lineChart.setScaleEnabled(false)
        lineChart.setDrawGridBackground(false)
        lineChart.setPinchZoom(false)
        lineChart.setBackgroundColor(Color.WHITE)

        val data = LineData()
        data.setValueTextColor(Color.WHITE)
        lineChart.data = data
    }

    private fun addEntry(event: SensorEvent?){
        val data = lineChart.data

        if(data != null){
            var set = data.getDataSetByIndex(0)

            if(set != null){
                set = createSet()
                data.addDataSet(set)
            }

            data.addEntry(Entry(set.entryCount.toFloat(), event!!.values[0] + 5), 0)
            data.notifyDataChanged()
            lineChart.setMaxVisibleValueCount(100)
            lineChart.moveViewToX(data.entryCount.toFloat())
        }
    }

    private fun createSet(): LineDataSet{
        val set = LineDataSet(null, "Dynamic Data")
        set.axisDependency = YAxis.AxisDependency.LEFT
        set.lineWidth = 3f
        set.color = Color.MAGENTA
        set.mode = LineDataSet.Mode.CUBIC_BEZIER
        set.cubicIntensity = 0.2f
        return set
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if(plotData){
            addEntry(event)
            plotData = false
        }
    }

    override fun onResume() {
        super.onResume()
        val stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)

        if(stepSensor == null){
            Toast.makeText(
                this,
                "Does not exist Light !!",
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
