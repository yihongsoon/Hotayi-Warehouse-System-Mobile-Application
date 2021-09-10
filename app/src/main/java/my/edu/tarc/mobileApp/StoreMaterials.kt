package my.edu.tarc.mobileApp

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
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
            //val splitSerial = serialCode?.split(",")
            val splitRack = rackCode?.split(",")

            //val resultSerial = splitSerial?.count()
            val resultRack = splitRack?.count()
            //Log.d("CountSerial", resultSerial.toString())
            Log.d("CountRack", resultRack.toString())

            if(resultRack == 2){

                val displayStoreRackID = splitRack?.get(0)
                val displayStoreRackNo = splitRack?.get(1)

               //val displayStorePartNo = splitSerial?.get(0)
                //val displayStoreSerialNo = splitSerial?.get(1)

                val storeRackID = findViewById<TextView>(R.id.txtStoreRackID)
                val storeRackNo = findViewById<TextView>(R.id.txtStoreRackNo)
                val storePartNo = findViewById<TextView>(R.id.txtPartNo)
                val storeSerialNo = findViewById<TextView>(R.id.txtStoreSerialNumber)
                val storeStatus = findViewById<TextView>(R.id.txtStoreStatus)
                val storeRackInDate = findViewById<TextView>(R.id.txtRackInDate)
                val storeQty = findViewById<TextView>(R.id.txtStoreQty)
                val sdf = SimpleDateFormat("dd/M/yyyy")
                val currentDate = sdf.format(Date())

                storeRackID.text= displayStoreRackID
                storeRackNo.text= displayStoreRackNo
               // storePartNo.text = displayStorePartNo
               // storeSerialNo.text = displayStoreSerialNo
                storeStatus.text = "RECEIVED"
                storeRackInDate.text = currentDate
                storeQty.text = "1"

                val query = FirebaseDatabase.getInstance().reference.child("Material").orderByChild("serial")
                    .startAt(serialCode).endAt(serialCode + "\uf8ff")
                query.addValueEventListener(object: ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot){
                        if(snapshot.exists()){
                            for (j in snapshot.children){
                                var partNum = j.child("part").getValue(String::class.java).toString()

                                storePartNo.text = partNum
                                storeSerialNo.text = serialCode.toString()
                            }
                        }
                    }
                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }


                })
            }
            else{
                Toast.makeText(applicationContext, "Wrong Barcode Scanned", Toast.LENGTH_SHORT).show()
            }

            btnSave.setOnClickListener {
                val displayStoreRackID = splitRack?.get(0)
                val displayStoreRackNo = splitRack?.get(1)

                val storeRackID = findViewById<TextView>(R.id.txtStoreRackID)
                val storeRackNo = findViewById<TextView>(R.id.txtStoreRackNo)
                val storePartNo = findViewById<TextView>(R.id.txtPartNo)
                val storeSerialNo = findViewById<TextView>(R.id.txtStoreSerialNumber)
                val storeStatus = findViewById<TextView>(R.id.txtStoreStatus)
                val storeRackInDate = findViewById<TextView>(R.id.txtRackInDate)
                val storeQty = findViewById<TextView>(R.id.txtStoreQty)
                val sdf = SimpleDateFormat("dd/M/yyyy")
                val currentDate = sdf.format(Date())

                storeRackID.text= displayStoreRackID
                storeRackNo.text= displayStoreRackNo
                // storePartNo.text = displayStorePartNo
                // storeSerialNo.text = displayStoreSerialNo
                storeStatus.text = "RECEIVED"
                storeRackInDate.text = currentDate
                storeQty.text = "1"

                val query = FirebaseDatabase.getInstance().reference.child("Material").orderByChild("serial")
                    .startAt(serialCode).endAt(serialCode + "\uf8ff")
                query.addValueEventListener(object: ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot){
                        if(snapshot.exists()){
                            for (j in snapshot.children){
                                var qty = j.child("qty").getValue(String::class.java).toString()
                                var status = j.child("status").getValue(String::class.java).toString()
                                val dbControl = FirebaseDatabase.getInstance().reference.child("Material").child(serialCode.toString())

                                dbControl.child("rackid").setValue(storeRackID.text)
                                dbControl.child("rackno").setValue(storeRackNo.text)
                                dbControl.child("rackin").setValue(currentDate)

                                if(qty == "1") {
                                    Toast.makeText(applicationContext,"This material has already been stored",Toast.LENGTH_SHORT).show()
                                }
                                else{
                                    dbControl.child("qty").setValue("1")
                                    Toast.makeText(applicationContext,"Material successfully stored",Toast.LENGTH_SHORT).show()
                                }
                                if(status == "STORED"){
                                    Toast.makeText(applicationContext,"This material has already been stored",Toast.LENGTH_SHORT).show()
                                }
                                else{
                                    dbControl.child("status").setValue("STORED")
                                    storeStatus.text="STORED"
                                }
                            }
                        }
                    }
                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }
                })

            }

        }

    }

}