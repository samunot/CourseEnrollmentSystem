import java.sql.SQLException;
import java.text.ParseException;
import java.util.Scanner;

public class appflow {
	static Scanner sc;
	static DatabaseHandler dbhandler;
	static String username;

	public static void main(String[] arg) throws SQLException, ParseException {
		dbhandler = new DatabaseHandler();
		sc = new Scanner(System.in);
		System.out.println("Welcome to the university course registration system.\n");
		while(true){
			if(StartMenu()==1)
				break;
		}
	}

	private static int StartMenu() throws SQLException, ParseException {
		sc = new Scanner(System.in);
		System.out.print("Select option\n1.Login\n2.Exit\nEnter choice:");
		int key = sc.nextInt();
		switch (key) {
		case 1:
			while(true){
				if(Login()==1)
					break;
			}
			break;
		case 2:
			System.out.println("Exiting the system!");
			dbhandler.close();
			sc.close();
			return 1;
		default:
			System.out.println("The option you entered is invalid. Please try again!");
			break;
		}
		return 0;
	}

	private static int Login() throws SQLException, ParseException {
		sc = new Scanner(System.in);
		System.out.print("\nEnter Username: ");
		username = sc.next();
		System.out.print("\nEnter Password: ");
		String password = sc.next();
		int isStudent = dbhandler.loginValidate(username, password);
		if (isStudent == 1) {
			System.out.println("You are a student");
			while(true){
				if(StudentFunctions()==1)
					break;
			}
		} else if (isStudent == 2) {
			System.out.println("You are an admin");
			while(true){
				if(AdminFunctions()==1)
					break;
			}
		} else {
			System.out.println("Invalid username or password. Please try again!");
			sc.close();
			return 0;
		}
		sc.close();
		return 1;
	}

	private static int AdminFunctions() throws SQLException,ParseException{
		sc = new Scanner(System.in);
		System.out.print("\nHello admin! Select option.\n1.View  Profile\n2.Enroll a new Student\n3.View Student's details\n"
				+ "4.View or Add Courses\n5.View or Add Course Offering\n"
				+ "6.View or Approve Special Enrollment Requests\n"
				+ "7.Enforce Add or Drop Deadline\n8.Logout\nEnter choice:");
		int key=sc.nextInt();
		switch(key){
		case 1:
			while(true){
				if(AdminViewProfile()==1)
					break;
			}
			break;
		case 2:
			while(true){
				if(AdminEnrollNewStudent()==1)
					break;
			}
			break;
		case 3:
			while(true){
				if(AdminViewStudent()==1)
					break;
			}
			break;
		case 4:
			while(true){
				if(AdminAddViewCourse()==1)
					break;
			}
			break;
		case 5:
			while(true){
				if(AdminAddOffering()==1)
					break;
			}
			break;
		case 6:
			while(true){
				if(AdminSpecialPermission()==1)
					break;
			}
			break;
		case 7:
			while(true){
				if(AdminEnforceDeadline()==1)
					break;
			}
			break;
		case 8:
			System.out.print("\nLogging out!");
			sc.close();
			return 1;
		}
		return 0;
	}
	private static int StudentFunctions() throws SQLException,ParseException{
		sc = new Scanner(System.in);
		System.out.print("Welcome student!\nSelect options:\n1.View/Edit Profile\n"
				+ "2.View Courses/Drop courses\n3.View Pending courses\n"
				+ "4.View Grades\n5.View/Pay bill\nEnter Choice:");
		int key=sc.nextInt();
		switch(key){
		case 1:
			while(true){
				if(StudentViewProfile()==1)
					break;
			}
			break;
		case 2:
			while(true){
				if(StudentEnrollViewDropCourse()==1)
					break;
			}
			break;
		case 3:
			while(true){
				if(StudentViewPendingCourse()==1)
					break;
			}
			break;
		case 4:
			while(true){
				if(StudentViewGrades()==1)
					break;
			}
			break;
		case 5:
			while(true){
				if(StudentViewPayBills()==1)
					break;
			}
			break;
		default:
			System.out.println("Invalid option selected. Please try again!");
			return 0;
		}
		sc.close();
		return 1;
	}

	private static int AdminViewProfile() throws SQLException,ParseException{
		sc=new Scanner(System.in);
		System.out.print("\nPress 0 to go back.");
		if(sc.nextInt()==0){
			sc.close();
			return 1;
		}
		else System.out.println("Please enter a valid option.");
		sc.close();
		return 0;
	}
	private static int AdminEnrollNewStudent() throws SQLException,ParseException{
		sc=new Scanner(System.in);
		System.out.print("\nStudent has been successfully enrolled!");	
		sc.close();
		return 1;
	}
	private static int AdminViewStudent() throws SQLException,ParseException{
		
		return 0;
	}
	private static int AdminAddViewCourse() throws SQLException,ParseException{
		return 0;
	}
	private static int AdminAddOffering() throws SQLException,ParseException{
		return 0;
	}
	private static int AdminSpecialPermission() throws SQLException,ParseException{
		return 0;
	}
	private static int AdminEnforceDeadline() throws SQLException,ParseException{
		return 0;
	}
	private static int StudentViewProfile() throws SQLException,ParseException{
		sc = new Scanner(System.in);
		//dbhandler.StudentViewProfile(username);
		return 0;
	}
	private static int StudentEnrollViewDropCourse() throws SQLException,ParseException{
		return 0;
	}
	private static int StudentViewPendingCourse() throws SQLException,ParseException{
		return 0;
	}
	private static int StudentViewGrades() throws SQLException,ParseException{
		return 0;
	}
	private static int StudentViewPayBills() throws SQLException,ParseException{
		return 0;
	}
}