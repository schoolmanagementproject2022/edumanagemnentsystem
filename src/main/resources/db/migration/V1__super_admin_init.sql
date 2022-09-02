CREATE SCHEMA IF NOT EXISTS edu_management;
CREATE TABLE super_admin (
       id       SERIAL PRIMARY KEY,
       email    VARCHAR ( 50 ) UNIQUE NOT NULL,
       password VARCHAR ( 255 ) NOT NULL
);

INSERT INTO super_admin (email, password)
VALUES('super@gmail.com','Sa123567+');