package com.lopukh.crossnote

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.widget.Button
import android.widget.EditText
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

        loginBtn.setOnClickListener(){
            login(emailInput.text.toString(), passInput.text.toString())
        }
    }

    private fun login(email: String?, password: String?){
        auth.signInWithEmailAndPassword(email.toString(), password.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    val snack = Snackbar.make(findViewById(R.id.rl), "Invalid email or password",Snackbar.LENGTH_LONG)
                        .setAction("Restore password") {sendPasswordReset(email.toString())}
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
                    val snack = Snackbar.make(findViewById(R.id.rl), "No such email",Snackbar.LENGTH_LONG)
                    snack.show()
                }
            }
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            startActivity(Intent(this, NoteActivity::class.java))
        }
    }
}