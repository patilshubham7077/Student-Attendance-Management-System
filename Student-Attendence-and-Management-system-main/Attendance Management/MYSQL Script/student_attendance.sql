create database student_attendance;
use student_attendance;
SELECT * FROM student_attendance.student_records;
SELECT * FROM student_attendance.users;
CREATE TABLE `student_records` (
  `Student_name` varchar(45) NOT NULL,
  `Student_id` varchar(45) NOT NULL,
  `Year` varchar(45) NOT NULL,
  `lec_attended` varchar(45) NOT NULL,
  `Lec_conducted` varchar(45) NOT NULL,
  `percentage` varchar(45) NOT NULL,
  PRIMARY KEY (`Student_id`),
  UNIQUE KEY `Student_id_UNIQUE` (`Student_id`)
);
create table users
(
	username varchar(45),
    password varchar(45)
);
create table studentUsers
(
	username varchar(45),
    password varchar(45)
);
create table Lecture_records
 (Date varchar(45),
 Time varchar(45),
 StudentId varchar(45),
 Name varchar(45),
 Attendance_status varchar(45)
 );
Insert into users values('harshal_kumbhar','Harshal@7709');
