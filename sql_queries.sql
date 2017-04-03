--Insert Queries
ALTER SESSION SET nls_date_format='mm-dd-yyyy';
--users
insert into users values('Shubham','Munot','samunot','11111','student','03-08-1994',654321,'Octavia street','Raleigh',27606,'NC');
insert into users values('Ankur','Saxena','asaxena','22222','student','01-31-1993',123456,'Avent Ferry Road','Raleigh',27606,'NC');
insert into users values('Rishi','Jain','rjain','33333','admin','01-01-1992',987654,'Avent Ferry Road','Raleigh',27606,'NC');
insert into users values('Sarat','Kavuru','skavuru','44444','faculty','12-31-1991',123654,'Avent Ferry Road','Raleigh',27606,'NC');
insert into users values('Albert','Einstein','aeinstien','55555','faculty','3-15-1962',123321,'Gormat street','Raleigh',27606,'NC');

--student
insert into student values(1111,'samunot',3.78,'fall 16',3,15670.0,0.0,9,9,2,'N','shubham99@gmail.com');
insert into student values(2222,'asaxena',3.33,'spring 16',1,5670.0,2300.0,0,9,1,'N','ankur.saxena@gmail.com');


--admin
insert into admin values('rjain',123456789);

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
insert into course values('Independent Study','CSC525',2,'1-3','N');

--department
insert into department values('CSC','Computer Science');
insert into department values('ECE','Electrical and Computer Engineering');
insert into department values('ST','Statistics');

--offering
insert into offering values('CSC505','CSC',4444,'F16',5,70,'EB2',62,0,'M W 1:00PM-2:00PM');
insert into offering values('CSC541','CSC',5555,'S17',10,110,'EB3',110,8,'Tu Th 3:00PM-4:00PM');

--departmentCourse
insert into departmentCourse values('CSC','CSC505');
insert into departmentCourse values('CSC','CSC541');
insert into departmentCourse values('CSC','CSC441');

--studentDepartment
insert into studentDepartment values('CSC',1111);
insert into studentDepartment values('ECE',2222);

--prereq
insert into prereq values('CSC441','CSC541','B',3);

--specialPermission
insert into specialPermission values(2222,'CSC541','rjain','S17','01-11-2017','01-18-2017');

--enrolled
insert into enrolled values(1111,'CSC505','F16','A',4,3,0,'Y');
insert into enrolled (student_id,course_id,sem,coursecredits,waitlistNumber,enrolledstatus)values(1111,'CSC541','S17',3,0,'Y');

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

