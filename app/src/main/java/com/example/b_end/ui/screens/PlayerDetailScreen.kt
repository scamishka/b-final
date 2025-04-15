package com.example.b_end.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerDetailScreen(
    navController: NavController,
    playerName: String,
    profession: String,
    biology: String,
    health: String,
    personality: String,
    luggage: String,
    fact: String,
    isActive: Boolean
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(playerName) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, "Назад")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            PlayerStatusIcon(isActive = isActive)

            DetailItem("Профессия", profession)
            DetailItem("Биология", biology)
            DetailItem("Здоровье", health)
            DetailItem("Характер", personality)
            DetailItem("Багаж", luggage)
            DetailItem("Факт", fact)
        }
    }
}

@Composable
fun DetailItem(label: String, value: String) {
    Column {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
        Text(
            text = value.ifEmpty { "Не указано" },
            style = MaterialTheme.typography.bodyMedium
        )
        Divider(modifier = Modifier.padding(vertical = 8.dp))
    }
}