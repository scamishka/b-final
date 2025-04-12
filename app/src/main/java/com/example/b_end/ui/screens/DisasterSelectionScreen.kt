package com.example.b_end.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun DisasterSelectionScreen(
    navController: NavController
) {
    // Списки данных
    val disasters = listOf(
        "Супервулканы - Глобальная засуха",
        "Зомби-вирус - Эпидемия",
        "Падение метеорита - Вечная зима",
        "Ядерная война - Радиация",
        "Химическая война - Отравление",
        "Биотерроризм - Мутации",
        "Глобальное наводнение"
    )

    val bunkers = listOf(
        "Бункер 1 (150 м², 90 дней)",
        "Бункер 2 (300 м², 90 дней)",
        "Бункер 3 (80 м², 120 дней)",
        "Бункер 4 (50 м², 90 дней)",
        "Бункер 5 (200 м², 12 мес)"
    )

    // Состояния выбора
    var selectedDisaster by remember { mutableStateOf("") }
    var selectedBunker by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        // Выбор катастрофы
        DropdownSelector(
            label = "Катастрофа",
            items = disasters,
            selectedItem = selectedDisaster,
            onItemSelected = { selectedDisaster = it }
        )

        // Выбор бункера
        DropdownSelector(
            label = "Бункер",
            items = bunkers,
            selectedItem = selectedBunker,
            onItemSelected = { selectedBunker = it }
        )

        // Кнопка продолжения
        Button(
            onClick = {
                navController.navigate("gameScreen") {
                    // Очищаем стек навигации до главного экрана
                    popUpTo("mainScreen") { inclusive = true }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = selectedDisaster.isNotEmpty() && selectedBunker.isNotEmpty(),
            shape = MaterialTheme.shapes.medium
        ) {
            Text("Продолжить", style = MaterialTheme.typography.titleMedium)
        }
    }
}

@Composable
fun DropdownSelector(
    label: String,
    items: List<String>,
    selectedItem: String,
    onItemSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()) {
        // Поле выбора
        OutlinedTextField(
            value = selectedItem,
            onValueChange = {},
            label = { Text(label) },
            modifier = Modifier.fillMaxWidth(),
            readOnly = true,
            trailingIcon = {
                Row {
                    // Кнопка случайного выбора
                    IconButton(onClick = { onItemSelected(items.random()) }) {
                        Icon(
                            imageVector = Icons.Default.Shuffle,
                            contentDescription = "Случайный выбор"
                        )
                    }
                    // Стрелка раскрытия
                    IconButton(onClick = { expanded = !expanded }) {
                        Icon(
                            imageVector = if (expanded) Icons.Default.ArrowDropUp
                            else Icons.Default.ArrowDropDown,
                            contentDescription = if (expanded) "Свернуть" else "Развернуть"
                        )
                    }
                }
            },
            shape = MaterialTheme.shapes.medium,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        )

        // Выпадающее меню
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth(0.95f)
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = item,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    },
                    onClick = {
                        onItemSelected(item)
                        expanded = false
                    }
                )
            }
        }
    }
}