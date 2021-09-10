package my.edu.tarc.mobileApp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        auth = FirebaseAuth.getInstance()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnStore = findViewById<Button>(R.id.btnStore)
        val btnReceive = findViewById<Button>(R.id.btnReceive)
        val btnRetrieve = findViewById<Button>(R.id.btnRetrieve)
        val btnReport = findViewById<Button>(R.id.btnReport)
        val btnLogOut = findViewById<Button>(R.id.buttonLogOut)

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

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if(currentUser == null){
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }
    }


}