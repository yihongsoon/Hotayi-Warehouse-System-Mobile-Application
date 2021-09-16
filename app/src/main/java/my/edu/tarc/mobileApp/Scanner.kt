package my.edu.tarc.mobileApp

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.budiyev.android.codescanner.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.*

private const val CAMERA_REQUEST_CODE = 101

class Scanner : AppCompatActivity() {
    private lateinit var codeScanner: CodeScanner
    private lateinit var firebaseAuth : FirebaseAuth
    private lateinit var string: String
    private lateinit var qty:String
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scanner)
        val actionbar = supportActionBar
        actionbar!!.title = "Retrieve Material"

        setupPermission()
        codeScanner()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    public fun codeScanner(){
        val scanner = findViewById<CodeScannerView>(R.id.ScannerView)
        val text = findViewById<TextView>(R.id.textViewScannerView)
        firebaseAuth = FirebaseAuth.getInstance()

        codeScanner = CodeScanner(this, scanner)
        codeScanner.apply{
            camera = CodeScanner.CAMERA_BACK
            formats = CodeScanner.ALL_FORMATS

            autoFocusMode = AutoFocusMode.SAFE
            scanMode = ScanMode.CONTINUOUS
            isAutoFocusEnabled = true
            isFlashEnabled = false

            decodeCallback = DecodeCallback {
                runOnUiThread{
                    text.text = it.text
                    var str1= text.text
                    string = str1.toString()
                    Log.d("QRCodeContent", str1.toString())
                    startFunction(string)
                }

            }
            errorCallback = ErrorCallback {
                runOnUiThread{
                    Log.e("Main", "Camera initialization error: ${it.message}")
                }
            }
        }
        scanner.setOnClickListener{
            codeScanner.startPreview()
        }
    }

    private fun startFunction(string: String) {
        val query = FirebaseDatabase.getInstance().reference.child("Material").orderByChild("serial")
            .startAt(string).endAt(string)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (userSnapshot in snapshot.children) {
                        qty = userSnapshot.child("qty").getValue(String::class.java).toString()
                        Log.d("QUANTITY", qty)

                        if(qty.isNullOrEmpty()){

                        }else{
                            setValue(string, qty)

                        }

                    }
                }else{
                    Toast.makeText(applicationContext, "No such material exists", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@Scanner, RetrieveMaterials::class.java)
                    startActivity(intent)

                }
            }
            override fun onCancelled(error: DatabaseError) {
            }


        })




    }

    private fun setValue(serial: String, qty:String) {
        val sdf = SimpleDateFormat("dd/M/yyyy")
        val currentDate = sdf.format(Date())
        val emailStaff = firebaseAuth.currentUser?.email.toString()
        if(qty == "1"){
            val db = FirebaseDatabase.getInstance().reference.child("Material").child(serial)
            db.child("qty").setValue("0")
            db.child("rackout").setValue(currentDate)
            db.child("status").setValue("RETRIEVE")
            db.child("retrieveby").setValue(emailStaff)

            Log.d("qty", qty)
            Toast.makeText(applicationContext, "Material retrieved successfully", Toast.LENGTH_SHORT).show()

        }else {


        Toast.makeText(applicationContext, "0 quantity, already retrieved", Toast.LENGTH_SHORT).show()
        }

        val intent = Intent(this@Scanner, RetrieveMaterials::class.java)
        intent.putExtra("qrCodeContent", serial)
        intent.putExtra("scanStatus","true")
        //intent.putExtra("quantity", qty)

        startActivity(intent)
    }


    override fun onResume(){
        super.onResume()
        codeScanner.startPreview()
    }
    override fun onPause(){
        codeScanner.releaseResources()
        super.onPause()

    }

    private fun setupPermission(){
        val permission = ContextCompat.checkSelfPermission(this,
            android.Manifest.permission.CAMERA)

        if(permission != PackageManager.PERMISSION_GRANTED){
            makeRequest()
        }
    }

    private fun makeRequest() {
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA),
            CAMERA_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            CAMERA_REQUEST_CODE -> {
                if(grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this,"You need the camera permission to be able to scan",Toast.LENGTH_SHORT)
                }
                else{
                }
            }
        }
    }
}