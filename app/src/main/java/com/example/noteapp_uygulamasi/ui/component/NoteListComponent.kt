package com.example.noteapp_uygulamasi.ui.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.noteapp_uygulamasi.core.database.entity.NoteEntity
import com.example.noteapp_uygulamasi.ui.viewmodel.NoteViewModel

@Composable
fun NoteListComponent(
    noteList: List<NoteEntity>,
    noteViewModel: NoteViewModel
) {
    if (noteList.isEmpty()) {
        Text(
            text = "Henüz not bulunmuyor",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 120.dp),
            textAlign = TextAlign.Center
        )
    } else {
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            contentPadding = PaddingValues(8.dp),
            reverseLayout = false
        ) {
            items(
                items = noteList,
                key = { it.noteID }
            ) { note ->
                NoteCard(
                    note = note,
                    noteViewModel = noteViewModel
                )
            }
        }
    }
}