package com.example.translatorapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.translatorapp.ui.theme.TranslatorAppTheme
import com.example.translatorapp.ui.translator_screen.TranslatorScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TranslatorApp()
        }

    }
}
@Composable
fun TranslatorApp() {
    val navController = rememberNavController()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate("favorites")
            }) {
                Icon(Icons.Default.Favorite, contentDescription = "Избранное")
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = "translator",
            modifier = Modifier.padding(padding)
        ) {
            composable("translator") { TranslatorScreen() }
            //composable("favorites") { FavoritesScreen(navController) }
        }
    }
}

