package com.example.noteapp_uygulamasi.ui.component

import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material3.Badge
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.noteapp_uygulamasi.core.database.entity.NoteEntity
import com.example.noteapp_uygulamasi.ui.screen.NoteEditScreen


import com.example.noteapp_uygulamasi.ui.viewmodel.NoteViewModel
import com.example.noteapp_uygulamasi.widget.DeleteConfirmDialog


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteCard(
    modifier: Modifier = Modifier,
    note: NoteEntity,
    noteViewModel: NoteViewModel
) {
    var showDeleteDialog by remember { mutableStateOf(false) }
    if (showDeleteDialog){
        DeleteConfirmDialog(
            onDismiss = {showDeleteDialog = false},
            onConfirm = {noteViewModel.removeNote(note)},
            title = "SİL",
            message = "${note.noteTitle} silinecektir. Emin misiniz?",
        )
    }

    var showEditSheet = remember { mutableStateOf(false) }
    if (showEditSheet.value){
        // NoteUpdateScreen yerine NoteEditScreen kullanımı
        NoteEditScreen(
            note = note,
            noteViewModel = noteViewModel
        ) {
            showEditSheet.value = false
        }
    }


    Card(
        modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp))
            .fillMaxWidth()
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(16.dp))
            .combinedClickable(
                onClick = {},
                onDoubleClick = { showEditSheet.value = true }
            ),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f)),
    ) {
        // Kart başlığı ve tarih
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = note.noteDateEntry,
                style = MaterialTheme.typography.labelSmall,
                fontStyle = FontStyle.Italic,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )

            // Kategori göstergesi
            Badge(
                containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Text(note.noteCategory, modifier = Modifier.padding(horizontal = 4.dp))
            }
        }

        // Not içeriği
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Başlık
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = note.noteTitle,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f)
                )

                if (note.isPinned) {
                    Icon(
                        imageVector = Icons.Default.PushPin,
                        contentDescription = "Sabitlenmiş",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            // Alt başlık
            Text(
                text = note.noteSubtitle,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
            )

            // Açıklama önizlemesi (ilk 100 karakter)
            if (note.noteDescription.isNotEmpty()) {
                Text(
                    text = note.noteDescription.take(100) + if (note.noteDescription.length > 100) "..." else "",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }

            // Görsel varsa önizleme
            note.noteImage?.let {
                AsyncImage(
                    model = Uri.parse(it),
                    contentDescription = "Not Resmi",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            }
        }

        // İşlem butonları
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(
                onClick = { showEditSheet.value = true }
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Düzenle",
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            IconButton(
                onClick = { showDeleteDialog = true }
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Sil",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}