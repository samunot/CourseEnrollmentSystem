
--Complete
CREATE OR REPLACE TRIGGER add_min_max_trigger
BEFORE INSERT OR UPDATE OF RESIDENCYLEVEL,GRADLEVEL ON student
FOR EACH ROW
DECLARE
      s_minCredit  student.minCredit%type;
      s_maxCredit  student.maxCredit%type;
BEGIN
    SELECT minCredit,maxCredit INTO s_minCredit,s_maxCredit
    FROM costandlimit
    WHERE residency = :new.residencyLevel AND educationLevel=:new.gradLevel;
    :new.minCredit := s_minCredit;
    :new.maxCredit := s_maxCredit;
END;
/


---complete
CREATE OR REPLACE TRIGGER default_password_trigger
BEFORE INSERT ON student
FOR EACH ROW
BEGIN
    UPDATE USERS
    SET password = :new.username
    WHERE username = :new.username;
END;
/

--complete

CREATE OR REPLACE TRIGGER increment_tuition_bill
BEFORE INSERT  ON enrolled
FOR EACH ROW
DECLARE
      s_numCredit  enrolled.coursecredits%type;
      s_costPerCredit  costandlimit.costPerCredit%type;
BEGIN
    SELECT SUM(courseCredits) INTO s_numCredit
    FROM enrolled
    WHERE student_id = :new.student_id AND sem=:new.sem AND enrolledstatus='Y';

    s_numCredit :=  :new.coursecredits + s_numCredit;

    SELECT C.costPerCredit INTO s_costPerCredit
    FROM costandlimit C,student S 
    WHERE C.residency=S.residencyLevel AND C.educationLevel=S.gradLevel AND S.student_id=:new.student_id;

    UPDATE STUDENT
    SET tuitionOwed = s_numCredit * s_costPerCredit
    WHERE student_id = :new.student_id; 

END;
/




--Drop or Waitlist Accepted

CREATE OR REPLACE TRIGGER readjust_tuition_bill
BEFORE UPDATE  ON enrolled
FOR EACH ROW
DECLARE
      s_numCredit  enrolled.coursecredits%type;
      s_costPerCredit  costandlimit.costPerCredit%type;
BEGIN

    SELECT C.costPerCredit INTO s_costPerCredit
    FROM costandlimit C,student S 
    WHERE C.residency=S.residencyLevel AND C.educationLevel=S.gradLevel AND S.student_id=:new.student_id;
          
         s_numCredit := :new.coursecredits;
          if  (:new.enrolledStatus  = 'N') THEN 
          UPDATE STUDENT
          SET tuitionOwed = tuitionOwed - s_numCredit * s_costPerCredit
          WHERE student_id = :new.student_id; 
        end if;

          if  (:new.enrolledStatus  = 'Y') THEN 
          UPDATE STUDENT
          SET tuitionOwed = tuitionOwed + s_numCredit * s_costPerCredit
          WHERE student_id = :new.student_id;  
        end if;
END;
/





--Complete but one grade slow
------GPA Calculation
CREATE OR REPLACE TRIGGER gpa_calculator
BEFORE INSERT ON ENROLLED
FOR EACH ROW
DECLARE

      s_gpa  student.gpa%type;
BEGIN
SELECT (sum(g.pointsperhour*e.coursecredits)/sum(e.coursecredits)) INTO s_gpa
FROM enrolled e, gradingsystem g
WHERE e.student_id=:new.student_id and e.grade = g.grade;

UPDATE STUDENT
SET gpa = s_gpa
WHERE student_id = :new.student_id;
   
END;
/

/*
SELECT (sum(g.pointsperhour*e.coursecredits)/sum(e.coursecredits)) AS gpa
FROM enrolled e, gradingsystem g
WHERE e.student_id=1111 and e.grade=g.grade;
*/

---When someone enrolls increment studentsEnrolled by 1
CREATE OR REPLACE TRIGGER increment_studentsEnrolled
BEFORE INSERT OR UPDATE OF ENROLLEDSTATUS ON ENROLLED
FOR EACH ROW
DECLARE
BEGIN
if (:new.enrolledstatus='Y')
    THEN UPDATE OFFERING
    SET studentsEnrolled=studentsEnrolled+1
    WHERE course_id=:new.course_id and sem=:new.sem ;
end if;
   END;
/


---When someone drops decrement studentsEnrolled by 1
CREATE OR REPLACE TRIGGER decrement_studentsEnrolled
BEFORE INSERT OR UPDATE OF ENROLLEDSTATUS ON ENROLLED
FOR EACH ROW
DECLARE
BEGIN
    if (:old.enrolledstatus='Y' and :new.enrolledstatus='N' )
    THEN UPDATE OFFERING
    SET studentsEnrolled=studentsEnrolled-1
    WHERE course_id=:new.course_id and sem=:new.sem ;
    end if;
   END;
/

--When someone gets into waitlist, increment studentswaitlisted by 1
CREATE OR REPLACE TRIGGER increment_studentsWaitlisted
BEFORE INSERT ON ENROLLED
FOR EACH ROW
DECLARE
s_num  offering.studentsWaitlisted%type;

BEGIN
SELECT COUNT(*) INTO s_num
FROM enrolled
WHERE course_id=:new.course_id and sem=:new.sem and :new.enrolledstatus = 'N' and :new.waitlistNumber>0;
    
    UPDATE OFFERING
    SET studentswaitlisted=s_num;
    WHERE course_id=:new.course_id and sem=:new.sem;
   END;
/



--When someone drops the course


----Approving specialpermssion to enroll
/*CREATE OR REPLACE TRIGGER special_permission_enrollment
BEFORE UPDATE OF APPROVESTATUS ON SPECIALPERMISSION
FOR EACH ROW
DECLARE
     c_credits =course.credits%type;
BEGIN
SELECT CREDITS INTO c_credits
 FROM COURSE
 WHERE course_id= :new.course_id;

 if  (:new.enrolledStatus  = 'Y') THEN 
          UPDATE STUDENT
          SET tuitionOwed = tuitionOwed + s_numCredit * s_costPerCredit
          WHERE student_id = :new.student_id;  
        end if;
   
    UPDATE STUDENT
    SET gpa = s_gpa
    WHERE student_id = :new.student_id; 

END;
/
insert into specialPermission values(2222,'CSC541','rjain','S17','01-11-2017',NULL,NULL);

select credits from course where course_id='CSC505';



----Adding someone on Waitlist
CREATE OR REPLACE TRIGGER readjust_waiting_list
BEFORE UPDATE  ON enrolled
FOR EACH ROW
DECLARE
BEGIN

    SELECT C.costPerCredit INTO s_costPerCredit
    FROM costandlimit C,student S 
    WHERE C.residency=S.residencyLevel AND C.educationLevel=S.gradLevel AND S.student_id=:new.student_id;
          
         s_numCredit := :new.coursecredits;
          if  (:new.enrolledStatus  = 'N') THEN

          UPDATE STUDENT
          SET tuitionOwed = tuitionOwed - s_numCredit * s_costPerCredit
          WHERE student_id = :new.student_id; 
        end if;

          if  (:new.enrolledStatus  = 'Y') THEN 
          UPDATE STUDENT
          SET tuitionOwed = tuitionOwed + s_numCredit * s_costPerCredit
          WHERE student_id = :new.student_id;  
        end if;
END;
/





--Queries

 SELECT SUM(courseCredits)
    FROM enrolled
    WHERE student_id = 1111 AND sem='F16' AND enrolledstatus='Y';

 SELECT username
    FROM student
    where student_id= 1111;   

SELECT costPerCredit 
    FROM costandlimit C,student S
    WHERE C.residency=S.residencyLevel AND C.educationLevel=s.gradLevel AND S.student_id=1111;



SELECT trigger_name, status FROM user_triggers;
