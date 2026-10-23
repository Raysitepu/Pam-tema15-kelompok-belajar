package com.example.myfirstkmpapp.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myfirstkmpapp.daftarAnggota
import com.example.myfirstkmpapp.data.cariSesi
import com.example.myfirstkmpapp.data.daftarSesiBelajarContoh
import com.example.myfirstkmpapp.data.filterBerdasarkanStatus
import com.example.myfirstkmpapp.data.urutkanBerdasarkanWaktu
import com.example.myfirstkmpapp.model.SesiBelajar
import com.example.myfirstkmpapp.model.StatusSesiBelajar

private data class PilihanStatus(val label: String, val status: StatusSesiBelajar?)

// Filter membandingkan kategori; ruang dan catatan tidak memengaruhi hasilnya.
private val pilihanStatus = listOf(
    PilihanStatus("Semua", null),
    PilihanStatus("Dijadwalkan", StatusSesiBelajar.Dijadwalkan),
    PilihanStatus("Berlangsung", StatusSesiBelajar.Berlangsung("")),
    PilihanStatus("Selesai", StatusSesiBelajar.Selesai(""))
)

/** Layar pemakai dataset dan helper koleksi, tanpa menyalin logika filter di UI. */
@Composable
fun LayarSesiBelajar(semuaSesi: List<SesiBelajar> = daftarSesiBelajarContoh) {
    var daftarSesi by remember { mutableStateOf(semuaSesi) }
    var kataKunci by rememberSaveable { mutableStateOf("") }
    var indeksStatus by rememberSaveable { mutableIntStateOf(0) }
    var terbaruDulu by rememberSaveable { mutableStateOf(true) }
    var tampilKelompok by rememberSaveable { mutableStateOf(false) }
    var tampilDialogTambah by rememberSaveable { mutableStateOf(false) }
    // Posisi baru mencegah key item mempertahankan guliran dari urutan sebelumnya.
    val posisiDaftar = key(kataKunci, indeksStatus, terbaruDulu) {
        rememberLazyListState()
    }
    val sesiTerlihat = remember(daftarSesi, kataKunci, indeksStatus, terbaruDulu) {
        daftarSesi
            .filterBerdasarkanStatus(pilihanStatus[indeksStatus].status)
            .cariSesi(kataKunci)
            .urutkanBerdasarkanWaktu(terbaruDulu)
    }
    val jumlahMataKuliah = daftarSesi.map { it.mataKuliah.kode }.distinct().size
    val jumlahDijadwalkan = daftarSesi.filterBerdasarkanStatus(StatusSesiBelajar.Dijadwalkan).size

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { tampilDialogTambah = true },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White,
                shape = RoundedCornerShape(16.dp),
                icon = {
                    Text("+", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                },
                text = {
                    Text("Buat Sesi", fontWeight = FontWeight.SemiBold)
                }
            )
        }
    ) { padding ->
        Box(Modifier.fillMaxSize().padding(padding).consumeWindowInsets(padding).imePadding(), contentAlignment = Alignment.TopCenter) {
            Column(Modifier.widthIn(max = 760.dp).fillMaxWidth().fillMaxHeight()) {
                Row(
                    Modifier.fillMaxWidth().padding(start = 20.dp, end = 12.dp, top = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(Modifier.weight(1f)) {
                        Text(
                            "TEMA 15 / IF ITERA",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                        Text("Sesi belajar", fontSize = 28.sp, fontWeight = FontWeight.Bold)
                    }
                    TextButton(onClick = { tampilKelompok = true }) { Text("Kelompok") }
                }
                Text(
                    "Temukan teman diskusi untuk mata kuliahmu.",
                    modifier = Modifier.padding(start = 20.dp, top = 4.dp, bottom = 18.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Surface(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                    shape = RoundedCornerShape(20.dp),
                    color = MaterialTheme.colorScheme.primary
                ) {
                    Row(Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
                        Column(Modifier.weight(1f)) {
                            Text("Belajar bersama", color = Color.White, fontWeight = FontWeight.SemiBold)
                            Spacer(Modifier.height(6.dp))
                            Text(
                                "$jumlahMataKuliah mata kuliah · $jumlahDijadwalkan dijadwalkan",
                                color = Color.White.copy(alpha = 0.85f),
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text("${daftarSesi.size}", color = Color.White, fontSize = 32.sp, fontWeight = FontWeight.Bold)
                            Text("total sesi", color = Color.White, style = MaterialTheme.typography.labelSmall)
                        }
                    }
                }
                OutlinedTextField(
                    value = kataKunci,
                    onValueChange = { kataKunci = it },
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 14.dp),
                    label = { Text("Cari sesi atau mata kuliah") },
                    singleLine = true,
                    shape = RoundedCornerShape(16.dp),
                    leadingIcon = {
                        val warna = MaterialTheme.colorScheme.onSurfaceVariant
                        Canvas(Modifier.size(22.dp)) {
                            drawCircle(warna, size.width * 0.29f, Offset(size.width * 0.4f, size.height * 0.4f), style = Stroke(2.dp.toPx()))
                            drawLine(warna, Offset(size.width * 0.62f, size.height * 0.62f), Offset(size.width * 0.9f, size.height * 0.9f), 2.dp.toPx())
                        }
                    },
                    trailingIcon = if (kataKunci.isNotEmpty()) {
                        { TextButton(onClick = { kataKunci = "" }) { Text("Hapus") } }
                    } else null
                )
                Row(
                    Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()).padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    pilihanStatus.forEachIndexed { indeks, pilihan ->
                        FilterChip(
                            selected = indeksStatus == indeks,
                            onClick = { indeksStatus = indeks },
                            label = { Text(pilihan.label) }
                        )
                    }
                }
                Row(
                    Modifier.fillMaxWidth().padding(start = 20.dp, end = 12.dp, top = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "${sesiTerlihat.size} sesi ditemukan",
                        modifier = Modifier.weight(1f),
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    TextButton(onClick = { terbaruDulu = !terbaruDulu }) {
                        Text(if (terbaruDulu) "Paling akhir ↓" else "Paling awal ↑")
                    }
                }
                LazyColumn(
                    state = posisiDaftar,
                    modifier = Modifier.weight(1f).fillMaxWidth(),
                    contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 4.dp, bottom = 88.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    if (sesiTerlihat.isEmpty()) {
                        item {
                            Column(Modifier.fillMaxWidth().padding(vertical = 40.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("Belum ada sesi yang cocok", fontWeight = FontWeight.SemiBold)
                                Text("Coba kata kunci atau status lain.", style = MaterialTheme.typography.bodyMedium)
                                TextButton(onClick = { kataKunci = ""; indeksStatus = 0 }) { Text("Atur ulang pencarian") }
                            }
                        }
                    }
                    items(sesiTerlihat, key = { it.id }) { sesi -> KartuSesiBelajar(sesi) }
                }
            }
        }
    }
    if (tampilDialogTambah) {
        DialogTambahSesi(
            daftarMataKuliah = remember(daftarSesi) {
                daftarSesi.map { it.mataKuliah }.distinctBy { it.kode }
            },
            onDismiss = { tampilDialogTambah = false },
            onTambahSesi = { sesiBaru ->
                daftarSesi = listOf(sesiBaru) + daftarSesi
                tampilDialogTambah = false
            }
        )
    }
    if (tampilKelompok) {
        AlertDialog(
            onDismissRequest = { tampilKelompok = false },
            title = { Text("Kelompok belajar") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    daftarAnggota.forEach { anggota ->
                        Column {
                            Text(anggota.nama, fontWeight = FontWeight.SemiBold)
                            Text("${anggota.nim} · ${anggota.peran}", style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            },
            confirmButton = { TextButton(onClick = { tampilKelompok = false }) { Text("Tutup") } }
        )
    }
}
