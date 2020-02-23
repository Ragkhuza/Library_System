import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JSeparator;

public class UpdateAcc {
	Connection conn = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	private JFrame frmUpdateAcc;
	private JTextField jtxtOldUser;
	private JTextField jtxtNewUser;
	private JTextField jtxtOldPass;
	private JTextField jtxtNewPass;
	private JTextField jtxtFirstName;
	private JTextField jtxtLastName;

	public UpdateAcc() {
		
		// frame
		frmUpdateAcc = new JFrame();
		frmUpdateAcc.setTitle("Settings - Update Account");
		frmUpdateAcc.setBounds(100, 100, 387, 382);
		frmUpdateAcc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmUpdateAcc.setLayout(null);
		frmUpdateAcc.setLocationRelativeTo(null);
		frmUpdateAcc.setResizable(false);
		frmUpdateAcc.setVisible(true);
		
		// declaration of elements
		JLabel lblOldUser = new JLabel("Old Username");
		JLabel lblNewUser = new JLabel("New Username");
		JLabel lblOldPass = new JLabel("Old Password");
		JLabel lblNewPass = new JLabel("New Password");
		JLabel lblFirstName = new JLabel("First Name");
		JLabel lblLastName = new JLabel("Last Name");
		
		JButton btnApply = new JButton("Apply");
		JButton btnCancel = new JButton("Cancel");
		JButton btnMainMenu = new JButton("Home");
		
		jtxtOldUser = new JTextField(CredentialData.username);
		jtxtOldPass = new JTextField(CredentialData.password);
		jtxtNewUser = new JTextField(CredentialData.username);
		jtxtNewPass = new JTextField(CredentialData.password);
		jtxtFirstName = new JTextField(CredentialData.firstname); // will only work if user did log in
		jtxtLastName = new JTextField(CredentialData.lastname); // will only work if user did log in
		jtxtOldUser.setEditable(false);
		jtxtOldPass.setEditable(false);
		
		JSeparator separator = new JSeparator(); // divider
		
		// positioning of elements
		
		lblOldUser.setBounds(44, 24, 82, 14);
		lblNewUser.setBounds(44, 65, 119, 14);
		lblOldPass.setBounds(44, 107, 82, 14);
		lblNewPass.setBounds(44, 148, 119, 14);
		lblFirstName.setBounds(44, 214, 119, 14);
		lblLastName.setBounds(44, 255, 119, 14);
		
		separator.setBounds(44, 188, 290, 2);
				
		jtxtOldUser.setBounds(184, 16, 150, 30);
		jtxtNewUser.setBounds(184, 57, 150, 30);
		jtxtOldPass.setBounds(184, 99, 150, 30);
		jtxtNewPass.setBounds(184, 140, 150, 30);
		jtxtFirstName.setBounds(184, 206, 150, 30);
		jtxtLastName.setBounds(184, 247, 150, 30);
		
		btnApply.setBounds(44, 300, 90, 23);
		btnCancel.setBounds(144, 300, 90, 23);
		btnMainMenu.setBounds(244, 300, 90, 23);
		
		// displaying of elements
		
		frmUpdateAcc.add(lblOldUser);
		frmUpdateAcc.add(jtxtOldUser);
		frmUpdateAcc.add(lblNewUser);
		frmUpdateAcc.add(jtxtNewUser);
		frmUpdateAcc.add(lblOldPass);
		frmUpdateAcc.add(jtxtOldPass);
		frmUpdateAcc.add(lblNewPass);		
		frmUpdateAcc.add(jtxtNewPass);
		
		frmUpdateAcc.add(separator);
			
		frmUpdateAcc.add(jtxtFirstName);
		frmUpdateAcc.add(lblFirstName);
		frmUpdateAcc.add(jtxtLastName);
		frmUpdateAcc.add(lblLastName);

		frmUpdateAcc.add(btnApply);
		frmUpdateAcc.add(btnCancel);
		frmUpdateAcc.add(btnMainMenu); 
		
		
		btnMainMenu.addActionListener(e -> { 
			frmUpdateAcc.dispose();
 			new MainWindow();
		});
		
		btnCancel.addActionListener(e -> {
			frmUpdateAcc.dispose();
			new Settings();
		});
		
		btnApply.addActionListener(e -> {
			// Will not work unless user is logged in
			String oldUsername = jtxtOldUser.getText();
			String newUsername = jtxtNewUser.getText();
			String newPassword = jtxtNewPass.getText();
			String newFirstname = jtxtFirstName.getText();
			String newLastname = jtxtLastName.getText();
			
			if(updateAccount(oldUsername, newUsername, newPassword, newFirstname, newLastname)) {
				// update fields
				jtxtOldUser.setText(newUsername);
				jtxtOldPass.setText(newPassword);
				jtxtNewUser.setText("");
				jtxtNewPass.setText("");
			}
		});
	}

	private boolean updateAccount(String oldUsername, String newUsername, String newPassword,String newFirstname, String newLastname) {
		boolean result = false;

		if (!Validator.validateCredentials(newUsername, newPassword))
			return false;
		
		DBConnection.closeAllConnection(); // close connection to prevent overlap
		conn = DBConnection.getConnection(); // open new connection
		
		String sql = "UPDATE users SET username ='" + newUsername
					+ "', password ='" + newPassword
					+ "', first_name ='" + newFirstname
					+ "', last_name ='" + newLastname
					+ "' WHERE username = '" + oldUsername + "' ;";

		System.out.println("modifyTable- SQL : " + sql);

		try {
			pst = conn.prepareStatement(sql);
			int count = pst.executeUpdate();

			if(count > 0) {
				result = true;
				NotificationManager.Success("Update success!");
			} else {
				NotificationManager.Error("[UpdateAcc.java]Update Unsuccessful");
			}
		} catch (Exception e) {
			NotificationManager.Error("[UpdateAcc.java] " + e.getMessage());
		}
		
		return result;
	}
}
