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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Карточка ввода
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
                    textStyle = MaterialTheme.typography.headlineSmall.copy( // жирный и крупный текст пользователя
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    ),
                    maxLines = 5
                )

            }
        }


        // Кнопка перевода или лоадер
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

        // Карточка перевода
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
                if (state.translation.isEmpty()) {
                    Text(
                        "Здесь будет перевод",
                        color = Color.Gray.copy(alpha = 0.5f),
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Normal,
                            textAlign = TextAlign.Center
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                } else {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = state.translation,
                            style = MaterialTheme.typography.headlineSmall.copy(
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            ),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                        IconButton(onClick = {
                            viewModel.onEvent(TranslatorEvent.ToggleFavorite(state.inputWord))
                        }) {
                            Icon(
                                imageVector = if (state.isFavorite)
                                    Icons.Filled.Favorite
                                else
                                    Icons.Outlined.Favorite,
                                contentDescription = "Избранное",
                                tint = if (state.isFavorite) Color.Red else Color.Gray
                            )
                        }
                    }
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

