package com.example.noteapp_uygulamasi.core.database.dao


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.noteapp_uygulamasi.core.database.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(newNote: NoteEntity)

    @Update
    suspend fun updateNote(noteEntity: NoteEntity)

    @Delete
    suspend fun deleteNote(deleteNote: NoteEntity)

    @Query("SELECT * FROM notes_table")
    fun getAllNotes(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes_table WHERE note_id =:selectedNoteID")
    suspend fun getNote(selectedNoteID: Int): NoteEntity

    @Query("SELECT * FROM notes_table WHERE note_title LIKE '%' || :noteSearch || '%' OR note_subtitle LIKE '%' || :noteSearch || '%'")
    suspend fun searchNote(noteSearch: String): List<NoteEntity>

    @Query("DELETE FROM notes_table")
    suspend fun deleteAllNotes()

    @Query("SELECT * FROM notes_table WHERE note_category = :category")
    fun getNotesByCategory(category: String): Flow<List<NoteEntity>>

    @Query("SELECT DISTINCT note_category FROM notes_table")
    fun getAllCategories(): Flow<List<String>>

    @Query("SELECT * FROM notes_table ORDER BY note_title ASC")
    fun getNotesSortedByTitleAsc(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes_table ORDER BY note_title DESC")
    fun getNotesSortedByTitleDesc(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes_table ORDER BY note_date ASC")
    fun getNotesSortedByDateAsc(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes_table ORDER BY note_date DESC")
    fun getNotesSortedByDateDesc(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes_table ORDER BY note_priority DESC")
    fun getNotesSortedByPriority(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes_table WHERE note_is_pinned = 1")
    fun getPinnedNotes(): Flow<List<NoteEntity>>
}