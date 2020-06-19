-- phpMyAdmin SQL Dump
-- version 5.0.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 19 Jun 2020 pada 12.25
-- Versi server: 10.4.11-MariaDB
-- Versi PHP: 7.4.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `pbo_donasi`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `makanan`
--

CREATE TABLE `makanan` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `status_id` int(11) NOT NULL DEFAULT 1,
  `nama` varchar(100) NOT NULL,
  `jumlah_awal` int(11) NOT NULL,
  `jumlah` int(11) NOT NULL,
  `expired_date` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `keterangan` mediumtext DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `makanan`
--

INSERT INTO `makanan` (`id`, `user_id`, `status_id`, `nama`, `jumlah_awal`, `jumlah`, `expired_date`, `keterangan`, `created_at`, `updated_at`) VALUES
(1, 2, 3, 'Sate Kambing + Gule', 100, 95, '2020-06-19 08:54:27', 'Nasi kotak isi sate kambing dan gule', '2020-06-19 08:44:00', '2020-06-19 08:54:27'),
(2, 2, 3, 'Nasi Padang', 45, 38, '2020-06-19 08:54:11', 'Nasi Bungkus', '2020-06-19 08:46:49', '2020-06-19 08:54:11'),
(3, 3, 1, 'Soto Ayam', 123, 123, '2020-06-30 07:37:00', 'Bungkus plastik tanpa nasi', '2020-06-19 09:37:39', '2020-06-19 09:37:39');

-- --------------------------------------------------------

--
-- Struktur dari tabel `penerima`
--

CREATE TABLE `penerima` (
  `id` int(11) NOT NULL,
  `nama` varchar(50) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `no_telp` varchar(15) NOT NULL,
  `alamat` mediumtext NOT NULL,
  `jumlah_orang` int(11) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `deleted_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `penerima`
--

INSERT INTO `penerima` (`id`, `nama`, `email`, `no_telp`, `alamat`, `jumlah_orang`, `created_at`, `updated_at`, `deleted_at`) VALUES
(1, 'Ade Irwan Chandra', 'adeir32@gmail.com', '087885680319', 'JL. KH Ahmad Sidiq No. 81 Kelurahan Jember Kidu, Jember Kidul, Kec. Kaliwates, Kabupaten Jember, Jawa Timur 68131', 3, '2020-06-19 08:26:58', '2020-06-19 08:29:43', NULL),
(2, 'Ratna Devi Lesmono', 'ratnadevi67@yahoo.com', '081331566821', 'JL Gajah Mada No. 54 Kec. Kaliwates, Kabupaten Jember, Jawa Timur 68131\n', 5, '2020-06-19 08:28:35', '2020-06-19 08:28:35', NULL),
(3, 'Cinta Nirmala', 'nirmala77@gmail.com', '087662781990', 'JL Bedadung, no 7 Kel/Ds. Sumbersari Kec. Sumber Sari - Jember Jawa Timur', 4, '2020-06-19 08:31:00', '2020-06-19 08:31:00', NULL),
(4, 'Sofyan Sauri', 'Tidak Punya Email', '085232776191', 'Gg. Central No. 60 Patrang Jember, Baratan Wetan, Baratan, Patrang, Jember Regency, East Java 68112', 5, '2020-06-19 08:32:23', '2020-06-19 08:32:23', NULL),
(5, 'Firdaus', 'Tidak Punya Email', '08722618101', 'Jl PB Sudirman 11 Kec. Patrang, Kabupaten Jember, Jawa Timur\n', 3, '2020-06-19 08:35:01', '2020-06-19 08:40:56', NULL),
(6, 'Panti Asuhan Raudlatul Akbar', 'raudlatulakbar@yahoo.com', '081223561099', ' Jl Basuki Rahmat Kelurahan Tegal Besar Kec Kaliwates Kab Jember', 120, '2020-06-19 08:39:19', '2020-06-19 08:39:19', NULL);

-- --------------------------------------------------------

--
-- Struktur dari tabel `penyaluran_makanan`
--

CREATE TABLE `penyaluran_makanan` (
  `id` int(11) NOT NULL,
  `penerima_id` int(11) NOT NULL,
  `makanan_id` int(11) NOT NULL,
  `jumlah` int(11) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `penyaluran_makanan`
--

INSERT INTO `penyaluran_makanan` (`id`, `penerima_id`, `makanan_id`, `jumlah`, `created_at`, `updated_at`) VALUES
(1, 1, 2, 3, '2020-06-19 08:51:20', '2020-06-19 08:51:20'),
(2, 2, 1, 5, '2020-06-19 08:52:11', '2020-06-19 08:52:11'),
(3, 3, 2, 4, '2020-06-19 08:54:11', '2020-06-19 08:54:11');

-- --------------------------------------------------------

--
-- Struktur dari tabel `penyaluran_uang`
--

CREATE TABLE `penyaluran_uang` (
  `id` int(11) NOT NULL,
  `penerima_id` int(11) NOT NULL,
  `jumlah` bigint(20) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `penyaluran_uang`
--

INSERT INTO `penyaluran_uang` (`id`, `penerima_id`, `jumlah`, `created_at`, `updated_at`) VALUES
(1, 6, 2000000, '2020-06-19 08:56:54', '2020-06-19 08:56:54');

-- --------------------------------------------------------

--
-- Struktur dari tabel `rekening`
--

CREATE TABLE `rekening` (
  `id` int(11) NOT NULL,
  `nama_bank` varchar(50) NOT NULL,
  `atas_nama` varchar(50) NOT NULL,
  `no_rekening` varchar(25) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `deleted_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `rekening`
--

INSERT INTO `rekening` (`id`, `nama_bank`, `atas_nama`, `no_rekening`, `created_at`, `updated_at`, `deleted_at`) VALUES
(1, 'BRI', 'Jendra Bayu Nugraha', '92829229921', '2020-06-19 08:44:50', '2020-06-19 08:44:50', NULL),
(2, 'Bank Syariah Mandiri', 'Devita Risky', '9910002333', '2020-06-19 10:23:09', '2020-06-19 10:23:09', NULL);

-- --------------------------------------------------------

--
-- Struktur dari tabel `status`
--

CREATE TABLE `status` (
  `id` int(11) NOT NULL,
  `nama` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `status`
--

INSERT INTO `status` (`id`, `nama`) VALUES
(1, 'Sedang Diproses'),
(2, 'Ditolak'),
(3, 'Diterima');

-- --------------------------------------------------------

--
-- Struktur dari tabel `total_uang`
--

CREATE TABLE `total_uang` (
  `id` int(11) NOT NULL,
  `total` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `total_uang`
--

INSERT INTO `total_uang` (`id`, `total`) VALUES
(1, 350000);

-- --------------------------------------------------------

--
-- Struktur dari tabel `uang`
--

CREATE TABLE `uang` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `rekening_id` int(11) NOT NULL,
  `status_id` int(11) NOT NULL DEFAULT 1,
  `nama_bank` varchar(50) NOT NULL,
  `atas_nama` varchar(50) NOT NULL,
  `no_rekening` varchar(25) NOT NULL DEFAULT '1',
  `jumlah` bigint(20) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `uang`
--

INSERT INTO `uang` (`id`, `user_id`, `rekening_id`, `status_id`, `nama_bank`, `atas_nama`, `no_rekening`, `jumlah`, `created_at`, `updated_at`) VALUES
(1, 2, 1, 1, 'BCA', 'Rahmat Rudy', '00900900912', 1250000, '2020-06-19 08:45:58', '2020-06-19 08:45:58'),
(2, 2, 1, 3, 'BCA', 'Rahmat Rudy', '00900900912', 2350000, '2020-06-19 08:46:22', '2020-06-19 08:56:32'),
(3, 3, 1, 1, 'BNI', 'Vina', '00217929099', 3523000, '2020-06-19 09:36:45', '2020-06-19 09:36:45');

-- --------------------------------------------------------

--
-- Struktur dari tabel `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `nama` varchar(50) NOT NULL,
  `email` varchar(255) NOT NULL,
  `no_telp` varchar(15) NOT NULL,
  `alamat` mediumtext NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` tinyint(4) DEFAULT 2 COMMENT '1=>Admin\r\n2=>User',
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `created_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `users`
--

INSERT INTO `users` (`id`, `nama`, `email`, `no_telp`, `alamat`, `password`, `role`, `updated_at`, `created_at`) VALUES
(1, 'Jendra Bayu Nugraha', 'jendra455@gmail.com', '08755465721009', 'Jalan Bangka VII No.12 Kec. Sumbersari Kab. Jember, Jawa Timur', '21232f297a57a5a743894a0e4a801fc3', 1, '2020-06-19 08:21:51', '2020-06-19 08:22:24'),
(2, 'Rahmat Rudy Irawan', 'rudytotok@yahoo.com', '089776421990', 'Jalan Panjaitan Gg Melati No 12 Kel/Kec Sumbersari Kabupaten Jember Jawa Timur', '827ccb0eea8a706c4c34a16891f84e7b', 2, '2020-06-19 08:42:28', '2020-06-19 08:42:28'),
(3, 'Leviana', 'leviana@yahoo.com', '08766522345', 'Jalan Pajajaran Blok E No.5 Perum Bukit Permai Jember', '827ccb0eea8a706c4c34a16891f84e7b', 2, '2020-06-19 09:30:16', '2020-06-19 09:30:16');

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `makanan`
--
ALTER TABLE `makanan`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `status_id` (`status_id`);

--
-- Indeks untuk tabel `penerima`
--
ALTER TABLE `penerima`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `no_telp` (`no_telp`),
  ADD UNIQUE KEY `email_2` (`email`,`no_telp`);

--
-- Indeks untuk tabel `penyaluran_makanan`
--
ALTER TABLE `penyaluran_makanan`
  ADD PRIMARY KEY (`id`),
  ADD KEY `makanan_id` (`makanan_id`),
  ADD KEY `penerima_id` (`penerima_id`);

--
-- Indeks untuk tabel `penyaluran_uang`
--
ALTER TABLE `penyaluran_uang`
  ADD PRIMARY KEY (`id`),
  ADD KEY `penerima_id` (`penerima_id`);

--
-- Indeks untuk tabel `rekening`
--
ALTER TABLE `rekening`
  ADD PRIMARY KEY (`id`);

--
-- Indeks untuk tabel `status`
--
ALTER TABLE `status`
  ADD PRIMARY KEY (`id`);

--
-- Indeks untuk tabel `total_uang`
--
ALTER TABLE `total_uang`
  ADD PRIMARY KEY (`id`);

--
-- Indeks untuk tabel `uang`
--
ALTER TABLE `uang`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `rekening_id` (`rekening_id`),
  ADD KEY `status_id` (`status_id`);

--
-- Indeks untuk tabel `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email` (`email`),
  ADD UNIQUE KEY `no_telp` (`no_telp`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `makanan`
--
ALTER TABLE `makanan`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT untuk tabel `penerima`
--
ALTER TABLE `penerima`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT untuk tabel `penyaluran_makanan`
--
ALTER TABLE `penyaluran_makanan`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT untuk tabel `penyaluran_uang`
--
ALTER TABLE `penyaluran_uang`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT untuk tabel `rekening`
--
ALTER TABLE `rekening`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT untuk tabel `status`
--
ALTER TABLE `status`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT untuk tabel `total_uang`
--
ALTER TABLE `total_uang`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT untuk tabel `uang`
--
ALTER TABLE `uang`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT untuk tabel `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Ketidakleluasaan untuk tabel pelimpahan (Dumped Tables)
--

--
-- Ketidakleluasaan untuk tabel `makanan`
--
ALTER TABLE `makanan`
  ADD CONSTRAINT `makanan_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `makanan_ibfk_2` FOREIGN KEY (`status_id`) REFERENCES `status` (`id`) ON DELETE CASCADE;

--
-- Ketidakleluasaan untuk tabel `penyaluran_makanan`
--
ALTER TABLE `penyaluran_makanan`
  ADD CONSTRAINT `penyaluran_makanan_ibfk_1` FOREIGN KEY (`makanan_id`) REFERENCES `makanan` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `penyaluran_makanan_ibfk_2` FOREIGN KEY (`penerima_id`) REFERENCES `penerima` (`id`) ON DELETE CASCADE;

--
-- Ketidakleluasaan untuk tabel `penyaluran_uang`
--
ALTER TABLE `penyaluran_uang`
  ADD CONSTRAINT `penyaluran_uang_ibfk_1` FOREIGN KEY (`penerima_id`) REFERENCES `penerima` (`id`) ON DELETE CASCADE;

--
-- Ketidakleluasaan untuk tabel `uang`
--
ALTER TABLE `uang`
  ADD CONSTRAINT `uang_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `uang_ibfk_2` FOREIGN KEY (`rekening_id`) REFERENCES `rekening` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `uang_ibfk_3` FOREIGN KEY (`status_id`) REFERENCES `status` (`id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
