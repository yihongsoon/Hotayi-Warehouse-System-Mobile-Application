package my.edu.tarc.mobileApp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import org.w3c.dom.Text
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

        val intent = intent
        val qrTest = intent.getStringExtra("qrCodeContent")
        val scanningStatus = intent.getStringExtra("scanStatus")

        if(scanningStatus == "true"){
            val split = qrTest?.split("-")

            val result = split?.count()
            Log.d("Count", result.toString())

            if(result == 2){
                val showPartNo = split?.get(0)
                val showQty = split?.get(1)

                val txtDate = findViewById<TextView>(R.id.txtRecDate)
                val df = SimpleDateFormat("dd/MM/yyyy")
                val currentDate = df.format(Calendar.getInstance().time)

                val partNo = findViewById<TextView>(R.id.txtPartNo)
                val qty = findViewById<TextView>(R.id.txtQuantity)
                val status = findViewById<TextView>(R.id.txtStatus)

                txtDate.text = currentDate.toString()
                partNo.text = showPartNo
                qty.text = showQty
                status.text = "RECEIVED"
            }else{
                Toast.makeText(applicationContext, "Please scan again!", Toast.LENGTH_SHORT).show()
            }
        }else{

        }
    }

}