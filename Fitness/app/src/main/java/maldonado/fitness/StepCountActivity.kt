package maldonado.fitness

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.jjoe64.graphview.series.LineGraphSeries
import com.jjoe64.graphview.series.DataPoint
import kotlin.math.pow
import kotlin.math.sqrt

class StepCountActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var textViewCount: TextView
    private lateinit var imageView: ImageView

    private lateinit var sensorManager: SensorManager
    private val mSmoothing = 20

    // mSmoothing accelerometer signal variables
    private var mHistory = Array(3) { FloatArray(mSmoothing) }
    private var mRunning = FloatArray(3)
    private var mAvg = FloatArray(3)
    private var mIdx = 0
    private var mStepCounter = 0

    private var mGraphLastX = 0.0f

    private var mSeries: LineGraphSeries<DataPoint>? = null

    private var lastMag = 0.0f
    private var avgMag = 0.0f
    private var netMag = 0.0f

    //peak detection variables
    private var lastXPoint = 1.0
    private var stepThreshold = 1.0
    private var noiseThreshold = 2.0
    private val windowSize = 20

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_step_count)

        textViewCount = findViewById(R.id.sc_count)
        imageView = findViewById(R.id.sc_image)
        textViewCount.text = "$mStepCounter"

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        mSeries = LineGraphSeries()
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    override fun onSensorChanged(event: SensorEvent?) {
        lastMag = sqrt(event!!.values[0].pow(2) + event.values[1].pow(2) +
                event.values[2].pow(2))

        for (i in 0..2) {
            mRunning[i] = mRunning[i] - mHistory[i][mIdx]
            mHistory[i][mIdx] = event.values[i]
            mRunning[i] = mRunning[i] + mHistory[i][mIdx]
            mAvg[i] = mRunning[i] / mSmoothing
        }
        mIdx++
        if (mIdx >= mSmoothing) {
            mIdx = 0
        }

        avgMag = sqrt(mAvg[0].pow(2) + mAvg[1].pow(2) + mAvg[2].pow(2))
        netMag = lastMag - avgMag

        mGraphLastX += 1.0f
        mSeries!!.appendData(DataPoint(mGraphLastX.toDouble(), netMag.toDouble()), true,
            60)

        peakDetection()

        textViewCount.text = "$mStepCounter"
    }

    private fun peakDetection() {
        val highestValX = mSeries!!.highestValueX

        if (highestValX - lastXPoint < windowSize) {
            return
        }

        val valuesInWindow = mSeries!!.getValues(lastXPoint, highestValX)

        lastXPoint = highestValX

        val dataPointList = ArrayList<DataPoint>()
        valuesInWindow.forEach { pt -> dataPointList.add(pt) }

        for (i in dataPointList.indices) {
            if (i == 0)
                continue
            else if (i < dataPointList.size - 1) {
                val forwardSlope = dataPointList[i + 1].y - dataPointList[i].y
                val downwardSlope = dataPointList[i].y - dataPointList[i - 1].y

                if (forwardSlope < 0 && downwardSlope > 0 && dataPointList[i].y > stepThreshold
                    && dataPointList[i].y < noiseThreshold) {
                    mStepCounter += 1
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val accSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        if(accSensor == null){
            Toast.makeText(
                this,
                "Does not exist Accelerometer Sensor!!",
                Toast.LENGTH_SHORT).show()
        }
        else{
            sensorManager.registerListener(
                this,
                accSensor,
                SensorManager.SENSOR_DELAY_NORMAL
            )
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

}