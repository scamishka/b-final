package com.example.b_end.ui.main

import GameSettings
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MainScreen(
    onStartGame: (GameSettings) -> Unit // Переход на экран 2
) {
    var ageGroup by remember { mutableStateOf("Для взрослых") }
    var playerCount by remember { mutableStateOf(4) }
    var includeCharacter by remember { mutableStateOf(true) }
    var includeHobbies by remember { mutableStateOf(true) }
    var expanded by remember { mutableStateOf(false) }
    val playerCountOptions = listOf(4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    var selectedOption by remember { mutableStateOf(playerCountOptions[0]) }

    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 1. Версия игры (неизменяемое поле)
        Text(
            text = "Бункер: первое убежище",
            style = MaterialTheme.typography.titleMedium
        )

        // 2. Возраст
        Row {
            Text("Возраст:", modifier = Modifier.weight(1f))
            RadioGroup(ageGroup, listOf("Для взрослых", "С детьми")) {
                ageGroup = it
            }
        }



        // 4. Набор карточек
        Text("Карточки:")
        CheckboxWithLabel("Характер", includeCharacter) {
            includeCharacter = it
        }
        CheckboxWithLabel("Хобби", includeHobbies) {
            includeHobbies = it
        }

        Column {
            Text("Количество игроков:", style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(4.dp))
            ) {
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    playerCountOptions.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option.toString()) },
                            onClick = {
                                selectedOption = option
                                playerCount = option
                                expanded = false
                            }
                        )
                    }
                }
                Text(
                    text = selectedOption.toString(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { expanded = true }
                        .padding(16.dp)
                )
            }
        }

        // Кнопка
        Button(
            onClick = {
                onStartGame(
                    GameSettings(
                        ageGroup = ageGroup,
                        playerCount = playerCount,
                        useCharacterCards = includeCharacter,
                        useHobbyCards = includeHobbies
                    )
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Начать игру")
        }
    }
}

// Вспомогательные компоненты
@Composable
fun RadioGroup(selected: String, options: List<String>, onSelect: (String) -> Unit) {
    Row {
        options.forEach { option ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = option == selected,
                    onClick = { onSelect(option) }
                )
                Text(option)
            }
        }
    }
}

@Composable
fun CheckboxWithLabel(text: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
        Text(text)
    }
}