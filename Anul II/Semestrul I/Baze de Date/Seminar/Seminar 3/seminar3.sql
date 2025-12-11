-- EXEMPLU PROCEDURA

-- daca procedura nu exista, va fi creata
-- altfel, va fi modificata
-- localitate are o valoare default data, 'Cluj-Napoca'

CREATE OR ALTER PROCEDURE AdaugaPersoana @nume VARCHAR(50), @prenume VARCHAR(50), @localitate VARCHAR(50)='Cluj-Napoca'
AS
BEGIN
	INSERT INTO Persoane(nume, prenume, localitate) VALUES
		(@nume, @prenume, @localitate);
END;
GO
-- Apelul procedurii stocate
EXEC AdaugaPersoane 'Pop', 'Andrei', 'Sibiu';
GO

---------------------------------------------------------

-- 1

-- Sa se creeze o procedura stocata care insereaza o sectiune noua in tabelul Sectiuni.
-- Procedura va avea doi parametri de intrare: numele si descrierea sectiunii.

CREATE OR ALTER PROCEDURE AdaugaSectiune @nume VARCHAR(50), @descriere VARCHAR(50)
AS
BEGIN
	INSERT INTO Sectiuni(nume, descriere) VALUES
		(@nume, @descriere);
END;
GO
EXEC AdaugaSectiune 'SectiuneProcedura', 'DescriereSectiuneProcedura'
GO

-- 2

-- Sa se creeze o procedura stocata care actualizeaza adresa de email a unui vizitator din tabelul Vizitatori.
-- Procedura stocata primeste 2 parametri de intrare: codul vizitatorului si noua adresa de email.

CREATE OR ALTER PROCEDURE UpdateEmail @cod_v INT, @email VARCHAR(50)
AS
BEGIN
	UPDATE Vizitatori SET email=@email WHERE cod_v = @cod_v
END;
GO
EXEC UpdateEmail 1, 'emailProcedura@gmail.com'
GO

-- 3

-- Sa se creeze o procedura stocata care returneaza numele vizitatorului, adresa de email si numarul total de note
-- pentru toti vizitatorii care au dat cel putin o nota unei atractii.

CREATE OR ALTER PROCEDURE UtilizatorNota
AS
BEGIN
	
END;
GO
UtilizatorNota
Go

---------------------------------------------------------

-- 1

--  Sa se creeze o procedura stocata care sterge o atractie din tabelul Atractii.
-- Procedura stocata va avea un singur parametru de intrare: numele atractiei.
-- Inainte de stergerea atractiei, se va verifica daca exista note pentru acea atractie.
-- In cazul in care exista note date acelei atractii, se va afisa pe ecran un mesaj corespunzator
-- si nu se va sterge atractia respectiva.

CREATE OR ALTER PROCEDURE StergeAtractie @nume VARCHAR(50)
AS
BEGIN
	SELECT cod_a FROM Atractii WHERE nume = @nume AS codAtractie
	IF((SELECT nota FROM Note WHERE cod_a = codAtractie) > 0)
		RETURN 'Nu s-a putut sterge'
	ELSE
		
END;
GO
EXEC StergeAtractie 'Masinute busitoare'
GO


-- 2

-- Sa se creeze o procedura stocata care insereaza o nota in tabelul Note.
-- Procedura stocata va avea 3 parametri de intrare: codul atractiei, codul vizitatorului si nota.
-- Inainte de inserare, se va verifica daca exista codul atractiei si codul vizitatorului in tabelele Atractii si Vizitatori.
-- Daca nu exista, se va genera un mesaj de eroare. Daca exista, se verifica daca nota este cuprinsa intre 1 si 10.
-- Se va returna un mesaj de eroare daca nota nu are o valoare valida

CREATE OR ALTER PROCEDURE AdaugaNota @cod_a INT, @cod_v INT, @nota FLOAT
AS
BEGIN
	SELECT * FROM Atractii WHERE (Atractii.cod_a = @cod_a), COUNT(*) AS xista
	IF(exista == 1)
END;
GO
EXEC AdaugaNota 99, 99, 1.2