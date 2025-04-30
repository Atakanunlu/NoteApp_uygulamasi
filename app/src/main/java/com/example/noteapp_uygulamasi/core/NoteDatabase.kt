package com.example.noteapp_uygulamasi.core

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.noteapp_uygulamasi.core.database.dao.NoteDAO
import com.example.noteapp_uygulamasi.core.database.entity.NoteEntity

@Database(
    entities = [NoteEntity::class],
    version = 3 ,
    exportSchema = false
)

abstract class NoteDatabase:RoomDatabase() {
    abstract fun noteDao(): NoteDAO
}