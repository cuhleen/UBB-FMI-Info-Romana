CREATE TABLE VersionInfo(
    version INT NOT NULL
);

-- versiunea initiala = 0
INSERT INTO VersionInfo(version) VALUES (0);
GO

----------------------------------------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------------------------------------

CREATE OR ALTER PROCEDURE Upgrade_V1_ModificaTipColoana
AS
BEGIN
    ALTER TABLE Client ALTER COLUMN nr_tel NVARCHAR(50);
    PRINT 'Versiunea 1';
    PRINT 'Coloana "nr_tel" din tabela "Client" este acum NVARCHAR(50)';
    UPDATE VersionInfo SET version = 1;
END;
GO

CREATE OR ALTER PROCEDURE Downgrade_V1_ModificaTipColoana
AS
BEGIN
    ALTER TABLE Client ALTER COLUMN nr_tel VARCHAR(50);
    PRINT 'Versiunea 0';
    PRINT 'Coloana "nr_tel" din tabela "Client" este acum VARCHAR(50)';
    UPDATE VersionInfo SET version = 0;
END;
GO

----------------------------------------------------------------------------------------------------------------------------

CREATE OR ALTER PROCEDURE Upgrade_V2_DefaultStaffNrTel
AS
BEGIN
    ALTER TABLE Staff
    ADD CONSTRAINT DF_Staff_nr_tel DEFAULT '-' FOR nr_tel;
    PRINT 'Versiunea 2';
    PRINT 'Coloana "nr_tel" din tabela "Staff" are acum valoarea default "-"';
    UPDATE VersionInfo SET version = 2;
END;
GO

CREATE OR ALTER PROCEDURE Downgrade_V2_DefaultStaffNrTel
AS
BEGIN
    ALTER TABLE Staff DROP CONSTRAINT DF_Staff_nr_tel;
    PRINT 'Versiunea 1';
    PRINT 'Coloana "nr_tel" din tabela "Staff" nu are nicio valoare default';
    UPDATE VersionInfo SET version = 1;
END;
GO

----------------------------------------------------------------------------------------------------------------------------

CREATE OR ALTER PROCEDURE Upgrade_V3_CreateLogTable
AS
BEGIN
    CREATE TABLE LogModificari(
        id INT IDENTITY PRIMARY KEY,
        actiune VARCHAR(50),
        data_op DATETIME DEFAULT GETDATE()
    );
    PRINT 'Versiunea 3';
    PRINT 'S-a creat tabela "LogModificari", ce primeste un INT "id", VARCHAR(50) "actiune", DATETIME "data_op"';
    UPDATE VersionInfo SET version = 3;
END;
GO

CREATE OR ALTER PROCEDURE Downgrade_V3_DropLogTable
AS
BEGIN
    DROP TABLE LogModificari;
    PRINT 'Versiunea 2';
    PRINT 'S-a sters tabela "LogModificari"';
    UPDATE VersionInfo SET version = 2;
END;
GO

----------------------------------------------------------------------------------------------------------------------------

CREATE OR ALTER PROCEDURE Upgrade_V4_AddDescriereRuta
AS
BEGIN
    ALTER TABLE Ruta ADD descriere VARCHAR(255) NULL;
    PRINT 'Versiunea 4';
    PRINT 'S-a adaugat coloana VARCHAR(255) "descriere" in tabela "Ruta"';
    UPDATE VersionInfo SET version = 4;
END;
GO

CREATE OR ALTER PROCEDURE Downgrade_V4_RemoveDescriereRuta
AS
BEGIN
    ALTER TABLE Ruta DROP COLUMN descriere;
    PRINT 'Versiunea 3';
    PRINT 'S-a sters coloana VARCHAR(255) "descriere" in tabela "Ruta"';
    UPDATE VersionInfo SET version = 3;
END;
GO

----------------------------------------------------------------------------------------------------------------------------

CREATE OR ALTER PROCEDURE Upgrade_V5_AddFKClientSala
AS
BEGIN
    ALTER TABLE Client
    ADD CONSTRAINT FK_Client_Sala FOREIGN KEY (id_sala) REFERENCES Sala(id);
    PRINT 'Versiunea 5';
    PRINT 'S-a adaugat o cheie straina in Client, ce referentiaza "id_sala" din tabela "Sala"';
    UPDATE VersionInfo SET version = 5;
END;
GO

CREATE OR ALTER PROCEDURE Downgrade_V5_RemoveFKClientSala
AS
BEGIN
    ALTER TABLE Client DROP CONSTRAINT FK_Client_Sala;
    PRINT 'Versiunea 4';
    PRINT 'S-a sters cheia straina din Client, ce referentia "id_sala" din tabela "Sala"';
    UPDATE VersionInfo SET version = 4;
END;
GO

----------------------------------------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------------------------------------

CREATE OR ALTER PROCEDURE SetDatabaseVersion @target INT
AS
BEGIN
    DECLARE @current INT;
    SELECT @current = version FROM VersionInfo;

    IF @target > 5
    BEGIN
        PRINT 'Versiunea nu exista.';
        RETURN;
    END

    IF @target < 0
    BEGIN
        PRINT 'Versiune invalida.';
        RETURN;
    END

    WHILE @current < @target
    BEGIN
        SET @current = @current + 1;

        IF @current = 1 EXEC Upgrade_V1_ModificaTipColoana;
        IF @current = 2 EXEC Upgrade_V2_DefaultStaffNrTel;
        IF @current = 3 EXEC Upgrade_V3_CreateLogTable;
        IF @current = 4 EXEC Upgrade_V4_AddDescriereRuta;
        IF @current = 5 EXEC Upgrade_V5_AddFKClientSala;
    END

    WHILE @current > @target
    BEGIN
        IF @current = 5 EXEC Downgrade_V5_RemoveFKClientSala;
        IF @current = 4 EXEC Downgrade_V4_RemoveDescriereRuta;
        IF @current = 3 EXEC Downgrade_V3_DropLogTable;
        IF @current = 2 EXEC Downgrade_V2_DefaultStaffNrTel;
        IF @current = 1 EXEC Downgrade_V1_ModificaTipColoana;

        SET @current = @current - 1;
    END
END;

----------------------------------------------------------------------------------------------------------------------------

EXEC SetDatabaseVersion 0
EXEC SetDatabaseVersion 1
EXEC SetDatabaseVersion 2
EXEC SetDatabaseVersion 3
EXEC SetDatabaseVersion 4
EXEC SetDatabaseVersion 5
EXEC SetDatabaseVersion 99
EXEC SetDatabaseVersion -1
