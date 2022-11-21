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

CREATE TABLE if not exists academic_class (
    id       SERIAL PRIMARY KEY,
    class_number    VARCHAR ( 50 ) UNIQUE NOT NULL,
    classroom_teacher_id    bigint references teacher(id)
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
    password VARCHAR ( 255 ) NOT NULL,
    academic_class_id bigint references academic_class(id)
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

CREATE TABLE if not exists academic_year (
    id       SERIAL PRIMARY KEY,
    end_date date NOT NULL,
    start_date date NOT NULL
);

CREATE TABLE if not exists subject (
    id       SERIAL PRIMARY KEY,
    name VARCHAR ( 50 ) NOT NULL UNIQUE
);

CREATE TABLE if not exists subject_teacher_mapping (
    subject_id       bigint,
    teacher_id       bigint
);

CREATE TABLE if not exists academic_course (
    id       SERIAL PRIMARY KEY,
    name VARCHAR ( 50 ) NOT NULL,
    subject_id bigint NOT NULL references subject(id)
);

CREATE TABLE if not exists academic_course_teacher_mapping (
    academic_course_id       bigint,
    teacher_id       bigint
);

CREATE TABLE if not exists academic_class_academic_course_mapping (
    academic_class_id       bigint,
    academic_course_id       bigint
);

CREATE TABLE if not exists vacation (
    id       SERIAL PRIMARY KEY,
    start_date date NOT NULL,
    end_date date NOT NULL
);

CREATE TABLE if not exists academic_class_teacher_mapping (
    academic_class_id       bigint,
    teacher_id       bigint
);

INSERT INTO user_table (email, role)
VALUES('heghine9696@gmail.com','ADMIN');

INSERT INTO user_table (email, role)
VALUES('gayaneHovhannisyan@gmail.com','TEACHER');

INSERT INTO user_table (email, role)
VALUES('gayushPoghosyan@gmail.com','TEACHER');

INSERT INTO user_table (email, role)
VALUES('student@gmail.com','STUDENT');

INSERT INTO user_table (email, role)
VALUES('parent@gmail.com','PARENT');

INSERT INTO user_table (email, role)
VALUES('nairaBogdanyan@gmail.com','PARENT');

INSERT INTO admin (username, surname, password, user_id)
VALUES ('Heghine', 'Khachatryan' ,'$2a$10$Bc4E52S/HJLeWUmZZT5/YOOI49CfeOeLUOix9CShwWkN7aOLTL9FC',
        (SELECT id FROM user_table WHERE email='heghine9696@gmail.com'));

INSERT INTO parent (name, surname, user_id, password)
VALUES ('Parent', 'Parentyan' , (SELECT id FROM user_table WHERE email='parent@gmail.com'),
        '$2a$10$Bc4E52S/HJLeWUmZZT5/YOOI49CfeOeLUOix9CShwWkN7aOLTL9FC');

INSERT INTO parent (name, surname, user_id, password)
VALUES ('Naira', 'Bogdanyan' , (SELECT id FROM user_table WHERE email='nairaBogdanyan@gmail.com'),
        '$2a$10$Bc4E52S/HJLeWUmZZT5/YOOI49CfeOeLUOix9CShwWkN7aOLTL9FC');

INSERT INTO teacher (name, surname, user_id, password)
VALUES ('Gayane', 'Hovhannisyan', (SELECT id FROM user_table WHERE email='gayaneHovhannisyan@gmail.com'),
        '$2a$10$Bc4E52S/HJLeWUmZZT5/YOOI49CfeOeLUOix9CShwWkN7aOLTL9FC');

INSERT INTO teacher (name, surname, user_id, password)
VALUES ('Gayush', 'Poghosyan', (SELECT id FROM user_table WHERE email='gayushPoghosyan@gmail.com'),
        '$2a$10$Bc4E52S/HJLeWUmZZT5/YOOI49CfeOeLUOix9CShwWkN7aOLTL9FC');

INSERT INTO academic_class (class_number, classroom_teacher_id)
VALUES ('1A', (SELECT id FROM teacher WHERE teacher.user_id=(SELECT id FROM user_table WHERE email='gayushPoghosyan@gmail.com')));

INSERT INTO student (name, surname, user_id, address, date, gender, blood_group, password, academic_class_id)
VALUES ('Student', 'Studentyan', (SELECT id FROM user_table WHERE email='student@gmail.com'), 'Gyumri',
        '2019-10-09','MALE', 'A_PLUS',
        '$2a$10$Bc4E52S/HJLeWUmZZT5/YOOI49CfeOeLUOix9CShwWkN7aOLTL9FC', (SELECT id FROM academic_class WHERE class_number='1A'));

INSERT INTO academic_year (end_date, start_date)
VALUES ('2023-11-09', '2024-11-09');

INSERT INTO subject (name)
VALUES ('Languages');

INSERT INTO subject_teacher_mapping (subject_id, teacher_id)
VALUES ((SELECT id FROM subject WHERE name='Languages'), (SELECT id FROM teacher WHERE teacher.user_id=(SELECT id FROM user_table WHERE email='gayaneHovhannisyan@gmail.com')));
INSERT INTO academic_course (name, subject_id)
VALUES ('English', (SELECT id FROM subject WHERE name='Languages'));

INSERT INTO academic_course_teacher_mapping (academic_course_id, teacher_id)
VALUES ((SELECT id FROM academic_course WHERE name='English'), (SELECT id FROM teacher WHERE teacher.user_id=(SELECT id FROM user_table WHERE email = 'gayaneHovhannisyan@gmail.com')));

INSERT INTO academic_class_academic_course_mapping (academic_class_id, academic_course_id)
VALUES ((SELECT id FROM academic_class WHERE class_number='1A'), (SELECT id FROM academic_course WHERE name='English'));

INSERT INTO vacation (start_date, end_date)
VALUES ('2023-11-09', '2024-11-09');

INSERT INTO academic_class_teacher_mapping (academic_class_id, teacher_id)
VALUES ((SELECT id FROM academic_class WHERE class_number = '1A'), (SELECT id FROM teacher WHERE teacher.user_id=(SELECT id FROM user_table WHERE email = 'gayushPoghosyan@gmail.com')));
