package com.lopukh.crossnote

import android.content.Intent
import android.support.design.widget.Snackbar
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView

class NoteAdapter(val noteList: MutableList<NoteModel>) : RecyclerView.Adapter<NoteAdapter.ViewHolder>(){
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val v = LayoutInflater.from(p0.context).inflate(R.layout.note_item, p0, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val note: NoteModel = noteList[p1]
        p0.titleItem.text = note.title
        p0.textItem.text = note.textOfNote

        p0.noteItem.setOnClickListener {
            val intent = Intent(it.context, ShowActivity::class.java)
            intent.putExtra("title", noteList[p1].title)
            intent.putExtra("text", noteList[p1].textOfNote)
            intent.putExtra("key", noteList[p1].key)
            it.context.startActivity(intent)
        }
    }


    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        val titleItem = itemView.findViewById<TextView>(R.id.titleItem)
        val textItem = itemView.findViewById<TextView>(R.id.textItem)
        val noteItem = itemView.findViewById<LinearLayout>(R.id.noteItem)
    }
}