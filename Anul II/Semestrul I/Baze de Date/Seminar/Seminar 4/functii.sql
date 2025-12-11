------------------------------------------------------------------------
-- FUNCTII
------------------------------------------------------------------------

-- Sa se creeze o functie scalara care primeste ca parametru numele unei
-- categorii si returneaza codul acesteia

CREATE OR ALTER FUNCTION CodCategorie (@nume VARCHAR(70))
RETURNS INT
AS
BEGIN
    DECLARE @cod INT;

    SELECT @cod = C.cod_c
    FROM Categorii AS C
    WHERE C.nume = @nume;

    RETURN @cod;

END;
GO

PRINT dbo.CodCategorie('Elev');
GO

PRINT dbo.CodCategorie('Elev')
GO

-- Creati o functie de tip inline table valued care returneaza toate
-- inregistrarile din tabelul Sectiuni al caror nume se termina cu o litera data
-- ca parametru de intrare si au cel putin doua caractere

GO
CREATE OR ALTER FUNCTION SectiuniDupaUltimaLitera(@ultimaLitera VARCHAR(1))
RETURNS TABLE AS
RETURN SELECT S.nume, S.descriere FROM Sectiuni S
WHERE S.nume LIKE '%_' + @ultimaLitera + '%';
GO

SELECT * FROM dbo.SectiuniDupaUltimaLitera('1')

------------------------------------------------------------------------
-- VIEW
------------------------------------------------------------------------

-- Creati un view care afiseaza toate inregistrarile din tabelul Categorii al
-- caror nume este egal cu ‘pensionari’ sau ‘copii’.

GO
CREATE OR ALTER VIEW vw_PensionariSauCopii
AS
SELECT C.nume FROM Categorii AS C
WHERE C.nume = 'Pensionar' OR C.nume = 'Elev';
GO

SELECT * FROM vw_PensionariSauCopii

-- Creati un view care afiseaza toate inregistrarile din tabelul Sectiuni al
-- caror nume incepe cu litera C

GO
CREATE OR ALTER VIEW vw_SectiuniC
AS
SELECT * FROM Categorii as C
WHERE LEFT(C.nume, 1) = 'A';
GO

SELECT * FROM vw_SectiuniC

-- Creati un view care care afiseaza numele vizitatorilor, nota si numele
-- atractiei

GO
CREATE OR ALTER VIEW vw_NotaDetaliata
AS
SELECT V.nume as vizitator, N.nota, A.nume FROM Note N
INNER JOIN Vizitatori V ON N.cod_v = V.cod_v
INNER JOIN Atractii A ON N.cod_a = A.cod_a;
GO

SELECT * FROM vw_NotaDetaliata

------------------------------------------------------------------------
-- TRIGGER
------------------------------------------------------------------------

-- Creati un trigger care impiedica executia operatiilor de stergere din
-- tabelul Categorii si afiseaza un mesaj corespunzator.

GO
CREATE TRIGGER StergereCategorie
ON Categorii
INSTEAD OF DELETE
AS
BEGIN
RAISERROR('Momentan nu se pot sterge date in acest tabel',16,1);
END;

