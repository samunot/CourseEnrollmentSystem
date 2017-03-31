
//create table queries 
create table fallcourse(
	name varchar(50),
	fallcourse_id varchar(20),
	maxCredits int,
	minCredits int,
	department varchar(20),
	gradLevel varchar(20),
	classSize int,
	waitlistSize int,
	schedule varchar(100),
	prerequisiteCourses varchar(50),
	primary key (fallcourse_id)
	);

create table springcourse(
	name varchar(50),
	springcourse_id varchar(20),
	maxCredits int,
	minCredits int,
	department varchar(20),
	gradLevel varchar(20),
	classSize int,
	waitlistSize int,
	schedule varchar(100),
	prerequisiteCourses varchar(50),
	primary key (springcourse_id)
	);

create table student(
	name varchar(50),
	student_id int,
	dob date,
	status varchar(50),
	gradLevel varchar(20),
	department varchar(20),
	billAmount int,
	primary key (student_id)
	);

create table fallcoursegrade(
	student_id int,
	fallcourse_id varchar(20),
	grade varchar(2),
	foreign key (student_id) references student,
	foreign key (fallcourse_id) references fallcourse,
	primary key (student_id,fallcourse_id)
	);

Create
//insert in 

insert into fallcourse values('Introduction to Computer Science', 'CSC401', 3, 3, 'CS', 'Undergraduate', 2, 2, 'M,W 12:00PM-1:00PM', null);
insert into fallcourse values('Independent Study', 'CSC525', 3, 1, 'CSC525', 'Graduate', 2, 0, 'M,W 3:00PM-4:00PM', 'Requires Special Permission');
insert into fallcourse values('Database', 'CSC510', 3, 3, 'CS', 'Graduate', 5, 2, 'Tu,Th 1:00PM-2:00PM', null);
insert into fallcourse values('Software Engineering', 'CSC515', 3, 3, 'CS', 'Graduate', 3, 2, 'Tu,Th 2:00PM-3:00PM', null);