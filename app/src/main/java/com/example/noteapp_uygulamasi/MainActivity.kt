package com.example.noteapp_uygulamasi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.noteapp_uygulamasi.navigation.MainNavigation
import com.example.noteapp_uygulamasi.ui.theme.NoteApp_uygulamasiDarkTheme
import com.example.noteapp_uygulamasi.ui.theme.NoteApp_uygulamasiLightTheme
import com.example.noteapp_uygulamasi.ui.viewmodel.NoteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val noteViewModel: NoteViewModel = hiltViewModel()
            val isDarkTheme by noteViewModel.isDarkTheme.collectAsState()

            MainContent(isDarkTheme = isDarkTheme) {
                Surface(modifier = Modifier.fillMaxSize()) {
                    MainNavigation(isDarkTheme = isDarkTheme) {
                        noteViewModel.toggleTheme()
                    }
                }
            }
        }
    }
}

@Composable
fun MainContent(
    isDarkTheme: Boolean,
    onToggleTheme: @Composable () -> Unit
) {
    if (isDarkTheme) {
        NoteApp_uygulamasiDarkTheme {
            onToggleTheme.invoke()
        }
    } else {
        NoteApp_uygulamasiLightTheme {
            onToggleTheme.invoke()
        }
    }
}