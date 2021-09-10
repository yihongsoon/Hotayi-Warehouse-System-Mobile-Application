package my.edu.tarc.mobileApp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.database.FirebaseDatabase
//import com.google.firebase.ktx.Firebase
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.util.*

class Receive : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receive)
        val actionbar = supportActionBar
        actionbar!!.title = "Receive"
        actionbar.setDisplayHomeAsUpEnabled(true)

        val btnScan = findViewById<Button>(R.id.buttonScan)

        btnScan.setOnClickListener{
            val intent = Intent(this, ReceiveScanner::class.java)
            startActivity(intent)
        }

        val intent = intent
        val qrTest = intent.getStringExtra("receiveCodeContent")
        val scanningStatus = intent.getStringExtra("scanStatus")
        val randomNumber = (100000000..999999999).random()

        //val database = FirebaseDatabase.getInstance()
        //val myReference = database.getReference("Material")

        /*val Material = Material(serial=serialNo.text.toString(),part=partNo.text.toString(),qty=qty.text.toString(),
                    receivedate=txtDate.text.toString(),status=status.text.toString(),staffid=staffID.text.toString(),rackid="",rackin="",rackno="",rackout="")
                myReference.child(Material.serialNo.text.toString()).child("serial").setValue(Material.serial)*/

        if(scanningStatus == "true"){
            val split = qrTest?.split("-")

            val result = split?.count()
            Log.d("Count Part", result.toString())

            if(result == 2){
                val showPartNo = split?.get(0)
                val showQty = split?.get(1)

                val txtDate = findViewById<TextView>(R.id.txtRecDate)
                val df = SimpleDateFormat("dd/MM/yyyy")
                val currentDate = df.format(Calendar.getInstance().time)
                val partNo = findViewById<TextView>(R.id.txtPartNo)
                val qty = findViewById<TextView>(R.id.txtQuantity)
                val status = findViewById<TextView>(R.id.txtStatus)
                val serialNo = findViewById<TextView>(R.id.txtSerialNo)
                val staffID = findViewById<TextView>(R.id.txtRecBy)

                txtDate.text = currentDate.toString()
                partNo.text = showPartNo
                qty.text = showQty
                status.text = "RECEIVED"
                serialNo.text = "S"+ randomNumber
                staffID.text = ""

            }else{
                Toast.makeText(applicationContext, "Please scan again!", Toast.LENGTH_SHORT).show()
            }
        }else{

        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}