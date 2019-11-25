package maldonado.items

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.functions.FirebaseFunctions
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var functions: FirebaseFunctions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        functions = FirebaseFunctions.getInstance()
        val dbFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()

        fab.setOnClickListener {
            intent = Intent(this@MainActivity, ListActivity::class.java)
            startActivity(intent)
        }

        val btnAdd: Button = findViewById(R.id.cmBtnAdd)
        val itemEditText: EditText = findViewById(R.id.cmItemEditText)
        val countText: TextView = findViewById(R.id.cmCountText)

        dbFirestore.collection("Counter").document("Items").get()
            .addOnSuccessListener {
                countText.text = ("Counter: ${it.get("value").toString()}")
            }

        btnAdd.setOnClickListener {
            val name: String = itemEditText.text.toString()

            if(name.isEmpty()){
                Toast.makeText(this, "Write Item", Toast.LENGTH_SHORT).show()
            }
            else{
                addItem(name)
                    .addOnCompleteListener {
                        task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Response Ok: ${task.result}", Toast.LENGTH_SHORT).show()
                        }
                        else{
                            Toast.makeText(this, "Response Fail: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }

    private fun addItem(text: String): Task<String> {
        // Create the arguments to the callable function.
        val data = hashMapOf(
            "text" to text
        )

        return functions
            .getHttpsCallable("add")
            .call(data)
            .continueWith { task ->
                // This continuation runs on either success or failure, but if the task
                // has failed then result will throw an Exception which will be
                // propagated down.
                val result = task.result?.data as String
                result
            }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
