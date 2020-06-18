# Aplikasi Pengelolaan Donasi Uang & Makanan
Project Pemrograman Berorientasi Obyek 2 Menggunakan Metode MVC

## Apa Saja Yang Dipakai ?
1. __JDK__           : JDK 8 (8u181)
2. __IDE__           : Apache NetBeans 11.3
3. __User Interface__: Java FX, Jfoenix 8.0.8, SceneBuilder 8.5.0
4. __Database__      : XAMPP 3.2.4, MariaDB, mysql connector java 5.1.23

## Struktur Package
- __app__         : Berisi Main Class 
- __controllers__ : Berisi class-class untuk mengatur view, mendapatkan data yang dibutukan view dari model, dan tempat semua logic
- __helpers__     : Berisi class-class yang menyediakan fungsi-fungsi yang dibutuhkan dan sering dipakai
- __models__      : Berisi class-class yang menyediakan data dari database
- __views__       : Berisi semua file-file FXML

## Fitur
1. Login Multiuser : role (1=>Admin, 2=>User)
2. Cookie : menyimpan data user dalam file xml (mirip fitur ingat saya pada website)
3. SoftDeletes : agar data tidak benar-benar terhapus dari database
4. Edit profil dan ubah password
3. Admin
    - Melihat, pencarian data donatur
    - Melihat, Membuat, mengedit, menghapus(softdeletes), dan pencarian data penerima
    - Melihat data makanan dengan status (1 => sedang diproses) dan mengubah statusnya (2 => ditolak, 3=>diterima)
    - Melihat data makanan dengan status (2 => ditolak, 3=>diterima)
    - Melihat data uang dengan status (1 => sedang diproses) dan mengubah statusnya (2 => ditolak, 3=>diterima)
    - Melihat data uang dengan status (2 => ditolak, 3=>diterima)
    - Membuat dan menghapus data penyaluran makanan (di hari itu)
    - Membuat dan menghapus data penyaluran uang (di hari itu)
    - Membuat, mengubah, dan menghapus(softdeletes) data rekening admin 
    -
5. User
    - Membuat data donasi makanan
    - Membuat data donasi uang
    - Melihat semua riwayat donasi pribadi
    - Melihat data penerima
    - 
    

    