package my.edu.tarc.mobileApp

import android.app.ProgressDialog.show
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.budiyev.android.codescanner.*

private const val CAMERA_REQUEST_CODE = 101

class Scanner : AppCompatActivity() {
    private lateinit var codeScanner: CodeScanner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scanner)

        setupPermission()
        codeScanner()
    }

    public fun codeScanner(){
        val scanner = findViewById<CodeScannerView>(R.id.ScannerView)
        val text = findViewById<TextView>(R.id.textViewScanner)
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

// DONT COPY INI //
                var str1= text.text
                 Log.d("QRCodeContent", str1.toString())

                    val intent = Intent(this@Scanner, RetrieveMaterials::class.java)
                    intent.putExtra("qrCodeContent", str1)
                    intent.putExtra("scanStatus","true")
                    startActivity(intent)

                    Toast.makeText(applicationContext,str1.toString()+"    added ", Toast.LENGTH_SHORT).show()


// DONT COPY INI //

                }

            }
            errorCallback = ErrorCallback {
                runOnUiThread{
                    Log.e("Main", "Camera initialization error: ${it.message}")
// DONT COPY INI //
                    intent.putExtra("scanStatus","false")
// DONT COPY INI //
                }
            }
        }
        scanner.setOnClickListener{
            codeScanner.startPreview()
        }
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