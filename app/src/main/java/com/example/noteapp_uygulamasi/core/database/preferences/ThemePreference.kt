package com.example.noteapp_uygulamasi.core.database.preferences

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ThemePreference @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val sharedPreferences = context.getSharedPreferences("ThemePreferences", Context.MODE_PRIVATE)

    fun saveThemePreference(isDarkMode: Boolean) {
        sharedPreferences.edit().putBoolean("isDarkMode", isDarkMode).apply()
    }

    fun getThemePreference(): Boolean {
        return sharedPreferences.getBoolean("isDarkMode", false)
    }
}
