import java.util.Scanner;

public class Controller {

	static Scanner sc;
	static DatabaseHandler dbhandler;
	static String username;

	public static void main(String[] arg) {
		dbhandler = new DatabaseHandler();
		sc = new Scanner(System.in);
		loginExit();
	}

	private static void loginExit() {
		sc = new Scanner(System.in);
		System.out.println("1.Login\n2.Exit\nEnter choice");
		int key = sc.nextInt();
		switch (key) {
		case 1:
			login();
			break;
		case 2:
			dbhandler.close();
			System.out.println("System terminated!");
			System.exit(0);
		default:
			break;
		}
	}

	private static void login() {
		System.out.print("1.Username: ");
		username = sc.next();
		System.out.print("2.Password: ");
		String password = sc.next();
		int isStudent = dbhandler.loginValidate(username, password);
		if (isStudent == 1) {
			while (true)
				if (studentFunctions() == 1)
					break;
			loginExit();
		} else if (isStudent == 2) {
			adminFunctions();
		} else {
			System.out.println("Invalid username or password. Please try again!");
			login();
		}
	}

	private static void adminFunctions() {
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
		case 4:
			dbhandler.viewAddCourse();
			adminFunctions();
			break;
		case 5:
			dbhandler.viewAddOffering();
			adminFunctions();
			break;
		case 6:
			dbhandler.viewApprovePermisiion();
			adminFunctions();
			break;
		case 7:
			dbhandler.enforceDeadline();
			adminFunctions();
			return;
		case 8:
			loginExit();
			break;
		default:
			break;
		}
	}

	private static int studentFunctions() {
		sc = new Scanner(System.in);
		System.out.print("Welcome student!\nSelect options:\n1.View/Edit Profile\n"
				+ "2.View Courses/Drop courses\n3.View Pending courses\n"
				+ "4.View Grades\n5.View/Pay bill\n6.Logout\nEnter Choice:");
		int key = sc.nextInt();
		switch (key) {
		case 1:
			while (true) {
				if (StudentViewProfile() == 1)
					break;
			}
			break;
		case 2:
			while (true) {
				if (StudentEnrollViewDropCourse() == 1)
					break;
			}
			break;
		case 3:
			while (true) {
				if (StudentViewPendingCourse() == 1)
					break;
			}
			break;
		case 4:
			while (true) {
				if (StudentViewGradesGPA() == 1)
					break;
			}
			break;
		case 5:
			while (true) {
				if (StudentViewPayBills() == 1)
					break;
			}
			break;
		case 6:
			return 1;
		default:
			System.out.println("Invalid option selected. Please try again!");
			break;
		}
		return 0;
	}

	private static int StudentViewProfile() {
		sc = new Scanner(System.in);
		dbhandler.StudentViewProfile(username);
		int key = sc.nextInt();
		switch (key) {
		case 1:
			System.out.print("\nEnter First Name: ");
			String fname = sc.next();
			dbhandler.StudentEditFirstName(username, fname);
			break;
		case 2:
			System.out.print("\nEnter Last Name: ");
			String lname = sc.next();
			dbhandler.StudentEditLastName(username, lname);
			break;
		case 3:
			System.out.print("\nEnter email: ");
			String email = sc.next();
			dbhandler.StudentEditEmail(username, email);
			break;
		case 4:
			System.out.print("\nEnter phone: ");
			String phone = sc.next();
			dbhandler.StudentEditPhone(username, phone);
			break;
		case 5:
			System.out.print("\nYou don't have permission to edit this field");
			break;
		case 6:
			System.out.print("\nYou don't have permission to edit this field");
			break;
		case 0:
			return 1;
		}
		return 0;
	}

	private static int StudentViewGradesGPA() {
		sc = new Scanner(System.in);
		System.out.print("\n1.Display letter grades\n2.Display GPA\nPress 0 to go back\nEnter choice:");
		int key = sc.nextInt();
		switch (key) {
		case 1:
			dbhandler.StudentViewGrades(username);
			break;
		case 2:
			dbhandler.StudentViewGPA(username);
			break;
		case 0:
			return 1;
		}
		return 0;
	}

	private static int StudentViewPayBills() {
		sc = new Scanner(System.in);
		System.out.print("\n1.Display balance\n2.Pay bills\nPress 0 to go back\nEnter choice:");
		int key = sc.nextInt();
		switch (key) {
		case 1:
			dbhandler.StudentViewBill(username);
			break;
		case 2:
			dbhandler.StudentPayBill(username);
			break;
		case 0:
			return 1;
		}
		return 0;
	}
	
	private static int StudentEnrollViewDropCourse(){
		sc=new Scanner(System.in);
		System.out.print("\n1.View Available Courses\n2. View My Courses\n3. Drop Course\nPress 0 to go back.\nEnter Choice:");
		int key=sc.nextInt();
		switch(key){
		case 1:
			dbhandler.StudentViewAvailableCourse(username);		
			break;
		case 2:
			dbhandler.StudentViewMyCourses(username);
			break;
		case 3: 
			dbhandler.StudentDropCourses(username);
			break;
		case 0:
			return 1;
		}
		return 0;
	}
	
	private static int StudentViewPendingCourse() {
		sc=new Scanner(System.in);
		dbhandler.StudentViewPendingCourse(username);
		System.out.print("\nPress 0 to go back.");
		int key=sc.nextInt();
		if(key==0)
			return 1;
		else return 0;
	}	
}
