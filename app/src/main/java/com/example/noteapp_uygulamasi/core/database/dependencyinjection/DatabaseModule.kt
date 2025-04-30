package com.example.noteapp_uygulamasi.core.database.dependencyinjection

import android.content.Context
import androidx.room.Room
import com.example.noteapp_uygulamasi.core.NoteDatabase
import com.example.noteapp_uygulamasi.core.database.dao.NoteDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Singleton
    @Provides
    fun provideRoomDatabase(
        @ApplicationContext context: Context
    ): NoteDatabase {
        return Room.databaseBuilder(
            context,
            NoteDatabase::class.java,
            "notes_db"
        )
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    @Singleton
    @Provides
    fun provideNoteDAO(noteDatabase: NoteDatabase): NoteDAO {
        return noteDatabase.noteDao()
    }
}
