package finals;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class AdminData {
	static Connection conn = null;
	static PreparedStatement pst = null;
	static ResultSet rs = null;
	static DefaultTableModel model = new DefaultTableModel();
	
	// Doggo doesn't know  if this will cause error
//	public static String username;
//	public static String password;
//	public static String firstname;
//	public static String lastname;
//	public String username = "admin";
//	public String password = "admin";
	
//	public String getPassword() {
//		return password;
//	}
//	
//	public void setPassword(String password) {
//		this.password = password;
//	}
	
	// by doggo attempting to authenticate user
	// userN: prevent ambiguity/confusion with username 
	public static boolean authenticate(String userN, String pass) {
		boolean result = false;
		conn = DBConnection.getConnection();
		int rowCount;
		
		if(conn != null) {
			// @DOGOO symbols breaks sql statement without single qoutes
			// may i?
			// double quotes kasi baka ibang symbols IASJDHAISHDASK OK WAIT IESUIRHEUR RUNRUNRUN
			String sql = "SELECT * FROM users where username = \"" + userN + "\" AND password = \"" + pass + "\" ;";
			System.out.println("refreshTable- SQL : " + sql);

			try {
				pst = conn.prepareStatement(sql);
				rs = pst.executeQuery();
				
//				rowCount = rs.last() ? rs.getRow() : 0; // move cursor to the end, get total result row count
//	            rs.beforeFirst(); // move cursor back to start
//	            
//				if(rowCount != 1) return false;  // reject if result count is less than 1 or greater than 1
				
				rs.next();
				if( userN.equals(rs.getString("username")) && pass.equals(rs.getString("password")) ) {
					result = true;
					CredentialData.username = rs.getString("username");
					CredentialData.password = rs.getString("password");
					CredentialData.firstname = rs.getString("first_name");
					CredentialData.lastname = rs.getString("last_name");
					System.out.println("AdminData variables initialized");
				} else {
					// cute hahahaha HAHAHA
					System.out.println("All this time doggo never notify for wrong credential");
				}
				
			}

			catch (Exception e) {
				JOptionPane.showMessageDialog(null, e);
			}
			// @doggo no need to close connection because only one connection is allowed
//			finally {
//				try {
//					conn.close();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				} // close connection
//				try {
//					pst.close();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//				try {
//					rs.close();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//			}
			
		} else {
			Notification.Error("[AdminData.java]ERROR! conn is null");
		}
		
		return result;
	}
	
}
