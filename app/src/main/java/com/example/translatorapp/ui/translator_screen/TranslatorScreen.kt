package com.example.translatorapp.ui.translator_screen

import android.widget.Toast
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.platform.LocalContext

@Composable
fun TranslatorScreen(
    viewModel: TranslatorViewModel = hiltViewModel(),
) {
    val state = viewModel.state.collectAsState().value
    val currentWord =
        state.history.find { it.english.trim().lowercase() == state.inputWord.trim().lowercase() }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                contentAlignment = Alignment.Center
            ) {
                TextField(
                    value = state.inputWord.trim(),
                    onValueChange = { viewModel.onEvent(TranslatorEvent.EnteredWord(it.trim())) },
                    placeholder = {
                        Text(
                            "Введите слово",
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = FontWeight.Normal,
                                textAlign = TextAlign.Center,
                                color = Color.Gray.copy(alpha = 0.5f)
                            ),
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    ),
                    maxLines = 5
                )
            }
        }

        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else {
            IconButton(
                onClick = { viewModel.onEvent(TranslatorEvent.TranslateClicked) },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowDownward,
                    contentDescription = "Перевести"
                )
            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                if (state.translation.isEmpty()) {
                    Text(
                        "Здесь будет перевод",
                        color = Color.Gray.copy(alpha = 0.5f),
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Normal
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.Center)
                    )
                } else {
                    Text(
                        text = state.translation.trim(),
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.Center)
                    )
                }

                IconButton(
                    onClick = {
                        currentWord?.let { viewModel.onEvent(TranslatorEvent.ToggleFavorite(it.id)) }
                    },
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    Icon(
                        imageVector = if (currentWord?.isFavorite == true) Icons.Filled.Favorite else Icons.Outlined.Favorite,
                        contentDescription = "Избранное",
                        tint = if (currentWord?.isFavorite == true) Color.Red else Color.Gray
                    )
                }

            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(state.history) { word ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp)
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onLongPress = {
                                    viewModel.onEvent(TranslatorEvent.DeleteWord(word.id))
                                    Toast.makeText(
                                        context,
                                        "Удалено: ${word.english}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            )
                        },
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = word.english,
                            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = "<->",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )
                        Text(
                            text = word.russian ?: "",
                            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.End
                        )
                    }
                }
            }
        }


    }
}
