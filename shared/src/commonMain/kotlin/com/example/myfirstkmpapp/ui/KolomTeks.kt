package com.example.myfirstkmpapp.ui

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Komponen input teks tanpa state (stateless) yang reusable.
 * Dikerjakan oleh Nadia Anatashiva (123140060) sesuai Modul 4 Langkah 5 & 6.
 *
 * Menerapkan pola State Hoisting murni:
 * - State turun (down): Menerima nilai `value` dari pemanggil.
 * - Event naik (up): Melaporkan perubahan teks melalui lambda callback `onValueChange`.
 */
@Composable
fun KolomTeks(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    singleLine: Boolean = true,
    isError: Boolean = false,
    pesanError: String? = null
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        placeholder = placeholder?.let { { Text(it) } },
        singleLine = singleLine,
        isError = isError || pesanError != null,
        supportingText = pesanError?.let {
            { Text(it, color = MaterialTheme.colorScheme.error) }
        },
        shape = RoundedCornerShape(12.dp),
        modifier = modifier
    )
}
