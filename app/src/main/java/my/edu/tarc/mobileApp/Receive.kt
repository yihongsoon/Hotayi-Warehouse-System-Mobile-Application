package my.edu.tarc.mobileApp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.fragment.app.activityViewModels
import com.budiyev.android.codescanner.BarcodeUtils.encodeBitmap
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import my.edu.tarc.mobileApp.databinding.ActivityReceiveBinding
import org.w3c.dom.Text
import java.io.File
import java.io.IOException
import java.net.URI
import java.text.SimpleDateFormat
import java.util.*

class Receive : AppCompatActivity(){

    private lateinit var firebaseAuth : FirebaseAuth
    private lateinit var preferences: SharedPreferences
    private var _binding: ActivityReceiveBinding? = null
    private val binding get() = _binding!!
    private val partViewModel: PartViewModel by viewModels()

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

        val database = FirebaseDatabase.getInstance()
        val myReference = database.getReference("Material")

        val randomNumber = (10000000..99999999).random()

        val txtDate = findViewById<TextView>(R.id.txtRecDate)
        val df = SimpleDateFormat("dd/MM/yyyy")
        val currentDate = df.format(Calendar.getInstance().time)

        val dateFormat = SimpleDateFormat("yyyMMdd")
        val serialDate = dateFormat.format(Calendar.getInstance().time)

        val partNo = findViewById<TextView>(R.id.txtPartNo)
        val qty = findViewById<TextView>(R.id.txtQuantity)
        val status = findViewById<TextView>(R.id.txtStatus)
        val serial = findViewById<TextView>(R.id.txtSerialNo)
        val staffID = findViewById<TextView>(R.id.txtRecBy)

        val imageBarcode = findViewById<ImageView>(R.id.imageViewBarcode)

        if(scanningStatus == "true"){
            val splitPart = partCode?.split("-")

            val resultPart = splitPart?.count()
            Log.d("CountPart", resultPart.toString())

            if(resultPart == 2){
                val showPartNo = splitPart?.get(0)
                val showQty = splitPart?.get(1)

                    txtDate.text = currentDate.toString()
                    partNo.text = showPartNo
                    qty.text = showQty
                    status.text = "RECEIVED"
                    serial.text = "S"+ randomNumber + serialDate.toString() + "HW"
                    staffID.text = emailStaff

                    val rackid = ""
                    val rackno = ""
                    val rackin = ""
                    val rackout = ""
                    val storeby = ""
                    val retrieveby = ""

                    val Material = Material(serial=serial.text.toString(),part=partNo.text.toString(), qty = qty.text.toString(),
                        status=status.text.toString(), receivedate=txtDate.text.toString(), rackid=rackid.toString(), rackno=rackno.toString(),
                        rackin=rackin.toString(), rackout=rackout.toString(), staffid=staffID.text.toString(),storeby=storeby.toString(),
                        retrieveby=retrieveby.toString())
                    myReference.child(Material.serial.toString()).child("serial").setValue(Material.serial)
                    myReference.child(Material.serial.toString()).child("part").setValue(Material.part)
                    myReference.child(Material.serial.toString()).child("qty").setValue(Material.qty)
                    myReference.child(Material.serial.toString()).child("rackid").setValue(Material.rackid)
                    myReference.child(Material.serial.toString()).child("rackno").setValue(Material.rackno)
                    myReference.child(Material.serial.toString()).child("rackin").setValue(Material.rackin)
                    myReference.child(Material.serial.toString()).child("rackout").setValue(Material.rackout)
                    myReference.child(Material.serial.toString()).child("receivedate").setValue(Material.receivedate)
                    myReference.child(Material.serial.toString()).child("status").setValue(Material.status)
                    myReference.child(Material.serial.toString()).child("receiveby").setValue(Material.staffid)
                    myReference.child(Material.serial.toString()).child("storeby").setValue(Material.storeby)
                    myReference.child(Material.serial.toString()).child("retrieveby").setValue(Material.retrieveby)

                    binding.apply {
                        partViewModel.addPart(Part(serial.text.toString(),part=partNo.text.toString(),qty = qty.text.toString(),
                            status=status.text.toString(),receivedate=txtDate.text.toString(),rackid=rackid.toString(),rackno=rackno.toString(),
                            rackin=rackin.toString(),rackout=rackout.toString(),staffid=staffID.text.toString(),storeby=storeby.toString(),retrieveby=retrieveby.toString()))
                    }
                
            }else{
                Toast.makeText(applicationContext, "Wrong Barcode Scanned", Toast.LENGTH_SHORT).show()
            }
        }else{

        }

        val buttonGenerate = findViewById<Button>(R.id.buttonGenerate)

        buttonGenerate.setOnClickListener(){
            //generate barcode
            val serialText = serial.text.toString()
            val width = 450
            val height = 150
            val bitmap = Bitmap.createBitmap(width,height,Bitmap.Config.RGB_565)
            val codeWriter = MultiFormatWriter()
            try {
                val bitMatrix = codeWriter.encode(serialText,BarcodeFormat.CODE_128,width,height)
                for(x in 0 until width){
                    for(y in 0 until height){
                        bitmap.setPixel(x,y,if(bitMatrix[x,y]) Color.BLACK else Color.WHITE)
                    }
                }
            }catch (ex:IOException){
                Log.d("ERROR",ex.message.toString())
            }
            imageBarcode.setImageBitmap(bitmap)
        }
    }
}