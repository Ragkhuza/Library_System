import java.sql.*;

public class DBConnection {
	
	private static Connection conn = null;// unify all connection

	public static Connection getConnection() {
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");

			Connection conn = DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:1521:orcl","doggo","Oracle123");

			System.out.println("Connected to database");
			return conn;
		} catch(Exception e) {
			System.out.println("Could not Connect to database");
			System.out.println(e);
			return null;
		}
	}
	
	public static boolean isAllConnectionClosed() {
		boolean result = false;
		try {
			result = conn.isClosed();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static void closeAllConnection() {
		try	{
			conn.close();
			conn = null;
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
