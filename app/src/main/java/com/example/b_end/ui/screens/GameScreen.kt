package com.example.b_end.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun GameScreen(
    navController: NavController,
    disaster: String = "Ядерная война",
    bunker: String = "Бункер 2 (300 м²)",
    roundText: String = "Раунд 1: Распределение профессий"
) {
    var disasterExpanded by remember { mutableStateOf(false) }
    var bunkerExpanded by remember { mutableStateOf(false) }

    val players = listOf(
        Player("Алексей", true),
        Player("Мария", true),
        Player("Иван", false), // Изгнан
        Player("Ольга", true)
    )

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        // Заголовок катастрофы
        ExpandableSection(
            title = disaster,
            content = "После выхода из бункера: радиоактивная пыль окутает планету, закрыв солнечный свет. Остаток населения - 5%",
            expanded = disasterExpanded,
            onExpandChange = { disasterExpanded = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Заголовок бункера
        ExpandableSection(
            title = bunker,
            content = "Площадь: 300 м²\nАвтономность: 90 дней\nОсобенности: медицинский кабинет, химическая лаборатория",
            expanded = bunkerExpanded,
            onExpandChange = { bunkerExpanded = it }
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Текущий раунд
        Text(
            text = roundText,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Список игроков
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(players) { player ->
                PlayerItem(
                    player = player,
                    onClick = { navController.navigate("playerDetail/${player.name}") }
                )
            }
        }
    }
}

@Composable
fun ExpandableSection(
    title: String,
    content: String,
    expanded: Boolean,
    onExpandChange: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable { onExpandChange(!expanded) }
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = if (expanded) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                    contentDescription = if (expanded) "Свернуть" else "Развернуть"
                )
            }

            if (expanded) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = content,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
fun PlayerItem(
    player: Player,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            PlayerStatusIcon(isActive = player.isActive)
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = player.name,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
fun PlayerStatusIcon(isActive: Boolean) {
    val icon: ImageVector
    val tint: Color

    if (isActive) {
        icon = Icons.Default.Check
        tint = Color.Green
    } else {
        icon = Icons.Default.Close
        tint = Color.Red
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(24.dp)
            .background(tint, CircleShape)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = if (isActive) "Активен" else "Изгнан",
            tint = Color.White,
            modifier = Modifier.size(16.dp)
        )
    }
}

data class Player(
    val name: String,
    val isActive: Boolean
)