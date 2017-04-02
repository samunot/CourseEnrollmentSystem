import java.sql.Connection;
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
			rs.next();
			// invalid username or password
			if (rs == null || !rs.getString(1).equals(password))
				return -1;
			// student
			if (rs.getString(2).equalsIgnoreCase("student"))
				return 1;
			// admin
			return 2;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
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
			String key = sc.next();
			while (!key.equals("0")) {
				System.out.println("Invalid Option");
				key = sc.next();
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

	public int viewStudent() {
		try {
			System.out.print("Please Enter Student ID: ");
			int studentid = sc.nextInt();
			state = con.prepareStatement(
					"select u.firstname,u.lastname,u.dob,s.residencylevel,s.gradlevel,s.tuitionowed,s.gpa from users u , student s where s.student_id = ? and s.username = u.username");
			ResultSet rs = state.executeQuery();
			if(!rs.next())	viewStudent();
			
			
			System.out.println("");
			return -1;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return -1;
		}
	}

	// Closing the database connection
	public void close() throws SQLException {
		state.close();
		con.close();
		conOracle.close();
	}
}
