DROP TABLE contact IF EXISTS;

CREATE TABLE contact(
    id IDENTITY primary key,
    email VARCHAR (20),
    first_name VARCHAR(20),
    last_name VARCHAR(20)
);