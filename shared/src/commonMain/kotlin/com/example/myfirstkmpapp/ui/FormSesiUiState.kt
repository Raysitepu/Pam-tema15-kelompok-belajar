package com.example.myfirstkmpapp.ui

import com.example.myfirstkmpapp.data.daftarSesiBelajarContoh
import com.example.myfirstkmpapp.model.MataKuliah

/**
 * State antarmuka untuk layar formulir tambah sesi belajar (Layar ke-2 Bank Proyek).
 * Dikerjakan oleh Marcel Kevin Togap Siagian (123140054).
 *
 * Menerapkan prinsip Modul 4:
 * 1. State seminimal mungkin: Properti `bisaSimpan` dihitung lewat `get()`, bukan disimpan manual.
 * 2. Seluruh state layar form dikemas dalam satu data class yang immutable.
 */
data class FormSesiUiState(
    val judul: String = "",
    val daftarMataKuliah: List<MataKuliah> = daftarSesiBelajarContoh.map { it.mataKuliah }.distinctBy { it.kode },
    val indeksMataKuliah: Int = 0,
    val waktuMulai: String = "2026-10-25T14:00",
    val isOnline: Boolean = false,
    val tempatAtauLink: String = "Lab Komputer 3",
    val sedangMenyimpan: Boolean = false,
    val pesanError: String? = null,
    val pesanSukses: String? = null
) {
    val mataKuliahTerpilih: MataKuliah
        get() = daftarMataKuliah.getOrElse(indeksMataKuliah) {
            MataKuliah("IF25-22017", "Pengembangan Aplikasi Mobile", 3)
        }

    val bisaSimpan: Boolean
        get() = judul.trim().isNotBlank() &&
                waktuMulai.trim().isNotBlank() &&
                tempatAtauLink.trim().isNotBlank() &&
                !sedangMenyimpan
}
