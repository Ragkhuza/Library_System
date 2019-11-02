package finals;
import java.sql.*;
import javax.swing.*;

public abstract class DBConnection {

	private static final String USERNAME = "admin";
	private static final String PASSWORD = "admin";
	private static Connection conn = null;// unify all connection

	public static Connection getConnection() {

		try {
			Class.forName("org.sqlite.JDBC");
//			Connection conn = DriverManager.getConnection("jdbc:sqlite:MusicSystem.db");
			// WHAT A LOGIC DOGGO HAHA
			if(conn == null) // dont make connection if there's already one
				conn = DriverManager.getConnection("jdbc:sqlite:MusicSystem.db");
			// Uncomment for testing database connection
//			JOptionPane.showMessageDialog(null, "Database connected!");
			return conn;
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
			return null;
		}

	}
	
	// RUN
	
	public static boolean isAllConnectionClosed() {
		boolean result = false;
		try {
			result = conn.isClosed();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public static void closeAllConnection() {
		try	{
			conn.close();
			conn = null; // :) RUN
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
