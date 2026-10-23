package com.example.myfirstkmpapp.ui

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.myfirstkmpapp.model.MataKuliah
import com.example.myfirstkmpapp.model.SesiBelajar
import com.example.myfirstkmpapp.model.StatusSesiBelajar

/**
 * Dialog formulir interaktif untuk menambahkan sesi belajar baru.
 * Dikerjakan oleh Nadia Anatashiva (123140060) sebagai pemenuhan aksi utama penambahan data (FAB).
 */
@Composable
fun DialogTambahSesi(
    daftarMataKuliah: List<MataKuliah>,
    onDismiss: () -> Unit,
    onTambahSesi: (SesiBelajar) -> Unit
) {
    var judul by rememberSaveable { mutableStateOf("") }
    var indeksMatkul by rememberSaveable { mutableIntStateOf(0) }
    var waktu by rememberSaveable { mutableStateOf("2026-10-25T14:00") }
    var tempatAtauLink by rememberSaveable { mutableStateOf("Lab Komputer 3") }
    var isOnline by rememberSaveable { mutableStateOf(false) }
    var pesanError by rememberSaveable { mutableStateOf<String?>(null) }

    val matkulTerpilih = daftarMataKuliah.getOrElse(indeksMatkul) {
        MataKuliah("IF25-22017", "Pengembangan Aplikasi Mobile", 3)
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                "Buat Sesi Belajar Baru",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    "Pilih mata kuliah dan isi rincian jadwal diskusi kelompok.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                // Input Judul Sesi
                OutlinedTextField(
                    value = judul,
                    onValueChange = {
                        judul = it
                        if (it.isNotBlank()) pesanError = null
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Topik / Judul Sesi *") },
                    placeholder = { Text("Contoh: Pembahasan LazyColumn") },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    isError = pesanError != null
                )

                if (pesanError != null) {
                    Text(
                        pesanError ?: "",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                // Pilihan Mata Kuliah
                Text(
                    "Mata Kuliah:",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    daftarMataKuliah.forEachIndexed { indeks, matkul ->
                        FilterChip(
                            selected = indeksMatkul == indeks,
                            onClick = { indeksMatkul = indeks },
                            label = { Text(matkul.kode) }
                        )
                    }
                }
                Text(
                    "${matkulTerpilih.nama} (${matkulTerpilih.sks} SKS)",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )

                // Input Jadwal / Waktu
                OutlinedTextField(
                    value = waktu,
                    onValueChange = { waktu = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Jadwal (Format ISO yyyy-MM-dd'T'HH:mm)") },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp)
                )

                // Toggle Moda Pertemuan (Online / Offline)
                Text(
                    "Moda Pertemuan:",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    FilterChip(
                        selected = !isOnline,
                        onClick = {
                            isOnline = false
                            if (tempatAtauLink.startsWith("http")) tempatAtauLink = "Lab Komputer 3"
                        },
                        label = { Text("Tatap Muka") }
                    )
                    FilterChip(
                        selected = isOnline,
                        onClick = {
                            isOnline = true
                            if (!tempatAtauLink.startsWith("http")) tempatAtauLink = "https://meet.google.com/xyz-belajar"
                        },
                        label = { Text("Daring (Online)") }
                    )
                }

                // Input Ruang atau Tautan
                OutlinedTextField(
                    value = tempatAtauLink,
                    onValueChange = { tempatAtauLink = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(if (isOnline) "Tautan Pertemuan (URL)" else "Ruang / Tempat Belajar") },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp)
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (judul.isBlank()) {
                        pesanError = "Judul sesi belajar wajib diisi"
                        return@Button
                    }
                    val status = StatusSesiBelajar.Dijadwalkan
                    val sesiBaru = SesiBelajar(
                        id = (100..9999).random(),
                        judul = "${matkulTerpilih.kode.substringAfter("-")}: ${judul.trim()}",
                        mataKuliah = matkulTerpilih,
                        waktuMulai = waktu.trim().ifBlank { "2026-10-25T14:00" },
                        tautanMeeting = if (isOnline && tempatAtauLink.startsWith("http")) tempatAtauLink.trim() else null,
                        status = status
                    )
                    onTambahSesi(sesiBaru)
                },
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Simpan Sesi")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Batal")
            }
        }
    )
}
