package com.lopukh.crossnote

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class Login : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance()

        val loginBtn = findViewById<Button>(R.id.loginBtn)
        val emailInput = findViewById<EditText>(R.id.emailInput)
        val passInput = findViewById<EditText>(R.id.passInput)
        val emalLayout = findViewById<TextInputLayout>(R.id.emailLayout)
        val passLayout = findViewById<TextInputLayout>(R.id.passLayout)
        loginBtn.setOnClickListener{
            val email = emailInput.text.toString()
            val pass = passInput.text.toString()
            emalLayout.error = null
            passLayout.error = null
            val valid: Boolean = validData(email, pass)
             if (valid) {startLogin(email, pass)}
        }
    }

    private fun validData(email: String?, password: String?): Boolean{
        var result = true
        val emalLayout = findViewById<TextInputLayout>(R.id.emailLayout)
        val passLayout = findViewById<TextInputLayout>(R.id.passLayout)
        if (email.isNullOrEmpty()) {
            emalLayout.error = "Field cannot be empty"
            result = false
        }
        if (password != null) {
            if (password.length < 6){
                passLayout.error = "Minimum password length is 6 characters"
                result = false
            }
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emalLayout.error = "Invalid email"
            result = false
        }
        return result
    }

    private fun startLogin(email: String, password: String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    val snack = Snackbar.make(findViewById(R.id.rl), "Invalid email or password",Snackbar.LENGTH_LONG)
                        .setAction("Restore password") {sendPasswordReset(email)}
                    snack.show()
                    // If sign in fails, display a message to the user.
                    updateUI(null)
                }
            }
    }

    private fun sendPasswordReset(email: String){
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val snack = Snackbar.make(findViewById(R.id.rl), "Check your email",Snackbar.LENGTH_LONG)
                    snack.show()
                }
                else {
                    val emalLayout = findViewById<TextInputLayout>(R.id.emailLayout)
                    emalLayout.error = "This email does not exist"
                }
            }
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            startActivity(Intent(this, NoteActivity::class.java))
        }
    }
}
