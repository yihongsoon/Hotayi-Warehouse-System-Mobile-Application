package my.edu.tarc.mobileApp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class retrieve_checklist : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retrieve_checklist)
        val actionbar = supportActionBar

        actionbar!!.title = "Checklist"
        actionbar.setDisplayHomeAsUpEnabled(true)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}