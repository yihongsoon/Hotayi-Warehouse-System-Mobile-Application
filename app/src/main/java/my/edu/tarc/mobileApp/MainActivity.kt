package my.edu.tarc.mobileApp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import androidx.navigation.ui.setupActionBarWithNavController


class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        auth = FirebaseAuth.getInstance()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnStore = findViewById<CardView>(R.id.btnStore)
        val btnReceive = findViewById<CardView>(R.id.btnReceive)
        val btnRetrieve = findViewById<CardView>(R.id.btnRetrieve)
        val btnReport = findViewById<CardView>(R.id.btnReport)
        val btnLogOut = findViewById<Button>(R.id.btnLogOut)

        btnStore.setOnClickListener{
            val intent = Intent(this, StoreMaterials::class.java)
            startActivity(intent)
        }
        btnReceive.setOnClickListener{
            val intent = Intent(this, Receive::class.java)
            startActivity(intent)
        }
        btnRetrieve.setOnClickListener{
            val intent = Intent(this, RetrieveMaterials::class.java)
            startActivity(intent)
        }
        btnReport.setOnClickListener{
            val intent = Intent(this, Report::class.java)
            startActivity(intent)
        }
        btnLogOut.setOnClickListener{
            auth.signOut()
            onStart()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
             R.id.action_generator -> {
                val intent = Intent(this, BarcodeGenerator::class.java)
                 startActivity(intent)
                 true
            }
            R.id.action_part_record -> {
                findNavController().navigate(R.id.action_mainActivity_to_partFragment)
            }else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if(currentUser == null){
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }
    }


}