package my.edu.tarc.mobileApp

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.*


class Report : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var arrayList: ArrayList<ReportClass>
    private lateinit var searchView: SearchView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report)

        val actionbar = supportActionBar

        actionbar!!.title = "Report"
        actionbar.setDisplayHomeAsUpEnabled(true)

        recyclerView = findViewById(R.id.recycleViewReport)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        arrayList = arrayListOf<ReportClass>()
        searchView = findViewById(R.id.searchView)
        var spinnerSearchCriteria = findViewById<Spinner>(R.id.spinner)
        searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                val searchText = query!!.uppercase().toString()
                if(searchText.isNotEmpty()){
                    when(spinnerSearchCriteria.selectedItemPosition){
                        0 -> search("part", searchText)
                        1 -> search("serial", searchText)
                        2 -> search("status", searchText)
                        3 -> search("rackid", searchText)
                    }
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val searchText = newText!!.uppercase().toString()
                if(searchText.isNotEmpty()){
                    when(spinnerSearchCriteria.selectedItemPosition){
                        0 -> search("part", searchText)
                        1 -> search("serial", searchText)
                        2 -> search("status", searchText)
                        3 -> search("rackid", searchText)
                    }
                }
                return false
            }

        })
    }

    private fun search(criteria:String, search:String) {
        arrayList.clear()
        val query = FirebaseDatabase.getInstance().reference.child("Material").orderByChild(criteria).startAt(search).endAt(search + "\uf8ff")
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (userSnapshot in snapshot.children) {
                        val data = userSnapshot.getValue(ReportClass::class.java)
                        arrayList.add(data!!)
                        Log.d("Test", userSnapshot.child("Status").getValue(String::class.java).toString())
                    }
                    recyclerView.adapter = ReportAdapter(arrayList)
                }else{
                    arrayList.clear()
                    recyclerView.adapter = ReportAdapter(arrayList)
                    Toast.makeText(applicationContext,"Empty Results",Toast.LENGTH_SHORT).show()
                }

            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    /*override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_report, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_PDF -> {
                true
            }
            R.id.action_Excel -> true
            else -> super.onOptionsItemSelected(item)
        }
    }*/
}