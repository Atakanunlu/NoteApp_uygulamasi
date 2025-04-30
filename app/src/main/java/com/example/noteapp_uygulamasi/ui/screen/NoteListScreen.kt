package com.example.noteapp_uygulamasi.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DoDisturb
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.noteapp_uygulamasi.navigation.AppScreens
import com.example.noteapp_uygulamasi.ui.component.NoteListComponent
import com.example.noteapp_uygulamasi.ui.viewmodel.NoteViewModel
import com.example.noteapp_uygulamasi.widget.DeleteAlertDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteListScreen(
    noteViewModel: NoteViewModel,
    navController: NavController,
    isDarkTheme: Boolean,
    onThemeChange: (Boolean) -> Unit
) {
    val noteList = noteViewModel.noteList.collectAsState().value

    //Tema değiştirme işlemi
    val dropDownMenu = remember { mutableStateOf(false) }

    //Search
    val isSearchState = remember { mutableStateOf(false) }
    var searchNote = remember { mutableStateOf("") }

    //AlertDialog
    val removeAllNotesAlertDialog = remember { mutableStateOf(false) }
    if (removeAllNotesAlertDialog.value) {
        DeleteAlertDialog(
            onDismissRequest = { removeAllNotesAlertDialog.value = false },
            onConfirmation = { noteViewModel.removeAllNotes() },
            dialogTitle = "TÜMÜNÜ SİL",
            dialogText = "Tüm notlar silinecektir. Silinsin mi ?"
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if (isSearchState.value) {
                        TextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = searchNote.value,
                            onValueChange = {
                                searchNote.value = it
                                noteViewModel.searchNote(it)
                            },
                            textStyle = TextStyle(
                                fontSize = 18.sp,
                                fontFamily = FontFamily.Serif
                            ),
                            label = {
                                Text(text = "Bul")
                            },
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                focusedTextColor = MaterialTheme.colorScheme.onPrimary,
                                focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                                unfocusedIndicatorColor = MaterialTheme.colorScheme.primary,
                                unfocusedContainerColor = Color.Transparent,
                                disabledContainerColor = Color.Transparent
                            ),
                            maxLines = 1,
                            singleLine = true
                        )
                    } else {
                        Text(
                            text = AppScreens.NOTE_LIST.topBarName,
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                actions = {
                    if (isSearchState.value) {
                        IconButton(onClick = {
                            isSearchState.value = false
                            noteViewModel.getAllNotes()
                            searchNote.value = ""
                        }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Search ı kapat.",
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    } else {
                        IconButton(onClick = {
                            isSearchState.value = true
                        }) {
                            Icon(
                                imageVector = Icons.Rounded.Search,
                                contentDescription = "Search",
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }
                    IconButton(onClick = {
                        dropDownMenu.value = true
                    }) {
                        Icon(
                            imageVector = Icons.Default.ExpandMore,
                            contentDescription = "ExpandMore",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            )
            DropdownMenu(
                expanded = dropDownMenu.value,
                onDismissRequest = { dropDownMenu.value = false }
            ) {
                DropdownMenuItem(
                    text = {
                        Text(
                            text = "Notları Sil",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    onClick = {
                        if (noteList.isNotEmpty()) {
                            removeAllNotesAlertDialog.value = true
                        }
                    }
                )
                DropdownMenuItem(
                    text = {
                        Text(
                            text = "Tema değiştir",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    onClick = {
                        onThemeChange.invoke(isDarkTheme)
                    }
                )
            }
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    navController.navigate(AppScreens.NOTE_ADD.name)
                },
                contentColor = MaterialTheme.colorScheme.onPrimary,
                containerColor = MaterialTheme.colorScheme.surface,
                text = {
                    Text(text = "Not Ekle")
                },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add"
                    )
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
        ) {
            NoteListComponent(noteList = noteList, noteViewModel = noteViewModel)
        }
    }
}