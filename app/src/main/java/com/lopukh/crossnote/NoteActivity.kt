package com.lopukh.crossnote

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.LinearLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class NoteActivity : AppCompatActivity() {

    lateinit var reference: DatabaseReference
    var notes: MutableList<NoteModel> = ArrayList()
    val adapter = NoteAdapter(notes)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)

        val rw = findViewById<RecyclerView>(R.id.recyclerView)
        rw.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        //rw.setHasFixedSize(true)
        rw.adapter = adapter
        val database = FirebaseDatabase.getInstance()
        val currentUser = UserModel(FirebaseAuth.getInstance().currentUser!!)
        reference = database.getReference("Users").child(currentUser.userId).child("notes")
        addEmailRecord()
        enableListeners()

        val newFab = findViewById<FloatingActionButton>(R.id.newFat)
        newFab.setOnClickListener {
            val intent = Intent(this, EditActivity::class.java)
            startActivity(intent)
        }
    }

    private fun enableListeners(){
        reference.addChildEventListener(
            object : ChildEventListener{

                override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                    val newNote: NoteModel = p0.getValue(NoteModel::class.java)!!
                    notes.add(0, newNote)
                    adapter.notifyDataSetChanged()
                }

                override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                    val changeNote: NoteModel = p0.getValue(NoteModel::class.java)!!
                    val index = getItemIndex(changeNote)
                    notes[index] = changeNote
                    adapter.notifyItemChanged(index)
                }

                override fun onChildRemoved(p0: DataSnapshot) {
                    val delNote = p0.getValue(NoteModel::class.java)!!
                    val index = getItemIndex(delNote)
                    notes.removeAt(index)
                    adapter.notifyItemRemoved(index)
                }

                override fun onCancelled(p0: DatabaseError) {}
                override fun onChildMoved(p0: DataSnapshot, p1: String?) {}

            }
        )
    }

    private fun getItemIndex(note: NoteModel): Int {
        var result = -1
        for(i in 0 until notes.size){
            if(notes[i].key == note.key){
                result = i
                break
            }
        }
        return result
    }

    private fun addEmailRecord() {
        val database = FirebaseDatabase.getInstance()
        val ref: DatabaseReference
        val currentUser = UserModel(FirebaseAuth.getInstance().currentUser!!)
        ref = database.getReference("Users").child(currentUser.userId)
        val emailRef = ref.child("email")
        emailRef.setValue(currentUser.email)
    }
}
