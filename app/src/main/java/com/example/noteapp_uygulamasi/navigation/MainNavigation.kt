package com.example.noteapp_uygulamasi.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.noteapp_uygulamasi.ui.screen.NoteAddScreen
import com.example.noteapp_uygulamasi.ui.screen.NoteListScreen
import com.example.noteapp_uygulamasi.ui.viewmodel.NoteViewModel

@Composable
fun MainNavigation(
    isDarkTheme: Boolean,
    onThemeChange: (Boolean) -> Unit
) {
    val controller = rememberNavController()
    val noteViewModel: NoteViewModel = hiltViewModel()

    NavHost(navController = controller, startDestination = AppScreens.NOTE_LIST.name) {
        composable(AppScreens.NOTE_LIST.name) {
            NoteListScreen(
                noteViewModel = noteViewModel,
                navController = controller,
                isDarkTheme = isDarkTheme,
                onThemeChange = onThemeChange
            )
        }
        composable(
            AppScreens.NOTE_ADD.name
        ) {
            NoteAddScreen(
                noteViewModel = noteViewModel,
                navController = controller
            )
        }
    }
}