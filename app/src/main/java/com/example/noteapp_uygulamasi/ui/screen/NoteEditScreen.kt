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
import androidx.compose.material.icons.filled.FileUpload
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
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
import coil.compose.AsyncImage
import com.example.noteapp_uygulamasi.core.database.entity.NoteEntity
import com.example.noteapp_uygulamasi.ui.viewmodel.NoteViewModel
import com.example.noteapp_uygulamasi.widget.NoteInputText
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteEditScreen(
    note: NoteEntity,
    noteViewModel: NoteViewModel,
    onDismissBottomSheet: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    ModalBottomSheet(
        onDismissRequest = { onDismissBottomSheet() },
        sheetState = sheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() }
    ) {
        EditNoteContent(
            noteEntity = note,
            onNoteUpdate = {
                noteViewModel.updateNote(it)
                onDismissBottomSheet()
            }
        )
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun EditNoteContent(
    noteEntity: NoteEntity,
    onNoteUpdate: (NoteEntity) -> Unit
) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    var newNoteTitle by remember { mutableStateOf(noteEntity.noteTitle) }
    var newNoteSubtitle by remember { mutableStateOf(noteEntity.noteSubtitle) }
    var newNoteDescription by remember { mutableStateOf(noteEntity.noteDescription) }
    var newNoteImageUri by remember { mutableStateOf(noteEntity.noteImage) }
    var isPinned by remember { mutableStateOf(noteEntity.isPinned) }
    var notePriority by remember { mutableStateOf(noteEntity.notePriority) }
    var noteCategory by remember { mutableStateOf(noteEntity.noteCategory) }

    var showCategoryDropdown by remember { mutableStateOf(false) }
    var showPriorityDropdown by remember { mutableStateOf(false) }

    val categories = listOf("Genel", "İş", "Kişisel", "Eğitim", "Alışveriş", "Diğer")

    // İzin yönetimi
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
                newNoteImageUri = data.data.toString()
            }
        }
    }

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 10.dp, vertical = 5.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        // Başlık satırı
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Not Düzenle",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Sabitle")
                Checkbox(
                    checked = isPinned,
                    onCheckedChange = { isPinned = it }
                )
            }
        }

        // Kategori ve öncelik seçim satırı
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Kategori seçimi
            Column {
                Text("Kategori", style = MaterialTheme.typography.labelMedium)
                Row(
                    modifier = Modifier
                        .clickable { showCategoryDropdown = true }
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(noteCategory)

                    DropdownMenu(
                        expanded = showCategoryDropdown,
                        onDismissRequest = { showCategoryDropdown = false }
                    ) {
                        categories.forEach { category ->
                            DropdownMenuItem(
                                text = { Text(category) },
                                onClick = {
                                    noteCategory = category
                                    showCategoryDropdown = false
                                }
                            )
                        }
                    }
                }
            }

            // Öncelik seçimi
            Column {
                Text("Öncelik", style = MaterialTheme.typography.labelMedium)
                Row(
                    modifier = Modifier
                        .clickable { showPriorityDropdown = true }
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        when (notePriority) {
                            0 -> "Düşük"
                            1 -> "Orta"
                            else -> "Yüksek"
                        }
                    )

                    DropdownMenu(
                        expanded = showPriorityDropdown,
                        onDismissRequest = { showPriorityDropdown = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Düşük") },
                            onClick = {
                                notePriority = 0
                                showPriorityDropdown = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Orta") },
                            onClick = {
                                notePriority = 1
                                showPriorityDropdown = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Yüksek") },
                            onClick = {
                                notePriority = 2
                                showPriorityDropdown = false
                            }
                        )
                    }
                }
            }
        }

        NoteInputText(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp, horizontal = 10.dp),
            text = newNoteTitle,
            label = "Başlık",
            onTextChange = {
                if (it.all { char -> char.isLetter() || char.isWhitespace() } && it.length <= 15) {
                    newNoteTitle = it
                }
                if (it.length > 15) {
                    focusManager.moveFocus(FocusDirection.Down)
                }
            },
            maxChar = 15
        ) {}

        NoteInputText(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 5.dp),
            text = newNoteSubtitle,
            label = "Alt Başlık",
            onTextChange = {
                if (it.all { char -> char.isLetter() || char.isWhitespace() } && it.length <= 50) {
                    newNoteSubtitle = it
                }
                if (it.length > 50) {
                    focusManager.moveFocus(FocusDirection.Down)
                }
            },
            maxChar = 50
        ) {}

        NoteInputText(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .padding(vertical = 5.dp, horizontal = 10.dp),
            text = newNoteDescription,
            label = "Açıklama",
            maxLine = 20,
            onTextChange = {
                if (it.length <= 500) {
                    newNoteDescription = it
                }
            },
            maxChar = 500
        ) {}

        if (newNoteImageUri == null) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 5.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.padding(5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Image,
                        contentDescription = "Resim",
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = "Resim Ekle",
                        modifier = Modifier.padding(horizontal = 10.dp),
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
                IconButton(onClick = {
                    if (hasMediaPermission) {
                        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                        galleryLauncher.launch(intent)
                    } else
                        mediaPermissionState.launchMultiplePermissionRequest()
                }) {
                    Icon(
                        imageVector = Icons.Default.FileUpload,
                        contentDescription = "Yükle",
                        tint = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.surface)
                            .clip(CircleShape)
                            .padding(8.dp)
                    )
                }
            }
        } else {
            AsyncImage(
                model = Uri.parse(newNoteImageUri!!),
                contentDescription = "Not Resmi",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp)
                    .clickable {
                        newNoteImageUri = null
                    }
            )
        }

        IconButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .height(60.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary)
                .align(Alignment.End),
            onClick = {
                if (newNoteTitle.isNotEmpty() && newNoteSubtitle.isNotEmpty() && newNoteDescription.isNotEmpty()) {
                    val updatedNote = NoteEntity(
                        noteID = noteEntity.noteID,
                        noteSubtitle = newNoteSubtitle,
                        noteTitle = newNoteTitle,
                        noteDescription = newNoteDescription,
                        noteImage = newNoteImageUri,
                        noteDateEntry = noteEntity.noteDateEntry,
                        noteCategory = noteCategory,
                        notePriority = notePriority,
                        isPinned = isPinned
                    )
                    onNoteUpdate(updatedNote)
                    Toast.makeText(context, "Not Güncellendi", Toast.LENGTH_LONG).show()
                }
            }
        ) {
            Icon(
                imageVector = Icons.Default.Save,
                contentDescription = "Kaydet",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}