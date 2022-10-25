CREATE TABLE if not exists admin (
    id       SERIAL PRIMARY KEY,
    username    VARCHAR ( 50 ) NOT NULL,
    surname VARCHAR ( 50 ) NOT NULL,
    password VARCHAR ( 255 ) NOT NULL,
    user_id bigint
);

CREATE TABLE if not exists teacher (
    id       SERIAL PRIMARY KEY,
    name    VARCHAR ( 50 ) NOT NULL,
    surname VARCHAR ( 50 ) NOT NULL,
    user_id bigint,
    password VARCHAR ( 255 ) NOT NULL
);

CREATE TABLE if not exists student (
    id       SERIAL PRIMARY KEY,
    name    VARCHAR ( 50 ) NOT NULL,
    surname VARCHAR ( 50 ) NOT NULL,
    user_id bigint,
    address VARCHAR ( 50 ) NOT NULL,
    date date NOT NULL,
    gender VARCHAR ( 50 ) NOT NULL,
    blood_group VARCHAR ( 50 ) NOT NULL,
    password VARCHAR ( 255 ) NOT NULL
);

CREATE TABLE if not exists parent (
    id       SERIAL PRIMARY KEY,
    name    VARCHAR ( 50 ) NOT NULL,
    surname VARCHAR ( 50 ) NOT NULL,
    user_id bigint,
    password VARCHAR ( 255 ) NOT NULL
);

CREATE TABLE if not exists user_table (
    id       SERIAL PRIMARY KEY,
    email    VARCHAR ( 50 ) UNIQUE NOT NULL,
    role VARCHAR ( 50 ) NOT NULL
);

CREATE TABLE if not exists academic_class (
    id       SERIAL PRIMARY KEY,
    class_number    VARCHAR ( 50 ) UNIQUE NOT NULL
);

INSERT INTO user_table (email, role)
VALUES('heghine9696@gmail.com','ADMIN');

INSERT INTO user_table (email, role)
VALUES('nairaBogdanyan@gmail.com','TEACHER');

INSERT INTO user_table (email, role)
VALUES('student@gmail.com','STUDENT');

INSERT INTO user_table (email, role)
VALUES('parent@gmail.com','PARENT');

INSERT INTO admin (username, surname, password, user_id)
VALUES ('Heghine', 'Khachatryan' ,'$2a$10$Bc4E52S/HJLeWUmZZT5/YOOI49CfeOeLUOix9CShwWkN7aOLTL9FC',
        (SELECT id FROM user_table WHERE email='heghine9696@gmail.com'));

INSERT INTO parent (name, surname, user_id, password)
VALUES ('Parent', 'Parentyan' , (SELECT id FROM user_table WHERE email='parent@gmail.com'),
        '$2a$10$Bc4E52S/HJLeWUmZZT5/YOOI49CfeOeLUOix9CShwWkN7aOLTL9FC');

INSERT INTO teacher (name, surname, user_id, password)
VALUES ('Naira', 'Bogdanyan', (SELECT id FROM user_table WHERE email='nairaBogdanyan@gmail.com'),
        '$2a$10$Bc4E52S/HJLeWUmZZT5/YOOI49CfeOeLUOix9CShwWkN7aOLTL9FC');

INSERT INTO student (name, surname, user_id, address, date, gender, blood_group, password)
VALUES ('Student', 'Studentyan', (SELECT id FROM user_table WHERE email='student@gmail.com'), 'Gyumri',
        '2019-10-09','MALE', 'A_PLUS',
        '$2a$10$Bc4E52S/HJLeWUmZZT5/YOOI49CfeOeLUOix9CShwWkN7aOLTL9FC');

INSERT INTO academic_class (class_number)
VALUES ('1A');
