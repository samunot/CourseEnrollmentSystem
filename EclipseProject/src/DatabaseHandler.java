import java.sql.Connection;
import java.util.ArrayList;
import java.util.Calendar;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
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

			System.out.println("Student enrolled successfully! :)");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
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
			break;
		case 2:
			addCourse();
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
			while (key != 0) {
				System.out.println("Invalid Option");
				key = sc.nextInt();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void addCourse() {
		try {
			System.out.print("Enter Course ID: ");
			String courseid = sc.nextLine();
			System.out.print("Enter Course Name: ");
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
					prereq[i] = sc.nextLine();
				}
			}

			System.out.print("Special Approval Required(Y/N): ");
			String perm = sc.next();
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
			state = con.prepareStatement("Insert into departmentcourse (DEPARTMENT_ID, Course_ID) values()");
			state.setString(1, depid);
			state.setString(2, courseid);
			state.executeUpdate();
			state = con.prepareStatement("Insert into prereq (courseprereq_id, course id)");
			state.setString(2, courseid);
			for (int i = 0; i < prereq.length; i++) {
				state.setString(1, prereq[i]);
				state.executeUpdate();
			}
			System.out.println("Course Successfully added!");
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
			ResultSet rs = state.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void StudentEditLastName(String username, String name) {
		try {
			state = con.prepareStatement("Update users set lastname=? where username=?");
			state.setString(1, name);
			state.setString(2, username);
			ResultSet rs = state.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void StudentEditEmail(String username, String email) {
		try {
			state = con.prepareStatement("Update student set email=? where username=?");
			state.setString(1, email);
			state.setString(2, username);
			ResultSet rs = state.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void StudentEditPhone(String username, String phone) {
		try {
			state = con.prepareStatement("Update users set phone=? where username=?");
			state.setString(1, phone);
			state.setString(2, username);
			ResultSet rs = state.executeQuery();
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
					ResultSet rs1 = state.executeQuery();
					System.out.print("\nYou have successfully paid " + amount + " amount.");
				} else
					System.out.print("\nThe payment amount exceeds the owed bill. Please choose a smaller amount.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Closing the database connection
	public void close() {
		try {
			if (state != null)
				state.close();
			con.close();
			conOracle.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
