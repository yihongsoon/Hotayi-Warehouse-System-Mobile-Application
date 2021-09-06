package my.edu.tarc.mobileApp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnStore = findViewById<Button>(R.id.btnStore)
        val btnReceive = findViewById<Button>(R.id.btnReceive)
        val btnRetrieve = findViewById<Button>(R.id.btnRetrieve)
        val btnReport = findViewById<Button>(R.id.btnReport)

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
    }
}