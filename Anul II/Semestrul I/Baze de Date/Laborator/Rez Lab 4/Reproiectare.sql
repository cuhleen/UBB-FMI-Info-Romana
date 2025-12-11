BACKUP DATABASE sala_catarat
TO DISK = 'E:\002 Facultate\Anul II\Semestrul I\Baze de Date\Laborator\Backup\sala_catarat.bak'
WITH FORMAT, INIT;

----------------------------------------------------------------------------------------------------------------------------

RESTORE DATABASE sala_catarat
FROM DISK = 'E:\002 Facultate\Anul II\Semestrul I\Baze de Date\Laborator\Backup\sala_catarat.bak'
WITH REPLACE,
     MOVE 'sala_catarat' TO 'E:\002 Facultate\SQL\MSSQL16.SQLEXPRESS\MSSQL\DATA\sala_catarat.mdf',
     MOVE 'sala_catarat_log' TO 'E:\002 Facultate\SQL\MSSQL16.SQLEXPRESS\MSSQL\DATA\sala_catarat_log.ldf';

----------------------------------------------------------------------------------------------------------------------------

-- 1:N -> N:1

IF OBJECT_ID('dbo.usp_Convert_Sala_Ruta_1N_to_N1', 'P') IS NOT NULL
    DROP PROCEDURE dbo.usp_Convert_Sala_Ruta_1N_to_N1;
GO

CREATE PROCEDURE dbo.usp_Convert_Sala_Ruta_1N_to_N1
AS
BEGIN
    SET NOCOUNT ON;

    BEGIN TRY
        BEGIN TRANSACTION;

        -- 1) Creaza tabel Legaturi_Eliminate daca nu exista
        IF OBJECT_ID('dbo.Legaturi_Eliminate','U') IS NULL
        BEGIN
            CREATE TABLE dbo.Legaturi_Eliminate(
                NumeTabelSt  SYSNAME NOT NULL,
                IdSt         INT NULL,
                NumeTabelDr  SYSNAME NOT NULL,
                IdDr         INT NULL,
                DataEliminare DATETIME2 DEFAULT SYSUTCDATETIME()
            );
        END

        -- 2) Stergem FK existent Ruta -> Sala
        IF OBJECT_ID('FK_Ruta_Sala','F') IS NOT NULL
            ALTER TABLE Ruta DROP CONSTRAINT FK_Ruta_Sala;

        -- 3) Adaugam coloana noua in Sala: Sala.fk_Ruta_id
        IF COL_LENGTH('Sala','fk_Ruta_id') IS NULL
        BEGIN
            ALTER TABLE Sala ADD fk_Ruta_id INT NULL;
        END

        -- 4) Populam noua coloana folosind executie dinamica
        DECLARE @sql NVARCHAR(MAX);
        SET @sql = '
            ;WITH MaxRutaPerSala AS (
                SELECT id_sala AS SalaID, MAX(id) AS RutaMaxID
                FROM Ruta
                GROUP BY id_sala
            )
            UPDATE s
            SET s.fk_Ruta_id = m.RutaMaxID
            FROM Sala s
            LEFT JOIN MaxRutaPerSala m ON m.SalaID = s.id;
        ';
        EXEC sp_executesql @sql;

        -- 5) Inseram in Legaturi_Eliminate toate legaturile care nu sunt pastrate
        INSERT INTO dbo.Legaturi_Eliminate(NumeTabelSt, IdSt, NumeTabelDr, IdDr)
        SELECT 
            'Sala' AS NumeTabelSt,
            r.id_sala AS IdSt,
            'Ruta' AS NumeTabelDr,
            r.id AS IdDr
        FROM Ruta r
        INNER JOIN (
            SELECT id_sala, MAX(id) AS RutaMaxID
            FROM Ruta
            GROUP BY id_sala
        ) m ON r.id_sala = m.id_sala
        WHERE r.id <> m.RutaMaxID;

        -- 6) Setam vechiul FK din Ruta la NULL
        UPDATE Ruta SET id_sala = NULL WHERE id_sala IS NOT NULL;

        -- 7) Adaugam FK nou: Sala.fk_Ruta_id -> Ruta.id
        ALTER TABLE Sala
        ADD CONSTRAINT FK_Sala_Ruta FOREIGN KEY(fk_Ruta_id) REFERENCES Ruta(id);

        COMMIT TRANSACTION;
    END TRY
    BEGIN CATCH
        IF XACT_STATE() <> 0
            ROLLBACK TRANSACTION;

        DECLARE @errmsg NVARCHAR(4000) = ERROR_MESSAGE();
        RAISERROR('A aparut o eroare: %s', 16, 1, @errmsg);
        RETURN;
    END CATCH

    PRINT 'Conversia Sala (1) -> Ruta (N) in Sala.fk_Ruta_id (N:1) a fost efectuata.';
END
GO

EXEC dbo.usp_Convert_Sala_Ruta_1N_to_N1;

SELECT * FROM Sala;
SELECT * FROM Ruta;
SELECT * FROM Legaturi_Eliminate;

----------------------------------------------------------------------------------------------------------------------------

-- 1:N -> M:N

IF OBJECT_ID('dbo.usp_Convert_Sala_Ruta_1N_to_MN', 'P') IS NOT NULL
    DROP PROCEDURE dbo.usp_Convert_Sala_Ruta_1N_to_MN;
GO

CREATE PROCEDURE dbo.usp_Convert_Sala_Ruta_1N_to_MN
AS
BEGIN
    SET NOCOUNT ON;

    BEGIN TRY
        BEGIN TRANSACTION;

        -- 1) Creaza tabel Legaturi_Eliminate daca nu exista
        IF OBJECT_ID('dbo.Legaturi_Eliminate','U') IS NULL
        BEGIN
            CREATE TABLE dbo.Legaturi_Eliminate(
                NumeTabelSt  SYSNAME NOT NULL,
                IdSt         INT NULL,
                NumeTabelDr  SYSNAME NOT NULL,
                IdDr         INT NULL,
                DataEliminare DATETIME2 DEFAULT SYSUTCDATETIME()
            );
        END

        -- 2) Creaza tabelul intermediar Sala_Ruta daca nu exista
        IF OBJECT_ID('dbo.Sala_Ruta','U') IS NULL
        BEGIN
            CREATE TABLE dbo.Sala_Ruta(
                id_sala INT NOT NULL,
                id_ruta INT NOT NULL,
                PRIMARY KEY(id_sala, id_ruta)
            );
        END

        -- 3) Populam tabelul intermediar cu legaturile existente (din Sala.fk_Ruta_id daca exista, altfel din Ruta.id_sala)
        -- Folosim executie dinamica, altfel nu recunoaste campul nou creat
        DECLARE @sql NVARCHAR(MAX);
        SET @sql = '
            INSERT INTO dbo.Sala_Ruta(id_sala, id_ruta)
            SELECT s.id, s.fk_Ruta_id
            FROM Sala s
            WHERE s.fk_Ruta_id IS NOT NULL;
        ';
        EXEC sp_executesql @sql;

        -- Daca mai exista legaturi vechi din Ruta.id_sala, le salvam in tabel intermediar
        INSERT INTO dbo.Sala_Ruta(id_sala, id_ruta)
        SELECT id_sala, id
        FROM Ruta
        WHERE id_sala IS NOT NULL;

        -- 4) Stergem coloana Sala.fk_Ruta_id si orice FK vechi din Ruta
        IF COL_LENGTH('Sala','fk_Ruta_id') IS NOT NULL
        BEGIN
            ALTER TABLE Sala DROP CONSTRAINT IF EXISTS FK_Sala_Ruta;
            ALTER TABLE Sala DROP COLUMN fk_Ruta_id;
        END

        IF OBJECT_ID('FK_Ruta_Sala','F') IS NOT NULL
        BEGIN
            ALTER TABLE Ruta DROP CONSTRAINT FK_Ruta_Sala;
        END

        -- 5) Adaugam FK-uri in tabelul intermediar
        IF OBJECT_ID('FK_Sala_Ruta_MN_Sala','F') IS NULL
        BEGIN
            ALTER TABLE dbo.Sala_Ruta
            ADD CONSTRAINT FK_Sala_Ruta_MN_Sala FOREIGN KEY(id_sala) REFERENCES Sala(id);
        END

        IF OBJECT_ID('FK_Sala_Ruta_MN_Ruta','F') IS NULL
        BEGIN
            ALTER TABLE dbo.Sala_Ruta
            ADD CONSTRAINT FK_Sala_Ruta_MN_Ruta FOREIGN KEY(id_ruta) REFERENCES Ruta(id);
        END

        COMMIT TRANSACTION;
    END TRY
    BEGIN CATCH
        IF XACT_STATE() <> 0
            ROLLBACK TRANSACTION;

        DECLARE @errmsg NVARCHAR(4000) = ERROR_MESSAGE();
        RAISERROR('A aparut o eroare: %s', 16, 1, @errmsg);
        RETURN;
    END CATCH

    PRINT 'Conversia Sala (1:N) -> Sala_Ruta (M:N) a fost efectuata.';
END
GO

EXEC dbo.usp_Convert_Sala_Ruta_1N_to_MN;

SELECT * FROM Sala_Ruta;

----------------------------------------------------------------------------------------------------------------------------

-- M:N -> 1:N

IF OBJECT_ID('dbo.usp_Convert_SalaStaff_MN_to_1N', 'P') IS NOT NULL
    DROP PROCEDURE dbo.usp_Convert_SalaStaff_MN_to_1N;
GO

CREATE PROCEDURE dbo.usp_Convert_SalaStaff_MN_to_1N
AS
BEGIN
    SET NOCOUNT ON;

    BEGIN TRY
        BEGIN TRANSACTION;

        -- 1) Creaza tabel Legaturi_Eliminate daca nu exista
        IF OBJECT_ID('dbo.Legaturi_Eliminate','U') IS NULL
        BEGIN
            CREATE TABLE dbo.Legaturi_Eliminate(
                NumeTabelSt  SYSNAME NOT NULL,
                IdSt         INT NULL,
                NumeTabelDr  SYSNAME NOT NULL,
                IdDr         INT NULL,
                DataEliminare DATETIME2 DEFAULT SYSUTCDATETIME()
            );
        END

        -- 2) Creaza coloana noua Sala.fk_Staff_id daca nu exista
        IF COL_LENGTH('Sala','fk_Staff_id') IS NULL
        BEGIN
            ALTER TABLE Sala ADD fk_Staff_id INT NULL;
        END

        -- 3) Populam coloana noua cu id_staff maxim pentru fiecare sala
        DECLARE @sql NVARCHAR(MAX);
        SET @sql = '
            ;WITH MaxStaffPerSala AS (
                SELECT id_sala AS SalaID, MAX(id_staff) AS StaffMaxID
                FROM SalaStaff
                GROUP BY id_sala
            )
            UPDATE s
            SET s.fk_Staff_id = m.StaffMaxID
            FROM Sala s
            LEFT JOIN MaxStaffPerSala m ON m.SalaID = s.id;
        ';
        EXEC sp_executesql @sql;

        -- 4) Salvam legaturile eliminate in Legaturi_Eliminate
        INSERT INTO dbo.Legaturi_Eliminate(NumeTabelSt, IdSt, NumeTabelDr, IdDr)
        SELECT 
            'Sala' AS NumeTabelSt,
            ss.id_sala AS IdSt,
            'Staff' AS NumeTabelDr,
            ss.id_staff AS IdDr
        FROM SalaStaff ss
        INNER JOIN (
            SELECT id_sala, MAX(id_staff) AS StaffMaxID
            FROM SalaStaff
            GROUP BY id_sala
        ) m ON ss.id_sala = m.id_sala
        WHERE ss.id_staff <> m.StaffMaxID;

        -- 5) Stergem tabelul intermediar SalaStaff (optional)
        -- Daca vrei sa pastrezi M:N, poti sari peste aceasta parte
        DROP TABLE SalaStaff;

        -- 6) Creem FK nou: Sala.fk_Staff_id -> Staff.id
        ALTER TABLE Sala
        ADD CONSTRAINT FK_Sala_Staff FOREIGN KEY(fk_Staff_id) REFERENCES Staff(id);

        COMMIT TRANSACTION;
    END TRY
    BEGIN CATCH
        IF XACT_STATE() <> 0
            ROLLBACK TRANSACTION;

        DECLARE @errmsg NVARCHAR(4000) = ERROR_MESSAGE();
        RAISERROR('A aparut o eroare: %s', 16, 1, @errmsg);
        RETURN;
    END CATCH

    PRINT 'Conversia SalaStaff (M:N) -> Sala.fk_Staff_id (1:N) a fost efectuata.';
END
GO

EXEC dbo.usp_Convert_SalaStaff_MN_to_1N;

SELECT * FROM Sala;
SELECT * FROM Staff;
SELECT * FROM Legaturi_Eliminate;


----------------------------------------------------------------------------------------------------------------------------

-- 1:N -> 1:1

IF OBJECT_ID('dbo.usp_Convert_Client_Locatie_1N_to_1_1', 'P') IS NOT NULL
    DROP PROCEDURE dbo.usp_Convert_Client_Locatie_1N_to_1_1;
GO

CREATE PROCEDURE dbo.usp_Convert_Client_Locatie_1N_to_1_1
AS
BEGIN
    SET NOCOUNT ON;

    BEGIN TRY
        BEGIN TRANSACTION;

        -- 1) Creaza tabel Legaturi_Eliminate daca nu exista
        IF OBJECT_ID('dbo.Legaturi_Eliminate','U') IS NULL
        BEGIN
            CREATE TABLE dbo.Legaturi_Eliminate(
                NumeTabelSt  SYSNAME NOT NULL,
                IdSt         INT NULL,
                NumeTabelDr  SYSNAME NOT NULL,
                IdDr         INT NULL,
                DataEliminare DATETIME2 DEFAULT SYSUTCDATETIME()
            );
        END

        -- 2) Creaza coloana noua in Client daca nu exista
        IF COL_LENGTH('Client','fk_LocatieClient_id') IS NULL
        BEGIN
            ALTER TABLE Client ADD fk_LocatieClient_id INT NULL;
        END

        -- 3) Populam coloana cu id-ul maxim al locatiei pentru fiecare client
        DECLARE @sql NVARCHAR(MAX);
        SET @sql = '
            ;WITH MaxLocatiePerClient AS (
                SELECT client_id, MAX(id) AS LocatieMaxID
                FROM LocatieClient
                GROUP BY client_id
            )
            UPDATE c
            SET c.fk_LocatieClient_id = m.LocatieMaxID
            FROM Client c
            LEFT JOIN MaxLocatiePerClient m ON m.client_id = c.id;
        ';
        EXEC sp_executesql @sql;

        -- 4) Salvam legaturile eliminate in Legaturi_Eliminate
        INSERT INTO dbo.Legaturi_Eliminate(NumeTabelSt, IdSt, NumeTabelDr, IdDr)
        SELECT 
            'Client' AS NumeTabelSt,
            lc.client_id AS IdSt,
            'LocatieClient' AS NumeTabelDr,
            lc.id AS IdDr
        FROM LocatieClient lc
        INNER JOIN (
            SELECT client_id, MAX(id) AS LocatieMaxID
            FROM LocatieClient
            GROUP BY client_id
        ) m ON lc.client_id = m.client_id
        WHERE lc.id <> m.LocatieMaxID;

        -- 5) Creem un tabel temporar cu doar locatiile pastrate
        SELECT lc.*
        INTO #LocatieClientTemp
        FROM LocatieClient lc
        INNER JOIN (
            SELECT client_id, MAX(id) AS LocatieMaxID
            FROM LocatieClient
            GROUP BY client_id
        ) m ON lc.id = m.LocatieMaxID;

        -- 6) Stergem tabela veche si recreem tabela LocatieClient
        DROP TABLE LocatieClient;

        CREATE TABLE LocatieClient(
            id INT PRIMARY KEY,
            localitate VARCHAR(50),
            oras VARCHAR(50),
            strada VARCHAR(50),
            numar INT
        );

        -- 7) Inseram datele pastrate inapoi
        INSERT INTO LocatieClient(id, localitate, oras, strada, numar)
        SELECT id, localitate, oras, strada, numar
        FROM #LocatieClientTemp;

        DROP TABLE #LocatieClientTemp;

        -- 8) Creem FK nou: Client.fk_LocatieClient_id -> LocatieClient.id
        ALTER TABLE Client
        ADD CONSTRAINT FK_Client_LocatieClient FOREIGN KEY(fk_LocatieClient_id)
            REFERENCES LocatieClient(id);

        COMMIT TRANSACTION;

        PRINT 'Conversia Client → LocatieClient (1:N -> 1:1) a fost efectuata cu succes.';

    END TRY
    BEGIN CATCH
        IF XACT_STATE() <> 0
            ROLLBACK TRANSACTION;

        DECLARE @errmsg NVARCHAR(4000) = ERROR_MESSAGE();
        RAISERROR('A aparut o eroare: %s', 16, 1, @errmsg);
    END CATCH
END
GO

EXEC dbo.usp_Convert_Client_Locatie_1N_to_1_1;

SELECT * FROM Client;
SELECT * FROM LocatieClient;
SELECT * FROM Legaturi_Eliminate;
