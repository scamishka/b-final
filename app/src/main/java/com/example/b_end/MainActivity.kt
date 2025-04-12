package com.example.b_end

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.b_end.ui.main.MainScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.b_end.ui.screens.DisasterSelectionScreen
import com.example.b_end.ui.screens.GameScreen
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.navArgument
import java.net.URLEncoder
import java.net.URLDecoder

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
    val context = LocalContext.current
    var showError by remember { mutableStateOf(false) }

    if (showError) {
        AlertDialog(
            onDismissRequest = { showError = false },
            title = { Text("Ошибка") },
            text = { Text("Не удалось загрузить данные игры") },
            confirmButton = {
                Button(onClick = { showError = false }) {
                    Text("OK")
                }
            }
        )
    }

    NavHost(
        navController = navController,
        startDestination = "mainScreen"
    ) {
        composable("mainScreen") {
            MainScreen(
                onStartGame = { navController.navigate("disasterSelection") }
            )
        }

        composable("disasterSelection") {
            DisasterSelectionScreen(
                context = context,
                onStartGame = { disaster, bunker ->
                    if (disaster.isNotBlank() && bunker.isNotBlank()) {
                        navController.navigate("gameScreen?disaster=${disaster.toEncodedUrl()}&bunker=${bunker.toEncodedUrl()}") {
                            popUpTo("mainScreen") { inclusive = true }
                        }
                    } else {
                        showError = true
                    }
                }
            )
        }

        composable(
            "gameScreen?disaster={disaster}&bunker={bunker}",
            arguments = listOf(
                navArgument("disaster") {
                    type = NavType.StringType
                    defaultValue = ""
                },
                navArgument("bunker") {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )
        ) { backStackEntry ->
            val disaster = backStackEntry.arguments?.getString("disaster").orEmpty()
            val bunker = backStackEntry.arguments?.getString("bunker").orEmpty()

            if (disaster.isNotBlank() && bunker.isNotBlank()) {
                GameScreen(
                    navController = navController,
                    selectedDisaster = disaster,
                    selectedBunker = bunker,
                    context = context
                )
            } else {
                LaunchedEffect(Unit) {
                    navController.popBackStack("disasterSelection", false)
                    showError = true
                }
            }
        }
    }
}

// Функция расширения для безопасного кодирования URL
fun String.toEncodedUrl(): String {
    return try {
        URLEncoder.encode(this, "UTF-8")
    } catch (e: Exception) {
        this
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