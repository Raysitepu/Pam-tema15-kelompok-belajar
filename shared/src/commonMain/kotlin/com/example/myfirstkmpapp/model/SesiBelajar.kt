package com.example.myfirstkmpapp.model

data class SesiBelajar(
    val id: Int,
    val judul: String,
    val mataKuliah: MataKuliah,
    val waktuMulai: String,
    val tautanMeeting: String?,
    val status: StatusSesiBelajar = StatusSesiBelajar.Dijadwalkan
)

fun SesiBelajar.ringkas(): String = "#$id $judul [${mataKuliah.kode} - ${mataKuliah.nama}], $waktuMulai"
