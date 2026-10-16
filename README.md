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
* **Nomor Tema**: 15
* **Topik Tema**: Kelompok Belajar
* **Fitur Platform Terintegrasi**: Notifikasi
* **Teknologi**: Kotlin Multiplatform (KMP) & Compose Multiplatform
* **Target Platform**: Android, Desktop (JVM), iOS

---

## Branching
Branch `main` ini digunakan sebagai *base template* proyek. Setiap penambahan fitur dan tugas mingguan akan dikerjakan pada branch terpisah:
* `main`: Base template awal proyek KMP.
* `Tugas<minggu>`: Branch pengerjaan checkpoint/fitur berkala sebelum di-merge via Pull Request.

---

## Cara Menjalankan Aplikasi

Pastikan Android Studio telah terpasang dengan plugin **Kotlin Multiplatform** serta **JDK 17+**.

### 1. Menjalankan di Desktop (JVM)
Cara paling cepat dan ringan untuk pengujian kode bersama tanpa emulator:
```bash
./gradlew :desktopApp:run
```
*(Atau dengan hot reload: `./gradlew :desktopApp:hotRun --auto`)*

### 2. Menjalankan di Android
Pasang dan jalankan aplikasi ke emulator atau device fisik yang terhubung:
```bash
./gradlew :androidApp:installDebug
```
*Atau pilih konfigurasi `androidApp` di dropdown toolbar Android Studio lalu klik tombol **Run (▶️)**.*

### 3. Menjalankan di iOS
*(Khusus macOS dengan Xcode)*
* Buka folder `./iosApp` pada Xcode, tentukan simulator target, dan jalankan proyek.

### 4. Menjalankan Unit Tests
```bash
# Desktop
./gradlew :shared:jvmTest

# Android
./gradlew :shared:testAndroidHostTest

# iOS
./gradlew :shared:iosSimulatorArm64Test
```