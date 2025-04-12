package com.example.b_end.ui.screens

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.b_end.utils.Resources

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DisasterSelectionScreen(
    context: Context = LocalContext.current,
    onStartGame: (String, String) -> Unit
) {
    // Списки данных
    val disasters = remember { Resources.getDisasters(context) }
    val bunkers = remember { Resources.getBunkers(context) }

    // Состояния выбора
    var disasterExpanded by remember { mutableStateOf(false) }
    var bunkerExpanded by remember { mutableStateOf(false) }
    var selectedDisaster by remember { mutableStateOf("") }
    var selectedBunker by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }

    if (disasters.isEmpty() || bunkers.isEmpty()) {
        LaunchedEffect(Unit) {
            showError = true
        }
    }

    if (showError) {
        AlertDialog(
            onDismissRequest = { showError = false },
            title = { Text("Ошибка загрузки") },
            text = {
                Text(
                    when {
                        disasters.isEmpty() && bunkers.isEmpty() -> "Не загружены катастрофы и бункеры"
                        disasters.isEmpty() -> "Не загружены катастрофы"
                        else -> "Не загружены бункеры"
                    }
                )
            },
            confirmButton = {
                Button(onClick = { showError = false }) {
                    Text("OK")
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Выбор катастрофы
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            ExposedDropdownMenuBox(
                modifier = Modifier.weight(1f),
                expanded = disasterExpanded,
                onExpandedChange = { disasterExpanded = it }
            ) {
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    readOnly = true,
                    value = selectedDisaster.split("\n").firstOrNull() ?: "",
                    onValueChange = {},
                    label = { Text("Катастрофа") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = disasterExpanded)
                    }
                )

                ExposedDropdownMenu(
                    expanded = disasterExpanded,
                    onDismissRequest = { disasterExpanded = false }
                ) {
                    disasters.forEach { disaster ->
                        DropdownMenuItem(
                            text = { Text(disaster.split("\n").firstOrNull() ?: "") },
                            onClick = {
                                selectedDisaster = disaster
                                disasterExpanded = false
                            }
                        )
                    }
                }
            }

            IconButton(
                onClick = {
                    selectedDisaster = disasters.random()
                },
                modifier = Modifier.size(48.dp)
            ) {
                Icon(Icons.Default.Shuffle, "Случайная катастрофа")
            }
        }

        // Выбор бункера (аналогично)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            ExposedDropdownMenuBox(
                modifier = Modifier.weight(1f),
                expanded = bunkerExpanded,
                onExpandedChange = { bunkerExpanded = it }
            ) {
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    readOnly = true,
                    value = selectedBunker.split("\n").firstOrNull() ?: "",
                    onValueChange = {},
                    label = { Text("Бункер") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = bunkerExpanded)
                    }
                )

                ExposedDropdownMenu(
                    expanded = bunkerExpanded,
                    onDismissRequest = { bunkerExpanded = false }
                ) {
                    bunkers.forEach { bunker ->
                        DropdownMenuItem(
                            text = { Text(bunker.split("\n").firstOrNull() ?: "") },
                            onClick = {
                                selectedBunker = bunker
                                bunkerExpanded = false
                            }
                        )
                    }
                }
            }

            IconButton(
                onClick = {
                    selectedBunker = bunkers.random()
                },
                modifier = Modifier.size(48.dp)
            ) {
                Icon(Icons.Default.Shuffle, "Случайный бункер")
            }
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
