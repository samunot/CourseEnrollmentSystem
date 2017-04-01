--create table queries 

create table users(
	firstName varchar(20) NOT NULL,
	lastName varchar(20),
	username varchar(40) NOT NULL UNIQUE,
	password varchar(20) NOT NULL,
	role varchar(20) NOT NULL,
	dob date NOT NULL,
	street varchar(50),
	city varchar(20),
	zip int,
	state varchar(20),
	primary key (username),
	constraint zip_check check (zip>=0 AND zip<100000),
	constraint role_check check(role like 'student|admin|faculty')
	);

create table admin(
	employee_id int,
	username varchar(20)
	ssn int NOT NULL UNIQUE,
	foreign key (username) REFERENCES users,
	primary key (employee_id)
	);

create table student(
	student_id int,
	username varchar(20),
	gpa float,
	termEnrolled varchar(10),
	residencyLevel varchar(20) NOT NULL,
	tuitionBill float,
	tuitionOwed float,
	phone int NOT NULL,
	gradLevel int NOT NULL,
	hasGraduated char(1) NOT NULL,
	email varchar(40) NOT NULL,
	foreign key (username) REFERENCES users,
	primary key (student_id),
	constraint hasGraduated_check check (hasGraduated like '[YyNn]')
	);

create table faculty(
	faculty_id int,
	username int,
	foreign key (username) REFERENCES users,
	primary key (faculty_id)
	);

create table semester(
	sem varchar(3) UNIQUE NOT NULL,
	year int NOT NULL,
	term varchar(10) NOT NULL,
	addDeadline date,
	dropDeadline date,
	primary key (sem)
	);

create table course(
	title varchar(50) NOT NULL UNIQUE,
	course_id varchar(20),
	employee_id int,
	gradLevel int NOT NULL,
	credits int NOT NULL,
	permission char(1) NOT NULL,
	primary key (course_id),
	foreign key (employee_id) REFERENCES admin,
	constraint perm_check check (permission like '[YyNn]')
	);

	create table department(
	department_id varchar(20),
	name varchar(40),
	primary key (department_id)
	);

create table offering(
	course_id varchar(20),
--	department_id varchar(20),
	faculty_id int,
	sem varchar(3),
	maxWaitist int,
	maxSize int,
	location varchar(20),
	studentsEnrolled int,
	studentsWaitlisted int,
	scheduleDay varchar(10),
	scheduleTime varchar(20),
	foreign key (faculty_id) REFERENCES faculty,
	foreign key (course_id) REFERENCES course,
--	foreign key (department_id) REFERENCES department,
	foreign key (sem) REFERENCES semester,
	primary key (course_id,sem),
	constraint waitlist_check check(maxWaitist>=studentsWaitlisted),
	constraint size_check check(maxSize>=studentsEnrolled),
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
	minGPA float,
	foreign key (coursePrereq_id) REFERENCES course,
	foreign key (course_id) REFERENCES course,
	primary key (coursePrereq_id,course_id)
	);

create table specialPermission(
	student_id int,
	course_id varchar(20),
	employee_id int,
	sem varchar(3),
	reqDate date,
	approveDate date,
	foreign key (student_id) REFERENCES student,
	foreign key (employee_id) REFERENCES admin,
	foreign key (course_id) REFERENCES course,
	foreign key (sem) REFERENCES semester,
	primary key (student_id,course_id,sem)
	);

--create table createCourse(
--	employee_id int,
--	course_id varchar(20),
--	foreign key (employee_id) REFERENCES admin,
--	foreign key (course_id) REFERENCES course,
--	primary key (employee_id,course_id)
--	);

create table enrolled(
	student_id int,
	course_id varchar(20),
	sem varchar(3),
	grade varchar(2),
	gpa float,
	courseCredits int, 
	waitlistNumber int,
	enrolledStatus char(1) NOT NULL,
	foreign key (student_id) REFERENCES student,
	foreign key (course_id,sem) REFERENCES semester,
	primary key (student_id,sem,course_id),
	constraint enrolled_check check (enrolledStatus like '[YyNn]')
	);

create table costAndLimit(
	educationlevel varchar(20) ,
	residency varchar(20) ,
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

drop table departmentCourse;
drop table studentDepartment;
drop table prereq;
drop table specialpermission;
drop table createCourse;
drop table enrolled;
drop table costandlimit;
drop table gradingsystem;
drop table sem;
drop table student;
drop table faculty;
drop table admin;
drop table term;
drop table department;
drop table course;
drop table users;
