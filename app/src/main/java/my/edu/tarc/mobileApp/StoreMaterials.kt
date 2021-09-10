package my.edu.tarc.mobileApp

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*


class StoreMaterials : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store_materials)

        val actionbar = supportActionBar

        actionbar!!.title = "Store"
        actionbar.setDisplayHomeAsUpEnabled(true)

        val btnScan = findViewById<Button>(R.id.btnScanMaterial)
        val btnSave = findViewById<Button>(R.id.btnStoreSave)

        btnScan.setOnClickListener{
            val intent = Intent(this, StoreScanner::class.java)
            startActivity(intent)
        }

        val intent = intent
        val serialCode = intent.getStringExtra("serialCodeContent")
        val rackCode = intent.getStringExtra("rackCodeContent")
        val scanningStatus = intent.getStringExtra("scanStatus")

        if(scanningStatus == "true"){
            val splitSerial = serialCode?.split(",")
            val splitRack = rackCode?.split(",")

            val resultSerial = splitSerial?.count()
            val resultRack = splitRack?.count()
            Log.d("CountSerial", resultSerial.toString())
            Log.d("CountRack", resultRack.toString())

            if(resultSerial == 2 && resultRack ==2){

                val displayStoreRackID = splitRack?.get(0)
                val displayStoreRackNo = splitRack?.get(1)

                val displayStorePartNo = splitSerial?.get(0)
                val displayStoreSerialNo = splitSerial?.get(1)

                val storeRackID = findViewById<TextView>(R.id.txtStoreRackID)
                val storeRackNo = findViewById<TextView>(R.id.txtStoreRackNo)
                val storePartNo = findViewById<TextView>(R.id.txtPartNo)
                val storeSerialNo = findViewById<TextView>(R.id.txtStoreSerialNumber)
                val storeStatus = findViewById<TextView>(R.id.txtStoreStatus)
                val storeRackInDate = findViewById<TextView>(R.id.txtRackInDate)
                val storeQty = findViewById<TextView>(R.id.txtStoreQty)

                storeRackID.text= displayStoreRackID
                storeRackNo.text= displayStoreRackNo

                storePartNo.text = displayStorePartNo
                storeSerialNo.text = displayStoreSerialNo

                storeStatus.text = "STORED"

                val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
                val currentDate = sdf.format(Date())

                storeRackInDate.text = currentDate
                storeQty.text = "1"
            }
            else{
                Toast.makeText(applicationContext, "Wrong Barcode Scanned", Toast.LENGTH_SHORT)
                    .show()
            }

            btnSave.setOnClickListener {
             val dbControl = FirebaseDatabase.getInstance().getReference()
            }

        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }










}