# PAM — Tema 15: Kelompok Belajar
**IF25-22017 Pengembangan Aplikasi Mobile**  
Program Studi Teknik Informatika — Institut Teknologi Sumatera (ITERA)

---

## Anggota Kelompok

| No | NIM | Nama | Peran |
|:---:|:---:|:---|:---:|
| 1 | 123140054 | Marcel Kevin Togap Siagian | Ketua Kelompok |
| 2 | 123140060 | Nadia Anatashiva | Anggota |
| 3 | 123140047 | Ray Regan Sitepu | Anggota |
| 4 | 123140179 | Ripaldy Saputra Lumbantoruan | Anggota |

---

## Informasi Proyek
- **Nomor Tema**: 15
- **Topik Tema**: Kelompok Belajar
- **Fitur Platform Terintegrasi**: Notifikasi
- **Teknologi**: Kotlin Multiplatform (KMP) & Compose Multiplatform
- **Target Platform**: Android, Desktop (JVM), iOS

---

## Branching
Branch `main` digunakan sebagai *base template* proyek. Setiap penambahan fitur dan tugas mingguan dikerjakan pada branch terpisah:
- `main`: Base template awal proyek KMP.
- `Tugas<minggu>`: Branch pengerjaan checkpoint/fitur berkala sebelum di-merge via Pull Request (contoh: `Tugas1`).

---

## Dokumentasi Checkpoint 1

Pada tahap **Checkpoint 1**, kelompok telah berhasil menyelesaikan target utama pertemuan pertama:

1. **Setup Lingkungan & KMP**:
    - Berhasil menginisialisasi proyek berbasis **Compose Multiplatform** yang dapat berjalan pada target **Desktop (JVM)** dan **Android**.
    - Menerapkan mekanisme **`expect` / `actual`** pada `Platform.kt` untuk menampilkan nama dan versi runtime platform aktif secara dinamis (`getPlatform().name`) pada header aplikasi.

2. **Tampilan Identitas & Tema**:
    - Menampilkan identitas **Tema 15: Kelompok Belajar** beserta deskripsinya pada header utama.
    - Menampilkan kartu profil seluruh anggota kelompok (`AnggotaCard`) yang memuat Nama, NIM, inisial avatar dinamis, dan penanda peran (Ketua/Anggota).

3. **Fitur Bonus Interaktivitas State & Animasi**:
    - Menerapkan **State Management** menggunakan `remember { mutableStateOf(false) }` untuk mengontrol tombol interaktif buka/tutup (*toggle*) informasi jadwal sesi belajar kelompok.
    - Mengintegrasikan komponen **`AnimatedVisibility`** agar kemunculan jadwal belajar memiliki efek transisi animasi yang halus dan responsif.

---

## Tangkapan Layar Aplikasi (Screenshots)

### 1. Tampilan di Desktop (JVM)
![Desktop View](./screenshoot/screenshot-desktop.png)

### 2. Tampilan di Android (Emulator / Device Fisik)
![Android View](./screenshoot/screenshot-android.jpeg)
---

## Cara Menjalankan Aplikasi
Pastikan Android Studio telah terpasang dengan plugin **Kotlin Multiplatform** serta **JDK 17+**.

### 1. Menjalankan di Desktop (JVM)
Cara paling cepat dan ringan untuk pengujian kode bersama tanpa emulator:
```bash
./gradlew :desktopApp:run