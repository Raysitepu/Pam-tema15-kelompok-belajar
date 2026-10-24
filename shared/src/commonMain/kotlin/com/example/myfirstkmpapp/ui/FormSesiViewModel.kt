package com.example.myfirstkmpapp.ui

import androidx.lifecycle.ViewModel
import com.example.myfirstkmpapp.data.PenyimpananSesiBelajar
import com.example.myfirstkmpapp.model.SesiBelajar
import com.example.myfirstkmpapp.model.StatusSesiBelajar
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * ViewModel untuk mengelola state dan logika bisnis formulir penambahan sesi belajar.
 * Dikerjakan oleh Marcel Kevin Togap Siagian (123140054).
 *
 * Menerapkan prinsip Modul 4:
 * 1. Single Source of Truth: State form dikelola eksklusif oleh ViewModel.
 * 2. Hanya pemilik yang mengubah: `_uiState` privat, `uiState` publik bertipe StateFlow.
 * 3. Ubah dengan salinan: Setiap perubahan memanggil `_uiState.update { it.copy(...) }`.
 * 4. Bertahan saat konfigurasi berubah: Berjalan di atas lifecycle ViewModel.
 */
class FormSesiViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(FormSesiUiState())
    val uiState: StateFlow<FormSesiUiState> = _uiState.asStateFlow()

    fun ubahJudul(teks: String) {
        _uiState.update {
            it.copy(
                judul = teks.take(60),
                pesanError = if (teks.isNotBlank()) null else it.pesanError
            )
        }
    }

    fun ubahIndeksMataKuliah(indeks: Int) {
        _uiState.update { it.copy(indeksMataKuliah = indeks) }
    }

    fun ubahWaktuMulai(teks: String) {
        _uiState.update { it.copy(waktuMulai = teks) }
    }

    fun ubahIsOnline(online: Boolean) {
        _uiState.update { state ->
            val defaultTempat = if (online) {
                if (!state.tempatAtauLink.startsWith("http")) "https://meet.google.com/xyz-belajar" else state.tempatAtauLink
            } else {
                if (state.tempatAtauLink.startsWith("http")) "Lab Komputer 3" else state.tempatAtauLink
            }
            state.copy(
                isOnline = online,
                tempatAtauLink = defaultTempat
            )
        }
    }

    fun ubahTempatAtauLink(teks: String) {
        _uiState.update { it.copy(tempatAtauLink = teks) }
    }

    fun simpan(onSelesai: () -> Unit = {}) {
        val state = _uiState.value
        if (!state.bisaSimpan) {
            _uiState.update { it.copy(pesanError = "Judul, jadwal, dan tempat sesi wajib diisi.") }
            return
        }

        val matkul = state.mataKuliahTerpilih
        val judulLengkap = "${matkul.kode.substringAfter("-")}: ${state.judul.trim()}"
        val sesiBaru = SesiBelajar(
            id = PenyimpananSesiBelajar.idBerikutnya(),
            judul = judulLengkap,
            mataKuliah = matkul,
            waktuMulai = state.waktuMulai.trim(),
            tautanMeeting = if (state.isOnline) state.tempatAtauLink.trim() else null,
            status = if (state.isOnline) {
                StatusSesiBelajar.Dijadwalkan
            } else {
                StatusSesiBelajar.Berlangsung(state.tempatAtauLink.trim())
            }
        )

        PenyimpananSesiBelajar.tambah(sesiBaru)

        _uiState.update {
            FormSesiUiState(
                pesanSukses = "Sesi belajar berhasil ditambahkan!"
            )
        }
        onSelesai()
    }

    fun resetForm() {
        _uiState.value = FormSesiUiState()
    }
}
