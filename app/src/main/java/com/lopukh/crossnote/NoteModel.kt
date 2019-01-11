package com.lopukh.crossnote

import java.util.HashMap

class NoteModel {

    var title: String = ""
    var textOfNote: String = ""
    var key: String = ""

    constructor(title: String, textOfNote: String, key: String) {
        this.title = title
        this.textOfNote = textOfNote
        this.key = key
    }

    constructor()

    fun toMap(): Map<String, Any> {
        val result = HashMap<String, Any>()
        result["title"] = title
        result["textOfNote"] = textOfNote
        result["key"] = key
        return result
    }
}
