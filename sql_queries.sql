--Insert Queries
ALTER SESSION SET nls_date_format='mm-dd-yyyy';
--users
insert into users values('Shubham','Munot','samunot','11111','student','03-08-1994',654321,'Octavia street','Raleigh',27606,'NC');
insert into users values('Ankur','Saxena','asaxena','22222','student','01-31-1993',123456,'Avent Ferry Road','Raleigh',27606,'NC');
insert into users values('Rishi','Jain','rjain','33333','admin','01-01-1992',987654,'Avent Ferry Road','Raleigh',27606,'NC');
insert into users values('Sarat','Kavuru','skavuru','44444','faculty','12-31-1991',123654,'Avent Ferry Road','Raleigh',27606,'NC');
insert into users values('Albert','Einstein','aeinstien','55555','faculty','3-15-1962',123321,'Gormat street','Raleigh',27606,'NC');
insert into users values('Toshal','Phene','tphene','66666','student','09-15-1993',154666,'Octavia street','Raleigh',27606,'NC');
insert into users values('Jaydeep','Rane','jrane','77777','student','01-29-1993',156464,'Octavia street','Raleigh',27606,'NC');

insert into users values('Harry','Potter','hpotter','101','student','01-12-1990',164664,'Ligon street','Raleigh',27606,'NC');
insert into users values('Ron','Weasley','rweasly','102','student','04-16-1989',145654,'Lils street','Raleigh',27606,'NC');
insert into users values('Hermonie','Granger','hgranger','103','student','12-19-1991',116664,'Octavia street','Raleigh',27606,'NC');
insert into users values('Draco','Malfoy','dmalfoy','104','student','03-21-1992',667464,'Hillsborough street','Raleigh',27606,'NC');
insert into users values('Albus','Dumbledore','alby','hogwarts','admin','05-26-1984',951357,'Gorman Street','Raleigh',27606,'NC');

--student
insert into student values(1111,'samunot',3.78,'fall 16',3,15670.0,0.0,9,9,2,'N','shubham99@gmail.com');
insert into student values(2222,'asaxena',3.33,'spring 16',1,5670.0,2300.0,0,9,1,'N','ankur.saxena@gmail.com');
insert into student values(1234,'tphene',3.67,'fall 16',2,5640.0,8000.0,3,9,2,'N','toshalrules@gmail.com');
insert into student values(4321,'jrane',3,'spring 16',1,5670.0,2300.0,6,9,1,'N','jaydeep.rane@gmail.com');

insert into student (student_id,username,termEnrolled,residencyLevel,tuitionOwed,gradLevel,email)
	values(101,'hpotter','fall 16',1,1200.0,1,'harry.potter@gmail.com');
insert into student (student_id,username,termEnrolled,residencyLevel,tuitionOwed,gradLevel,email)
	values(102,'rweasly','fall 16',1,0.0,2,'ron.weasley@gmail.com');
insert into student (student_id,username,termEnrolled,residencyLevel,tuitionOwed,gradLevel,email)
	values(103,'hgranger','fall 16',2,0.0,2,'hermonie.granger@gmail.com');
insert into student (student_id,username,termEnrolled,residencyLevel,tuitionOwed,gradLevel,email)
	values(104,'dmalfoy','fall 16',3,0.0,2,'draco.malfoy@gmail.com');


--admin
insert into admin values('rjain',123456789);
insert into admin values('alby',987654321,1111);


--faculty
insert into faculty values(4444,'skavuru');
insert into faculty values(5555,'aeinstien');

--sem
insert into semester values('F16',2016,'fall','08-24-2017','10-14-2016');
insert into semester values('S17',2017,'spring','01-22-2017','3-12-2017');

--course
insert into course values('Algorithms','CSC505',2,'3','N');
insert into course values('Data Structures','CSC441',1,'2','N');
insert into course values('Advance Data Structures','CSC541',2,'3','Y');
insert into course values('Independent Study','CSC525',2,'1-3','Y');

insert into course values('Introduction to Computer Science','CSC401',1,'3','N');
insert into course values('Database','CSC510',2,'3','N');
insert into course values('Software Engineering','CSC515',2,'3','N');
insert into course values('Internet Protocols','CSC520',2,'3','N');
insert into course values('VLSI','CE420',1,'3','N');
insert into course values('Independent Study','CSC525',2,'1-3','Y'); --delete this first
insert into course values('Numerical Methods','CSC402',1,'3','Y');
insert into course values('Cloud Computing','CSC521',2,'3','Y');
insert into course values('Dev-Ops','CSC530',2,'3','Y');
insert into course values('VLSI II','CE421',1,'3','Y');

--department
insert into department values('CSC','Computer Science');
insert into department values('ECE','Electrical and Computer Engineering');
insert into department values('ST','Statistics');

--offering
insert into offering values('CSC505','CSC',4444,'F16',5,70,'EB2',62,0,'M W 1:00PM-2:00PM');
insert into offering values('CSC541','CSC',5555,'S17',10,110,'EB3',110,8,'Tu Th 3:00PM-4:00PM');

--NEED TO BE ADDED AND REMOVE CSC 505
insert into offering values('CSC401','CSC',4444,'F16',2,2,'EB2',2,0,'M W 12:00PM-1:00PM');
insert into offering values('CSC510','CSC',5555,'F16',2,5,'EB1',3,0,'Tu Th 1:00PM-2:00PM');
insert into offering values('CSC515','CSC',4444,'F16',2,3,'EB3',3,0,'Tu Th 2:00PM-3:00PM');
insert into offering values('CSC520','CSC',5555,'F16',2,2,'EB2',1,0,'M W 11:00AM-12:00PM');
insert into offering values('CSC525','CSC',4444,'F16',0,2,'EB3',1,0,'M W 3:00PM-4:00PM');
insert into offering values('CE420','CSC',5555,'F16',2,4,'EB1',4,1,'F 3:00PM-5:00PM');

insert into offering values('CSC402','CSC',5555,'S17',2,2,'EB2',2,0,'M W 11:00AM-12:00PM');
insert into offering values('CSC510','CSC',4444,'S17',2,5,'EB1',5,1,'Tu Th 1:00PM-2:00PM');
insert into offering values('CSC505','CSC',5555,'S17',2,2,'EB2',2,2,'M W 11:00AM-12:00PM');
insert into offering values('CSC521','CSC',4444,'S17',2,3,'EB3',3,2,'Tu Th 1:00PM-2:00PM');
insert into offering values('CSC525','CSC',5555,'S17',0,2,'EB1',2,0,'M W 2:00PM-3:00PM');
insert into offering values('CSC530','CSC',4444,'S17',2,2,'EB3',1,0,'M TW 11:00AM-12:00PM');
insert into offering values('CE421','ECE',5555,'S17',4,2,'EB3',1,0,'Tu Th 4:00PM-5:00PM');

--departmentCourse
insert into departmentCourse values('CSC','CSC505');
insert into departmentCourse values('CSC','CSC541');
insert into departmentCourse values('CSC','CSC441');
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
insert into studentDepartment values('CSC',1111);
insert into studentDepartment values('ECE',2222);

insert into studentDepartment values('ECE',101);
insert into studentDepartment values('CSC',102);
insert into studentDepartment values('CSC',103);
insert into studentDepartment values('CSC',104);

--prereq
insert into prereq values('CSC441','CSC541','B',3);

insert into prereq (coursePrereq_id,course_id)values('CSC401','CSC402');
insert into prereq values('CSC521','CSC520','B+',3.5);
insert into prereq (coursePrereq_id,course_id)values('CSC515','CSC530');
insert into prereq (coursePrereq_id,course_id)values('CE420','CE421');

--specialPermission
insert into specialPermission values(2222,'CSC541','rjain','S17','01-11-2017','01-18-2017');

--enrolled
insert into enrolled values(1111,'CSC505','F16','A',4,3,0,'Y');
insert into enrolled (student_id,course_id,sem,grade,coursecredits,waitlistNumber,enrolledstatus)values(1111,'CSC541','S17',3,0,'Y');

insert into enrolled (student_id,course_id,sem,grade,coursecredits,enrolledstatus)values(101,'CE420','F16','A',3,'Y');
insert into enrolled (student_id,course_id,sem,grade,coursecredits,enrolledstatus)values(102,'CSC510','F16','B+',3,'Y');
insert into enrolled (student_id,course_id,sem,grade,coursecredits,enrolledstatus)values(102,'CSC515','F16','B+',3,'Y');
insert into enrolled (student_id,course_id,sem,grade,coursecredits,enrolledstatus)values(103,'CSC515','F16','A',3,'Y');
insert into enrolled (student_id,course_id,sem,grade,coursecredits,enrolledstatus)values(103,'CSC520','F16','A-',3,'Y');
insert into enrolled (student_id,course_id,sem,grade,coursecredits,enrolledstatus)values(103,'CSC530','F16','A+',3,'Y');
insert into enrolled (student_id,course_id,sem,grade,coursecredits,enrolledstatus)values(104,'CSC510','F16','A',3,'Y');
insert into enrolled (student_id,course_id,sem,grade,coursecredits,enrolledstatus)values(104,'CSC515','F16','B+',3,'Y');
insert into enrolled (student_id,course_id,sem,grade,coursecredits,enrolledstatus)values(104,'CSC525','F16','A+',3,'Y');




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


--misc
insert into users values('abc','xyz','abcxyz','55555','student','04-09-1992',null,null,null,null,null);
insert into student values(5555,'abcxyz',null,null,1,null,2300.0,null,null,1,null,null);

select u.firstname,u.lastname,u.dob,s.residencylevel,s.gradlevel,s.tuitionowed,s.gpa,u.phone,s.email,u.street,u.city,u.state,u.zip 
from users u , student s where s.student_id = 1111 and s.username = u.username;

update course
set mingpa=3.5 
where course_id = 'CSC521';