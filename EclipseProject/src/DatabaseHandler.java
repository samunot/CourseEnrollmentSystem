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

	public void viewAdminProfile(String username) throws SQLException {
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

	public void enrollStudent() throws SQLException {
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
			if (rs == null) {
				System.out.println("Invalid Student ID! Please try again");
				viewStudent();
				return;
			}
			rs.next();
			DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
			String address = rs.getString(10) + " " + rs.getString(11) + " " + rs.getInt(12) + " " + rs.getString(13);
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
				String key = sc.next();
				while (!key.equals("0")) {
					System.out.println("Invalid Option");
					key = sc.next();
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

	// Closing the database connection
	public void close() throws SQLException {
		if (state != null)
			state.close();
		con.close();
		conOracle.close();
	}
}
