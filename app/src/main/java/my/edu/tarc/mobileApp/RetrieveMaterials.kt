package my.edu.tarc.mobileApp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.renderscript.Sampler
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import my.edu.tarc.mobileApp.databinding.ActivityReceiveBinding
import my.edu.tarc.mobileApp.databinding.ActivityStoreMaterialsBinding
import java.text.SimpleDateFormat
import java.util.*


class RetrieveMaterials : AppCompatActivity() {
    var rackid = ""
    var rackNumber = ""
    var partNum = ""
    var matStatus= ""
    var qty =""
    var staffid = ""
    var rackOutDate = ""
    var retrieveby = ""
    lateinit var listener: ValueEventListener
    private lateinit var firebaseAuth : FirebaseAuth
    private var _binding: ActivityStoreMaterialsBinding? = null
    private val binding get() = _binding!!
    private val partViewModel: PartViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retrieve_materials)
        val actionbar = supportActionBar
        actionbar!!.title = "Retrieve Materials"
        actionbar.setDisplayHomeAsUpEnabled(true)
        firebaseAuth = FirebaseAuth.getInstance()


        val btnScan = findViewById<Button>(R.id.btnScanMaterial)
        val btnCheckList = findViewById<Button>(R.id.btnCheckList)
        var scanStatus: Boolean? = null


        btnScan.setOnClickListener {

            val intent = Intent(this, Scanner::class.java)
            startActivity(intent)
        }

        btnCheckList.setOnClickListener {
            val intent = Intent(this, RetrieveChecklist::class.java)
            startActivity(intent)
        }


        val intent = intent
        val qrTest = intent.getStringExtra("qrCodeContent")
        val scanningStatus = intent.getStringExtra("scanStatus")
        val sdf = SimpleDateFormat("dd/M/yyyy")
        val currentDate = sdf.format(Date())
        val rackidView = findViewById<TextView>(R.id.txtStoreRackID)
        val rackNumView = findViewById<TextView>(R.id.txtStoreRackNo)
        val partNumView = findViewById<TextView>(R.id.txtPartNo)
        val serialView = findViewById<TextView>(R.id.txtStoreSerialNumber)
        val statusView = findViewById<TextView>(R.id.txtStoreStatus)
        val rackoutView = findViewById<TextView>(R.id.txtRackOutDate)
        val qtyView = findViewById<TextView>(R.id.txtStoreQty)
        val staffidView = findViewById<TextView>(R.id.txtViewStaff)
        val emailStaff = firebaseAuth.currentUser?.email.toString()


        if (scanningStatus == "true") {

            val query = FirebaseDatabase.getInstance().reference.child("Material").orderByChild("serial")
                    .startAt(qrTest).endAt(qrTest)

        query.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (userSnapshot in snapshot.children) {

                            rackid =
                                userSnapshot.child("rackid").getValue(String::class.java)
                                    .toString()

                            rackNumber =
                                userSnapshot.child("rackno").getValue(String::class.java)
                                    .toString()

                            partNum =
                                userSnapshot.child("part").getValue(String::class.java)
                                    .toString()

                            matStatus =
                                userSnapshot.child("status").getValue(String::class.java)
                                    .toString()


                            qty = userSnapshot.child("qty").getValue(String::class.java)
                                .toString()

                            staffid = userSnapshot.child("staffid").getValue(String::class.java)
                                .toString()

                            rackOutDate = userSnapshot.child("rackout").getValue(String::class.java)
                                .toString()

                            retrieveby = userSnapshot.child("retrieveby").getValue(String::class.java)
                                .toString()


                                rackidView.text = rackid
                                rackNumView.text = rackNumber
                                partNumView.text = partNum
                                serialView.text = qrTest.toString()
                                statusView.text = matStatus
                                rackoutView.text = rackOutDate
                                qtyView.text = qty
                                staffidView.text = retrieveby
                        }



                    }else{
//                        Toast.makeText(
//                            applicationContext,
//                            "No such material exists",
//                            Toast.LENGTH_SHORT
//                        )
//                            .show()
                    }

                    query.removeEventListener(this)

                }

                override fun onCancelled(error: DatabaseError) {
                    query.removeEventListener(this)
                }


            })

        }


        /*when {
            matStatus == "RECEIVED" -> db.child("status")
                .setValue("RETRIEVED")

            matStatus == "STORED" -> db.child("status")
                .setValue("RETRIEVED")



            else -> Toast.makeText(
                applicationContext,
                "Already retrieved!",
                Toast.LENGTH_SHORT
            )
                .show()

        }*/
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed(){
        val intent = Intent(this@RetrieveMaterials, MainActivity::class.java)
        startActivity(intent)
    }
}


