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
1. Autentikasi
- Login Multiuser menggunkan role (1=>Admin, 2=>User)
- Tiruan Cookie dengan membuat file xml yang berisi data-data user secara otomatis ketika login dan menghapusnya ketika logout sehingga user tidak perlu login ulang jika user menutup aplikasi 