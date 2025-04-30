package com.example.noteapp_uygulamasi.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.noteapp_uygulamasi.util.currentEntryNoteDate

@Entity(tableName = "notes_table")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("note_id")
    val noteID: Int = 0,
    @ColumnInfo("note_title")
    val noteTitle: String,
    @ColumnInfo("note_subtitle")
    val noteSubtitle: String,
    @ColumnInfo("note_description")
    val noteDescription: String,
    @ColumnInfo("note_image")
    val noteImage: String?,
    @ColumnInfo("note_date")
    val noteDateEntry: String = currentEntryNoteDate(),
    @ColumnInfo("note_category")
    val noteCategory: String = "Genel",
    @ColumnInfo("note_priority")
    val notePriority: Int = 0, // 0-düşük, 1-orta, 2-yüksek
    @ColumnInfo("note_is_pinned")
    val isPinned: Boolean = false,
    @ColumnInfo("sync_status")
    val syncStatus: Int = 0,
    @ColumnInfo("last_modified")
    val lastModified: Long = System.currentTimeMillis()
)