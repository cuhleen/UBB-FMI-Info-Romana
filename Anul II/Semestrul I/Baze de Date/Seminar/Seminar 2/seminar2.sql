CREATE DATABASE seminar2
GO

USE seminar2
GO

CREATE TABLE Sectiuni(
	cod_s INT PRIMARY KEY IDENTITY(1, 1),
	nume VARCHAR(50),
	descriere VARCHAR(50)
)

CREATE TABLE Atractii(
	cod_a INT PRIMARY KEY IDENTITY(1, 1),
	nume VARCHAR(50),
	descriere VARCHAR(50),
	varsta_min INT,
	cod_s INT FOREIGN KEY REFERENCES Sectiuni(cod_s)
)

CREATE TABLE Categorii(
	cod_c INT PRIMARY KEY IDENTITY(1, 1),
	nume VARCHAR(50)
)

CREATE TABLE Vizitatori(
	cod_v INT PRIMARY KEY IDENTITY(1, 1),
	nume VARCHAR(50),
	email VARCHAR(50),
	cod_c INT FOREIGN KEY REFERENCES Categorii(cod_c)
)

CREATE TABLE Note(
	cod_a INT FOREIGN KEY REFERENCES Atractii(cod_a),
	cod_v INT FOREIGN KEY REFERENCES Vizitatori(cod_v),
	PRIMARY KEY(cod_a, cod_v),
	nota FLOAT CHECK(nota>0 AND nota <=10)
)

----------------------------------------------------------

INSERT INTO Sectiuni (nume, descriere) VALUES
('sectiunea 1', 'cea mai mare sectiune'),
('sectiunea 2', 'a doua cea mai mare sectiune'),
('sectiunea 3', 'cea mai mica sectiune'),
('sectiunea 4', 'sectiunea de copii'),
('sectiunea 5', 'sectiunea de mancare'),
('sectiunea 6', 'sectiune in constructie 1'),
('sectiunea 7', 'sectiune in constructie 2');
INSERT INTO Sectiuni(nume, descriere) VALUES
('C sectiune de test', 'ceva');
INSERT INTO Sectiuni(nume, descriere) VALUES
('sectiune de test n', 'altceva');

INSERT INTO Atractii(nume, descriere, varsta_min, cod_s) VALUES
('Masinute busitoare', 'Experienta traficului clujan', 10, 1),
('Motoscarta', 'Caii putere compenseaza pentru competenta ta', 15, 1),
('Barcute', 'Priveliste pe lac', 5, 2),
('Roata Ferris', 'Priveliste deasupra lacului', 5, 2),
('Rollercoaster rapid', 'Nu te uita prea aproape la suruburi', 15, 3),
('Rollercoaster lent', 'O briza lenta', 7, 3),
('Poze cu mascote', 'Nu garantam ca copiii dvs. vor tine minte ceva', 3, 4),
('Workshop pizza', 'Adu-ti pruncul sa faca o bomba biologica', 7, 5),
('Activitati in piscina', 'In constructie', 99, 6);
INSERT INTO Atractii(nume, descriere, varsta_min, cod_s) VALUES
('Activitate de sters', 'ignora-ma', 98, 7);

INSERT INTO Categorii(nume) VALUES
('Infantil'),
('Elev'),
('Student'),
('Adult'),
('Pensionar'),
('VIP'),
('Alta Reducere');

INSERT INTO Vizitatori(nume, email, cod_c) VALUES
('Mihus', 'climbingbro1@gmail.com', 5),
('Teo', 'climbingbro2@gmail.com', 5),
('Aurora', 'AUUUUUUU@hotmail.com', 6),
('David', 'germanandy@yahoo.com', 6),
('Kit', 'waningmoon@gmail.com', 7),
('Tudor', 'todorosigma67@proton.me', 8),
('Aron', 'mtbbvidk@gmail.com', 4);

INSERT INTO Note(cod_a, cod_v, nota) VALUES
(18, 1, 3.1),
(19, 2, 7.3),
(20, 3, 2.5),
(21, 4, 8.2),
(22, 5, 7.3),
(23, 6, 5.5),
(24, 7, 9.5),
(25, 1, 5.4),
(18, 2, 9.4),
(19, 3, 7.8),
(20, 4, 1.5),
(21, 5, 2.7),
(22, 6, 4.4),
(23, 7, 9.9);

----------------------------------------------------------

UPDATE Sectiuni SET descriere='sectiune nu inca in constructie' WHERE nume='sectiunea 7'
SELECT * FROM Sectiuni

UPDATE Atractii SET descriere='Inca in constructie' WHERE nume='Activitati in piscina'
SELECT * FROM Atractii

UPDATE Categorii SET nume='bebelus' WHERE nume='Infantil'
SELECT * FROM Categorii

UPDATE Vizitatori SET email='seminaristul@gmail.com' WHERE nume='Aron'
SELECT * FROM Vizitatori

UPDATE Note SET nota=6.7 WHERE (cod_v = 6 AND cod_a = 22)
SELECT * FROM Note

----------------------------------------------------------

DELETE FROM Note WHERE nota=1.5
SELECT * FROM Note

DELETE FROM Atractii WHERE nume='Activitate de sters'
SELECT * FROM Atractii

----------------------------------------------------------

SELECT * FROM Categorii WHERE (nume = 'Pensionar' OR nume = 'Elev')

SELECT * FROM Sectiuni WHERE nume LIKE '[C]%'

SELECT * FROM Sectiuni WHERE nume LIKE '%_n'
