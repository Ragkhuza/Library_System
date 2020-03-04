import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.swing.JButton;

public class WindowSettings {

	private JFrame frmSettings;

	public WindowSettings() {
		frmSettings = new JFrame();
		frmSettings.setTitle("Settings");
		frmSettings.setBounds(100, 100, 257, 212);
		frmSettings.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSettings.setLayout(null);
		frmSettings.setLocationRelativeTo(null);
		frmSettings.setResizable(false);
		frmSettings.setVisible(true);
		
		JButton btnUpdateAcc = new JButton("Update account");
		JButton btnDeleteAcc = new JButton("Delete account");
		JButton btnBack = new JButton("Back to main menu");
		JButton btnLogOut = new JButton("Log out");
		
		btnUpdateAcc.setBounds(36, 30, 164, 23);
		btnDeleteAcc.setBounds(36, 59, 164, 23);
		btnBack.setBounds(36, 88, 164, 23);
		btnLogOut.setBounds(36, 117, 164, 23);
		
		frmSettings.add(btnUpdateAcc);
		frmSettings.add(btnDeleteAcc);
		frmSettings.add(btnBack);
		frmSettings.add(btnLogOut);
		
		btnUpdateAcc.addActionListener(e -> {
 			frmSettings.dispose();
			new WindowUpdateAcc();
		});
		
		btnDeleteAcc.addActionListener(e -> { 
			
			int response = JOptionPane.showConfirmDialog(null,
					"Are you sure you want to delete your account?",
					"Alert",
					JOptionPane.YES_NO_OPTION);
			
			if(response == 0) {
				String id = CredentialData.getUsername();
				
				deleteAcc(id);
				
				frmSettings.dispose();
				NotificationManager.Success("Account deleted!");
				new WindowLogin();
			}
			
		});

		btnBack.addActionListener(e -> { 
 			frmSettings.dispose();

			if (CredentialData.getRole().toLowerCase().equals("librarian"))
				new WindowLibrarian(); // Run the window for librarian
			else if (CredentialData.getRole().toLowerCase().equals("patron"))
				new WindowPatron(); // Run the window for Patron
			else
				NotificationManager.Error("[WindowSettings.java] Unidentified User Type");
		});

		btnLogOut.addActionListener(e -> {
			frmSettings.dispose();
			NotificationManager.Message("Message", "Log out successful!");

			new WindowLogin();
		});
		
	}
	
	private void deleteAcc(String id) {
		Connection conn = null;
		PreparedStatement pst = null;
		conn= DBConnection.getConnection();

		if(conn != null){

			String sql= "DELETE FROM LIBRARYUSERS WHERE username = '" + CredentialData.getUsername() + "'";
			System.out.println("deleteAcc- SQL : " + sql);

			try {
				pst= conn.prepareStatement(sql);
				pst.executeUpdate();
			} catch (Exception e) {
				NotificationManager.Warning("[removeTable] " + e.getMessage());
			}
		}
	}

}
