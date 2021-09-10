package my.edu.tarc.mobileApp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.firebase.auth.FirebaseAuth
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.util.*

class Receive : AppCompatActivity(){

    private lateinit var firebaseAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {

        firebaseAuth = FirebaseAuth.getInstance()

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
        val partCode = intent.getStringExtra("partCodeContent")
        val scanningStatus = intent.getStringExtra("scanStatus")

        val emailStaff = firebaseAuth.currentUser?.email.toString()
        //val database = FirebaseDatabase.getInstance()
        //val myReference = database.getReference("Material")

        /*val Material = Material(serial=serialNo.text.toString(),part=partNo.text.toString(),qty=qty.text.toString(),
                    receivedate=txtDate.text.toString(),status=status.text.toString(),staffid=staffID.text.toString(),rackid="",rackin="",rackno="",rackout="")
                myReference.child(Material.serialNo.text.toString()).child("serial").setValue(Material.serial)*/

        if(scanningStatus == "true"){
            val splitPart = partCode?.split("-")

            val resultPart = splitPart?.count()
            Log.d("CountPart", resultPart.toString())

            if(resultPart == 2){
                val showPartNo = splitPart?.get(0)
                val showQty = splitPart?.get(1)

                val randomNumber = (100000000..999999999).random()

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
                serialNo.text = "HWS"+ randomNumber.toString()
                staffID.text = emailStaff

            }else{
                Toast.makeText(applicationContext, "Wrong Barcode Scanned", Toast.LENGTH_SHORT).show()
            }
        }else{

        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}