package my.edu.tarc.mobileApp

import android.graphics.Bitmap
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import java.io.IOException

class BarcodeGenerator : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_barcode_generator)
        val actionbar = supportActionBar

        actionbar!!.title = "Barcode Generator"
        val barcodeInput = findViewById<EditText>(R.id.editTextBarcodeInput)
        val btnBarGenerator = findViewById<Button>(R.id.buttonBarGenerator)
        val imageViewBarcode = findViewById<ImageView>(R.id.imageViewBarGenerator)

        btnBarGenerator.setOnClickListener(){
            val serialText = barcodeInput.text.toString()
            val width = 450
            val height = 150
            val bitmap = Bitmap.createBitmap(width,height, Bitmap.Config.RGB_565)
            val codeWriter = MultiFormatWriter()
            try {
                val bitMatrix = codeWriter.encode(serialText, BarcodeFormat.CODE_128,width,height)
                for(x in 0 until width){
                    for(y in 0 until height){
                        bitmap.setPixel(x,y,if(bitMatrix[x,y]) Color.BLACK else Color.WHITE)
                    }
                }
            }catch (ex: IOException){
                Log.d("ERROR",ex.message.toString())
            }
            imageViewBarcode.setImageBitmap(bitmap)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}