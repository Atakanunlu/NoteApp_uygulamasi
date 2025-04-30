package com.example.noteapp_uygulamasi.core.database.repository

import com.example.noteapp_uygulamasi.core.database.dao.NoteDAO
import com.example.noteapp_uygulamasi.core.database.entity.NoteEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class NoteRepository @Inject constructor(
    private val noteDAO: NoteDAO
) {
    suspend fun addNote(addNote: NoteEntity) {
        noteDAO.insertNote(addNote)
    }

    suspend fun updateNote(updateNote: NoteEntity) {
        noteDAO.updateNote(updateNote)
    }

    suspend fun deleteNote(deleteNote: NoteEntity) {
        noteDAO.deleteNote(deleteNote)
    }

    fun getAllNotes() = noteDAO.getAllNotes()
        .flowOn(Dispatchers.IO)
        .conflate()

    suspend fun searchNote(searchNote: String): List<NoteEntity> {
        return noteDAO.searchNote(searchNote)
    }

    suspend fun deleteAllNotes() {
        noteDAO.deleteAllNotes()
    }

}
