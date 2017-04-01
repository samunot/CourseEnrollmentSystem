
--create table queries 

create table users(
	user_id int,
	firstName varchar(20) NOT NULL,
	lastName varchar(20),
	username varchar(40) NOT NULL UNIQUE,
	password varchar(20) NOT NULL,
	role varchar(20),
	street varchar(50),
	city varchar(20),
	zip int,
	state varchar(20),
	primary key (user_id),
	constraint zip_check check (zip>=0 AND zip<100000)
	);

create table admin(
	admin_id int,
	user_id int,
	ssn int NOT NULL UNIQUE,
	foreign key (user_id) REFERENCES users,
	primary key (admin_id)
	);

create table student(
	student_id int,
	user_id int,
	dob date NOT NULL,
	gpa float,
	termEnrolled varchar(10),
	residencyLevel varchar(20) NOT NULL,
	tuitionBill float,
	gradLevel int NOT NULL,
	hasGraduated char(1) NOT NULL,
	email varchar(40) NOT NULL,
	foreign key (user_id) REFERENCES users,
	primary key (student_id),
	constraint hasGraduated_check check (hasGraduated like '[YN]')
	);

create table faculty(
	faculty_id int,
	user_id int,
	foreign key (user_id) REFERENCES users,
	primary key (faculty_id)
	);

create table term(
	year int,
	semester varchar(10),
	addDeadline date,
	dropDeadline date,
	primary key (year,semester)
	);

create table course(
	title varchar(50) NOT NULL UNIQUE,
	course_id varchar(20),
	gradLevel int NOT NULL,
	credits int NOT NULL,
	permission char(1) NOT NULL,
	primary key (course_id),
	constraint perm_check check (permission like '[YN]')
	);

create table sem(
	course_id varchar(20),
	faculty_id int,
	year int,
	semester varchar(10),
	maxWaitist int,
	location varchar(20),
	studentsEnrolled int,
	maxSize int,
	scheduleDay varchar(10),
	scheduleTime varchar(20),
	foreign key (faculty_id) REFERENCES faculty,
	foreign key (course_id) REFERENCES course,
	foreign key (year,semester) REFERENCES term,
	primary key (course_id,year,semester)
	);

create table department(
	department_id varchar(20),
	name varchar(40),
	primary key (department_id)
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
	admin_id int,
	year int,
	semester varchar(10),
	reqDate date,
	approveDate date,
	foreign key (student_id) REFERENCES student,
	foreign key (admin_id) REFERENCES admin,
	foreign key (course_id) REFERENCES course,
	foreign key (year,semester) REFERENCES term,
	primary key (student_id,course_id,year,semester)
	);

create table createCourse(
	admin_id int,
	course_id varchar(20),
	foreign key (admin_id) REFERENCES admin,
	foreign key (course_id) REFERENCES course,
	primary key (admin_id,course_id)
	);

create table enrolled(
	student_id int,
	course_id varchar(20),
	year int,
	semester varchar(10),
	grade varchar(2),
	gpa float,
	courseCredits int, 
	waitlistNumber int,
	enrolledStatus char(1) NOT NULL,
	foreign key (student_id) REFERENCES student,
	foreign key (course_id,year,semester) REFERENCES sem,
	primary key (student_id,year,semester,course_id),
	constraint enrolled_check check (enrolledStatus like '[YN]')
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
	grade varchar(20),
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