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
			String sql = "SELECT * FROM users where username = \"" + userN + "\" AND password = \"" + pass + "\" ;";
			System.out.println("refreshTable- SQL : " + sql);

			try {
				pst = conn.prepareStatement(sql);
				rs = pst.executeQuery();
				
				rs.next();
				if(userN.equals(rs.getString("username")) && pass.equals(rs.getString("password"))) {
					result = true;
					// this will fetch user input data
					CredentialData.username = rs.getString("username");
					CredentialData.password = rs.getString("password");
					CredentialData.firstname = rs.getString("first_name");
					CredentialData.lastname = rs.getString("last_name");
					System.out.println("AdminData variables initialized");
				} else {
					System.out.println("Something wrong with the AdminData variables");
				}
				
			}

			catch (Exception e) {
				JOptionPane.showMessageDialog(null, e);
			}
			
		} else {
			NotificationManager.Error("[AdminData.java] conn is null");
		}
		
		return result;
	}
	
}
