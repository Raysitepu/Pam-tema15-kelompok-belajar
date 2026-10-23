package com.example.myfirstkmpapp

import com.example.myfirstkmpapp.data.cariSesi
import com.example.myfirstkmpapp.data.daftarSesiBelajarContoh
import com.example.myfirstkmpapp.data.filterBerdasarkanStatus
import com.example.myfirstkmpapp.data.urutkanBerdasarkanWaktu
import com.example.myfirstkmpapp.model.SesiBelajar
import com.example.myfirstkmpapp.model.StatusSesiBelajar
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class DataContohSesiTest {
    @Test
    fun datasetMemilikiSesiUnikDanVariasiYangDiminta() {
        val sesi = daftarSesiBelajarContoh
        assertTrue(sesi.size >= 10)
        assertEquals(sesi.size, sesi.map { it.id }.distinct().size)
        assertEquals(sesi.size, sesi.map { it.judul }.distinct().size)
        assertTrue(sesi.map { it.mataKuliah.nama }.distinct().size >= 4)
        assertTrue(sesi.all {
            it.waktuMulai.matches(Regex("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}"))
        })
        val kategori = listOf(
            sesi.filter { it.status is StatusSesiBelajar.Dijadwalkan },
            sesi.filter { it.status is StatusSesiBelajar.Berlangsung },
            sesi.filter { it.status is StatusSesiBelajar.Selesai }
        )
        kategori.forEach { kelompok ->
            assertTrue(kelompok.any { it.tautanMeeting == null })
            assertTrue(kelompok.any { it.tautanMeeting?.startsWith("https://") == true })
        }
    }

    @Test
    fun pengurutanMemperhatikanTanggalDanJamTanpaMengubahSumber() {
        val sumber = daftarSesiBelajarContoh.toList()
        val urutan = listOf(11, 8, 3, 6, 12, 5, 2, 9, 1, 7, 4, 10)
        assertEquals(urutan, daftarSesiBelajarContoh.urutkanBerdasarkanWaktu().map { it.id })
        assertEquals(
            urutan.reversed(),
            daftarSesiBelajarContoh.urutkanBerdasarkanWaktu(terbaruDulu = true).map { it.id }
        )
        assertEquals(sumber, daftarSesiBelajarContoh)
    }

    @Test
    fun filterStatusMengabaikanPerbedaanRuangDanCatatan() {
        assertEquals(daftarSesiBelajarContoh, daftarSesiBelajarContoh.filterBerdasarkanStatus(null))
        assertEquals(
            listOf(1, 4, 7, 10),
            daftarSesiBelajarContoh.filterBerdasarkanStatus(StatusSesiBelajar.Dijadwalkan).map { it.id }
        )
        assertEquals(
            listOf(2, 5, 9, 12),
            daftarSesiBelajarContoh.filterBerdasarkanStatus(
                StatusSesiBelajar.Berlangsung("Ruang lain")
            ).map { it.id }
        )
        assertEquals(
            listOf(3, 6, 8, 11),
            daftarSesiBelajarContoh.filterBerdasarkanStatus(
                StatusSesiBelajar.Selesai("Catatan lain")
            ).map { it.id }
        )
    }

    @Test
    fun pencarianMendukungJudulNamaDanKodeTanpaMembedakanKapital() {
        assertEquals(listOf(1, 7), daftarSesiBelajarContoh.cariSesi("  pam  ").map { it.id })
        assertEquals(listOf(2, 8), daftarSesiBelajarContoh.cariSesi("berorientasi objek").map { it.id })
        assertEquals(listOf(3, 9), daftarSesiBelajarContoh.cariSesi("if25-21012").map { it.id })
        assertTrue(daftarSesiBelajarContoh.cariSesi("mata kuliah tidak ada").isEmpty())
        assertEquals(daftarSesiBelajarContoh, daftarSesiBelajarContoh.cariSesi("   "))
    }

    @Test
    fun fungsiDapatDirangkaiDanMenanganiKoleksiKosong() {
        assertEquals(
            listOf(7, 1),
            daftarSesiBelajarContoh
                .filterBerdasarkanStatus(StatusSesiBelajar.Dijadwalkan)
                .cariSesi("mobile")
                .urutkanBerdasarkanWaktu(terbaruDulu = true)
                .map { it.id }
        )
        val kosong = emptyList<SesiBelajar>()
        assertTrue(kosong.urutkanBerdasarkanWaktu().isEmpty())
        assertTrue(kosong.urutkanBerdasarkanWaktu(terbaruDulu = true).isEmpty())
        assertTrue(kosong.filterBerdasarkanStatus(StatusSesiBelajar.Dijadwalkan).isEmpty())
        assertTrue(kosong.cariSesi("PAM").isEmpty())
    }
}
