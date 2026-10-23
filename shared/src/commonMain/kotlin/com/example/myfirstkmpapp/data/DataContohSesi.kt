package com.example.myfirstkmpapp.data

import com.example.myfirstkmpapp.model.MataKuliah
import com.example.myfirstkmpapp.model.SesiBelajar
import com.example.myfirstkmpapp.model.StatusSesiBelajar

/**
 * Dataset untuk daftar sesi belajar. Semua waktu memakai zona WIB dan format
 * yyyy-MM-dd'T'HH:mm agar dapat diurutkan berdasarkan tanggal sekaligus jam.
 * Kode mata kuliah merupakan data contoh.
 */
val daftarSesiBelajarContoh: List<SesiBelajar> = listOf(
    SesiBelajar(
        id = 1,
        judul = "PAM: Dasar Compose Multiplatform",
        mataKuliah = MataKuliah("IF25-22017", "Pengembangan Aplikasi Mobile", 3),
        waktuMulai = "2026-10-24T09:00",
        tautanMeeting = "https://meet.google.com/abc-defg-hij",
        status = StatusSesiBelajar.Dijadwalkan
    ),
    SesiBelajar(
        id = 2,
        judul = "PBO: Pewarisan dan Polimorfisme",
        mataKuliah = MataKuliah("IF25-21011", "Pemrograman Berorientasi Objek", 3),
        waktuMulai = "2026-10-23T10:00",
        tautanMeeting = null,
        status = StatusSesiBelajar.Berlangsung("Lab Komputer 1")
    ),
    SesiBelajar(
        id = 3,
        judul = "Basis Data: Normalisasi Tabel",
        mataKuliah = MataKuliah("IF25-21012", "Basis Data", 3),
        waktuMulai = "2026-10-21T13:00",
        tautanMeeting = "https://zoom.us/j/1234567890",
        status = StatusSesiBelajar.Selesai("Latihan normalisasi hingga 3NF selesai")
    ),
    SesiBelajar(
        id = 4,
        judul = "Jaringan Komputer: Latihan Subnetting",
        mataKuliah = MataKuliah("IF25-22014", "Jaringan Komputer", 3),
        waktuMulai = "2026-10-25T08:00",
        tautanMeeting = null,
        status = StatusSesiBelajar.Dijadwalkan
    ),
    SesiBelajar(
        id = 5,
        judul = "Struktur Data: Stack dan Queue",
        mataKuliah = MataKuliah("IF25-21013", "Struktur Data", 3),
        waktuMulai = "2026-10-23T09:00",
        tautanMeeting = "https://meet.google.com/klm-nopq-rst",
        status = StatusSesiBelajar.Berlangsung("Google Meet")
    ),
    SesiBelajar(
        id = 6,
        judul = "Sistem Operasi: Penjadwalan Proses",
        mataKuliah = MataKuliah("IF25-22015", "Sistem Operasi", 3),
        waktuMulai = "2026-10-22T15:00",
        tautanMeeting = null,
        status = StatusSesiBelajar.Selesai("Diskusi algoritma Round Robin selesai")
    ),
    SesiBelajar(
        id = 7,
        judul = "PAM: State dan Recomposition",
        mataKuliah = MataKuliah("IF25-22017", "Pengembangan Aplikasi Mobile", 3),
        waktuMulai = "2026-10-24T13:30",
        tautanMeeting = null,
        status = StatusSesiBelajar.Dijadwalkan
    ),
    SesiBelajar(
        id = 8,
        judul = "PBO: Interface dan Abstract Class",
        mataKuliah = MataKuliah("IF25-21011", "Pemrograman Berorientasi Objek", 3),
        waktuMulai = "2026-10-20T10:00",
        tautanMeeting = "https://zoom.us/j/2345678901",
        status = StatusSesiBelajar.Selesai("Contoh implementasi interface sudah dibahas")
    ),
    SesiBelajar(
        id = 9,
        judul = "Basis Data: SQL Join dan Agregasi",
        mataKuliah = MataKuliah("IF25-21012", "Basis Data", 3),
        waktuMulai = "2026-10-23T10:30",
        tautanMeeting = null,
        status = StatusSesiBelajar.Berlangsung("Ruang Diskusi Perpustakaan")
    ),
    SesiBelajar(
        id = 10,
        judul = "Jaringan Komputer: Routing Statis",
        mataKuliah = MataKuliah("IF25-22014", "Jaringan Komputer", 3),
        waktuMulai = "2026-10-26T16:00",
        tautanMeeting = "https://meet.google.com/uvw-xyza-bcd",
        status = StatusSesiBelajar.Dijadwalkan
    ),
    SesiBelajar(
        id = 11,
        judul = "Struktur Data: Penelusuran Binary Tree",
        mataKuliah = MataKuliah("IF25-21013", "Struktur Data", 3),
        waktuMulai = "2026-10-19T08:30",
        tautanMeeting = null,
        status = StatusSesiBelajar.Selesai("Latihan traversal preorder, inorder, dan postorder selesai")
    ),
    SesiBelajar(
        id = 12,
        judul = "Sistem Operasi: Manajemen Memori",
        mataKuliah = MataKuliah("IF25-22015", "Sistem Operasi", 3),
        waktuMulai = "2026-10-23T08:30",
        tautanMeeting = "https://zoom.us/j/3456789012",
        status = StatusSesiBelajar.Berlangsung("Zoom")
    )
)

/**
 * Mengurutkan waktu paling awal terlebih dahulu, atau paling akhir jika [terbaruDulu].
 * Waktu harus berformat yyyy-MM-dd'T'HH:mm dengan zona waktu yang sama.
 * Contoh: daftarSesiBelajarContoh.urutkanBerdasarkanWaktu(terbaruDulu = true).
 */
fun List<SesiBelajar>.urutkanBerdasarkanWaktu(
    terbaruDulu: Boolean = false
): List<SesiBelajar> = if (terbaruDulu) {
    sortedByDescending { it.waktuMulai }
} else {
    sortedBy { it.waktuMulai }
}

/**
 * Menyaring kategori status tanpa membandingkan isi ruang atau catatan.
 * Nilai null berarti semua status, misalnya saat filter "Semua" dipilih.
 */
fun List<SesiBelajar>.filterBerdasarkanStatus(
    status: StatusSesiBelajar?
): List<SesiBelajar> = filter { sesi ->
    when (status) {
        null -> true
        StatusSesiBelajar.Dijadwalkan -> sesi.status is StatusSesiBelajar.Dijadwalkan
        is StatusSesiBelajar.Berlangsung -> sesi.status is StatusSesiBelajar.Berlangsung
        is StatusSesiBelajar.Selesai -> sesi.status is StatusSesiBelajar.Selesai
    }
}

/**
 * Mencari sebagian judul sesi, nama, atau kode mata kuliah tanpa membedakan
 * huruf besar/kecil. Kata kunci kosong atau spasi menampilkan seluruh sesi.
 */
fun List<SesiBelajar>.cariSesi(kataKunci: String): List<SesiBelajar> {
    val kueri = kataKunci.trim()
    return filter { sesi ->
        sesi.judul.contains(kueri, ignoreCase = true) ||
            sesi.mataKuliah.nama.contains(kueri, ignoreCase = true) ||
            sesi.mataKuliah.kode.contains(kueri, ignoreCase = true)
    }
}
