
//create table queries 

create table user(
	firstName varchar(20) NOT NULL,
	lastName varchar(20),
	email varchar(40) NOT NULL UNIQUE,
	password varchar(20) NOT NULL,
	role int,
	street varchar(50),
	city varchar(20),
	zip int,
	state varchar(20),
	user_id int,
	primary key (user_id),
	constraint role_check check (role>=0 AND role<=2)
	);

create table admin(
	ssn int NOT NULL UNIQUE,
	admin_id int,
	user_id int,
	foreign key (user_id) REFERENCES user,
	primary key (admin_id)
	);

create table student(
	dob date NOT NULL,
	student_id int,
	user_id int,
	gpa float,
	termEnrolled varchar(10),
	residencyLevel int NOT NULL,
	tuitionBill float,
	gradLevel int NOT NULL,
	hasGraduated boolean,
	foreign key (user_id) REFERENCES user,
	primary key (student_id)
	);

create table faculty(
	faculty_id int,
	user_id int,
	foreign key (user_id) REFERENCES user,
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
	minGPA float,
	gradLevel int NOT NULL,
	credits int NOT NULL,
	permission boolean DEFAULT FALSE,
	primary key (course_id)
	);

create table offering(
	course_id varchar(20),
	year int,
	semester varchar(10),
	maxWaitist int,
	location varchar(20),
	maxSize int,
	scheduleDay varchar(20),
	scheduleTime time,
	foreign key (course_id) REFERENCES course,
	foreign key (year) REFERENCES term,
	foreign key (semester) REFERENCES term,
	primary key (course_id,year,semester)
	);

create table department(
	department_id varchar(20),
	name varchar(40),
	primary key (department_id)
	);

create table offers(
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

create table specperm(
	student_id int,
	course_id varchar(20),
	admin_id int,
	foreign key (student_id) REFERENCES student,
	foreign key (admin_id) REFERENCES admin,
	foreign key (course_id) REFERENCES course,
	primary key (student_id,course_id,admin_id)
	);

create table createCourse(
	admin_id int,
	course_id varchar(20),
	foreign key (admin_id) REFERENCES admin,
	foreign key (course_id) REFERENCES course,
	primary key (admin_id,course_id)
	);

create table teaches(
	faculty_id int,
	year int,
	semester varchar(10)
	course_id varchar(20),
	foreign key (faculty_id) REFERENCES faculty,
	foreign key (year) REFERENCES term,
	foreign key (semester) REFERENCES term,
	foreign key (course_id) REFERENCES course,
	primary key (faculty_id,year,semester,course_id)
	);

create table enrolled(
	student_id int,
	course_id varchar(20),
	year int,
	semester varchar(10),
	grade varchar(2),
	waitlistNumber int,
	enrolledStatus boolean,
	foreign key (student_id) REFERENCES student,
	foreign key (year) REFERENCES offering,
	foreign key (semester) REFERENCES offering,
	foreign key (course_id) REFERENCES offering,
	primary key (student_id,year,semester,course_id)
	);


Create
//insert in 

insert into fallcourse values('Introduction to Computer Science', 'CSC401', 3, 3, 'CS', 'Undergraduate', 2, 2, 'M,W 12:00PM-1:00PM', null);
insert into fallcourse values('Independent Study', 'CSC525', 3, 1, 'CSC525', 'Graduate', 2, 0, 'M,W 3:00PM-4:00PM', 'Requires Special Permission');
insert into fallcourse values('Database', 'CSC510', 3, 3, 'CS', 'Graduate', 5, 2, 'Tu,Th 1:00PM-2:00PM', null);
insert into fallcourse values('Software Engineering', 'CSC515', 3, 3, 'CS', 'Graduate', 3, 2, 'Tu,Th 2:00PM-3:00PM', null);