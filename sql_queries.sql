--Insert Queries

--users
insert into users values('Shubham','Munot','samunot','11111','student','03-08-1994',654321,'Octavia street','Raleigh',27606,'NC');
insert into users values('Ankur','Saxena','asaxena','22222','student','01-31-1993',123456,'Avent Ferry Road','Raleigh',27606,'NC');
insert into users values('Rishi','Jain','rjain','33333','admin','01-01-1992',987654,'Avent Ferry Road','Raleigh',27606,'NC');
insert into users values('Sarat','Kavuru','skavuru','44444','faculty','12-31-1991',123654,'Avent Ferry Road','Raleigh',27606,'NC');

--student
insert into student values(1111,'samunot',3.78,'fall 16',3,15670.0,0.0,9,9,2,'N','shubham99@gmail.com');
insert into student values(2222,'asaxena',3.33,'spring 16',1,5670.0,2300.0,0,9,1,'N','ankur.saxena@gmail.com');


--admin
insert into admin values('rjain',123456789);

--faculty
insert into faculty values(4444,'skavuru');

insert into users values('abc','xyz','abcxyz','55555','student','04-09-1992',null,null,null,null,null);
insert into student values(5555,'abcxyz',null,null,1,null,2300.0,null,null,1,null,null);

select u.firstname,u.lastname,u.dob,s.residencylevel,s.gradlevel,s.tuitionowed,s.gpa,u.phone,s.email,u.street,u.city,u.state,u.zip from users u , student s where s.student_id = 1111 and s.username = u.username;