package com.example.myfirstkmpapp.data

import com.example.myfirstkmpapp.model.SesiBelajar
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * Objek penyimpanan terpusat (Single Source of Truth) untuk daftar sesi belajar.
 * Dikerjakan oleh Ripaldy (123140179) Menggunakan MutableStateFlow privat dan StateFlow.
 */
object PenyimpananSesiBelajar {

    private val _daftar = MutableStateFlow<List<SesiBelajar>>(daftarSesiBelajarContoh)

    val daftar: StateFlow<List<SesiBelajar>> = _daftar.asStateFlow()
    
    fun tambah(sesi: SesiBelajar) {
        _daftar.update { daftarLama ->
            listOf(sesi) + daftarLama
        }
    }

    fun idBerikutnya(): Int {
        return (_daftar.value.maxOfOrNull { it.id } ?: 0) + 1
    }

    fun reset() {
        _daftar.value = daftarSesiBelajarContoh
    }
}