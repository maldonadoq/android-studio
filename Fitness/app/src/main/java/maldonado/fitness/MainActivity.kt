package maldonado.fitness

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Button

import kotlinx.android.synthetic.main.activity_main.*
import android.content.Intent

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        val buttonOne = findViewById<Button>(R.id.btnPlank)
        val buttonTwo = findViewById<Button>(R.id.btnStep)
        val buttonThree = findViewById<Button>(R.id.btnHand)
        val buttonFour = findViewById<Button>(R.id.btnPlot)

        buttonOne.setOnClickListener{
            intent = Intent(this@MainActivity, PlankActivity::class.java)
            startActivity(intent)
        }

        buttonTwo.setOnClickListener{
            intent = Intent(this@MainActivity, StepCountActivity::class.java)
            startActivity(intent)
        }

        buttonThree.setOnClickListener{
            intent = Intent(this@MainActivity, HandActivity::class.java)
            startActivity(intent)
        }

        buttonFour.setOnClickListener{
            intent = Intent(this@MainActivity, PlotActivity::class.java)
            startActivity(intent)
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
