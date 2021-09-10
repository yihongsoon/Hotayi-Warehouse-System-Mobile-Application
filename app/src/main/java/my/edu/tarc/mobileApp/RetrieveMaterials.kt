package my.edu.tarc.mobileApp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
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
        val sdf = SimpleDateFormat("dd/M/yyyy")
        val currentDate = sdf.format(Date())

        if (scanningStatus == "true") {
            val query =
                FirebaseDatabase.getInstance().reference.child("Material").orderByChild("serial")
                    .startAt(qrTest).endAt(qrTest + "\uf8ff")


            query.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (userSnapshot in snapshot.children) {

                            var rackid =
                                userSnapshot.child("rackid").getValue(String::class.java)
                                    .toString()


                            var rackNumber =
                                userSnapshot.child("rackno").getValue(String::class.java)
                                    .toString()

                            var partNum =
                                userSnapshot.child("part").getValue(String::class.java)
                                    .toString()

                            var matStatus =
                                userSnapshot.child("status").getValue(String::class.java)
                                    .toString()


                            var qty = userSnapshot.child("qty").getValue(String::class.java)
                                .toString()

                            val db = FirebaseDatabase.getInstance().reference.child("Material")
                                .child(qrTest.toString())


                            if (qty == "1") {
                                db.child("qty").setValue("0")

                            } else {
                                Toast.makeText(
                                    applicationContext,
                                    "0 quantity, already retrieved",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }

                            if (matStatus == "RECEIVE") {

                                db.child("status").setValue("RETRIEVED")

                            } else {
                                Toast.makeText(
                                    applicationContext,
                                    "Already retrieved!",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }

                            db.child("rackout").setValue(currentDate)

                            var rackOutDate =
                                userSnapshot.child("rackout").getValue(String::class.java)
                                    .toString()


                            val rackidView = findViewById<TextView>(R.id.txtStoreRackID)
                            val rackNumView = findViewById<TextView>(R.id.txtStoreRackNo)
                            val partNumView = findViewById<TextView>(R.id.txtPartNo)
                            val serialView = findViewById<TextView>(R.id.txtStoreSerialNumber)
                            val statusView = findViewById<TextView>(R.id.txtStoreStatus)
                            val rackoutView = findViewById<TextView>(R.id.txtRackOutDate)
                            val qtyView = findViewById<TextView>(R.id.txtStoreQty)

                            rackidView.text = rackid
                            rackNumView.text = rackNumber
                            partNumView.text = partNum
                            serialView.text = qrTest.toString()
                            statusView.text = matStatus
                            rackoutView.text = rackOutDate
                            qtyView.text = qty


                        }




//        if (scanningStatus == "true") {
//
//
//            val split = qrTest?.split(",")
//            //VALIDATION OF BARCODE ITEMS
//            val result = split?.count()
//            Log.d("Count", result.toString())
//
//            if (result == 4) {
//
//                val displayRackId = split?.get(0)
//
//                val displayRackNo = split?.get(1)
//
//                val displayPartNo = split?.get(2)
//
//                val displaySerialNo = split?.get(3)
//
//                //display scanned barcode items into the text views
//                val rackId = findViewById<TextView>(R.id.txtStoreRackID)
//                val rackNo = findViewById<TextView>(R.id.txtStoreRackNo)
//                val partNo = findViewById<TextView>(R.id.txtPartNo)
//                val serialNo = findViewById<TextView>(R.id.txtStoreSerialNumber)
//                val retrieveStatus = findViewById<TextView>(R.id.txtStoreStatus)
//                val rackInDate = findViewById<TextView>(R.id.txtRackInDate)
//                val qty = findViewById<TextView>(R.id.txtStoreQty)
//
//                rackId.text = displayRackId
//                rackNo.text = displayRackNo
//                partNo.text = displayPartNo
//                serialNo.text = displaySerialNo
//                retrieveStatus.text = "RETRIEVED"
//
//                val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
//                val currentDate = sdf.format(Date())
//
//                rackInDate.text = currentDate
//                qty.text = "1"
//
//            } else {
//                Toast.makeText(applicationContext, "Wrong Barcode Scanned", Toast.LENGTH_SHORT)
//                    .show()
//
//
//            }
//
//
//        }else {
//        }


                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })

        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}


