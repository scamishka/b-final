package com.example.b_end

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.b_end.ui.main.MainScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.b_end.ui.screens.DisasterSelectionScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BunkerAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation()
                }
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "mainScreen"
    ) {
        // Главный экран
        composable("mainScreen") {
            MainScreen(
                onStartGame = {
                    navController.navigate("disasterSelection")
                }
            )
        }

        // Экран выбора катастрофы
        composable("disasterSelection") {
            DisasterSelectionScreen(navController = navController)
        }
    }
}

@Composable
fun BunkerAppTheme(content: @Composable () -> Unit) {
    MaterialTheme(content = content)
}

@Preview(showBackground = true)
@Composable
fun PreviewApp() {
    BunkerAppTheme {
        AppNavigation()
    }
}