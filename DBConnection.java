package finals;
import java.sql.*;
import javax.swing.*;

public class DBConnection {

	private static final String USERNAME = "admin";
	private static final String PASSWORD = "admin";

	public static Connection getConnection() {

		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:MusicSystem.db");
			// Uncomment for testing database connection
//			JOptionPane.showMessageDialog(null, "Database connected!");
			return conn;
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
			return null;
		}

	}
}
