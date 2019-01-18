package com.lopukh.crossnote

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_edit.*
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DatabaseReference
import java.util.Calendar


class EditActivity : AppCompatActivity() {

    var database = FirebaseDatabase.getInstance()
    var ref: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)


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
        val date = Calendar.getInstance().time
        val note = NoteModel(titleInput.text.toString(), textInput.text.toString(), noteRef.key!!, date, tagInput.text.toString())
        noteRef.setValue(note.toMap())
    }
}
