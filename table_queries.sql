
--create table queries 

create table users(
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
	constraint role_check check (role>=0 AND role<=2),
	constraint zip_check check (zip>=0 AND zip<100000)
	);

create table admin(
	ssn int NOT NULL UNIQUE,
	admin_id int,
	user_id int,
	foreign key (user_id) REFERENCES users,
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
	hasGraduated int,
	foreign key (user_id) REFERENCES users,
	primary key (student_id),
	constraint boolean_check check (hasGraduated>=0 AND hasGraduated<=1)
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
	minGPA float,
	gradLevel int NOT NULL,
	credits int NOT NULL,
	permission int,
	primary key (course_id),
	constraint perm_check check (permission>=0 AND permission<=1)
	);

create table offering(
	course_id varchar(20),
	year int,
	semester varchar(10),
	maxWaitist int,
	location varchar(20),
	maxSize int,
	scheduleDay varchar(10),
	scheduleTime varchar(20),
	foreign key (course_id) REFERENCES course,
	foreign key (year,semester) REFERENCES term,
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
	semester varchar(10),
	course_id varchar(20),
	foreign key (faculty_id) REFERENCES faculty,
	foreign key (year,semester) REFERENCES term,
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
	enrolledStatus int,
	foreign key (student_id) REFERENCES student,
	foreign key (course_id,year,semester) REFERENCES offering,
	primary key (student_id,year,semester,course_id),
	constraint enrolled_check check (enrolledStatus>=0 AND enrolledStatus<=1)
	);

create table CostAndLimit(
educationlevel varchar(20) ,
residency varchar(20) ,
minCredit int,
maxCredit int,
costPerCredit int,
primary key (educationlevel,residency)
);

create table GradingSystem(
grade varchar(20),
pointsPerHour float,
primary key(grade)
);