package com.lopukh.crossnote

import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.android.synthetic.main.activity_edit.view.*
import java.util.*

class ShowActivity : AppCompatActivity() {

    var database = FirebaseDatabase.getInstance()
    var ref: DatabaseReference? = null
    lateinit var note: NoteModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        val titleInput = findViewById<EditText>(R.id.titleInput)
        val textInput = findViewById<EditText>(R.id.textInput)
        val date = Calendar.getInstance().time
        note = NoteModel(intent.getStringExtra("title"), intent.getStringExtra("text"),
            intent.getStringExtra("key"), date, intent.getStringExtra("tag"))

        titleInput.setText(note.title)
        textInput.setText(note.textOfNote)
        tagInput.setText(note.tag)
    }

    override fun onBackPressed() {
        if (validNote())
            newNote()
        super.onBackPressed()
    }

    private fun validNote(): Boolean{
        var result = true
        val titleInput = findViewById<EditText>(R.id.titleInput)
        val textInput = findViewById<EditText>(R.id.textInput)
        val tagInput = findViewById<EditText>(R.id.tagInput)
        if ((note.title == titleInput.text.toString())&&(note.textOfNote == textInput.text.toString())
            &&(note.tag == tagInput.text.toString()) ||
            (titleInput.text.trim().isEmpty()) && (textInput.text.trim().isEmpty()))
            result = false
        return result
    }

    private fun newNote(){
        val titleInput = findViewById<EditText>(R.id.titleInput)
        val textInput = findViewById<EditText>(R.id.textInput)
        val tagInput = findViewById<EditText>(R.id.tagInput)
        val currentUser = UserModel(FirebaseAuth.getInstance().currentUser!!)
        ref = database.getReference("Users").child(currentUser.userId).child("notes")
        val noteRef = ref!!.child(note.key)
        val date = Calendar.getInstance().time
        val note = NoteModel(titleInput.text.toString(), textInput.text.toString(), note.key, date, tagInput.text.toString())
        noteRef.setValue(note.toMap())
    }
}
