import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseHandler {

	Connection con;
	ConnectionToOracle conOracle;
	PreparedStatement state;

	// Initializing connection
	public DatabaseHandler() {
		Connection con = null;
		ConnectionToOracle conOracle = new ConnectionToOracle();
		con = conOracle.returnConnection();
		state = null;
	}

	// Closing the database connection
	public void close() throws SQLException {
		state.close();
		con.close();
		conOracle.close();
	}

	public int loginValidate(String username, String password)throws SQLException {
		state = con.prepareStatement("Select password, role from user where username = ?");
		state.setString(1, username);
		ResultSet rs = state.executeQuery();
		//invalid username or password
		if(rs==null || rs.getString(1)!= password)	return 3;
		//student
		if(rs.getString(2).equalsIgnoreCase("student"))	return 1;
		//admin
		return 2;
	}
}
