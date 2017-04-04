create table users(
	firstName varchar(20) NOT NULL,
	lastName varchar(20),
	username varchar(20),
	password varchar(20) NOT NULL,
	role varchar(20) NOT NULL,
	dob date NOT NULL,
	phone int NOT NULL,
	street varchar(50),
	city varchar(20),
	zip int,
	state varchar(20),
	primary key (username),
	constraint zip_check check (zip>=0 AND zip<100000),
	constraint role_check check(role in ('student','admin','faculty'))
	);

create table admin(
	username varchar(20) ,
	ssn int NOT NULL UNIQUE,
	foreign key (username) REFERENCES users,
	primary key (username)
	);

create table student(
	student_id int,
	username varchar(20) ,
	gpa float,
	termEnrolled varchar(10),
	residencyLevel varchar(20) NOT NULL,
	tuitionBill float,
	tuitionOwed float,
	gradLevel int NOT NULL,
	hasGraduated char(1) NOT NULL,
	email varchar(40) NOT NULL,
	foreign key (username) REFERENCES users,
	primary key (student_id),
	constraint hasGraduated_check check (hasGraduated like '[YyNn]')
	);