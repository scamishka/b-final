package com.example.b_end.ui.screens

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.b_end.utils.Resources

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DisasterSelectionScreen(
    context: Context = LocalContext.current,
    onStartGame: (String, String) -> Unit
) {
    // Списки данных
    val disasters = remember {
        Resources.getDisasters(context).ifEmpty {
            listOf(
                "Ядерная зима\nГлобальное похолодание после ядерной войны",
                "Пандемия\nСмертельный вирус уничтожает человечество"
            ).also { println("Using default disasters") }
        }.also { println("Loaded ${it.size} disasters") }
    }
    val bunkers = remember { Resources.getBunkers(context) }

    // Состояния выбора
    var showDisasterMenu by remember { mutableStateOf(false) }
    var showBunkerMenu by remember { mutableStateOf(false) }
    var selectedDisaster by remember { mutableStateOf("") }
    var selectedBunker by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }

    // Отладочный вывод
    LaunchedEffect(disasters, bunkers) {
        println("Disasters loaded (${disasters.size}):")
        disasters.take(3).forEachIndexed { i, d ->
            println("[$i] ${d.replace("\n", "\\n")}")
        }
        println("Bunkers loaded (${bunkers.size})")
    }

    if (disasters.isEmpty() || bunkers.isEmpty()) {
        LaunchedEffect(Unit) {
            showError = true
        }
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Выбор катастрофы - новая реализация
        Box(modifier = Modifier.fillMaxWidth()) {
            // Кнопка для открытия меню
            OutlinedButton(
                onClick = { showDisasterMenu = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = selectedDisaster.split("\n").firstOrNull()?.takeIf { it.isNotBlank() }
                        ?: "Выберите катастрофу",
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Start
                )
                Icon(
                    imageVector = if (showDisasterMenu) Icons.Default.ArrowDropUp
                    else Icons.Default.ArrowDropDown,
                    contentDescription = null
                )
            }

            // Выпадающее меню
            DropdownMenu(
                expanded = showDisasterMenu,
                onDismissRequest = { showDisasterMenu = false },
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .heightIn(max = 400.dp)
            ) {
                disasters.forEach { disaster ->
                    val title = disaster.split("\n").firstOrNull() ?: ""
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = title,
                                modifier = Modifier.fillMaxWidth(),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        },
                        onClick = {
                            selectedDisaster = disaster
                            showDisasterMenu = false
                            println("Selected: $title")
                        }
                    )
                }
            }
        }

        // 4. Кнопка случайного выбора
        Button(
            onClick = {
                selectedDisaster = disasters.random()
                println("Random selected: ${selectedDisaster.split("\n").firstOrNull()}")
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            )
        ) {
            Icon(Icons.Default.Shuffle, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Случайная катастрофа")
        }

        // выбор бункера
        Box(modifier = Modifier.fillMaxWidth()) {
            // Кнопка для открытия меню
            OutlinedButton(
                onClick = { showBunkerMenu = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = selectedBunker.split("\n").firstOrNull()?.takeIf { it.isNotBlank() }
                        ?: "Выберите бункер",
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Start
                )
                Icon(
                    imageVector = if (showBunkerMenu) Icons.Default.ArrowDropUp
                    else Icons.Default.ArrowDropDown,
                    contentDescription = null
                )
            }

            // Выпадающее меню
            DropdownMenu(
                expanded = showBunkerMenu,
                onDismissRequest = { showBunkerMenu = false },
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .heightIn(max = 400.dp)
            ) {
                bunkers.forEach { bunker ->
                    val title = bunker.split("\n").firstOrNull() ?: ""
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = title,
                                modifier = Modifier.fillMaxWidth(),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        },
                        onClick = {
                            selectedBunker = bunker
                            showBunkerMenu = false
                            println("Selected: $title")
                        }
                    )
                }
            }
        }

        // 4. Кнопка случайного выбора
        Button(
            onClick = {
                selectedBunker = bunkers.random()
                println("Random selected: ${selectedBunker.split("\n").firstOrNull()}")
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            )
        ) {
            Icon(Icons.Default.Shuffle, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Случайный бункер")
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { onStartGame(selectedDisaster, selectedBunker) },
            modifier = Modifier.fillMaxWidth(),
            enabled = selectedDisaster.isNotBlank() && selectedBunker.isNotBlank()
        ) {
            Text("Продолжить")
        }
    }
}
