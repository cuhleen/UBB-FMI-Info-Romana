CREATE TABLE ducks (
    id BIGINT PRIMARY KEY,
    username VARCHAR(255),
    email VARCHAR(255),
    password VARCHAR(255),
    tip VARCHAR(50),
    viteza DOUBLE PRECISION,
    rezistenta DOUBLE PRECISION
);

CREATE TABLE persons (
    id BIGINT PRIMARY KEY,
    username VARCHAR(255),
    email VARCHAR(255),
    password VARCHAR(255),
    nume VARCHAR(255),
    prenume VARCHAR(255),
    data_nasterii DATE,
    ocupatie VARCHAR(255),
    nivel_empatie DOUBLE PRECISION
);


SELECT * FROM persons;
SELECT * FROM ducks;