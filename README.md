# Aplikasi Pengelolaan Donasi Uang & Makanan
Project Pemrograman Berorientasi Obyek 2 Menggunakan Metode MVC

## Apa Saja Yang Dipakai ?
1. __JDK__           : JDK 8 (8u181)
2. __IDE__           : Apache NetBeans 11.3
3. __User Interface__: Java FX, Jfoenix 8.0.8, SceneBuilder 8.5.0
4. __Database__      : XAMPP 3.2.4, MariaDB, mysql connector java 5.1.23

## Struktur Package
- __app__         : Berisi Main Class
- __controllers__ : Berisi class controller untuk mengatur view, logic dan data
- __helpers__     : Berisi class yang menyediakan fungsi-fungsi yang dibutuhkan dan sering dipakai
- __models__      : Berisi class yang menyediakan data dari database
- __views__       : Berisi semua file-file UI FXML

## Fitur & To Do
1. [x] Login Multiuser : role (1=>Admin, 2=>User)
2. [x] Registasi
3. [x] Cookie : menyimpan data user dalam file xml (jika user menutup aplikasi tanpa logout maka user bisa membuka aplikasi tanpa harus login kembali)
4. [x] SoftDeletes : agar data tidak benar-benar terhapus dari database
5. [x] Edit profil dan ubah password
6. [x] Untuk Admin
    - [x] Menampilkan data donatur
    - [x] Pencarian data donatur
    - [x] Menampilkan data penerima
    - [x] Pencarian data penerima
    - [x] Tambah penerima
    - [x] Edit penerima
    - [x] Hapus penerima (softdeletes)
    - [x] Menampilkan data makanan (status 1=>sedang diproses)
    - [x] Edit status data makanan (status 2=>ditolak, 3=>diterima)
    - [x] Menampilkan data uang (status 1=>sedang diproses)
    - [x] Edit status data uang (status 2=>ditolak, 3=>diterima)
    - [x] Menampilkan data penyaluran makanan hari ini
    - [x] Tambah data penyaluran makanan hari ini
    - [x] Hapus data penyaluran makanan hari ini
    - [ ] Print data penyaluran makanan hari ini (belum)
    - [x] Menampilkan data penyaluran uang hari ini
    - [x] Tambah data penyaluran uang hari ini
    - [x] Hapus data penyaluran uang hari ini
    - [ ] Print data penyaluran uang hari ini (belum)
    - [x] Menampilkan rekap penyaluran makanan semua waktu/minggu ini/bulan ini/tahun ini
    - [ ] Print rekap penyaluran makanan semua waktu/minggu ini/bulan ini/tahun ini (belum)
    - [x] Menampilkan rekap penyaluran uang semua waktu/minggu ini/bulan ini/tahun ini
    - [ ] Print rekap penyaluran uang semua waktu/minggu ini/bulan ini/tahun ini (belum)
    - [x] Menampilkan data rekening
    - [x] Edit data rekening
    - [x] Hapus data rekening (softdeletes)

7. User
    - [x] Menampilkan data makanan (by user_id)
    - [x] Menampilkan data uang (by user_id)
    - [x] Tambah data donasi makanan
    - [x] Tambah data donasi uang
    - [x] Menampilkan rekap penyaluran makanan bulan ini
    - [x] Menampilkan rekap penyaluran uang bulan ini
    - [x] Menampilkan data penerima

    

    