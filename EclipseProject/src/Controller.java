import java.sql.SQLException;
import java.text.ParseException;
import java.util.Scanner;

public class Controller {

	static Scanner sc;
	static DatabaseHandler dbhandler;
	static String username;

	public static void main(String[] arg) throws SQLException, ParseException {
		dbhandler = new DatabaseHandler();
		sc = new Scanner(System.in);
		loginExit();
	}

	private static void loginExit() throws SQLException, ParseException {
		sc = new Scanner(System.in);
		System.out.println("1.Login\n2.Exit\nEnter choice");
		int key = sc.nextInt();
		switch (key) {
		case 1:
			login();
			break;
		case 2:
			dbhandler.close();
			System.exit(0);
		default:
			break;
		}
	}

	private static void login() throws SQLException, ParseException {
		System.out.print("1.Username: ");
		username = sc.next();
		System.out.print("2.Password: ");
		String password = sc.next();
		int isStudent = dbhandler.loginValidate(username, password);
		if (isStudent == 1) {
			// studentFunctions();
		} else if (isStudent == 2) {
			adminFunctions();
		} else {
			System.out.println("Invalid username or password. Please try again!");
			login();
		}
	}

	private static void adminFunctions() throws SQLException, ParseException {
		System.out.println(
				"1.View  Profile\n2.Enroll a new Student\n3.View Student's details\n4.View or Add Courses\n5.View or Add Course Offering\n6.View or Approve Special Enrollment Requests\n7.Enforce Add or Drop Deadline\n8.Logout");

		int key = sc.nextInt();
		switch (key) {
		case 1:
			dbhandler.viewAdminProfile(username);
			adminFunctions();
			break;
		case 2:
			dbhandler.enrollStudent();
			adminFunctions();
			break;
		case 3:
			dbhandler.viewStudent();
			adminFunctions();
			break;
		case 8:
			loginExit();
			break;
		default:
			break;
		}
	}

	private static void studentFunctions() {
		System.out.println("1.Enroll courses\n2.Drop courses\n");
		int choice = sc.nextInt();
		switch (choice) {
		case 1:
			enrollCourse();
			break;

		case 2:
			dropCourse();
			break;

		case 3:
		default:
			break;
		}
	}

	private static void enrollCourse() {

	}

	private static void dropCourse() {

	}
}
