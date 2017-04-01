import java.sql.SQLException;
import java.util.Scanner;

public class Controller {
	
	static Scanner sc;
	static DatabaseHandler dbhandler;
	static String username;
	
	
	public static void main(String[] arg)throws SQLException{
		dbhandler = new DatabaseHandler();
		int isStudent = login();
		if(isStudent==1){
			studentFunctions();
		}else if (isStudent==2){
			adminFunctions();
		}else{
			System.out.println("Invalid username or password. Please try again!");
			login();
		}
	}
	
	private static int login()throws SQLException{
		sc = new Scanner(System.in);
		System.out.print("Enter username: ");
		username = sc.next();
		System.out.print("\nEnter Password: ");
		String password = sc.next();
		int isStudent = dbhandler.loginValidate(username, password);
		return isStudent;	
	}
	
	private static void studentFunctions(){
		System.out.println("1.Enroll courses\n2.Drop courses\n");
		int choice  = sc.nextInt();
		switch (choice) {
		case 1: enrollCourse();
			break;
			
		case 2: dropCourse();
			break;
			
		case 3: 
		default:
			break;
		}
	}
	
	private static void adminFunctions(){
		
	}
	
	private static void enrollCourse(){
		
	}
	
	private static void dropCourse(){
		
	}
}
