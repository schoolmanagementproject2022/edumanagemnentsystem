CREATE TABLE super_admin (
      id       SERIAL PRIMARY KEY,
      email    VARCHAR ( 50 ) UNIQUE NOT NULL,
      password VARCHAR ( 255 ) NOT NULL
);

INSERT INTO super_admin (email, password)
VALUES('super@gmail.com','$2a$12$afCkykYWEECra01rkCoNfeOBnKKgN/cG1sCoHB3Cfa37M99mXlJ.q');