package com.lopukh.crossnote

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_edit.*
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DatabaseReference





class EditActivity : AppCompatActivity() {

    var database = FirebaseDatabase.getInstance()
    var ref: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        val titleInput = findViewById<EditText>(R.id.titleInput)
        val textInput = findViewById<EditText>(R.id.textInput)


    }

    override fun onBackPressed() {
        if (validNote())
            newNote()
        super.onBackPressed()
    }

    private fun validNote(): Boolean{
        var result = true
        if ((titleInput.text.trim().isEmpty())&&(textInput.text.trim().isEmpty()))
            result = false
        return result
    }

    private fun newNote(){
        val currentUser = UserModel(FirebaseAuth.getInstance().currentUser!!)
        ref = database.getReference("Users").child(currentUser.userId).child("notes")
        val noteRef = ref!!.push()
        val note = NoteModel(titleInput.text.toString(), textInput.text.toString(), noteRef.key!!)
        noteRef.setValue(note.toMap())
    }
}
