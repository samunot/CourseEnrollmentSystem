import java.sql.Connection;
import java.util.Calendar;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class DatabaseHandler {

	Connection con;
	ConnectionToOracle conOracle;
	PreparedStatement state;
	Scanner sc;

	// Initializing connection
	DatabaseHandler() {
		conOracle = new ConnectionToOracle();
		con = conOracle.returnConnection();
		state = null;
		sc = new Scanner(System.in);
	}

	public int loginValidate(String username, String password) {
		try {
			state = con.prepareStatement("Select password, role from users where username = ?");
			state.setString(1, username);
			ResultSet rs = state.executeQuery();
			// invalid username
			if (rs == null)
				return -1;
			rs.next();
			// invalid password
			if (!rs.getString(1).equals(password))
				return -1;
			// student
			if (rs.getString(2).equalsIgnoreCase("student"))
				return 1;
			// admin
			return 2;
		} catch (Exception e) {
			// TODO: handle exception
			return -1;
		}
	}

	public void viewAdminProfile(String username) {
		try {
			state = con.prepareStatement("Select firstname, lastname, dob from users where username = ?");
			state.setString(1, username);
			ResultSet rs = state.executeQuery();
			rs.next();
			System.out.println("Press 0 to go back\nFirst Name: " + rs.getString(1) + "\nLast Name: " + rs.getString(2)
					+ "\nDate Of Birth: " + rs.getDate(3) + "\nEmployee ID: " + username);
			int key = sc.nextInt();
			while (key != 0) {
				System.out.println("Invalid Option");
				key = sc.nextInt();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void enrollStudent() {
		try {
			System.out.print("1.Enter Student Username: ");
			String username = sc.next();
			System.out.print("2.Enter Student ID: ");
			int studentid = sc.nextInt();
			System.out.print("3.Enter Student's First Name: ");
			String fname = sc.next();
			System.out.print("4.Enter Student's Last Name: ");
			String lname = sc.next();
			System.out.print("5 Enter Student’s D.O.B(MM-DD-YYYY): ");
			String dob = sc.next();

			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dob.split("-")[1]));
			cal.set(Calendar.MONTH, Integer.parseInt(dob.split("-")[0]) - 1);
			cal.set(Calendar.YEAR, Integer.parseInt(dob.split("-")[2]));

			System.out.print("6.Enter Student’s Level: ");
			int level = sc.nextInt();
			System.out.print("7.Enter Student’s Residency Status: ");
			int resi = sc.nextInt();
			System.out.print("8.Enter Amount Owed(if any): ");
			float amt = sc.nextFloat();

			state = con.prepareStatement(
					"insert into users(firstname,lastname,username,password,role,dob) values(?,?,?,?,'student',?)");
			state.setString(1, fname);
			state.setString(2, lname);
			state.setString(3, username);
			state.setString(4, studentid + "");
			state.setDate(5, new Date(cal.getTimeInMillis()));
			state.execute();

			state = con.prepareStatement(
					"insert into student(student_id, username, residencylevel, tuitionowed, gradlevel) values(?,?,?,?,?)");
			state.setInt(1, studentid);
			state.setString(2, username);
			state.setInt(3, resi);
			state.setFloat(4, amt);
			state.setInt(5, level);
			state.execute();
			con.commit();
			System.out.println("Student enrolled successfully! :)");
		} catch (Exception e) {
			// TODO: handle exception
			try {
				con.rollback();
			} catch (Exception ex) {
				// TODO: handle exception
				ex.printStackTrace();
			}
			System.out.println("Invalid values!");
		}
	}

	public void viewStudent() {
		try {
			System.out.print("Please Enter Student ID: ");
			int studentid = sc.nextInt();
			state = con.prepareStatement(
					"select u.firstname,u.lastname,u.dob,s.gradlevel,s.residencylevel,s.tuitionowed,s.gpa,u.phone,s.email,u.street,u.city,u.zip,u.state from users u , student s where s.student_id = ? and s.username = u.username");
			state.setInt(1, studentid);
			ResultSet rs = state.executeQuery();
			if (rs.next()) {
				DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
				String address = rs.getString(10) + " " + rs.getString(11) + " " + rs.getInt(12) + " "
						+ rs.getString(13);
				System.out.println("1.First Name: " + rs.getString(1) + "\n2.Last Name: " + rs.getString(2)
						+ "\n3.Date of birth(MM-DD-YYYY): " + df.format(rs.getDate(3)) + "\n4.Student’s Level: "
						+ rs.getInt(4) + "\n5.Student’s Residency Status: " + rs.getInt(5) + "\n6.Amount Owed(if any): "
						+ rs.getFloat(6) + "\n7.GPA: " + rs.getFloat(7) + "\n8.Phone: " + rs.getInt(8) + "\n9.Email: "
						+ rs.getString(9) + "\n10.Address: " + address);
				System.out.println("Press 0 To Go Back To Previous Menu\nPress 1 To View or Enter Grades");
				int key = sc.nextInt();
				while (!(key == 0 || key == 1)) {
					System.out.println("Invalid Option");
					key = sc.nextInt();
				}
				if (key == 1) {
					viewEnterGrade(studentid);
				}

			} else {
				System.out.println("Invalid Student ID! Please try again");
				viewStudent();
				return;
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void viewEnterGrade(int studentid) {
		System.out.println("1.View Grades\n2.Enter Grades\nPress 0 to go back");
		int key = sc.nextInt();
		switch (key) {
		case 0:
			return;
		case 1:
			viewGrade(studentid);
			break;
		case 2:
			enterGrade(studentid);
			break;
		}
	}

	public void viewGrade(int studentid) {
		try {
			state = con.prepareStatement(
					"select course_id,grade from enrolled where grade is not null and student_id = ?");
			state.setInt(1, studentid);
			ResultSet rs = state.executeQuery();
			int count = 0;
			while (rs.next()) {
				System.out.println("Course ID: " + rs.getString(1) + "Grade: " + rs.getString(2));
				count++;
			}
			if (count == 0) {
				System.out.println("No courses graded yet!");
			} else {
				System.out.println("Press 0 to go back");
				int key = sc.nextInt();
				while (key != 0) {
					System.out.println("Invalid Option");
					key = sc.nextInt();
				}
			}
			viewEnterGrade(studentid);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void enterGrade(int studentid) {
		try {
			state = con.prepareStatement("select course_id from enrolled where grade is null and student_id = ?");
			state.setInt(1, studentid);
			ResultSet rs = state.executeQuery();

			String[] courseid = new String[rs.getFetchSize()];
			int i = 0;
			while (rs.next()) {
				courseid[i] = rs.getString(1);
				System.out.println((i + 1) + ".Course ID: " + courseid[i]);
				i++;
			}
			if (i == 0) {
				System.out.println("No courses to be graded!");
			} else {
				System.out.print("Enter choice: ");
				int choice = sc.nextInt() - 1;
				System.out.print("Enter grade: ");
				String grade = sc.next();
				state = con.prepareStatement("update enrolled set grade = ? where student_id = ? and course_id = ?");
				state.setString(1, grade);
				state.setInt(2, studentid);
				state.setString(3, courseid[choice]);
				if (state.executeUpdate() >= 1) {
					System.out.println("Grade successfully entered!");
					con.commit();
				} else {
					System.out.println("Failed!");
				}
			}
			viewEnterGrade(studentid);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void viewAddCourse() {
		System.out.println(
				"Select Appropriate Menu Option:\n0.Go Back To Previous Menu\n1.View Course\n2.Add New Course");
		int key = sc.nextInt();
		switch (key) {
		case 0:
			return;
		case 1:
			viewCourse();
			viewAddCourse();
			break;
		case 2:
			addCourse();
			viewAddCourse();
			break;
		default:
			System.out.println("Invalid choice. Please try again!");
			viewAddCourse();
			break;
		}
	}

	public void viewCourse() {
		try {
			System.out.println("Please Enter Course Id:");
			String courseid = sc.next();
			state = con.prepareStatement(
					"select c.title, d.name, c.gradlevel, c.mingpa, c.permission, c.credits from course c, department d where c.course_id = ? and d.department_id in (select department_id from departmentcourse where course_id = ?)");
			state.setString(1, courseid);
			state.setString(2, courseid);
			ResultSet rs = state.executeQuery();
			if (rs.next())
				System.out.println("1.Course Name: " + rs.getString(1) + "\n2.Department Name: " + rs.getString(2)
						+ "\n3.Level: " + rs.getInt(3) + "\n4.GPA Requirement(if any): " + rs.getFloat(4)
						+ "\n5.Special Approval Required: " + rs.getString(5) + "\n6.Number Of Credits: "
						+ rs.getString(6));
			state = con.prepareStatement(
					"select title from course where course_id in (select courseprereq_id from prereq where course_id = ?)");
			state.setString(1, courseid);
			rs = state.executeQuery();
			System.out.println("List of prerequisite courses: ");
			while (rs.next()) {
				System.out.println(rs.getString(1));
			}
			System.out.println("Press 0 to go back");
			int key = sc.nextInt();
			if (key == 0) {
				return;
			} else {
				while (key != 0) {
					System.out.println("Invalid Option");
					key = sc.nextInt();
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("Invalid values! Press 0 to go back or 1 to retry");
			int choice = sc.nextInt();
			while (!(choice == 0 || choice == 1)) {
				System.out.println("Please enter valid option");
				choice = sc.nextInt();
			}
			if (choice == 1)
				viewCourse();
			else
				return;
		}
	}

	public void addCourse() {
		try {
			sc = new Scanner(System.in);
			System.out.print("Enter Course ID: ");
			String courseid = sc.nextLine();
			System.out.println("Enter Course Name: ");
			String title = sc.nextLine();
			System.out.print("Enter Department ID: ");
			String depid = sc.nextLine();
			System.out.print("Enter Course Level: ");
			int gradlevel = sc.nextInt();
			System.out.print("Enter GPA Requirement(enter 0 if no gpa requirement): ");
			float mingpa = sc.nextFloat();

			System.out.print("Enter the number of prerequiste courses: ");
			int pr = sc.nextInt();
			String[] prereq = new String[pr];
			if (pr > 0) {
				System.out.print("Enter prerequisite course IDs: ");
				for (int i = 0; i < prereq.length; i++) {
					prereq[i] = sc.next();
				}
			}

			System.out.print("Special Approval Required(Y/N): ");
			String perm = sc.next();
			if (perm.equalsIgnoreCase("y"))
				perm = "Y";
			else
				perm = "N";
			System.out.print("Are there variable number of credits?(y/n): ");
			String yes = sc.next();
			String credits;
			if (yes.equalsIgnoreCase("y")) {
				System.out.print("Enter minimum number of credits: ");
				int min = sc.nextInt();
				System.out.print("Enter maximum number of credits: ");
				int max = sc.nextInt();
				credits = min + "-" + max;
			} else {
				System.out.print("Enter Number Of Credits: ");
				credits = sc.nextInt() + "";
			}
			state = con.prepareStatement(
					"Insert into course (title, course_id, gradlevel, credits, permission, mingpa) values(?,?,?,?,?,?)");
			state.setString(1, title);
			state.setString(2, courseid);
			state.setInt(3, gradlevel);
			state.setString(4, credits);
			state.setString(5, perm);
			state.setFloat(6, mingpa);
			state.executeUpdate();
			state = con.prepareStatement("Insert into departmentcourse (DEPARTMENT_ID, Course_ID) values(?,?)");
			state.setString(1, depid);
			state.setString(2, courseid);
			state.executeUpdate();
			state = con.prepareStatement("Insert into prereq (courseprereq_id, course_id) values(?,?)");
			state.setString(2, courseid);
			for (int i = 0; i < prereq.length; i++) {
				state.setString(1, prereq[i]);
				state.executeUpdate();
			}
			con.commit();
			System.out.println("Course Successfully added!");
		} catch (Exception e) {
			// TODO: handle exception
			try {
				con.rollback();
			} catch (Exception ex) {
				// TODO: handle exception
				ex.printStackTrace();
			}
			System.out.println("Invalid Value/s. Please try again!");
			addCourse();
		}
	}

	public void viewAddOffering() {
		System.out.println(
				"Select Appropriate Menu Option:\n0.Go Back To Previous Menu\n1.View Course Offerings\n2.Add New Course Offering");
		int key = sc.nextInt();
		switch (key) {
		case 0:
			return;
		case 1:
			viewOffering();
			viewAddOffering();
			break;
		case 2:
			addOffering();
			viewAddOffering();
			break;
		default:
			System.out.println("Invalid choice. Please try again!");
			viewAddOffering();
			break;
		}
	}

	public void viewOffering() {
		try {
			System.out.print("Enter Course ID: ");
			String courseid = sc.next();
			System.out.println("Enter Semester: ");
			String sem = sc.next();
			state = con.prepareStatement(
					"Select schedule, maxsize, maxwaitist from offering where course_id = ? and sem = ?");
			state.setString(1, courseid);
			state.setString(2, sem);
			ResultSet rs = state.executeQuery();
			if (rs.next()) {
				System.out.println("Schedule: " + rs.getString(1));
				System.out.println("Class size: " + rs.getInt(2));
				System.out.println("Waitlist size:" + rs.getInt(3));
				state = con.prepareStatement(
						"Select firstname, lastname from users where username in (Select username from faculty where faculty_id in (Select faculty_id from facultyoffering where course_id = ? and sem = ?))");
				state.setString(1, courseid);
				state.setString(2, sem);
				rs = state.executeQuery();
				System.out.println("Faculty Name/s: ");
				while (rs != null && rs.next()) {
					System.out.println(rs.getString(1) + " " + rs.getString(2));
				}
			} else {
				System.out.println("Invalid values! Press 0 to go back or 1 to retry");
				int choice = sc.nextInt();
				while (!(choice == 0 || choice == 1)) {
					System.out.println("Please enter valid option");
					choice = sc.nextInt();
				}
				if (choice == 1)
					viewOffering();
				else
					return;
			}
			System.out.println("Press 0 to go back");
			int key = sc.nextInt();
			if (key == 0)
				return;
			else {
				while (key != 0) {
					System.out.println("Invalid Option");
					key = sc.nextInt();
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Invalid values! Press 0 to go back or 1 to retry");
			int choice = sc.nextInt();
			while (!(choice == 0 || choice == 1)) {
				System.out.println("Please enter valid option");
				choice = sc.nextInt();
			}
			if (choice == 1)
				viewOffering();
			else
				return;
		}
	}

	public void addOffering() {
		try {
			System.out.print("Enter course ID: ");
			String courseid = sc.next();
			System.out.print("Enter Semester(eg. S17 for Spring 2017): ");
			String sem = sc.next();
			System.out.print("Enter number of faculty members: ");
			int n = sc.nextInt();
			int[] faculty = new int[n];
			for (int i = 0; i < faculty.length; i++) {
				System.out.print("Enter FacultyID:");
				faculty[i] = sc.nextInt();
			}
			sc.nextLine();
			System.out.println("Enter schedule(eg M W 11:00PM-12:00PM): ");
			String schedule = sc.nextLine();
			System.out.print("Enter class size: ");
			int csize = sc.nextInt();
			System.out.print("Enter wait list size: ");
			int wsize = sc.nextInt();
			state = con.prepareStatement(
					"Insert into offering(course_id, sem, schedule, maxsize, maxwaitist) values (?,?,?,?,?)");
			state.setString(1, courseid);
			state.setString(2, sem);
			state.setString(3, schedule);
			state.setInt(4, csize);
			state.setInt(5, wsize);
			state.executeQuery();
			state = con.prepareStatement("Insert into facultyoffering (course_id, sem, faculty_id) values(?,?,?)");
			state.setString(1, courseid);
			state.setString(2, sem);
			for (int i = 0; i < faculty.length; i++) {
				state.setInt(3, faculty[i]);
				state.executeQuery();
			}
			con.commit();
			System.out.println("Offering successfully added!");
		} catch (Exception e) {
			// TODO: handle exception
			try {
				con.rollback();
			} catch (Exception ex) {
				// TODO: handle exception
				ex.printStackTrace();
			}
			System.out.println("Invalid values. Please try again!");
			addOffering();
		}
	}

	public void viewApprovePermisiion() {
		try {
			System.out.println("List of pending requests:");
			state = con.prepareStatement(
					"select student_id, course_id, sem, reqdate from specialpermission where approvedate is NULL");
			DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
			ResultSet rs = state.executeQuery();
			String[] specialperm = new String[rs.getFetchSize()];
			int i = 0;
			System.out.println("  student_id\tcourse_id\tsem\treqdate");
			while (rs.next()) {
				System.out.println((i + 1) + ". " + rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3)
						+ "\t" + df.format(rs.getDate(4)));
				specialperm[i] = rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3);
				i++;
			}
			if (i == 0) {
				System.out.println("No requests");
			} else {
				Date date = new Date(System.currentTimeMillis());
				System.out.println("Enter choice:");
				int choice = sc.nextInt();
				System.out.println("1.Approve Request\n2.Reject request\nEnter choice: ");
				int status = sc.nextInt();
				state = con.prepareStatement(
						"update specialpermission set approvedate = ?, approvestatus = ? where student_id= ? and course_id = ? and sem = ?");
				state.setDate(1, date);
				if (status == 1)
					state.setString(2, "Y");
				else
					state.setString(2, "N");
				state.setInt(3, Integer.parseInt(specialperm[choice - 1].split("\t")[0]));
				state.setString(4, specialperm[choice - 1].split("\t")[1]);
				state.setString(5, specialperm[choice - 1].split("\t")[2]);
				state.executeQuery();
				con.commit();
				System.out.println("Succesfully responded!");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void enforceDeadline() {
		try {
			System.out.println("Enforce Deadline:\n1.Yes\n2.No");
			int key = sc.nextInt();
			switch (key) {
			case 1:
				state = con.prepareStatement("select addDeadline from semester where sem='S17'");
				ResultSet rs = state.executeQuery();
				rs.next();
				Date d = rs.getDate(1);
				if (d == null) {
					state = con.prepareStatement(
							"update offering set maxwaitist = 0, studentswaitlisted=0 where sem='S17'");
					state.executeUpdate();
					state = con.prepareStatement("update semester set adddeadline = ? where sem='S17'");
					Date dnow = new Date(System.currentTimeMillis());
					DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
					String mydateStr = df.format(dnow);
					Calendar cal = Calendar.getInstance();
					cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(mydateStr.split("-")[1]));
					cal.set(Calendar.MONTH, Integer.parseInt(mydateStr.split("-")[0]) - 1);
					cal.set(Calendar.YEAR, Integer.parseInt(mydateStr.split("-")[2]));
					state.setDate(1, new Date(cal.getTimeInMillis()));
					System.out.println(state.executeUpdate());
					state = con.prepareStatement("delete from enrolled where waitlistNumber > 0");
					state.executeQuery();
					System.out.println("Deadline succesfully enforced.");
				} else
					System.out.println("Deadline already enforced");
				break;
			case 2:
				return;
			default:
				System.out.println("Invalid choice. Please try again!");
				enforceDeadline();
				break;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	// Student Functions

	public void StudentViewProfile(String username) {
		try {
			state = con.prepareStatement(
					"select u.firstname,u.lastname,s.email,u.phone,s.gradlevel,s.residencylevel from student s,users u where s.username= ? and s.username = u.username");
			state.setString(1, username);
			ResultSet rs = state.executeQuery();
			// if (rs != null)
			rs.next();
			System.out.print("\n1. First Name:\t" + rs.getString(1) + "\n2. Last Name:\t" + rs.getString(2)
					+ "\n3. Email:\t" + rs.getString(3) + "\n4. Phone:\t" + rs.getString(4) + "\n5. Level:\t"
					+ (rs.getString(5).equalsIgnoreCase("1") ? "undergraduate" : "graduate") + "\n6. Status:\t"
					+ (rs.getString(6).equalsIgnoreCase("1") ? "in state"
							: rs.getString(6).equalsIgnoreCase("2") ? "out of state" : "international"));
			System.out.print("\nSelect option 1-4 to edit.\nPress 0 to go back.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void StudentEditFirstName(String username, String name) {
		try {
			state = con.prepareStatement("Update users set firstname=? where username=?");
			state.setString(1, name);
			state.setString(2, username);
			state.executeQuery();
			con.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void StudentEditLastName(String username, String name) {
		try {
			state = con.prepareStatement("Update users set lastname=? where username=?");
			state.setString(1, name);
			state.setString(2, username);
			state.executeQuery();
			con.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void StudentEditEmail(String username, String email) {
		try {
			state = con.prepareStatement("Update student set email=? where username=?");
			state.setString(1, email);
			state.setString(2, username);
			state.executeQuery();
			con.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void StudentEditPhone(String username, String phone) {
		try {
			state = con.prepareStatement("Update users set phone=? where username=?");
			state.setString(1, phone);
			state.setString(2, username);
			state.executeQuery();
			con.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void StudentViewGrades(String username) {
		try {
			state = con.prepareStatement(
					"select e.course_id,e.grade from enrolled e,student s where s.username=? and s.student_id=e.student_id and e.grade is not null");
			state.setString(1, username);
			ResultSet rs = state.executeQuery();
			if (rs.isBeforeFirst()) {
				System.out.println("Course \t Grade");
				while (rs.next()) {
					System.out.println(rs.getString(1) + "\t" + rs.getString(2));
				}
			} else
				System.out.println("You have no graded courses.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void StudentViewPendingCourse(String username) {
		try {
			state = con.prepareStatement("select c.course_id,c.title from course c,enrolled e,student s,offering o "
					+ "where s.username=? and e.student_id=s.student_id and c.course_id=e.course_id and e.sem='S17' "
					+ "and e.waitlistnumber>0 and o.maxwaitist>0");
			state.setString(1, username);
			ResultSet rs = state.executeQuery();
			if (rs.isBeforeFirst()) {
				System.out.println("Waitlisted courses:\nCourseID\tCourse Name");
				while (rs.next()) {
					System.out.println(rs.getString(1) + "\t\t" + rs.getString(2));
				}
			} else
				System.out.print("\nNo waitlisted courses.");
			state = con.prepareStatement("select c.course_id,c.title from course c,student s,specialpermission sp "
					+ "where s.username=? and sp.student_id=s.student_id and c.course_id=sp.course_id and sp.sem='S17' "
					+ "and sp.approvedate is null");
			state.setString(1, username);
			ResultSet rs1 = state.executeQuery();
			if (rs1.isBeforeFirst()) {
				System.out.println("Rejected courses:\nCourseID\tCourse Name");
				while (rs1.next()) {
					System.out.println(rs1.getString(1) + "\t\t" + rs1.getString(2));
				}
			} else
				System.out.print("\n No rejected courses.");
			state = con.prepareStatement("select c.course_id,c.title from course c,student s,specialpermission sp "
					+ "where s.username=? and sp.student_id=s.student_id and c.course_id=sp.course_id and sp.sem='S17' "
					+ "and sp.approvestatus ='N'");
			state.setString(1, username);
			ResultSet rs2 = state.executeQuery();
			if (rs2.isBeforeFirst()) {
				System.out.println("Pending courses:\nCourseID\tCourse Name");
				while (rs2.next()) {
					System.out.println(rs2.getString(1) + "\t\t" + rs2.getString(2));
				}
			} else
				System.out.print("\n No Pending courses");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// graded courses will have null values in grade attribute of enrolled
	// table.
	public void StudentViewGPA(String username) {
		try {
			state = con.prepareStatement("select gpa from student where username=?");
			state.setString(1, username);
			ResultSet rs = state.executeQuery();
			if (rs.next()) {
				System.out
						.println("Your GPA is " + (rs.getString(1) != null ? rs.getString(1) : "still not available."));

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void StudentViewBill(String username) {
		try {
			state = con.prepareStatement("select tuitionowed from student where username=?");
			state.setString(1, username);
			ResultSet rs = state.executeQuery();
			if (rs.next()) {
				System.out.println("Your total bill is " + rs.getString(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void StudentPayBill(String username) {
		try {
			sc = new Scanner(System.in);
			System.out.print("Enter the amount to be paid: ");
			float amount = sc.nextFloat();
			state = con.prepareStatement("select tuitionowed from student where username=?");
			state.setString(1, username);
			ResultSet rs = state.executeQuery();
			if (rs.next()) {
				if (rs.getFloat(1) >= amount) {
					state = con.prepareStatement("update student set tuitionowed=tuitionowed-? where username=?");
					state.setFloat(1, amount);
					state.setString(2, username);
					state.executeQuery();
					con.commit();
					System.out.print("\nYou have successfully paid " + amount + " amount.");
				} else
					System.out.print("\nThe payment amount exceeds the owed bill. Please choose a smaller amount.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void StudentViewMyCourses(String username) {
		try {
			sc = new Scanner(System.in);
			state = con.prepareStatement(
					"select c.course_id,c.title from course c,enrolled e,student s where s.username=? and e.student_id=s.student_id and e.enrolledstatus='Y' and c.course_id=e.course_id");
			state.setString(1, username);
			ResultSet rs = state.executeQuery();
			System.out.println("CourseID\tCourse Name");
			while (rs.next()) {
				System.out.println(rs.getString(1) + "\t\t" + rs.getString(2));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void StudentDropCourses(String username) {
		try {
			sc = new Scanner(System.in);
			state = con.prepareStatement(
					"select c.course_id,c.title from course c,enrolled e,student s where s.username=? and e.student_id=s.student_id and e.enrolledstatus='Y' and c.course_id=e.course_id");
			state.setString(1, username);
			ResultSet rs = state.executeQuery();
			System.out.println("CourseID\tCourse Name");
			int i=0;
			String[] course = new String[rs.getFetchSize()];
			while (rs.next()) {
				course[i] = rs.getString(1);
				System.out.println((i+1)+"."+course[i]+ "\t\t" + rs.getString(2));
				i++;
			}
		
			System.out.println("Select option: ");
			int k = sc.nextInt();
			state = con.prepareStatement("delete from enrolled where course_id = ? and student_id in (Select student_id from student where username = ?)");
			state.setString(1, course[k-1]);
			state.setString(2, username);
			state.executeQuery();
			con.commit();
			System.out.println("Course dropped successfully!");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Course dropping failed!");

		}
	}

	public void StudentViewAvailableCourse(String username) {
		try {
			sc = new Scanner(System.in);
			state = con.prepareStatement(
					"select o1.course_id from offering o1 where o1.sem='S17' AND o1.maxsize>o1.studentsenrolled union select o2.course_id from offering o2 where o2.sem='S17' AND o2.maxsize=o2.studentsenrolled AND o2.maxwaitist>o2.studentswaitlisted MINUS select course_id from enrolled e where e.student_id in (select student_id from student where username = ?)");
			state.setString(1, username);
			ResultSet rs = state.executeQuery();
			if (rs.isBeforeFirst()) {
				System.out.println("Available courses are:");
				int i = 1;
				String[] course = new String[rs.getFetchSize()];
				while (rs.next()) {
					course[i - 1] = rs.getString(1);
					System.out.println(i + "." + course[i - 1]);
					i++;
				}
				System.out.print("Select a course to enroll.");
				int id = sc.nextInt();
				state = con.prepareStatement(
						"select count(*) from prereq where courseprereq_id not in (select e.course_id from enrolled e,student s where s.student_id=e.student_id and s.username=?) and course_id=?");
				state.setString(1, username);
				state.setString(2, course[id - 1]);
				ResultSet rs1 = state.executeQuery();
				if (rs1.next()) {
					if (rs1.getInt(1) > 0) {
						System.out.println("You can't enroll since prequisite course/s is not taken.");
					} else {
						state = con.prepareStatement("Select credits from course where course_id = ?");
						state.setString(1, course[id - 1]);
						rs = state.executeQuery();
						rs.next();
						String credit = rs.getString(1);
						state = con.prepareStatement("Select student_id from student where username = ?");
						state.setString(1, username);
						rs = state.executeQuery();
						rs.next();
						int studentid = rs.getInt(1);
						state = con.prepareStatement("Select mingpa from course where course_id = ?");
						state.setString(1, course[id - 1]);
						rs = state.executeQuery();
						if (!rs.next()) {
							enroll(studentid, course[id - 1], "S17", credit);
						} else {
							float mingpa = rs.getFloat(1);
							state = con.prepareStatement("Select gpa from student where username = ?");
							state.setString(1, username);
							rs = state.executeQuery();
							if (rs.next()) {
								if (rs.getFloat(1) < mingpa) {
									System.out.println("You can't enroll since minimum gpa requirement is not met.");
								} else {
									enroll(studentid, course[id - 1], "S17", credit);
								}
							} else {
								System.out.println("You can't enroll since minimum gpa requirement is not met.");
							}
						}
					}

				} else
					System.out.print("\nNo courses available right now.");

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void enroll(int studentid, String courseid, String sem, String credits) {
		try {
			state = con.prepareStatement("Select permission from course where course_id = ?");
			state.setString(1, courseid);
			ResultSet rs = state.executeQuery();
			rs.next();
			if (rs.getString(1).equals("Y")) {
				state = con.prepareStatement(
						"Insert into specialpermission (Course_id, student_id, sem, reqdate) values (?,?,?,?)");
				state.setString(1, courseid);
				state.setInt(2, studentid);
				state.setString(3, sem);
				Date dnow = new Date(System.currentTimeMillis());
				DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
				String mydateStr = df.format(dnow);
				Calendar cal = Calendar.getInstance();
				cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(mydateStr.split("-")[1]));
				cal.set(Calendar.MONTH, Integer.parseInt(mydateStr.split("-")[0]) - 1);
				cal.set(Calendar.YEAR, Integer.parseInt(mydateStr.split("-")[2]));
				state.setDate(4, new Date(cal.getTimeInMillis()));
				state.executeQuery();
				System.out.println("Course requested for Permission!");
			} else {
				state = con.prepareStatement(
						"select maxsize, studentsenrolled, studentswaitlisted from offering where course_id = ? and sem = ?");
				state.setString(1, courseid);
				state.setString(2, sem);
				rs = state.executeQuery();
				rs.next();
				int size = rs.getInt(1);
				int enrolled = rs.getInt(2);
				int waitlist = rs.getInt(3);
				boolean available = (size > enrolled) ? true : false;

				state = con.prepareStatement(
						"insert into enrolled (student_id, course_id, sem, coursecredits, waitlistnumber, enrolledstatus) values (?,?,?,?,?,?)");
				state.setInt(1, studentid);
				state.setString(2, courseid);
				state.setString(3, sem);
				if (credits.length() > 1) {
					int min = Integer.parseInt(credits.split("-")[0]);
					int max = Integer.parseInt(credits.split("-")[1]);
					System.out.println("Enter credits between " + min + "and " + max + ": ");
					int val = sc.nextInt();
					while (!(val >= min && val <= max)) {
						System.out.println("Invalid Number of credits. Choose again");
						val = sc.nextInt();
					}
					state.setInt(4, val);

				} else {
					state.setInt(4, Integer.parseInt(credits));
				}
				if (available) {
					state.setInt(5, 0);
					state.setString(6, "Y");
				} else {
					state.setInt(5, waitlist + 1);
					state.setString(6, "N");
				}
				state.executeQuery();
				con.commit();
				System.out.println("Successfully enrolled!");
			}
		} catch (

		Exception e) {
			// TODO: handle exception
			System.out.println("Enrollment failed!");
			e.printStackTrace();

		}
	}

	// Closing the database connection
	public void close() {
		try {
			if (state != null)
				state.close();
			con.commit();
			con.close();
			conOracle.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
