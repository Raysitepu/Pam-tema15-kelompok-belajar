package com.example.myfirstkmpapp.runner

import com.example.myfirstkmpapp.data.SumberSesiBelajarDummy
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val sumber = SumberSesiBelajarDummy()

    // 1. Menguji fungsi suspend ambil()
    println("Mengambil data sesi belajar...")
    val sesi = sumber.ambil(1)
    println("Judul: ${sesi.judul}")
    println("Mata Kuliah: ${sesi.mataKuliah.nama}")
    println("Status awal: ${sesi.status}")

    println()

    // 2. Menguji Flow pantau()
    println("Sebelum collect flow")
    sumber.pantau(1).collect { update ->
        println("Status berubah: ${update.status}")
    }
    println("Sesudah collect flow")
}