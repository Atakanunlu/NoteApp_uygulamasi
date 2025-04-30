package com.example.noteapp_uygulamasi.ui.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapp_uygulamasi.core.database.entity.NoteEntity
import com.example.noteapp_uygulamasi.core.database.preferences.ThemePreference
import com.example.noteapp_uygulamasi.core.database.repository.NoteRepository

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val noteRepository: NoteRepository,
    private val themePreference: ThemePreference
): ViewModel() {
    private val _noteList = MutableStateFlow<List<NoteEntity>>(emptyList())
    val noteList = _noteList.asStateFlow()

    private val _isDarkTheme = MutableStateFlow(themePreference.getThemePreference())
    val isDarkTheme: StateFlow<Boolean> = _isDarkTheme



    init {
        getAllNotes()
    }

    fun toggleTheme() {
        val newTheme = !_isDarkTheme.value
        _isDarkTheme.value = newTheme
        themePreference.saveThemePreference(newTheme)
    }

    fun addNote(noteEntity: NoteEntity) {
        viewModelScope.launch {
            noteRepository.addNote(noteEntity)
        }
    }

    fun updateNote(noteEntity: NoteEntity) {
        viewModelScope.launch {
            noteRepository.updateNote(noteEntity)
        }
    }

    fun removeNote(noteEntity: NoteEntity) {
        viewModelScope.launch {
            noteRepository.deleteNote(noteEntity)
        }
    }

    fun getAllNotes() {
        viewModelScope.launch(Dispatchers.IO) {
            noteRepository.getAllNotes()
                .distinctUntilChanged()
                .collect { listNote ->
                    _noteList.value = listNote
                }
        }
    }

    fun searchNote(searchNote: String) {
        viewModelScope.launch {
            _noteList.value = noteRepository.searchNote(searchNote)
        }
    }

    fun removeAllNotes() {
        viewModelScope.launch {
            noteRepository.deleteAllNotes()
        }
    }

}


