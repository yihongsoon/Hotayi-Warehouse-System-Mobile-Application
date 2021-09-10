package my.edu.tarc.mobileApp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import kotlin.concurrent.schedule

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splashscreen)

        Timer().schedule(3000) {
            val intent = Intent(this@SplashScreen, MainActivity::class.java)
            startActivity(intent)
        }
    }
}