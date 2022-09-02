CREATE SCHEMA IF NOT EXISTS edu_management;
CREATE TABLE super_admin (
       id       int PRIMARY KEY,
       email    VARCHAR ( 50 ) UNIQUE NOT NULL,
       password VARCHAR ( 255 ) NOT NULL
);

INSERT INTO super_admin (id,email, password)
VALUES('1','super@gmail.com','Sa123567+');