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

## Fitur
1. Login Multiuser : role (1=>Admin, 2=>User)
2. Registasi
3. Cookie : menyimpan data user dalam file xml (jika user menutup aplikasi tanpa logout maka user bisa membuka aplikasi tanpa harus login kembali)
4. SoftDeletes : agar data tidak benar-benar terhapus dari database
5. Edit profil dan ubah password
6. Untuk Admin
    - Menampilkan data donatur
    - Pencarian data donatur
    - Menampilkan data penerima
    - Pencarian data penerima
    - Tambah penerima
    - Edit penerima
    - Hapus penerima (softdeletes)
    - Menampilkan data makanan (status 1=>sedang diproses)
    - Edit status data makanan (status 2=>ditolak, 3=>diterima)
    - Menampilkan data uang (status 1=>sedang diproses)
    - Edit status data uang (status 2=>ditolak, 3=>diterima)
    - Menampilkan data penyaluran makanan hari ini
    - Tambah data penyaluran makanan hari ini
    - Hapus data penyaluran makanan hari ini
    - Print data penyaluran makanan hari ini (belum)
    - Menampilkan data penyaluran uang hari ini
    - Tambah data penyaluran uang hari ini
    - Hapus data penyaluran uang hari ini
    - Print data penyaluran uang hari ini (belum)
    - Menampilkan rekap penyaluran makanan semua waktu/minggu ini/bulan ini/tahun ini
    - Print rekap penyaluran makanan semua waktu/minggu ini/bulan ini/tahun ini (belum)
    - Menampilkan rekap penyaluran uang semua waktu/minggu ini/bulan ini/tahun ini
    - Print rekap penyaluran uang semua waktu/minggu ini/bulan ini/tahun ini (belum)
    - Menampilkan data rekening
    - Edit data rekening
    - Hapus data rekening (softdeletes)

7. User
    - Menampilkan data makanan (by user_id)
    - Menampilkan data uang (by user_id)
    - Tambah data donasi makanan
    - Tambah data donasi uang
    - Menampilkan rekap penyaluran makanan bulan ini
    - Menampilkan rekap penyaluran uang bulan ini
    - Menampilkan data penerima

    

    