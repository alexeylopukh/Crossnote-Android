package com.lopukh.crossnote

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class NoteModel {

    var title: String = ""
    var textOfNote: String = ""
    var key: String = ""
    var date: String = ""
    var tag: String = ""

    constructor(title: String, textOfNote: String, key: String, date: Date, tag: String) {
        val format: DateFormat = SimpleDateFormat("MM/dd/yyyy HH:mm:ss")
        this.title = title
        this.textOfNote = textOfNote
        this.key = key
        this.date = format.format(date)
        this.tag = tag
    }

    constructor()

    fun toMap(): Map<String, Any> {
        val result = HashMap<String, Any>()
        result["title"] = title
        result["textOfNote"] = textOfNote
        result["key"] = key
        result["date"] = date
        if (tag != "")
            result["tag"] = tag
        return result
    }
}
