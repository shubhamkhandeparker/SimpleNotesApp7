package com.shubham.simplenotesapp7

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import java.util.UUID

class NotesViewModel : ViewModel() {
    private val _notes = mutableStateListOf<Note>()
    val notes: List<Note> = _notes

    init {
        _notes.add(Note(title = "Welcome Note", content = "This is your first Note"))

        _notes.add(Note(title = "Groceries", content = "Milk, Eggs, Bread"))

    }

    fun addNote(title: String, content: String) {
        val newNote = Note(title = title, content = content)
        _notes.add(newNote)
    }

    fun removeNote(note: Note) {
        _notes.remove(note)
    }

    fun getNoteById(id: UUID): Note? {
        return _notes.find { it.id == id }

    }
}