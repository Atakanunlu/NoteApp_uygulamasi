package com.example.noteapp_uygulamasi.ui.screen

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.READ_MEDIA_IMAGES
import android.Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.NoteAdd
import androidx.compose.material.icons.filled.DoDisturb
import androidx.compose.material.icons.filled.FileUpload
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.noteapp_uygulamasi.core.database.entity.NoteEntity
import com.example.noteapp_uygulamasi.navigation.AppScreens
import com.example.noteapp_uygulamasi.ui.viewmodel.NoteViewModel
import com.example.noteapp_uygulamasi.widget.DeleteAlertDialog
import com.example.noteapp_uygulamasi.widget.NoteInputText
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun NoteAddScreen(
    noteViewModel: NoteViewModel,
    navController: NavController
) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    var noteTitle by remember { mutableStateOf("") }
    var noteSubtitle by remember { mutableStateOf("") }
    var noteDescription by remember { mutableStateOf("") }
    var noteImageUri by remember { mutableStateOf<String?>(null) }

    var showImageAlertDialog by remember { mutableStateOf(false) }
    if (showImageAlertDialog){
        DeleteAlertDialog(
            onDismissRequest = {showImageAlertDialog = false},
            onConfirmation = {noteImageUri = null},
            dialogTitle = "SIL",
            dialogText = "Görsel silinsin mi?",
        )
    }

    // Permission handling
    val mediaPermissionState = rememberMultiplePermissionsState(
        permissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            listOf(READ_MEDIA_IMAGES, READ_MEDIA_VISUAL_USER_SELECTED)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            listOf(READ_MEDIA_IMAGES)
        } else listOf(WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE)
    )

    val hasMediaPermission = mediaPermissionState.allPermissionsGranted

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {
            val data: Intent? = it.data
            if (data != null) {
                noteImageUri = data.data.toString()
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = AppScreens.NOTE_ADD.topBarName,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        if (noteTitle.isNotEmpty() && noteDescription.isNotEmpty() && noteSubtitle.isNotEmpty()){
                            noteViewModel.addNote(
                                NoteEntity(
                                    noteTitle = noteTitle,
                                    noteDescription = noteDescription,
                                    noteImage = noteImageUri,
                                    noteSubtitle = noteSubtitle
                                )
                            )
                            noteDescription = ""
                            noteImageUri = null
                            noteTitle = ""
                            noteSubtitle = ""
                            Toast.makeText(context,"Note Eklenmiştir",Toast.LENGTH_LONG).show()
                            navController.popBackStack()
                        }
                    }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.NoteAdd,
                            contentDescription = "AddNote Save",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            NoteInputText(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp, horizontal = 10.dp),
                text = noteTitle,
                label = "Başlık",
                onTextChange = {
                    if (it.all { char -> char.isLetter() || char.isWhitespace() } && it.length <= 15){
                        noteTitle = it
                    }
                    if (it.length > 15){
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                },
                maxChar = 15
            ){}
            NoteInputText(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 5.dp),
                text = noteSubtitle,
                label = "Alt başlık",
                onTextChange = {
                    if (it.all { char -> char.isLetter() } && it.length <= 50){
                        noteSubtitle = it
                    }
                    if (it.length > 50){
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                },
                maxChar = 50
            ){}
            NoteInputText(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
                    .padding(vertical = 5.dp, horizontal = 10.dp),
                text = noteDescription,
                label = "Not",
                maxLine = 20,
                onTextChange = {

                    if (it.length <= 500) {
                        noteDescription = it
                    }
                },
                maxChar = 500
            ) {}

            if (noteImageUri == null) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = 10.dp, vertical = 5.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Row(
                        modifier = Modifier.padding(5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Icon(
                            imageVector = Icons.Default.Image,
                            contentDescription = "Image",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                        Text(
                            text = "./root.noteimage.jpg",
                            modifier = Modifier.padding(horizontal = 10.dp),
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                    IconButton(onClick = {
                        if (hasMediaPermission){
                            val intent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                            galleryLauncher.launch(intent)
                        } else
                            mediaPermissionState.launchMultiplePermissionRequest()
                    }) {
                        Icon(
                            imageVector = Icons.Default.FileUpload,
                            contentDescription = "Update",
                            tint = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.background(MaterialTheme.colorScheme.surface)
                                .clip(CircleShape)
                                .padding(8.dp)
                        )
                    }
                }
            } else {
                AsyncImage(model = Uri.parse(noteImageUri!!),
                    contentDescription = "upload image",
                    modifier = Modifier.fillMaxSize()
                        .padding(10.dp)
                        .clickable {
                            showImageAlertDialog = true
                        }
                )
            }
        }
    }
}
