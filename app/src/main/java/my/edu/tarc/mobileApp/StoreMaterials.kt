package my.edu.tarc.mobileApp

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button

import androidx.appcompat.app.AppCompatActivity


class StoreMaterials : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store_materials)

        val btnScan = findViewById<Button>(R.id.btnScanMaterial)

        btnScan.setOnClickListener{
            val intent = Intent(this, Scanner::class.java)
            startActivity(intent)
        }

    }










}