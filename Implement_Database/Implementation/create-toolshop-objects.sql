-- DROP DATABASE IF EXISTS university;
-- CREATE DATABASE university; 
-- USE university;

-- DROP TABLE IF EXISTS STUDENT;
-- CREATE TABLE STUDENT (
--   Name         		varchar(15),
--   Student_number      	int,
--   Class     			int, 
--   Major					varchar(5),
--   primary key (Student_number)
-- );

-- DROP TABLE IF EXISTS COURSE;
-- CREATE TABLE COURSE (
--   Course_name		    varchar(50), 
--   Course_number    		varchar(15),
--   Credit_hours		    int,
--   Department		    char(15),
--   primary key (Course_number)
-- );


-- DROP TABLE IF EXISTS SECTION;
-- CREATE TABLE SECTION (
--   Section_identifier	int,
--   Course_number			varchar(15),
--   Semester            	varchar(15),
--   Year 			        int,
--   Instructor		    varchar(15),
--   primary key (Section_identifier),
--   foreign key (Course_number) references COURSE(Course_number)
-- );


-- DROP TABLE IF EXISTS GRADE_REPORT;
-- CREATE TABLE GRADE_REPORT (
--   Student_number		int,
--   Section_identifier	int,
--   Grade					char,
--   foreign key (Student_number) references STUDENT(Student_number),
--   foreign key (Section_identifier) references SECTION(Section_identifier)
-- 			  ON DELETE SET NULL
-- );


-- DROP TABLE IF EXISTS PREREQUISITE;
-- CREATE TABLE PREREQUISITE (
--   Course_number      	varchar(15),
--   Prerequisite_number	varchar(15),
--   foreign key (Course_number) references COURSE(Course_number),
--     foreign key (Prerequisite_number) references COURSE(Course_number)
-- );



INSERT INTO STUDENT 
VALUES ('Smith', 17, 1, 'CS'),
	   ('Brown', 8, 2, 'CS');

INSERT INTO COURSE 
VALUES ('Intro to Computer Science', 'CS1310', 4, 'CS'),
	   ('Data Structures', 'CS3320', 4, 'CS'),
       ('Discrete Mathematics', 'MATH2410', 4, 'MATH'),
       ('Database', 'CS3380', 3, 'CS');

INSERT INTO SECTION
VALUES (85, 'MATH2410', 'Fall', 07, 'King'),
	   (92, 'CS1310', 'Fall', 07, 'Anderson'),
	   (102, 'CS3320', 'Spring', 08, 'Knuth'),
       (112, 'MATH2410', 'Fall', 08, 'Chang'),
	   (119, 'CS1310', 'Fall', 08, 'Anderson'),
       (135, 'CS3380', 'Fall', 08, 'Stone');

INSERT INTO GRADE_REPORT
VALUES (17, 112, 'B'),
	   (17, 119, 'C'),
       (8, 85, 'A'),
       (8, 92, 'A'),
       (8, 102, 'B'),
       (8, 135, 'A');

INSERT INTO PREREQUISITE
VALUES ('CS3380', 'CS3320'),
	   ('CS3380', 'MATH2410'),
       ('CS3320', 'CS1310');
