package finals;
import java.sql.*;
import javax.swing.*;

public abstract class DBConnection {
	
	private static Connection conn = null;// unify all connection

	public static Connection getConnection() {

		try {
			Class.forName("org.sqlite.JDBC");
			// Doggo must reopen connection if it's closed
			// closedConnection is not same as null
			if(conn == null || conn.isClosed()) // dont make connection if there's already one
				conn = DriverManager.getConnection("jdbc:sqlite:MusicSystem.db");
			// Uncomment for testing database connection
//			Alert.Success("Database connected!");
			return conn;
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
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
