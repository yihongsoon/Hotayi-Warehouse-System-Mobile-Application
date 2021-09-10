package my.edu.tarc.mobileApp

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.Toast
import com.airbnb.lottie.LottieAnimationView
import com.google.firebase.auth.FirebaseAuth


class Login : AppCompatActivity() {

    private lateinit var firebaseAuth : FirebaseAuth
    private var loadingDialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        firebaseAuth = FirebaseAuth.getInstance()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val email = findViewById<EditText>(R.id.eTEmail)
        val password = findViewById<EditText>(R.id.eTPass)

        btnLogin.setOnClickListener{

            var email: String = email.text.toString()
            var password: String = password.text.toString()

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(this, "Please fill in all the fields", Toast.LENGTH_LONG).show()
            } else {
                showLoading()
                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) {task ->
                    if (task.isSuccessful) {
                        hideLoading()
                        //val emailTest = firebaseAuth.currentUser?.email.toString()
                        Toast.makeText(this, "Successfully Login", Toast.LENGTH_LONG).show()
                        startActivity(Intent(this,MainActivity::class.java))

                    }
                    else {
                        hideLoading()
                        Toast.makeText(this, "Login Failed", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
    private fun hideLoading(){
        loadingDialog?.let { if(it.isShowing)it.cancel() }
    }

    private fun showLoading(){
        hideLoading()
        loadingDialog = CommonUtils.showLoadingDialog(this)
    }

}