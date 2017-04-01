import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

	public int loginValidate(String username, String password) throws SQLException {
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
	}

	public void viewAdminProfile(String username) throws SQLException {
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
		return;
	}

	// Closing the database connection
	public void close() throws SQLException {
		state.close();
		con.close();
		conOracle.close();
	}
}
