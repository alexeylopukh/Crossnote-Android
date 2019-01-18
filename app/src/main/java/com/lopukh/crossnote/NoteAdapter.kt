package com.lopukh.crossnote

import android.content.Intent
import android.graphics.*
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class NoteAdapter(val noteList: MutableList<NoteModel>) : RecyclerView.Adapter<NoteAdapter.ViewHolder>(){

    var currentNote: NoteModel? = null

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
        val date = (note.date.split(" "))
        p0.dateItem.text = date[0]

            val clr: Int = when(note.tag){
                "green" -> Color.parseColor("#90ee02")
                "blue" -> Color.parseColor("#021aee")
                "pink" -> Color.parseColor("#ee0290")
                "red" -> Color.parseColor("#FF0000")
                "orange" -> Color.parseColor("#ee6002")
                "black" -> Color.parseColor("#000000")
                "purple" -> Color.parseColor("#d602ee")
                else -> Color.parseColor("#00000000")
            }
            val bgCircle: GradientDrawable = (p0.tagItem.drawable) as GradientDrawable
            bgCircle.setColor(clr)




        p0.noteItem.setOnClickListener {
            val intent = Intent(it.context, ShowActivity::class.java)
            intent.putExtra("title", noteList[p1].title)
            intent.putExtra("text", noteList[p1].textOfNote)
            intent.putExtra("key", noteList[p1].key)
            intent.putExtra("tag", noteList[p1].tag)
            it.context.startActivity(intent)

        }
    }


    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        val titleItem = itemView.findViewById<TextView>(R.id.titleItem)
        val textItem = itemView.findViewById<TextView>(R.id.textItem)
        val noteItem = itemView.findViewById<LinearLayout>(R.id.noteItem)
        val dateItem = itemView.findViewById<TextView>(R.id.textDate)

        val tagItem = itemView.findViewById<ImageView>(R.id.tagItem)
    }
}