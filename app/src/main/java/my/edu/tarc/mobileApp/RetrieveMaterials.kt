package my.edu.tarc.mobileApp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.util.*


class RetrieveMaterials : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retrieve_materials)
        val actionbar = supportActionBar
        actionbar!!.title = "Retrieve Materials"
        actionbar.setDisplayHomeAsUpEnabled(true)

        val btnScan = findViewById<Button>(R.id.btnScanMaterial)
        var scanStatus: Boolean? = null

        btnScan.setOnClickListener {

            val intent = Intent(this, Scanner::class.java)
            startActivity(intent)

        }

        val intent = intent
        val qrTest = intent.getStringExtra("qrCodeContent")
        val scanningStatus = intent.getStringExtra("scanStatus")

//            Log.d("TESTING", qrTest.toString())
//            Log.d("TEST STATUS", scanningStatus.toString())

        if (scanningStatus == "true") {


            val split = qrTest?.split(",")
            //VALIDATION OF BARCODE ITEMS
            val result = split?.count()
            Log.d("Count", result.toString())

            if (result == 4) {

                val displayRackId = split?.get(0)

                val displayRackNo = split?.get(1)

                val displayPartNo = split?.get(2)

                val displaySerialNo = split?.get(3)

            //display scanned barcode items into the text views
                val rackId = findViewById<TextView>(R.id.txtStoreRackID)
                val rackNo = findViewById<TextView>(R.id.txtStoreRackNo)
                val partNo = findViewById<TextView>(R.id.txtPartNo)
                val serialNo = findViewById<TextView>(R.id.txtStoreSerialNumber)
                val retrieveStatus = findViewById<TextView>(R.id.txtStoreStatus)
                val rackInDate = findViewById<TextView>(R.id.txtRackInDate)
                val qty = findViewById<TextView>(R.id.txtStoreQty)

                rackId.text = displayRackId
                rackNo.text = displayRackNo
                partNo.text = displayPartNo
                serialNo.text = displaySerialNo
                retrieveStatus.text = "RETRIEVED"

                val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
                val currentDate = sdf.format(Date())

                rackInDate.text = currentDate
                qty.text = "1"

            } else {
                Toast.makeText(applicationContext, "Wrong Barcode Scanned", Toast.LENGTH_SHORT)
                    .show()
            }

        }else {

        }



    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


    }













