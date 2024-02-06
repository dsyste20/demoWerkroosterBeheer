-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Gegenereerd op: 06 feb 2024 om 15:00
-- Serverversie: 10.4.27-MariaDB
-- PHP-versie: 8.2.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `werkrooster`
--

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `beschikbaarheid`
--

CREATE TABLE `beschikbaarheid` (
  `id` int(4) NOT NULL,
  `medewerkerID` int(4) NOT NULL,
  `afdeling` varchar(50) NOT NULL,
  `maandag` varchar(25) NOT NULL,
  `dinsdag` varchar(25) NOT NULL,
  `woensdag` varchar(25) NOT NULL,
  `donderdag` varchar(25) NOT NULL,
  `vrijdag` varchar(25) NOT NULL,
  `zaterdag` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Gegevens worden geëxporteerd voor tabel `beschikbaarheid`
--

INSERT INTO `beschikbaarheid` (`id`, `medewerkerID`, `afdeling`, `maandag`, `dinsdag`, `woensdag`, `donderdag`, `vrijdag`, `zaterdag`) VALUES
(1, 22, 'Kassa', '07:00 - 17:00', '17:00 - 21:30', '09:00 - 16:00', 'x', '07:00 - 13:00', 'x'),
(2, 21, 'counter', '07:00 - 17:00', '17:00 - 21:30', '09:00 - 16:00', '07:00 - 13:00', '07:00 - 17:00', 'x'),
(3, 20, 'counter', '17:00 - 21:30', '17:00 - 21:30', '09:00 - 16:00', 'x', '17:00 - 21:30', '17:00 - 21:30'),
(4, 19, 'Kassa', '17:00 - 21:00', '17:00 - 21:30', '09:00 - 16:00', '17:00 - 21:30', '17:00 - 21:30', '12:00 - 17:00'),
(5, 18, 'sco', '17:00 - 21:15', '17:00 - 21:30', '09:00 - 16:00', 'x', '07:00 - 13:00', '12:00 - 17:00'),
(6, 10, 'counter', '17:00 - 21:30', '17:00 - 21:30', '17:00 - 21:00', '17:00 - 21:30', '07:00 - 13:00', '07:00 - 13:00'),
(7, 8, 'Kassa', '07:00 - 17:00', '17:00 - 21:30', '09:00 - 16:00', '12:00 - 17:00', '07:00 - 13:00', '17:00 - 21:30'),
(8, 1, 'counter', 'x', 'x', '17:00 - 21:30', 'x', '07:00 - 17:00', '17:00 - 21:30'),
(9, 4, 'sco', '07:00 - 17:00', '09:00 - 21:30', '07:00 - 16:00', 'x', 'x', 'x'),
(10, 13, 'Kassa', '07:00 - 17:00', '17:00 - 21:30', '09:00 - 16:00', 'x', '07:00 - 13:00', '12:00 - 19:00'),
(11, 2, 'counter', '07:00 - 17:00', '17:00 - 21:30', '09:00 - 16:00', 'x', '07:00 - 13:00', '07:00 - 13:00'),
(12, 9, 'Kassa', '07:00 - 17:00', '17:00 - 21:30', '09:00 - 16:00', 'x', '07:00 - 13:00', '17:00 - 21:15'),
(13, 15, 'Kassa', 'x', '17:00 - 21:30', '09:00 - 16:00', 'x', '07:00 - 13:00', '17:00 - 21:30'),
(14, 6, 'sco', '07:00 - 17:00', '17:00 - 21:30', '09:00 - 16:00', 'x', '07:00 - 13:00', '07:00 - 17:00'),
(15, 11, 'sco', '07:00 - 17:00', '17:00 - 21:30', '09:00 - 16:00', '07:00 - 17:00', '07:00 - 13:00', '21:30'),
(16, 3, 'counter', 'x', '07:00 - 17:00', '17:00 - 21:30', 'x', '17:00 - 21:00', '07:00 - 16:00'),
(17, 5, 'kassa', '07:00 - 21:30', 'x', '07:00 - 21:30', '07:00 - 21:30', '07:00 - 21:30', 'x'),
(18, 7, 'counter', '07:00 - 21:30', '07:00 - 21:30', 'x', 'x', '07:00 - 21:30', '07:00 - 21:30'),
(19, 14, 'sco', '07:00 - 21:30', '07:00 - 21:30', '07:00 - 21:30', '07:00 - 21:30', '07:00 - 21:30', '07:00 - 21:30'),
(20, 16, 'kassa', 'x', '07:00 - 21:30', 'x', ' 07:00 - 21:30', '07:00 - 21:30', 'x'),
(21, 17, 'kassa', 'x', 'x', '07:00 - 21:30', '07:00 - 21:30', 'x', 'x'),
(22, 12, 'kassa', '07:00 - 21:30', 'x', 'x', '07:00 - 21:30', 'x', '07:00 - 21:30'),
(23, 23, 'sco', '07:00 - 17:00', '17:00 - 21:30', '07:00 - 17:00', '17:00 - 21:30', '17:00 - 21:30', '12:00 - 17:00'),
(24, 24, 'counter', '12:00 - 17:00', '07:00 - 17:00', 'x', '07:00 - 17:00', '07:00 - 17:00', '12:00 - 17:00'),
(25, 25, 'kassa', '17:00 - 21:30', '17:00 - 21:30', '12:00 - 17:00', '07:00 - 17:00', 'x', '17:00 - 21:30'),
(26, 26, 'counter', '17:00 - 21:30', 'x', '07:00 - 17:00', '17:00 - 21:30', '17:00 - 21:30', '07:00 - 17:00'),
(27, 27, 'counter', '07:00 - 17:00', 'x', '07:00 - 17:00', '12:00 - 17:00', '12:00 - 17:00', '07:00 - 17:00'),
(28, 28, 'sco', '07:00 - 17:00', 'x', 'x', '12:00 - 17:00', '12:00 - 17:00', '12:00 - 17:00'),
(29, 29, 'kassa', 'x', 'x', '07:00 - 17:00', '07:00 - 17:00', '07:00 - 17:00', '07:00 - 17:00'),
(30, 30, 'kassa', '07:00 - 17:00', '12:00 - 17:00', '12:00 - 17:00', '07:00 - 17:00', '17:00 - 21:30', '17:00 - 21:30'),
(31, 31, 'kassa', '07:00 - 17:00', 'x', '07:00 - 17:00', '17:00 - 21:30', '12:00 - 17:00', 'x'),
(32, 32, 'counter', 'x', 'x', '07:00 - 17:00', '12:00 - 17:00', 'x', '07:00 - 17:00'),
(33, 33, 'kassa', '07:00 - 17:00', 'x', '07:00 - 17:00', '12:00 - 17:00', '12:00 - 17:00', '12:00 - 17:00'),
(34, 34, 'sco', 'x', 'x', '17:00 - 21:30', '17:00 - 21:30', '17:00 - 21:30', '17:00 - 21:30'),
(35, 35, 'sco', '12:00 - 17:00', 'x', '17:00 - 21:30', '07:00 - 17:00', '17:00 - 21:30', 'x'),
(36, 36, 'counter', '17:00 - 21:30', '12:00 - 17:00', 'x', '12:00 - 17:00', '07:00 - 17:00', '17:00 - 21:30'),
(37, 37, 'kassa', 'x', '17:00 - 21:30', '12:00 - 17:00', '12:00 - 17:00', '12:00 - 17:00', '17:00 - 21:30'),
(38, 38, 'kassa', '17:00 - 21:30', '07:00 - 17:00', '12:00 - 17:00', '12:00 - 17:00', '07:00 - 17:00', '07:00 - 17:00'),
(39, 39, 'kassa', '12:00 - 17:00', '12:00 - 17:00', '12:00 - 17:00', '17:00 - 21:30', '17:00 - 21:30', '12:00 - 17:00'),
(40, 40, 'counter', '17:00 - 21:30', 'x', '07:00 - 17:00', '17:00 - 21:30', '07:00 - 17:00', '17:00 - 21:30'),
(41, 41, 'kassa', '17:00 - 21:30', 'x', '17:00 - 21:30', '07:00 - 17:00', '07:00 - 17:00', '07:00 - 17:00'),
(42, 42, 'counter', '12:00 - 17:00', '12:00 - 17:00', '12:00 - 17:00', '12:00 - 17:00', 'x', '17:00 - 21:30');

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `dienstwisseling`
--

CREATE TABLE `dienstwisseling` (
  `id` int(4) NOT NULL,
  `medewerkerID` int(4) NOT NULL,
  `dienst` varchar(50) NOT NULL,
  `reden` text NOT NULL,
  `status` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Gegevens worden geëxporteerd voor tabel `dienstwisseling`
--

INSERT INTO `dienstwisseling` (`id`, `medewerkerID`, `dienst`, `reden`, `status`) VALUES
(1, 1, '2024-02-20', 'Artsbezoek', 'Afgewezen'),
(2, 2, '2024-03-15', 'Familie-evenement', 'Aangevraagd'),
(3, 3, '2024-03-10', 'Onderwijs', 'Aangevraagd'),
(4, 4, '2024-02-05', 'Persoonlijke afspraak', 'Goedgekeurd'),
(5, 5, '2024-02-12', 'Studieverplichtingen', 'Goedgekeurd'),
(6, 6, '2024-03-20', 'Kinderopvang', 'Aangevraagd'),
(7, 7, '2024-04-18', 'Vrijwilligerswerk', 'Aangevraagd'),
(8, 8, '2024-05-25', 'Sportevenement', 'Aangevraagd'),
(9, 9, '2024-06-30', 'Verhuizing', 'Aangevraagd'),
(10, 10, '2024-08-15', 'Reisplannen', 'Aangevraagd');

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `medewerker`
--

CREATE TABLE `medewerker` (
  `id` int(4) NOT NULL,
  `firstname` varchar(50) NOT NULL,
  `lastname` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Gegevens worden geëxporteerd voor tabel `medewerker`
--

INSERT INTO `medewerker` (`id`, `firstname`, `lastname`) VALUES
(1, 'Ana', 'Karina'),
(2, 'Margreet', 'oosten'),
(3, 'Sissy', 'Ambacht'),
(4, 'Alex', 'Kampen'),
(5, 'Jeannet', 'Verhoef'),
(6, 'Karin', 'Grand'),
(7, 'Angelina', 'Storm'),
(8, 'Bianca', 'Beek'),
(9, 'Britt', 'Koedorn'),
(10, 'Damyano', 'Smit'),
(11, 'Danielle', 'Amster'),
(12, 'Ellen', 'Handel'),
(13, 'Emilia', 'Argenti'),
(14, 'Ira', 'Giessen'),
(15, 'Jennifer', 'Barendse'),
(16, 'Juliette', 'Robine'),
(17, 'Lieve', 'Nice'),
(18, 'Vince', 'Anderweg'),
(19, 'Myrthe', 'Wontan'),
(20, 'Riley', 'Vermeulen'),
(21, 'Yvonne', 'Trendy'),
(22, 'Sabrina', 'Carpent'),
(23, 'James', 'Smith'),
(24, 'Mary', 'Johnson'),
(25, 'John', 'Williams'),
(26, 'Patricia', 'Brown'),
(27, 'Robert', 'Jones'),
(28, 'Jennifer', 'Miller'),
(29, 'Michael', 'Davis'),
(30, 'Linda', 'Garcia'),
(31, 'William', 'Rodriguez'),
(32, 'Elizabeth', 'Wilson'),
(33, 'David', 'Martinez'),
(34, 'Barbara', 'Anderson'),
(35, 'Richard', 'Taylor'),
(36, 'Susan', 'Thomas'),
(37, 'Joseph', 'Hernandez'),
(38, 'Jessica', 'Moore'),
(39, 'Thomas', 'Martin'),
(40, 'Sarah', 'Jackson'),
(41, 'Charles', 'Thompson'),
(42, 'Karen', 'White');

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `rollen`
--

CREATE TABLE `rollen` (
  `id` int(11) NOT NULL,
  `rol` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Gegevens worden geëxporteerd voor tabel `rollen`
--

INSERT INTO `rollen` (`id`, `rol`) VALUES
(1, 'Kassa 1'),
(2, 'Kassa 2'),
(3, 'Kassa 3'),
(4, 'Sco 1'),
(5, 'sco 2'),
(6, 'counter 1'),
(7, 'counter 2'),
(8, 'bestellingen');

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `rooster_6`
--

CREATE TABLE `rooster_6` (
  `id` int(11) NOT NULL,
  `periode` varchar(35) DEFAULT NULL,
  `maandag` varchar(250) DEFAULT NULL,
  `dinsdag` varchar(250) DEFAULT NULL,
  `woensdag` varchar(250) DEFAULT NULL,
  `donderdag` varchar(250) DEFAULT NULL,
  `vrijdag` varchar(250) DEFAULT NULL,
  `zaterdag` varchar(250) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Gegevens worden geëxporteerd voor tabel `rooster_6`
--

INSERT INTO `rooster_6` (`id`, `periode`, `maandag`, `dinsdag`, `woensdag`, `donderdag`, `vrijdag`, `zaterdag`) VALUES
(1, 'Week 6', 'Ellen Handel, Ira Giessen, Charles Thompson, Bianca Beek, Riley Vermeulen, Karin Grand, Jessica Moore, Margreet oosten', 'Sissy Ambacht, Ira Giessen, Sabrina Carpent, Jessica Moore, Danielle Amster, Mary Johnson, Alex Kampen, Angelina Storm', 'Michael Davis, James Smith, Sissy Ambacht, William Rodriguez, Barbara Anderson, Robert Jones, Jeannet Verhoef, Ira Giessen', 'Richard Taylor, Mary Johnson, Myrthe Wontan, Michael Davis, Damyano Smit, Lieve Nice, Sarah Jackson, Susan Thomas', 'Charles Thompson, Michael Davis, Riley Vermeulen, Yvonne Trendy, Juliette Robine, Jeannet Verhoef, Thomas Martin, Sarah Jackson', 'Karin Grand, Ellen Handel, Riley Vermeulen, Michael Davis, Susan Thomas, Robert Jones, Angelina Storm, Ira Giessen');

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `rooster_7`
--

CREATE TABLE `rooster_7` (
  `id` int(11) NOT NULL,
  `periode` varchar(35) DEFAULT NULL,
  `maandag` varchar(250) DEFAULT NULL,
  `dinsdag` varchar(250) DEFAULT NULL,
  `woensdag` varchar(250) DEFAULT NULL,
  `donderdag` varchar(250) DEFAULT NULL,
  `vrijdag` varchar(250) DEFAULT NULL,
  `zaterdag` varchar(250) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Gegevens worden geëxporteerd voor tabel `rooster_7`
--

INSERT INTO `rooster_7` (`id`, `periode`, `maandag`, `dinsdag`, `woensdag`, `donderdag`, `vrijdag`, `zaterdag`) VALUES
(1, 'Week 7', 'Ellen Handel, Angelina Storm, Patricia Brown, Britt Koedorn, Riley Vermeulen, Danielle Amster, Jeannet Verhoef, Yvonne Trendy', 'Juliette Robine, Angelina Storm, Damyano Smit, Ira Giessen, Karin Grand, Jessica Moore, Vince Anderweg, Linda Garcia', 'Robert Jones, Jeannet Verhoef, Sissy Ambacht, Patricia Brown, Barbara Anderson, Elizabeth Wilson, Ira Giessen, Michael Davis', 'Mary Johnson, Jeannet Verhoef, Patricia Brown, Ira Giessen, Thomas Martin, Michael Davis, Barbara Anderson, Jennifer Miller', 'Jessica Moore, Mary Johnson, Juliette Robine, Ana Karina, Riley Vermeulen, Michael Davis, Patricia Brown, Ira Giessen', 'Patricia Brown, Karin Grand, Linda Garcia, Ira Giessen, Susan Thomas, Elizabeth Wilson, Jennifer Barendse, Charles Thompson');

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `users`
--

CREATE TABLE `users` (
  `id` int(4) NOT NULL,
  `name` varchar(50) NOT NULL,
  `username` varchar(30) NOT NULL,
  `password` varchar(25) NOT NULL,
  `rol` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Gegevens worden geëxporteerd voor tabel `users`
--

INSERT INTO `users` (`id`, `name`, `username`, `password`, `rol`) VALUES
(1, 'Admin', 'testAdmin', 'DitisAdmin', 'admin'),
(2, 'User', 'testUser', 'DitisUser', 'user');

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `vakantieaanvragen`
--

CREATE TABLE `vakantieaanvragen` (
  `id` int(4) NOT NULL,
  `medewerkerID` int(4) NOT NULL,
  `aanvraag` varchar(50) NOT NULL,
  `status` varchar(20) NOT NULL,
  `aanvraagDatum` date NOT NULL,
  `eindDatum` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Gegevens worden geëxporteerd voor tabel `vakantieaanvragen`
--

INSERT INTO `vakantieaanvragen` (`id`, `medewerkerID`, `aanvraag`, `status`, `aanvraagDatum`, `eindDatum`) VALUES
(1, 1, 'Vakantie naar Griekenland', 'In behandeling', '2024-04-19', '2024-04-24'),
(2, 6, 'Vakantie naar Spanje', 'Goedgekeurd', '2024-03-08', '2024-03-22'),
(3, 3, 'Vakantie naar Frankrijk', 'In behandeling', '2024-08-10', '2024-08-17'),
(4, 11, 'Vakantie naar Portugal', 'Afgewezen', '2024-06-18', '2024-06-23'),
(5, 20, 'Vakantie naar Noorwegen', 'In behandeling', '2024-07-22', '2024-07-27'),
(6, 8, 'Vakantie naar Brazilië', 'In behandeling', '2024-12-13', '2024-12-27'),
(7, 18, 'Vakantie naar Canada', 'In behandeling', '2024-10-17', '2024-10-24'),
(8, 15, 'Vakantie naar België', 'In behandeling', '2024-07-24', '2024-07-29'),
(9, 9, 'Vakantie naar China', 'Afgewezen', '2024-08-14', '2024-08-28'),
(10, 10, 'Vakantie naar Rusland', 'Goedgekeurd', '2024-02-09', '2024-02-16'),
(11, 13, 'Vakantie naar Curaçao', 'Goedgekeurd', '2024-01-29', '2024-02-07');

--
-- Indexen voor geëxporteerde tabellen
--

--
-- Indexen voor tabel `beschikbaarheid`
--
ALTER TABLE `beschikbaarheid`
  ADD PRIMARY KEY (`id`);

--
-- Indexen voor tabel `dienstwisseling`
--
ALTER TABLE `dienstwisseling`
  ADD PRIMARY KEY (`id`);

--
-- Indexen voor tabel `medewerker`
--
ALTER TABLE `medewerker`
  ADD PRIMARY KEY (`id`);

--
-- Indexen voor tabel `rollen`
--
ALTER TABLE `rollen`
  ADD PRIMARY KEY (`id`);

--
-- Indexen voor tabel `rooster_6`
--
ALTER TABLE `rooster_6`
  ADD PRIMARY KEY (`id`);

--
-- Indexen voor tabel `rooster_7`
--
ALTER TABLE `rooster_7`
  ADD PRIMARY KEY (`id`);

--
-- Indexen voor tabel `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- Indexen voor tabel `vakantieaanvragen`
--
ALTER TABLE `vakantieaanvragen`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT voor geëxporteerde tabellen
--

--
-- AUTO_INCREMENT voor een tabel `beschikbaarheid`
--
ALTER TABLE `beschikbaarheid`
  MODIFY `id` int(4) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=43;

--
-- AUTO_INCREMENT voor een tabel `dienstwisseling`
--
ALTER TABLE `dienstwisseling`
  MODIFY `id` int(4) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT voor een tabel `medewerker`
--
ALTER TABLE `medewerker`
  MODIFY `id` int(4) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=43;

--
-- AUTO_INCREMENT voor een tabel `rollen`
--
ALTER TABLE `rollen`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT voor een tabel `rooster_6`
--
ALTER TABLE `rooster_6`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT voor een tabel `rooster_7`
--
ALTER TABLE `rooster_7`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT voor een tabel `users`
--
ALTER TABLE `users`
  MODIFY `id` int(4) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT voor een tabel `vakantieaanvragen`
--
ALTER TABLE `vakantieaanvragen`
  MODIFY `id` int(4) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
