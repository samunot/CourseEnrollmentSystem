import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Test {

	public static void main(String[] args)throws SQLException {
		// TODO Auto-generated method stub
		Connection conn = null;
		ConnectionToOracle con = new ConnectionToOracle();
		PreparedStatement state = null;
			conn = con.returnConnection();
			state = conn.prepareStatement("Insert into Student values (9999,'Rk')");
			boolean result = state.execute();
			state.close();
			conn.close();
			con.close();
			if (result)
				System.out.println("Success");
			else
				System.out.println("Fail");

	}
}