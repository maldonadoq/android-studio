package maldonado.items

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import com.google.firebase.firestore.FirebaseFirestore

class ListActivity : AppCompatActivity() {

    private lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        listView = findViewById(R.id.listListView)

        val dbFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()

        dbFirestore.collection("Items")
            .get()
            .addOnSuccessListener { documents ->
                val listItems = ArrayList<String>()

                for(doc in documents){
                    val data = doc.data
                    listItems.add(data["text"].toString())
                }

                val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listItems)
                listView.adapter = adapter
            }
    }
}