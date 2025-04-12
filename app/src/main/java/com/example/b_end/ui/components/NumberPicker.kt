// Путь: ui/components/NumberPicker.kt
package com.example.bunkerapp.ui.components // Замените на ваш пакет

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun NumberPicker(
    value: Int,
    range: IntRange,
    onValueChange: (Int) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = { if (value > range.first) onValueChange(value - 1) },
            enabled = value > range.first
        ) {
            Icon(
                imageVector = Icons.Default.Remove,
                contentDescription = "Уменьшить"
            )
        }

        Text(
            text = "$value",
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        IconButton(
            onClick = { if (value < range.last) onValueChange(value + 1) },
            enabled = value < range.last
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Увеличить"
            )
        }
    }
}