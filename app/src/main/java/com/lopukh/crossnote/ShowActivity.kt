package com.lopukh.crossnote

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_edit.*

class ShowActivity : AppCompatActivity() {

    var database = FirebaseDatabase.getInstance()
    var ref: DatabaseReference? = null
    lateinit var note: NoteModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        val titleInput = findViewById<EditText>(R.id.titleInput)
        val textInput = findViewById<EditText>(R.id.textInput)
        note = NoteModel(intent.getStringExtra("title"), intent.getStringExtra("text"),
            intent.getStringExtra("key"))

        titleInput.setText(note.title)
        textInput.setText(note.textOfNote)
    }

    override fun onBackPressed() {
        if (validNote())
            newNote()
        super.onBackPressed()
    }

    private fun validNote(): Boolean{
        var result = true
        if (((note.title.equals(titleInput.text))&&(note.title.equals(textInput.text))) ||
            (titleInput.text.trim().isEmpty()) && (textInput.text.trim().isEmpty()))
            result = false
        return result
    }

    private fun newNote(){
        val currentUser = UserModel(FirebaseAuth.getInstance().currentUser!!)
        ref = database.getReference("Users").child(currentUser.userId).child("notes")
        val noteRef = ref!!.child(note.key)
        val note = NoteModel(titleInput.text.toString(), textInput.text.toString(), note.key)
        noteRef.setValue(note.toMap())
    }
}
