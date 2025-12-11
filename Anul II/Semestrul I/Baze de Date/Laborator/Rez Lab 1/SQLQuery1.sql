CREATE DATABASE sala_catarat
GO

USE sala_catarat
GO

CREATE TABLE Sala(
	id INT PRIMARY KEY IDENTITY(1, 1),
	nume VARCHAR(50)
)

CREATE TABLE LocatieSala(
	id_sala INT FOREIGN KEY REFERENCES Sala(id),
	localitate VARCHAR(50),
	oras VARCHAR(50),
	strada VARCHAR(50),
	numar INT,
	PRIMARY KEY(id_sala)
)

CREATE TABLE Orar(
	id_sala INT FOREIGN KEY REFERENCES Sala(id),
	ora_deschidere VARCHAR(50),
	ora_inchidere VARCHAR(50),
	PRIMARY KEY(id_sala)
)

CREATE TABLE Ruta(
	id INT PRIMARY KEY IDENTITY(1, 1),
	dificultate INT,
	culoare_prize VARCHAR(50),
	nr_prize INT,
	id_sala INT FOREIGN KEY REFERENCES Sala(id)
)

CREATE TABLE Priza(
	id INT PRIMARY KEY IDENTITY(1, 1),
	culoare VARCHAR(50),
	tip VARCHAR(50),
	id_ruta INT FOREIGN KEY REFERENCES Ruta(id)
)

CREATE TABLE Organizatie(
	id INT PRIMARY KEY IDENTITY(1, 1),
	nume VARCHAR(50)
)

CREATE TABLE Client(
	id INT PRIMARY KEY IDENTITY(1, 1),
	nume VARCHAR(50),
	prenume VARCHAR(50),
	nr_tel VARCHAR(50),
	id_sala INT FOREIGN KEY REFERENCES Sala(id),
	organizatie_id INT FOREIGN KEY REFERENCES Organizatie(id)
)

CREATE TABLE LocatieClient(
	id INT PRIMARY KEY IDENTITY(1, 1),
	localitate VARCHAR(50),
	oras VARCHAR(50),
	strada VARCHAR(50),
	numar INT,
	client_id INT FOREIGN KEY REFERENCES Client(id)
)

CREATE TABLE Staff(
	id INT PRIMARY KEY IDENTITY(1, 1),
	nume VARCHAR(50),
	prenume VARCHAR(50),
	nr_tel VARCHAR(50)
)

CREATE TABLE SalaStaff(
	id_sala INT FOREIGN KEY REFERENCES Sala(id),
	id_staff INT FOREIGN KEY REFERENCES Staff(id)
	PRIMARY KEY(id_sala, id_staff),
	rol VARCHAR(50)
)