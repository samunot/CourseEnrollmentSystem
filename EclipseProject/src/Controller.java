import java.util.Scanner;

public class Controller {
	
	static DatabaseHandler dbhandler;
	
	public static void main(String[] arg){
		dbhandler = new DatabaseHandler();
		int isStudent = login();
		if(isStudent==1){
			//call student functions here
		}else if (isStudent==2){
			//call admin functions here
		}else{
			System.exit(0);
		}
	}
	
	private static int login(){
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter username: ");
		String username = sc.next();
		System.out.print("\nEnter Password: ");
		String password = sc.next();
		
		int isStudent = dbhandler.loginValidate(username, password);
		
		return isStudent;
		
	}
}
