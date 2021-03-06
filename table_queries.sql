--create table queries 

create table users(
	firstName varchar(20) NOT NULL,
	lastName varchar(20),
	username varchar(20),
	password varchar(20) NOT NULL,
	role varchar(20) NOT NULL,
	dob date NOT NULL,
	phone int,
	street varchar(50),
	city varchar(20),
	zip int,
	state varchar(20),
	primary key (username),
	constraint zip_check check (zip>=0 AND zip<100000),
	constraint role_check check(role in ('student','admin','faculty'))
	);

create table admin(
	username varchar(20),
	ssn int NOT NULL UNIQUE,
	employee_id int,
	foreign key (username) REFERENCES users,
	primary key (username)
	);

create table student(
	student_id int,
	username varchar(20),
	gpa float,
	termEnrolled varchar(10),
	residencyLevel int NOT NULL,
	tuitionOwed float,
	minCredit int,
	maxCredit int,
	gradLevel int NOT NULL,
	hasGraduated char(1) DEFAULT 'N',
	email varchar(40),
	foreign key (username) REFERENCES users,
	primary key (student_id),
	constraint hasGraduated_check check (hasGraduated in ('Y','N')),
	constraint level_check check(residencyLevel in (1,2,3)),
	constraint gradlevel_check check(gradLevel in (1,2)),
	constraint gpa_check check(GPA<=4.33)
	);


create table faculty(
	faculty_id int,
	username varchar(20),
	foreign key (username) REFERENCES users,
	primary key (faculty_id)
	);

create table semester(
	sem varchar(3), --eg F16 for fall 2016
	year int NOT NULL,
	term varchar(10) NOT NULL,
	addDeadline date DEFAULT NULL,
	dropDeadline date DEFAULT NULL,
	primary key (sem)
	);

create table course(
	title varchar(50) NOT NULL UNIQUE,
	course_id varchar(20),
	gradLevel int NOT NULL,
	minGPA float,
	credits varchar(3) NOT NULL,
	permission char(1) NOT NULL,
	primary key (course_id),
	constraint perm_check check (permission in ('Y','N')),
	constraint gradlevel2_check check(gradLevel in (1,2))
	);

create table department(
	department_id varchar(20),
	name varchar(40),
	primary key (department_id)
	);

create table offering(
	course_id varchar(20),
	department_id varchar(20),
	sem varchar(3),
	maxWaitist int,
	maxSize int,
	location varchar(20),
	studentsEnrolled int,
	studentsWaitlisted int,
	schedule varchar(20),
	foreign key (course_id) REFERENCES course,
	foreign key (department_id) REFERENCES department,
	foreign key (sem) REFERENCES semester,
	primary key (course_id,sem),
	constraint waitlist_check check(maxWaitist>=studentsWaitlisted),
	constraint size_check check(maxSize>=studentsEnrolled)
	);

create table departmentCourse(
	department_id varchar(20),
	course_id varchar(20),
	foreign key (department_id) REFERENCES department,
	foreign key (course_id) REFERENCES course,
	primary key (department_id,course_id)
	);
 
create table studentDepartment(
	department_id varchar(20),
	student_id int,
	foreign key (department_id) REFERENCES department,
	foreign key (student_id) REFERENCES student,
	primary key (department_id,student_id)
	);

create table prereq(
	coursePrereq_id varchar(20),
	course_id varchar(20),
	minGrade varchar(2),
	foreign key (coursePrereq_id) REFERENCES course,
	foreign key (course_id) REFERENCES course,
	primary key (coursePrereq_id,course_id)
	);

create table specialPermission(
	student_id int,
	course_id varchar(20),
	username varchar(20),
	sem varchar(3),
	reqDate date,
	approveDate date DEFAULT NULL,
	approveStatus char(1) DEFAULT NULL,
	foreign key (student_id) REFERENCES student,
	foreign key (username) REFERENCES admin,
	foreign key (course_id,sem) REFERENCES offering,
	primary key (student_id,course_id,sem),
	constraint approvestatus_check check (approveStatus in ('Y','N'))
	);

create table enrolled(
	student_id int,
	course_id varchar(20),
	sem varchar(3),
	grade varchar(2),
	courseCredits int, 
	waitlistNumber int DEFAULT 0,
	enrolledStatus char(1) NOT NULL,
	foreign key (student_id) REFERENCES student,
	foreign key (course_id,sem) REFERENCES offering,
	primary key (student_id,sem,course_id),
	constraint enrolled_check check (enrolledStatus in ('Y','N'))
	);

create table facultyOffering(
	faculty_id int,
	course_id varchar(20),
	sem varchar(3),
	foreign key (faculty_id) REFERENCES faculty,
	foreign key (course_id,sem) REFERENCES offering,
	primary key (faculty_id,course_id,sem)
	);

create table costAndLimit(
	educationlevel int ,
	residency int ,
	minCredit int,
	maxCredit int,
	costPerCredit int,
	primary key (educationlevel,residency)
	);

create table gradingSystem(
	grade varchar(2),
	pointsPerHour float,
	primary key(grade)
	);

