package my.edu.tarc.mobileApp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider

class PartRecord : AppCompatActivity() {

    private lateinit var partViewModel: PartViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_part_record)

        partViewModel = ViewModelProvider(this).get(PartViewModel::class.java)

        partViewModel.getAllPart.observe(this,
            {
                Log.d("MainActivity", "Contact List Size" + it.size)
            }
        )
    }
}