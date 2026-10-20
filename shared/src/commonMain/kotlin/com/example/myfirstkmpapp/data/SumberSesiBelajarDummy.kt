package com.example.myfirstkmpapp.data

import com.example.myfirstkmpapp.model.MataKuliah
import com.example.myfirstkmpapp.model.SesiBelajar
import com.example.myfirstkmpapp.model.StatusSesiBelajar
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SumberSesiBelajarDummy : Sumber<SesiBelajar> {

    private val daftarSesi: List<SesiBelajar> = listOf(
        SesiBelajar(
            id = 1,
            judul = "Belajar Bareng Coroutines & Flow KMP",
            mataKuliah = MataKuliah(kode = "IF25-22017", nama = "Pengembangan Aplikasi Mobile", sks = 3),
            waktuMulai = "Selasa, 14:00 WIB",
            tautanMeeting = "https://meet.google.com/abc-defg-hij",
            status = StatusSesiBelajar.Dijadwalkan
        ),
        SesiBelajar(
            id = 2,
            judul = "Review Modul 2 & Hands-on Kotlin Dasar",
            mataKuliah = MataKuliah(kode = "IF25-22017", nama = "Pengembangan Aplikasi Mobile", sks = 3),
            waktuMulai = "Rabu, 16:00 WIB",
            tautanMeeting = null,
            status = StatusSesiBelajar.Dijadwalkan
        ),
        SesiBelajar(
            id = 3,
            judul = "Diskusi Desain Database Kelompok Belajar",
            mataKuliah = MataKuliah(kode = "IF25-21012", nama = "Basis Data", sks = 3),
            waktuMulai = "Kamis, 19:30 WIB",
            tautanMeeting = "https://zoom.us/j/1234567890",
            status = StatusSesiBelajar.Dijadwalkan
        )
    )

    override suspend fun ambil(id: Int): SesiBelajar {
        delay(1000) // pura-pura menunggu respon server/jaringan
        return daftarSesi.find { it.id == id }
            ?: SesiBelajar(
                id = id,
                judul = "Sesi Belajar Mandiri #$id",
                mataKuliah = MataKuliah(kode = "IF25-22017", nama = "Pengembangan Aplikasi Mobile", sks = 3),
                waktuMulai = "Menyesuaikan",
                tautanMeeting = null,
                status = StatusSesiBelajar.Dijadwalkan
            )
    }

    override fun pantau(id: Int): Flow<SesiBelajar> = flow {
        val dasar = ambil(id)

        // Tahap 1: Sesi dijadwalkan
        val awal = dasar.copy(status = StatusSesiBelajar.Dijadwalkan)
        emit(awal)
        delay(1500)

        // Tahap 2: Sesi mulai berlangsung
        val berlangsung = dasar.copy(
            status = StatusSesiBelajar.Berlangsung(ruang = "Lab Komputer 3 / Google Meet")
        )
        emit(berlangsung)
        delay(2000)

        // Tahap 3: Sesi belajar selesai
        val selesai = dasar.copy(
            status = StatusSesiBelajar.Selesai(catatan = "Materi Coroutines dan Flow selesai didiskusikan")
        )
        emit(selesai)
    }

    fun pantauStatus(id: Int): Flow<StatusSesiBelajar> = flow {
        emit(StatusSesiBelajar.Dijadwalkan)
        delay(1500)
        emit(StatusSesiBelajar.Berlangsung(ruang = "Lab Komputer 3 / Google Meet"))
        delay(2000)
        emit(StatusSesiBelajar.Selesai(catatan = "Materi Coroutines dan Flow selesai didiskusikan"))
    }
}
