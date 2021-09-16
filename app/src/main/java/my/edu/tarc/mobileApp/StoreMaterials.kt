package my.edu.tarc.mobileApp

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels

import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import my.edu.tarc.mobileApp.databinding.ActivityReceiveBinding
import my.edu.tarc.mobileApp.databinding.ActivityStoreMaterialsBinding
import java.text.SimpleDateFormat
import java.util.*


class StoreMaterials : AppCompatActivity() {
    private lateinit var firebaseAuth : FirebaseAuth
    private var _binding: ActivityStoreMaterialsBinding? = null
    private val binding get() = _binding!!
    private val partViewModel: PartViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        firebaseAuth = FirebaseAuth.getInstance()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store_materials)

        val actionbar = supportActionBar

        actionbar!!.title = "Store"

        actionbar.setDisplayHomeAsUpEnabled(true)

        val btnScan = findViewById<Button>(R.id.btnScanMaterial)
        val btnSave = findViewById<Button>(R.id.btnStoreSave)
        var pattern = Regex(pattern = "^R[0-9]{4,5}")

        btnScan.setOnClickListener{
            val intent = Intent(this, StoreScanner::class.java)
            startActivity(intent)
        }

        val emailStaff = firebaseAuth.currentUser?.email.toString()
        val intent = intent
        val serialCode = intent.getStringExtra("serialCodeContent")
        val rackCode = intent.getStringExtra("rackCodeContent")
        val scanningStatus = intent.getStringExtra("scanStatus")

        if(scanningStatus == "true"){
            val splitRack = rackCode?.split("-")

            //val resultSerial = splitSerial?.count()
            val resultRack = splitRack?.count()
            //Log.d("CountSerial", resultSerial.toString())
            Log.d("CountRack", resultRack.toString())

            val displayStoreRackID = splitRack?.get(0)
            val displayStoreRackNo = splitRack?.get(1)

            val testRackID = displayStoreRackID.toString()
            val testRackNo = displayStoreRackNo.toString()

            if(resultRack == 2 && testRackID.matches(pattern) && testRackNo.matches(pattern)){

               //val displayStorePartNo = splitSerial?.get(0)
                //val displayStoreSerialNo = splitSerial?.get(1)

                val storeRackID = findViewById<TextView>(R.id.txtStoreMaterialRackID)
                val storeRackNo = findViewById<TextView>(R.id.txtStoreMaterialRackNo)
                val storePartNo = findViewById<TextView>(R.id.txtStoreMaterialPartNo)
                val storeSerialNo = findViewById<TextView>(R.id.txtStoreMaterialSerialNumber)
                val storeStatus = findViewById<TextView>(R.id.txtStoreMaterialStatus)
                val storeRackInDate = findViewById<TextView>(R.id.txtStoreRackInDate)
                val storeQty = findViewById<TextView>(R.id.txtStoreMaterialQty)
                val storedBy = findViewById<TextView>(R.id.txtStoredBy)
                val sdf = SimpleDateFormat("dd/M/yyyy")
                val currentDate = sdf.format(Date())

                storeRackID.text= displayStoreRackID
                storeRackNo.text= displayStoreRackNo
                storeRackInDate.text = currentDate
                storedBy.text = emailStaff

                val query = FirebaseDatabase.getInstance().reference.child("Material").orderByChild("serial")
                    .startAt(serialCode).endAt(serialCode + "\uf8ff")
                query.addValueEventListener(object: ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot){
                        if(snapshot.exists()){
                            for (j in snapshot.children){
                                var partNum = j.child("part").getValue(String::class.java).toString()
                                var qty = j.child("qty").getValue(String::class.java).toString()
                                var status = j.child("status").getValue(String::class.java).toString()

                                storePartNo.text = partNum
                                storeSerialNo.text = serialCode.toString()
                                storeStatus.text = status
                                storeQty.text = qty
                            }
                        }
                    }
                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }


                })
                btnSave.visibility = View.VISIBLE
            }
            else{
                Toast.makeText(applicationContext, "No Such Rack Exists", Toast.LENGTH_SHORT).show()
            }


            btnSave.setOnClickListener {
                val displayStoreRackID = splitRack?.get(0)
                val displayStoreRackNo = splitRack?.get(1)
                val storedBy = findViewById<TextView>(R.id.txtStoredBy)
                val storeRackID = findViewById<TextView>(R.id.txtStoreMaterialRackID)
                val storeRackNo = findViewById<TextView>(R.id.txtStoreMaterialRackNo)
                val storeStatus = findViewById<TextView>(R.id.txtStoreMaterialStatus)
                val storeRackInDate = findViewById<TextView>(R.id.txtStoreRackInDate)
                val storeQty = findViewById<TextView>(R.id.txtStoreMaterialQty)
                val storePartNo = findViewById<TextView>(R.id.txtStoreMaterialPartNo)
                val storeSerialNo = findViewById<TextView>(R.id.txtStoreMaterialSerialNumber)
                val sdf = SimpleDateFormat("dd/M/yyyy")
                val currentDate = sdf.format(Date())
                val rackout = ""
                val receiveby = ""
                val retrieveby = ""
                val receiveDate = ""

                storeRackID.text= displayStoreRackID
                storeRackNo.text= displayStoreRackNo
                storeRackInDate.text = currentDate
                storedBy.text = emailStaff

                val dbControl = FirebaseDatabase.getInstance().reference.child("Material").child(serialCode.toString())

                dbControl.child("rackid").setValue(storeRackID.text)
                dbControl.child("rackno").setValue(storeRackNo.text)
                dbControl.child("storeby").setValue(storedBy.text)
                dbControl.child("rackin").setValue(currentDate)
                if(storeStatus.text == "STORED") {
                    Toast.makeText(applicationContext,"This material has already been stored",Toast.LENGTH_SHORT).show()
                }
                else{
                    dbControl.child("qty").setValue("1")
                    dbControl.child("status").setValue("STORED")
                    storeQty.text="1"
                    storeStatus.text="STORED"
                    Toast.makeText(applicationContext,"The material has been stored successfully",Toast.LENGTH_SHORT).show()
                    val part = Part(storeSerialNo.text.toString(),storePartNo.text.toString(),storeQty.text.toString(),storeStatus.text.toString(),receiveDate,
                        storeRackID.text.toString(),storeRackNo.text.toString(),storeRackInDate.text.toString(),rackout,receiveby,storedBy.text.toString(), retrieveby)
                    partViewModel.addPart(part)
                }

            }

        }

    }

}