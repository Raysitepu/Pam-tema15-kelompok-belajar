package com.example.myfirstkmpapp.model

sealed class StatusSesiBelajar {
    data object Dijadwalkan : StatusSesiBelajar()
    data class Berlangsung(val ruang: String) : StatusSesiBelajar()
    data class Selesai(val catatan: String) : StatusSesiBelajar()
}

fun StatusSesiBelajar.label(): String = when (this) {
    StatusSesiBelajar.Dijadwalkan -> "Sesi dijadwalkan"
    is StatusSesiBelajar.Berlangsung -> "Sedang berlangsung di $ruang"
    is StatusSesiBelajar.Selesai -> "Selesai: $catatan"
}
