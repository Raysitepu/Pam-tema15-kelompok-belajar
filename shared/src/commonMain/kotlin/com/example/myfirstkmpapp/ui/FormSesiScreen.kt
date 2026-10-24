package com.example.myfirstkmpapp.ui

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

/**
 * Layar Formulir Tambah Sesi Belajar (Layar ke-2 dari 5 Layar Wajib Bank Proyek).
 * Dikerjakan oleh Nadia Anatashiva (123140060) sebagai pemenuhan Modul 4 (State dan MVVM).
 *
 * Menerapkan:
 * 1. MVVM: Layar membaca state melalui `viewModel.uiState.collectAsState()`.
 * 2. State Hoisting: Input teks menggunakan composable stateless `KolomTeks`.
 * 3. Reactive UI: Tombol simpan otomatis aktif hanya jika `state.bisaSimpan` valid.
 */
@Composable
fun FormSesiScreen(
    viewModel: FormSesiViewModel = viewModel { FormSesiViewModel() },
    onKembali: () -> Unit = {},
    onSelesai: () -> Unit = {}
) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier
                    .widthIn(max = 680.dp)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Header Navigasi
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextButton(onClick = onKembali) {
                        Text("← Kembali ke Daftar", fontWeight = FontWeight.SemiBold)
                    }
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)
                    ) {
                        Text(
                            "MODUL 4 / MVVM",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }
                }

                // Judul Layar
                Column {
                    Text(
                        "Buat Sesi Belajar Baru",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        "Formulir entitas utama dengan arsitektur MVVM, validasi otomatis, dan StateFlow.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                // Pesan Error jika ada
                state.pesanError?.let { error ->
                    Card(
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = error,
                            color = MaterialTheme.colorScheme.onErrorContainer,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(12.dp)
                        )
                    }
                }

                // Pesan Sukses jika ada
                state.pesanSukses?.let { sukses ->
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9)),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = sukses,
                            color = Color(0xFF2E7D32),
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(12.dp)
                        )
                    }
                }

                // Field 1: Topik / Judul Sesi (Menggunakan KolomTeks)
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    KolomTeks(
                        label = "Topik / Judul Sesi *",
                        value = state.judul,
                        onValueChange = viewModel::ubahJudul,
                        placeholder = "Contoh: Pembahasan ViewModel dan StateFlow",
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        "${state.judul.length} dari 60 karakter",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.align(Alignment.End)
                    )
                }

                // Field 2: Pilihan Mata Kuliah (FilterChip)
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        "Mata Kuliah *",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        state.daftarMataKuliah.forEachIndexed { index, matkul ->
                            FilterChip(
                                selected = state.indeksMataKuliah == index,
                                onClick = { viewModel.ubahIndeksMataKuliah(index) },
                                label = { Text(matkul.kode) }
                            )
                        }
                    }
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.08f)
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                "${state.mataKuliahTerpilih.nama} (${state.mataKuliahTerpilih.sks} SKS)",
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Medium,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }

                // Field 3: Jadwal / Waktu Mulai (Menggunakan KolomTeks)
                KolomTeks(
                    label = "Jadwal Sesi (Format ISO yyyy-MM-dd'T'HH:mm) *",
                    value = state.waktuMulai,
                    onValueChange = viewModel::ubahWaktuMulai,
                    placeholder = "2026-10-25T14:00",
                    modifier = Modifier.fillMaxWidth()
                )

                // Field 4: Moda Pertemuan (Daring vs Tatap Muka)
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        "Moda Pertemuan *",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        FilterChip(
                            selected = !state.isOnline,
                            onClick = { viewModel.ubahIsOnline(false) },
                            label = { Text("Tatap Muka (Luring)") }
                        )
                        FilterChip(
                            selected = state.isOnline,
                            onClick = { viewModel.ubahIsOnline(true) },
                            label = { Text("Daring (Online)") }
                        )
                    }
                }

                // Field 5: Ruang Belajar atau Tautan Meeting (Menggunakan KolomTeks)
                KolomTeks(
                    label = if (state.isOnline) "Tautan Pertemuan (Google Meet / Zoom URL) *" else "Ruang / Tempat Belajar *",
                    value = state.tempatAtauLink,
                    onValueChange = viewModel::ubahTempatAtauLink,
                    placeholder = if (state.isOnline) "https://meet.google.com/..." else "Lab Komputer 3 / Ruang Diskusi",
                    modifier = Modifier.fillMaxWidth()
                )

                // Pratinjau Cepat Ringkasan
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(
                            "PRATINJAU SESI",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            letterSpacing = 1.sp
                        )
                        val judulTampil = if (state.judul.isNotBlank()) {
                            "${state.mataKuliahTerpilih.kode.substringAfter("-")}: ${state.judul.trim()}"
                        } else {
                            "(Judul sesi masih kosong)"
                        }
                        Text(
                            judulTampil,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            "Waktu: ${state.waktuMulai} · Lokasi: ${state.tempatAtauLink}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Spacer(Modifier.height(8.dp))

                // Tombol Aksi Simpan & Batal
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = onKembali,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Batal")
                    }
                    Button(
                        onClick = { viewModel.simpan(onSelesai = onSelesai) },
                        enabled = state.bisaSimpan,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        if (state.sedangMenyimpan) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(16.dp),
                                color = Color.White,
                                strokeWidth = 2.dp
                            )
                            Spacer(Modifier.width(8.dp))
                            Text("Menyimpan...")
                        } else {
                            Text("Simpan Sesi")
                        }
                    }
                }
            }
        }
    }
}
