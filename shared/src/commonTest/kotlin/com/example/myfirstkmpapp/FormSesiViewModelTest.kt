package com.example.myfirstkmpapp

import com.example.myfirstkmpapp.data.PenyimpananSesiBelajar
import com.example.myfirstkmpapp.ui.FormSesiUiState
import com.example.myfirstkmpapp.ui.FormSesiViewModel
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

/**
 * Pengujian unit untuk arsitektur MVVM (FormSesiUiState & FormSesiViewModel).
 * Dikerjakan oleh Marcel Kevin Togap Siagian (123140054).
 */
class FormSesiViewModelTest {

    private lateinit var viewModel: FormSesiViewModel

    @BeforeTest
    fun setup() {
        PenyimpananSesiBelajar.reset()
        viewModel = FormSesiViewModel()
    }

    @Test
    fun validasiBisaSimpanHanyaBernilaiTrueJikaSemuaFieldWajibTerisi() {
        val stateAwal = viewModel.uiState.value
        // Judul awal kosong -> tidak bisa simpan
        assertFalse(stateAwal.bisaSimpan)

        viewModel.ubahJudul("Diskusi Arsitektur MVVM")
        assertTrue(viewModel.uiState.value.bisaSimpan)

        // Jika waktu dikosongkan -> tidak bisa simpan
        viewModel.ubahWaktuMulai("   ")
        assertFalse(viewModel.uiState.value.bisaSimpan)

        viewModel.ubahWaktuMulai("2026-10-25T15:00")
        assertTrue(viewModel.uiState.value.bisaSimpan)

        // Jika tempat atau link dikosongkan -> tidak bisa simpan
        viewModel.ubahTempatAtauLink("")
        assertFalse(viewModel.uiState.value.bisaSimpan)
    }

    @Test
    fun mutasiStateBerfungsiDenganBenarDanAman() {
        viewModel.ubahJudul("Sesi Belajar KMP".repeat(10))
        // Pembatasan take(60) berjalan
        assertTrue(viewModel.uiState.value.judul.length <= 60)

        // Ubah moda daring (online)
        viewModel.ubahIsOnline(true)
        assertTrue(viewModel.uiState.value.isOnline)
        assertTrue(viewModel.uiState.value.tempatAtauLink.startsWith("http"))

        // Ubah moda tatap muka (offline)
        viewModel.ubahIsOnline(false)
        assertFalse(viewModel.uiState.value.isOnline)
        assertFalse(viewModel.uiState.value.tempatAtauLink.startsWith("http"))
    }

    @Test
    fun simpanBerhasilMenambahkanDataKePenyimpananSesiBelajar() {
        val jumlahAwal = PenyimpananSesiBelajar.daftar.value.size

        viewModel.ubahJudul("Pembahasan ViewModel & StateFlow")
        viewModel.ubahIndeksMataKuliah(0) // IF25-22017 PAM
        viewModel.ubahWaktuMulai("2026-10-25T16:00")
        viewModel.ubahIsOnline(false)
        viewModel.ubahTempatAtauLink("Lab Komputer 3")

        var callbackDipanggil = false
        viewModel.simpan(onSelesai = { callbackDipanggil = true })

        assertTrue(callbackDipanggil)
        // Data bertambah 1 di PenyimpananSesiBelajar
        assertEquals(jumlahAwal + 1, PenyimpananSesiBelajar.daftar.value.size)

        val sesiTerbaru = PenyimpananSesiBelajar.daftar.value.first()
        assertTrue(sesiTerbaru.judul.contains("Pembahasan ViewModel & StateFlow"))
        assertEquals("2026-10-25T16:00", sesiTerbaru.waktuMulai)

        // Pesan sukses muncul dan form ter-reset
        assertNotNull(viewModel.uiState.value.pesanSukses)
        assertEquals("", viewModel.uiState.value.judul)
    }

    @Test
    fun simpanGagalDanMenampilkanPesanErrorJikaDataBelumValid() {
        val jumlahAwal = PenyimpananSesiBelajar.daftar.value.size

        // Judul masih kosong
        var callbackDipanggil = false
        viewModel.simpan(onSelesai = { callbackDipanggil = true })

        assertFalse(callbackDipanggil)
        assertEquals(jumlahAwal, PenyimpananSesiBelajar.daftar.value.size)
        assertNotNull(viewModel.uiState.value.pesanError)
    }
}
