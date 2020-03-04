import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class AdminData {
	// declaring for the connection to the database
	static Connection conn = null;
	static PreparedStatement pst = null;
	static ResultSet rs = null;
	static DefaultTableModel model = new DefaultTableModel();
	
	public static boolean authenticate(String userN, String pass) {
		boolean result = false;
		conn = DBConnection.getConnection(); // establishing of connection to the database


		if(conn != null) {
			String sql = "SELECT * FROM LIBRARYUSERS WHERE USERNAME = '"  + userN + "' AND USERPASSWORD = '" + pass + "'";
			System.out.println("refreshTable- SQL : " + sql);

			try {
				pst = conn.prepareStatement(sql);
				rs = pst.executeQuery();

				if (rs.next()) {
					System.out.println("Result: " + rs.getString("USERNAME") + " | " + rs.getString("USERPASSWORD"));
					if(userN.equals(rs.getString("USERNAME")) && pass.equals(rs.getString("USERPASSWORD"))) {
						result = true;
						// this will fetch user input data
						CredentialData.setUsername(rs.getString("USERNAME"));
						CredentialData.setPassword(rs.getString("USERPASSWORD"));
						CredentialData.setFirstname(rs.getString("FIRSTNAME"));
						CredentialData.setLastname(rs.getString("LASTNAME"));
						CredentialData.setRole(rs.getString("USERTYPE"));
						System.out.println("AdminData variables initialized");
					} else
						System.out.println("Something wrong with the AdminData variables");
				} else {
					NotificationManager.Error("Invalid Username or Password!");
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "[AdminData.java] " + e);
			}
		} else
			NotificationManager.Error("[AdminData.java] conn is null");
		
		return result;
	}
	
}
