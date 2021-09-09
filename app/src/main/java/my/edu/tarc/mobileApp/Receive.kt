package my.edu.tarc.mobileApp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.core.app.ActivityCompat
import java.text.SimpleDateFormat
import java.util.*

class Receive : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receive)

        val btnScan = findViewById<Button>(R.id.buttonScan)

        btnScan.setOnClickListener{
            val intent = Intent(this, ReceiveScanner::class.java)
            startActivity(intent)
        }

        val txtDate = findViewById<TextView>(R.id.txtRecDate)
        val df = SimpleDateFormat("dd/MM/yyyy")
        val currentDate = df.format(Calendar.getInstance().time)

        txtDate.text = currentDate.toString()

    }

}