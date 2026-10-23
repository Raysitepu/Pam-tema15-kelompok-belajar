package com.example.myfirstkmpapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.myfirstkmpapp.model.SesiBelajar
import com.example.myfirstkmpapp.model.StatusSesiBelajar

/**
 * Komponen chip/badge visual penanda status sesi belajar
 * dengan pembedaan warna latar dan teks serta urutan Modifier yang tepat.
 */
@Composable
fun LabelStatusSesi(
    status: StatusSesiBelajar,
    modifier: Modifier = Modifier
) {
    val (label, warnaTeks, warnaLatar) = when (status) {
        StatusSesiBelajar.Dijadwalkan -> Triple(
            "Dijadwalkan",
            Color(0xFF4F46B8), // Biru/Ungu
            Color(0xFF4F46B8).copy(alpha = 0.12f)
        )
        is StatusSesiBelajar.Berlangsung -> Triple(
            "Berlangsung",
            Color(0xFF087D62), // Hijau
            Color(0xFF087D62).copy(alpha = 0.12f)
        )
        is StatusSesiBelajar.Selesai -> Triple(
            "Selesai",
            Color(0xFF64748B), // Abu-abu netral
            Color(0xFF64748B).copy(alpha = 0.12f)
        )
    }

    // Urutan Modifier: pembulatan sudut & background dulu, baru padding dalam
    Text(
        text = label,
        color = warnaTeks,
        style = MaterialTheme.typography.labelSmall,
        fontWeight = FontWeight.SemiBold,
        modifier = modifier
            .background(color = warnaLatar, shape = RoundedCornerShape(8.dp))
            .padding(horizontal = 10.dp, vertical = 5.dp)
    )
}

/**
 * Komponen kartu entitas SesiBelajar dengan elevasi, Row, Column,
 * dan informasi sesi terstruktur.
 */
@Composable
fun KartuSesiBelajar(
    sesi: SesiBelajar,
    modifier: Modifier = Modifier,
    onKlik: () -> Unit = {}
) {
    val uriHandler = LocalUriHandler.current

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .clickable { onKlik() },
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Baris Atas: Nama Mata Kuliah & Badge Status
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = sesi.mataKuliah.nama,
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                LabelStatusSesi(status = sesi.status)
            }

            // Judul Sesi (Teks Tebal)
            Text(
                text = sesi.judul.substringAfter(": "),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            // Waktu Pelaksanaan
            Text(
                text = formatWaktuSesi(sesi.waktuMulai),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )

            // Keterangan Ruang / Catatan berdasarkan status
            val keterangan = when (val status = sesi.status) {
                StatusSesiBelajar.Dijadwalkan ->
                    if (sesi.tautanMeeting == null) "Tatap muka · ruang menyusul" else "Pertemuan online"
                is StatusSesiBelajar.Berlangsung -> "Ruang: ${status.ruang}"
                is StatusSesiBelajar.Selesai -> status.catatan
            }
            Text(
                text = keterangan,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            // Baris Bawah: Kode + SKS Mata Kuliah & Penanda Tautan Daring (Meeting Link)
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${sesi.mataKuliah.kode} · ${sesi.mataKuliah.sks} SKS",
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                val tautan = sesi.tautanMeeting
                if (tautan != null) {
                    TextButton(
                        onClick = { uriHandler.openUri(tautan) },
                        contentPadding = PaddingValues(horizontal = 8.dp)
                    ) {
                        Text("🔗 Tautan meeting", style = MaterialTheme.typography.labelMedium)
                    }
                } else {
                    Text(
                        text = "Tanpa tautan",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

/** Mengubah tampilan waktu ISO yyyy-MM-dd'T'HH:mm menjadi format mudah dibaca. */
fun formatWaktuSesi(waktu: String): String {
    val tanggalDanJam = waktu.split('T')
    val tanggal = tanggalDanJam.first().split('-')
    val bulan = listOf("Jan", "Feb", "Mar", "Apr", "Mei", "Jun", "Jul", "Agu", "Sep", "Okt", "Nov", "Des")
    val namaBulan = tanggal.getOrNull(1)?.toIntOrNull()?.let { bulan.getOrNull(it - 1) } ?: return waktu
    val hari = tanggal.getOrNull(2)?.toIntOrNull() ?: return waktu
    val jam = tanggalDanJam.getOrNull(1)?.replace(':', '.') ?: return waktu
    return "$hari $namaBulan ${tanggal.first()} · $jam WIB"
}