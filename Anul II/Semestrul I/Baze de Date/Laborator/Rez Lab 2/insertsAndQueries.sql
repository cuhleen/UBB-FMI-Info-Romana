INSERT INTO Sala (id, nume) VALUES
(1, 'Gravity'),
(2, 'Skai'),
(3, 'Natural High');

INSERT INTO LocatieSala (id_sala, localitate, oras, strada, numar) VALUES
(1, 'Cluj', 'Cluj-Napoca', 'Drumul Sf. Ioan', 1),
(2, 'Cluj', 'Cluj-Napoca', 'Calea Baciului', 3),
(3, 'Brasov', 'Brasov', 'Strada Panselelor', 1);

INSERT INTO Orar (id_sala, ora_deschidere, ora_inchidere) VALUES
(1, '7', '22'),
(2, '13', '19'),
(3, '16', '22');

INSERT INTO Ruta (id, dificultate, culoare_prize, nr_prize, id_sala) VALUES

-- Sala 1

(1, 1, 'Rosu', 6, 1),
(2, 2, 'Oranj', 9, 1),
(3, 3, 'Galben', 11, 1),
(4, 4, 'Verde', 6, 1),
(5, 5, 'Albastru', 9, 1),
(6, 6, 'Indigo', 11, 1),
(7, 7, 'Violet', 6, 1),

-- Sala 2

(8, 7, 'Rosu', 5, 2),
(9, 6, 'Oranj', 8, 2),
(10, 5, 'Galben', 10, 2),
(11, 4, 'Verde', 5, 2),
(12, 3, 'Albastru', 8, 2),
(13, 2, 'Indigo', 10, 2),
(14, 1, 'Violet', 5, 2),

-- Sala 3

(15, 1, 'Rosu', 6, 3),
(16, 7, 'Oranj', 8, 3),
(17, 2, 'Galben', 11, 3),
(18, 3, 'Verde', 5, 3),
(19, 6, 'Albastru', 9, 3),
(20, 5, 'Indigo', 10, 3),
(21, 4, 'Violet', 5, 3);

INSERT INTO Priza (id, culoare, tip, id_ruta) VALUES

-- tipuri: sloper, pocket, pinch, jug, volume, foot

-------------------------------------------------------- SALA 1

-- Ruta 1: Rosu, 6 prize

(1, 'Rosu', 'jug', 1),
(2, 'Rosu', 'pinch', 1),
(3, 'Rosu', 'sloper', 1),
(4, 'Rosu', 'pocket', 1),
(5, 'Rosu', 'foot', 1),
(6, 'Rosu', 'volume', 1),

-- Ruta 2: Oranj, 9 prize

(7, 'Oranj', 'pinch', 2),
(8, 'Oranj', 'jug', 2),
(9, 'Oranj', 'pocket', 2),
(10, 'Oranj', 'foot', 2),
(11, 'Oranj', 'sloper', 2),
(12, 'Oranj', 'pinch', 2),
(13, 'Oranj', 'jug', 2),
(14, 'Oranj', 'volume', 2),
(15, 'Oranj', 'foot', 2),

-- Ruta 3: Galben, 11 prize

(16, 'Galben', 'sloper', 3),
(17, 'Galben', 'pocket', 3),
(18, 'Galben', 'jug', 3),
(19, 'Galben', 'foot', 3),
(20, 'Galben', 'pinch', 3),
(21, 'Galben', 'sloper', 3),
(22, 'Galben', 'volume', 3),
(23, 'Galben', 'foot', 3),
(24, 'Galben', 'jug', 3),
(25, 'Galben', 'pinch', 3),
(26, 'Galben', 'pocket', 3),

-- Ruta 4: Verde, 6 prize

(27, 'Verde', 'foot', 4),
(28, 'Verde', 'sloper', 4),
(29, 'Verde', 'jug', 4),
(30, 'Verde', 'pinch', 4),
(31, 'Verde', 'volume', 4),
(32, 'Verde', 'pocket', 4),

-- Ruta 5: Albastru, 9 prize

(33, 'Albastru', 'jug', 5),
(34, 'Albastru', 'sloper', 5),
(35, 'Albastru', 'pinch', 5),
(36, 'Albastru', 'pocket', 5),
(37, 'Albastru', 'foot', 5),
(38, 'Albastru', 'volume', 5),
(39, 'Albastru', 'jug', 5),
(40, 'Albastru', 'pocket', 5),
(41, 'Albastru', 'foot', 5),

-- Ruta 6: Indigo, 11 prize

(42, 'Indigo', 'sloper', 6),
(43, 'Indigo', 'pocket', 6),
(44, 'Indigo', 'jug', 6),
(45, 'Indigo', 'foot', 6),
(46, 'Indigo', 'volume', 6),
(47, 'Indigo', 'pinch', 6),
(48, 'Indigo', 'jug', 6),
(49, 'Indigo', 'foot', 6),
(50, 'Indigo', 'pocket', 6),
(51, 'Indigo', 'sloper', 6),
(52, 'Indigo', 'volume', 6),

-- Ruta 7: Violet, 6 prize

(53, 'Violet', 'jug', 7),
(54, 'Violet', 'pocket', 7),
(55, 'Violet', 'foot', 7),
(56, 'Violet', 'volume', 7),
(57, 'Violet', 'pinch', 7),
(58, 'Violet', 'sloper', 7),

-------------------------------------------------------- SALA 2

-- Ruta 8: Rosu, 5 prize

(59, 'Rosu', 'pinch', 8),
(60, 'Rosu', 'sloper', 8),
(61, 'Rosu', 'jug', 8),
(62, 'Rosu', 'foot', 8),
(63, 'Rosu', 'pocket', 8),

-- Ruta 9: Oranj, 8 prize

(64, 'Oranj', 'volume', 9),
(65, 'Oranj', 'foot', 9),
(66, 'Oranj', 'jug', 9),
(67, 'Oranj', 'pinch', 9),
(68, 'Oranj', 'pocket', 9),
(69, 'Oranj', 'sloper', 9),
(70, 'Oranj', 'foot', 9),
(71, 'Oranj', 'jug', 9),

-- Ruta 10: Galben, 10 prize

(72, 'Galben', 'pocket', 10),
(73, 'Galben', 'jug', 10),
(74, 'Galben', 'pinch', 10),
(75, 'Galben', 'sloper', 10),
(76, 'Galben', 'volume', 10),
(77, 'Galben', 'foot', 10),
(78, 'Galben', 'pocket', 10),
(79, 'Galben', 'jug', 10),
(80, 'Galben', 'pinch', 10),
(81, 'Galben', 'foot', 10),

-- Ruta 11: Verde, 5 prize

(82, 'Verde', 'pinch', 11),
(83, 'Verde', 'jug', 11),
(84, 'Verde', 'pocket', 11),
(85, 'Verde', 'sloper', 11),
(86, 'Verde', 'foot', 11),

-- Ruta 12: Albastru, 8 prize

(87, 'Albastru', 'jug', 12),
(88, 'Albastru', 'foot', 12),
(89, 'Albastru', 'pocket', 12),
(90, 'Albastru', 'pinch', 12),
(91, 'Albastru', 'sloper', 12),
(92, 'Albastru', 'volume', 12),
(93, 'Albastru', 'foot', 12),
(94, 'Albastru', 'jug', 12),

-- Ruta 13: Indigo, 10 prize

(95, 'Indigo', 'foot', 13),
(96, 'Indigo', 'sloper', 13),
(97, 'Indigo', 'pocket', 13),
(98, 'Indigo', 'jug', 13),
(99, 'Indigo', 'volume', 13),
(100, 'Indigo', 'pinch', 13),
(101, 'Indigo', 'foot', 13),
(102, 'Indigo', 'jug', 13),
(103, 'Indigo', 'pocket', 13),
(104, 'Indigo', 'sloper', 13),

-- Ruta 14: Violet, 5 prize

(105, 'Violet', 'jug', 14),
(106, 'Violet', 'foot', 14),
(107, 'Violet', 'pinch', 14),
(108, 'Violet', 'sloper', 14),
(109, 'Violet', 'pocket', 14),

-------------------------------------------------------- SALA 3

-- Ruta 15: Rosu, 6 prize

(110, 'Rosu', 'jug', 15),
(111, 'Rosu', 'pocket', 15),
(112, 'Rosu', 'pinch', 15),
(113, 'Rosu', 'foot', 15),
(114, 'Rosu', 'sloper', 15),
(115, 'Rosu', 'volume', 15),

-- Ruta 16: Oranj, 8 prize

(116, 'Oranj', 'foot', 16),
(117, 'Oranj', 'jug', 16),
(118, 'Oranj', 'pocket', 16),
(119, 'Oranj', 'volume', 16),
(120, 'Oranj', 'sloper', 16),
(121, 'Oranj', 'pinch', 16),
(122, 'Oranj', 'foot', 16),
(123, 'Oranj', 'jug', 16),

-- Ruta 17: Galben, 11 prize

(124, 'Galben', 'pinch', 17),
(125, 'Galben', 'pocket', 17),
(126, 'Galben', 'foot', 17),
(127, 'Galben', 'jug', 17),
(128, 'Galben', 'volume', 17),
(129, 'Galben', 'sloper', 17),
(130, 'Galben', 'foot', 17),
(131, 'Galben', 'jug', 17),
(132, 'Galben', 'pinch', 17),
(133, 'Galben', 'pocket', 17),
(134, 'Galben', 'sloper', 17),

-- Ruta 18: Verde, 5 prize

(135, 'Verde', 'foot', 18),
(136, 'Verde', 'jug', 18),
(137, 'Verde', 'pocket', 18),
(138, 'Verde', 'pinch', 18),
(139, 'Verde', 'sloper', 18),

-- Ruta 19: Albastru, 9 prize

(140, 'Albastru', 'jug', 19),
(141, 'Albastru', 'foot', 19),
(142, 'Albastru', 'pocket', 19),
(143, 'Albastru', 'pinch', 19),
(144, 'Albastru', 'sloper', 19),
(145, 'Albastru', 'volume', 19),
(146, 'Albastru', 'foot', 19),
(147, 'Albastru', 'jug', 19),
(148, 'Albastru', 'pocket', 19),

-- Ruta 20: Indigo, 10 prize

(149, 'Indigo', 'jug', 20),
(150, 'Indigo', 'foot', 20),
(151, 'Indigo', 'pocket', 20),
(152, 'Indigo', 'pinch', 20),
(153, 'Indigo', 'volume', 20),
(154, 'Indigo', 'sloper', 20),
(155, 'Indigo', 'foot', 20),
(156, 'Indigo', 'jug', 20),
(157, 'Indigo', 'pocket', 20),
(158, 'Indigo', 'pinch', 20),

-- Ruta 21: Violet, 5 prize

(159, 'Violet', 'sloper', 21),
(160, 'Violet', 'foot', 21),
(161, 'Violet', 'jug', 21),
(162, 'Violet', 'pinch', 21),
(163, 'Violet', 'pocket', 21);

----------------------------------------------------------------------------------------------------------------------------

INSERT INTO Staff (id, nume, prenume, nr_tel) VALUES
(1, 'Tomulescu', 'Doina', '0770000001'),
(2, 'Dragan', 'Mara', '0770000002'),
(3, 'Almasan', 'Horia', '0770000003'),
(4, 'Munteanu', 'Ionut', '0770000004'),
(5, 'Stoian', 'Vlad', '0770000005'),
(6, 'Pitorac', 'Victor', '0770000006');

INSERT INTO SalaStaff (id_sala, id_staff, rol) VALUES
-- Sala 1

(1, 1, 'Director'),
(1, 2, 'Route Setter'),
(1, 3, 'Route Setter'),
(1, 4, 'Voluntar'),
(1, 5, 'Voluntar'),
(1, 6, 'Voluntar'),

-- Sala 2

(2, 2, 'Director'),
(2, 3, 'Route Setter'),
(2, 4, 'Route Setter'),
(2, 1, 'Voluntar'),
(2, 5, 'Voluntar'),
(2, 6, 'Voluntar'),

-- Sala 3

(3, 3, 'Director'),
(3, 4, 'Route Setter'),
(3, 5, 'Route Setter'),
(3, 1, 'Voluntar'),
(3, 2, 'Voluntar'),
(3, 6, 'Voluntar');

----------------------------------------------------------------------------------------------------------------------------

INSERT INTO Organizatie(id, nume) VALUES
(1, 'Cluj Climbers'),
(2, 'Brasov Bouldering'),
(3, 'All-Globe Ascenders');

INSERT INTO Client(id, nume, prenume, nr_tel, id_sala, organizatie_id) VALUES

-- Sala 1

(1, 'Russu', 'Mihus', 0774000001, 1, 2),
(2, 'Stoian', 'Tudor', 0774000002, 1, 2),
(3, 'Rusu', 'Sanziana', 0774000003, 1, 2),
(4, 'Calugaritoiu', 'Teo', 0774000004, 1, 3),
(5, 'Clujan', 'Petru', 0774000005, 1, 1),
(6, 'Petreanu', 'Aurora', 0774000006, 1, 2),

-- Sala 2

(7,  'Moldovan', 'Ioana', 0774000007, 2, 3),
(8,  'Pop', 'Sergiu', 0774000008, 2, 1),
(9,  'Enache', 'Delia', 0774000009, 2, 3),
(10, 'Ciobanu', 'Marius', 0774000010, 2, 1),
(11, 'Gavril', 'Tania', 0774000011, 2, 3),
(12, 'Todor', 'Cristian', 0774000012, 2, 1),

-- Sala 3

(13, 'Sandu', 'Andreea', 0774000013, 3, 2),
(14, 'Iacob', 'Lucian', 0774000014, 3, 2),
(15, 'Banu', 'Oana',  0774000015, 3, 1),
(16, 'Vasilescu', 'Andrei', 0774000016, 3, 3),
(17, 'Nistor', 'Maria', 0774000017, 3, 1),
(18, 'Matei', 'Rares', 0774000018, 3, 3);

INSERT INTO LocatieClient (id, localitate, oras, strada, numar, client_id) VALUES

-- Cluj

(1, 'Cluj-Napoca', 'Cluj', 'Str. Memorandumului', 12, 5),
(2, 'Cluj-Napoca', 'Cluj', 'Str. Memorandumului', 12, 8),   -- aceeasi adresa cu 5
(3, 'Cluj-Napoca', 'Cluj', 'Str. Observatorului', 25, 10),
(4, 'Cluj-Napoca', 'Cluj', 'Str. Observatorului', 25, 17),  -- aceeasi adresa cu 10
(5, 'Cluj-Napoca', 'Cluj', 'Str. Universitatii', 9, 15),

-- Brasov

(6, 'Brasov', 'Brasov', 'Str. Lunga', 45, 1),
(7, 'Brasov', 'Brasov', 'Str. Lunga', 45, 2),    -- aceeasi adresa cu 1
(8, 'Brasov', 'Brasov', 'Str. Carpatilor', 18, 3),
(9, 'Brasov', 'Brasov', 'Str. Carpatilor', 18, 13), -- aceeasi adresa cu 3
(10, 'Brasov', 'Brasov', 'Str. Muresenilor', 7, 14),
(11, 'Brasov', 'Brasov', 'Str. Muresenilor', 7, 6),  -- aceeasi adresa cu 14

-- Alte tari

(12, 'Viena', 'Austria', 'Mariahilfer Strasse', 101, 4),
(13, 'Viena', 'Austria', 'Mariahilfer Strasse', 101, 7),  -- aceeasi adresa cu 4
(14, 'Budapesta', 'Ungaria', 'Andrassy ut', 22, 9),
(15, 'Budapesta', 'Ungaria', 'Andrassy ut', 22, 11),      -- aceeasi adresa cu 9
(16, 'Milano', 'Italia', 'Via Roma', 33, 16),
(17, 'Praga', 'Cehia', 'Karlova', 10, 18),
(18, 'Praga', 'Cehia', 'Karlova', 10, 12);  -- aceeasi adresa cu 18



----------------------------------------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------------------------------------



-- 1

-- Clientii care sunt din Brasov (foloseste WHERE si JOIN intre 2 tabele)

-- Afiseaza numele, prenumele, si orasul clientilor care locuiesc in Brasov

SELECT c.nume, c.prenume, l.localitate, l.strada, l.numar
FROM Client c
JOIN LocatieClient l ON c.id = l.client_id
WHERE l.localitate = 'Brasov';

----------------------------------------------------------------------------------------------------------------------------

-- 2

-- Toate rutele din salile din Cluj (WHERE + JOIN intre 3 tabele)

-- Afiseaza sala, culoarea prizelor, si dificultatea rutelor pentru salile din Cluj

SELECT s.nume AS Sala, r.culoare_prize, r.dificultate
FROM Ruta r
JOIN Sala s ON r.id_sala = s.id
JOIN LocatieSala ls ON s.id = ls.id_sala
WHERE ls.localitate = 'Cluj';

----------------------------------------------------------------------------------------------------------------------------

-- 3

-- Clientii si organizatiile lor (JOIN intre 3 tabele + DISTINCT)

-- Afiseaza numele organizatiilor, si clientii asociati

SELECT DISTINCT o.nume AS Organizatie, c.nume, c.prenume
FROM Client c
JOIN Organizatie o ON c.organizatie_id = o.id
JOIN Sala s ON c.id_sala = s.id;

----------------------------------------------------------------------------------------------------------------------------

-- 4

-- Numarul de clienti pe fiecare sala (GROUP BY)

-- Afiseaza sala si cati clienti are

SELECT s.nume AS Sala, COUNT(c.id) AS Numar_Clienti
FROM Sala s
JOIN Client c ON s.id = c.id_sala
GROUP BY s.nume;

----------------------------------------------------------------------------------------------------------------------------

-- 5

-- Numarul total de prize per culoare, pe toate rutele (GROUP BY + SUM)

-- Afiseaza cate prize exista pentru fiecare culoare de ruta

SELECT r.culoare_prize, SUM(r.nr_prize) AS Total_Prize
FROM Ruta r
GROUP BY r.culoare_prize
ORDER BY Total_Prize DESC;

----------------------------------------------------------------------------------------------------------------------------

-- 6

-- Salile care au mai mult de un route-setter (JOIN intre 3 tabele + GROUP BY + HAVING)

-- Afiseaza sala si numarul de route setteri asociati

SELECT s.nume AS Sala, COUNT(ss.id_staff) AS Nr_Route_Setteri
FROM Sala s
JOIN SalaStaff ss ON s.id = ss.id_sala
JOIN Staff st ON ss.id_staff = st.id
WHERE ss.rol = 'Route Setter'
GROUP BY s.nume
HAVING COUNT(ss.id_staff) > 1;

----------------------------------------------------------------------------------------------------------------------------

-- 7

-- Clienti si rutele disponibile in sala lor (JOIN din 3 tabele)

-- Afiseaza fiecare client si ce rute poate incerca in sala lui

SELECT c.nume, c.prenume, s.nume AS Sala, r.culoare_prize, r.dificultate
FROM Client c
JOIN Sala s ON c.id_sala = s.id
JOIN Ruta r ON s.id = r.id_sala
ORDER BY s.nume, c.nume;

----------------------------------------------------------------------------------------------------------------------------

-- 8

-- Prizele de tip jug folosite in rute de dificultate peste 5 (JOIN intre 3 tabele + WHERE)

-- Afiseaza id-ul prizei, culoarea, si sala in care se afla

SELECT p.id AS ID_Priza, p.culoare, r.dificultate, s.nume AS Sala
FROM Priza p
JOIN Ruta r ON p.id_ruta = r.id
JOIN Sala s ON r.id_sala = s.id
WHERE r.dificultate > 5 AND p.tip = 'jug';

----------------------------------------------------------------------------------------------------------------------------

-- 9

-- Numarul de clienti per organizatie si numele salilor in care merg (JOIN + GROUP BY + HAVING)

-- Afiseaza organizatiile care au mai mult de 3 clienti

SELECT o.nume AS Organizatie, COUNT(c.id) AS Nr_Clienti
FROM Client AS c
INNER JOIN Organizatie AS o ON c.organizatie_id = o.id
INNER JOIN Sala AS s ON c.id_sala = s.id
GROUP BY o.nume
HAVING COUNT(c.id) > 3;

----------------------------------------------------------------------------------------------------------------------------

-- 10

-- Toate perechile Sala–Staff (relatie M:N) (JOIN intre 3 tabele + DISTINCT)

-- Afiseaza sala, numele angajatului, si rolul sau

SELECT DISTINCT s.nume AS Sala, st.nume AS Staff_Nume, st.prenume AS Staff_Prenume, ss.rol
FROM Sala s
JOIN SalaStaff ss ON s.id = ss.id_sala
JOIN Staff st ON ss.id_staff = st.id
ORDER BY s.nume, ss.rol;

----------------------------------------------------------------------------------------------------------------------------

