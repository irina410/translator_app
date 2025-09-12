package com.example.translatorapp.ui.translator_screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

@Composable
fun TranslatorScreen(
    viewModel: TranslatorViewModel = hiltViewModel(),
) {
    val state = viewModel.state.collectAsState().value
    val currentWord = state.history.find { it.english.lowercase() == state.inputWord.lowercase() }

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
                    .height(120.dp)
                    .padding(8.dp)
            ) {
                TextField(
                    value = state.inputWord,
                    onValueChange = { viewModel.onEvent(TranslatorEvent.EnteredWord(it)) },
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
                // Текст по центру
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
                        text = state.translation,
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.Center)
                    )
                }

            }
        }
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(state.history) { word ->
                Text(
                    text = "${word.english} → ${word.russian}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

    }

}

