import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

public class Test {

	public static void special(){
		
		// TODO Auto-generated method stub
		Connection conn = null;
		ConnectionToOracle conOracle = new ConnectionToOracle();
		conn = conOracle.returnConnection();
		PreparedStatement state = null;
		Scanner sc = new Scanner(System.in);
	
		}

}
