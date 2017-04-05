ALTER SESSION SET nls_date_format='mm-dd-yyyy';

--users
insert into users values('Sarat','Kavuru','skavuru','44444','faculty','12-31-1991',123654,'Avent Ferry Road','Raleigh',27606,'NC');
insert into users values('Albert','Einstein','aeinstien','55555','faculty','3-15-1962',123321,'Gormat street','Raleigh',27606,'NC');

insert into users values('Harry','Potter','hpotter','101','student','01-12-1990',164664,'Ligon street','Raleigh',27606,'NC');
insert into users values('Ron','Weasley','rweasly','102','student','04-16-1989',145654,'Lils street','Raleigh',27606,'NC');
insert into users values('Hermonie','Granger','hgranger','103','student','12-19-1991',116664,'Octavia street','Raleigh',27606,'NC');
insert into users values('Draco','Malfoy','dmalfoy','104','student','03-21-1992',667464,'Hillsborough street','Raleigh',27606,'NC');
insert into users values('Albus','Dumbledore','alby','hogwarts','admin','05-26-1984',951357,'Gorman Street','Raleigh',27606,'NC');

--student
insert into student (student_id,username,termEnrolled,residencyLevel,tuitionOwed,gradLevel,email)
	values(101,'hpotter','fall 16',1,1200.0,1,'harry.potter@gmail.com');
insert into student (student_id,username,termEnrolled,residencyLevel,tuitionOwed,gradLevel,email)
	values(102,'rweasly','fall 16',1,0.0,2,'ron.weasley@gmail.com');
insert into student (student_id,username,termEnrolled,residencyLevel,tuitionOwed,gradLevel,email)
	values(103,'hgranger','fall 16',2,0.0,2,'hermonie.granger@gmail.com');
insert into student (student_id,username,termEnrolled,residencyLevel,tuitionOwed,gradLevel,email)
	values(104,'dmalfoy','fall 16',3,0.0,2,'draco.malfoy@gmail.com');


--admin
insert into admin values('alby',987654321,1111);


--faculty
insert into faculty values(4444,'skavuru');
insert into faculty values(5555,'aeinstien');

--sem
insert into semester (sem,year,term)values('F16',2016,'fall');
insert into semester (sem,year,term)values('S17',2017,'spring');

--course

insert into course (title,course_id,gradLevel,credits,permission)values('Introduction to Computer Science','CSC401',1,'3','N');
insert into course (title,course_id,gradLevel,credits,permission)values('Database','CSC510',2,'3','N');
insert into course (title,course_id,gradLevel,credits,permission)values('Software Engineering','CSC515',2,'3','N');
insert into course (title,course_id,gradLevel,credits,permission)values('Internet Protocols','CSC520',2,'3','N');
insert into course (title,course_id,gradLevel,credits,permission)values('VLSI','CE420',1,'3','N');
insert into course (title,course_id,gradLevel,credits,permission)values('Independent Study','CSC525',2,'1-3','Y');
insert into course (title,course_id,gradLevel,credits,permission)values('Numerical Methods','CSC402',1,'3','Y');
insert into course (title,course_id,gradLevel,credits,permission)values('Algorithms','CSC505',2,'3','N');
insert into course values('Cloud Computing','CSC521',2,3.5,'3','Y');
insert into course (title,course_id,gradLevel,credits,permission)values('Dev-Ops','CSC530',2,'3','Y');
insert into course (title,course_id,gradLevel,credits,permission)values('VLSI II','CE421',1,'3','Y');

--department
insert into department values('CSC','Computer Science');
insert into department values('ECE','Electrical and Computer Engineering');

--offering
--fall
insert into offering values('CSC401','CSC','F16',2,2,'EB2',2,0,'M W 12:00PM-1:00PM');
insert into offering values('CSC510','CSC','F16',2,5,'EB1',3,0,'Tu Th 1:00PM-2:00PM');
insert into offering values('CSC515','CSC','F16',2,3,'EB3',3,0,'Tu Th 2:00PM-3:00PM');
insert into offering values('CSC520','CSC','F16',2,2,'EB2',1,0,'M W 11:00AM-12:00PM');
insert into offering values('CSC525','CSC','F16',0,2,'EB3',1,0,'M W 3:00PM-4:00PM');
insert into offering values('CE420','CSC','F16',2,4,'EB1',4,1,'F 3:00PM-5:00PM');
--spring
insert into offering values('CSC402','CSC','S17',2,2,'EB2',0,0,'M W 11:00AM-12:00PM');
insert into offering values('CSC510','CSC','S17',2,5,'EB1',0,0,'Tu Th 1:00PM-2:00PM');
insert into offering values('CSC505','CSC','S17',2,2,'EB2',0,0,'M W 11:00AM-12:00PM');
insert into offering values('CSC521','CSC','S17',2,3,'EB3',0,0,'Tu Th 1:00PM-2:00PM');
insert into offering values('CSC525','CSC','S17',0,2,'EB1',0,0,'M W 2:00PM-3:00PM');
insert into offering values('CSC530','CSC','S17',2,2,'EB3',0,0,'M TW 11:00AM-12:00PM');
insert into offering values('CE421','ECE','S17',4,2,'EB3',0,0,'Tu Th 4:00PM-5:00PM');

--departmentCourse
insert into departmentCourse values('CSC','CSC505');
insert into departmentCourse values('CSC','CSC401');
insert into departmentCourse values('CSC','CSC510');
insert into departmentCourse values('CSC','CSC515');
insert into departmentCourse values('CSC','CSC520');
insert into departmentCourse values('CSC','CSC525');
insert into departmentCourse values('CSC','CSC402');
insert into departmentCourse values('CSC','CSC521');
insert into departmentCourse values('CSC','CSC530');
insert into departmentCourse values('ECE','CE420');
insert into departmentCourse values('ECE','CE421');

--studentDepartment

insert into studentDepartment values('ECE',101);
insert into studentDepartment values('CSC',102);
insert into studentDepartment values('CSC',103);
insert into studentDepartment values('CSC',104);

--prereq
insert into prereq (coursePrereq_id,course_id)values('CSC401','CSC402');
insert into prereq (coursePrereq_id,course_id)values('CSC520','CSC521');
insert into prereq (coursePrereq_id,course_id)values('CSC515','CSC530');
insert into prereq (coursePrereq_id,course_id)values('CE420','CE421');

--specialPermission


--enrolled
insert into enrolled (student_id,course_id,sem,grade,coursecredits,enrolledstatus)values(101,'CE420','F16','A',3,'Y');
insert into enrolled (student_id,course_id,sem,grade,coursecredits,enrolledstatus)values(102,'CSC510','F16','B+',3,'Y');
insert into enrolled (student_id,course_id,sem,grade,coursecredits,enrolledstatus)values(102,'CSC515','F16','B+',3,'Y');
insert into enrolled (student_id,course_id,sem,grade,coursecredits,enrolledstatus)values(103,'CSC515','F16','A',3,'Y');
insert into enrolled (student_id,course_id,sem,grade,coursecredits,enrolledstatus)values(103,'CSC520','F16','A-',3,'Y');
--insert into enrolled (student_id,course_id,sem,grade,coursecredits,enrolledstatus)values(103,'CSC530','F16','A+',3,'Y'); 530 not there in fall
insert into enrolled (student_id,course_id,sem,grade,coursecredits,enrolledstatus)values(104,'CSC510','F16','A',3,'Y');
insert into enrolled (student_id,course_id,sem,grade,coursecredits,enrolledstatus)values(104,'CSC515','F16','B+',3,'Y');
insert into enrolled (student_id,course_id,sem,grade,coursecredits,enrolledstatus)values(104,'CSC525','F16','A+',3,'Y');

--facultyOffering
insert into facultyOffering values(4444,'CSC505','S17');
insert into facultyOffering values(5555,'CSC401','F16');
insert into facultyOffering values(5555,'CSC510','F16');
insert into facultyOffering values(5555,'CSC510','S17');
insert into facultyOffering values(4444,'CSC515','F16');
insert into facultyOffering values(4444,'CSC520','F16');
insert into facultyOffering values(5555,'CSC525','F16');
insert into facultyOffering values(5555,'CSC525','S17');
insert into facultyOffering values(4444,'CSC402','S17');
insert into facultyOffering values(4444,'CSC521','S17');
insert into facultyOffering values(4444,'CSC530','S17');
insert into facultyOffering values(5555,'CE420','F16');
insert into facultyOffering values(5555,'CE421','S17');


--costAndLimit
INSERT into costAndLimit values(2,1,0,9,500);
INSERT into costAndLimit values(2,2,0,9,800);
INSERT into costAndLimit values(2,3,9,9,1000);
INSERT into costAndLimit values(1,1,0,12,400);
INSERT into costAndLimit values(1,2,0,12,700);
INSERT into costAndLimit values(1,3,9,12,900);

--gradingSystem
INSERT into gradingsystem values('A+',4.33);
INSERT into gradingsystem values('A',4);
INSERT into gradingsystem values('A-',3.67);
INSERT into gradingsystem values('B+',3.33);
INSERT into gradingsystem values('B',3);
INSERT into gradingsystem values('B-',2.67);
INSERT into gradingsystem values('C+',2.33);
INSERT into gradingsystem values('C',2);
INSERT into gradingsystem values('C-',1.67);