package finals;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
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
	
	public static void showWindow() {
		UpdateAcc window = new UpdateAcc();
		window.frmUpdateAcc.setVisible(true);
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UpdateAcc window = new UpdateAcc();
					window.frmUpdateAcc.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	// run

	/**
	 * Create the application.
	 */
	public UpdateAcc() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmUpdateAcc = new JFrame();
		frmUpdateAcc.setTitle("Settings - Update Account");
		frmUpdateAcc.setBounds(100, 100, 387, 382);
		frmUpdateAcc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmUpdateAcc.getContentPane().setLayout(null);
		
		JLabel lblOldUser = new JLabel("Old Username");
		lblOldUser.setBounds(44, 24, 82, 14);
		frmUpdateAcc.getContentPane().add(lblOldUser);
		
		jtxtOldUser = new JTextField(CredentialData.username);
		jtxtOldUser.setEditable(false);
		jtxtOldUser.setBounds(184, 16, 150, 30);
		frmUpdateAcc.getContentPane().add(jtxtOldUser);
		jtxtOldUser.setColumns(10);
		
		JLabel lblNewUser = new JLabel("New Username");
		lblNewUser.setBounds(44, 65, 119, 14);
		frmUpdateAcc.getContentPane().add(lblNewUser);
		
		jtxtNewUser = new JTextField(CredentialData.username);
		jtxtNewUser.setColumns(10);
		jtxtNewUser.setBounds(184, 57, 150, 30);
		frmUpdateAcc.getContentPane().add(jtxtNewUser);
		
		JLabel lblOldPass = new JLabel("Old Password");
		lblOldPass.setBounds(44, 107, 82, 14);
		frmUpdateAcc.getContentPane().add(lblOldPass);
		
		jtxtOldPass = new JTextField(CredentialData.password);
		jtxtOldPass.setEditable(false);
		jtxtOldPass.setColumns(10);
		jtxtOldPass.setBounds(184, 99, 150, 30);
		frmUpdateAcc.getContentPane().add(jtxtOldPass);
		
		JLabel lblNewPass = new JLabel("New Password");
		lblNewPass.setBounds(44, 148, 119, 14);
		frmUpdateAcc.getContentPane().add(lblNewPass);
		
		jtxtNewPass = new JTextField(CredentialData.password);
		jtxtNewPass.setColumns(10);
		jtxtNewPass.setBounds(184, 140, 150, 30);
		frmUpdateAcc.getContentPane().add(jtxtNewPass);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(44, 188, 290, 2);
		frmUpdateAcc.getContentPane().add(separator);
		
		jtxtFirstName = new JTextField(CredentialData.firstname); // will only work if user did log in
		jtxtFirstName.setColumns(10);
		jtxtFirstName.setBounds(184, 206, 150, 30);
		frmUpdateAcc.getContentPane().add(jtxtFirstName);
		
		JLabel lblFirstName = new JLabel("First Name");
		lblFirstName.setBounds(44, 214, 119, 14);
		frmUpdateAcc.getContentPane().add(lblFirstName);
		
		jtxtLastName = new JTextField(CredentialData.lastname); // will only work if user did log in
		jtxtLastName.setColumns(10);
		jtxtLastName.setBounds(184, 247, 150, 30);
		frmUpdateAcc.getContentPane().add(jtxtLastName);
		
		JLabel lblLastName = new JLabel("Last Name");
		lblLastName.setBounds(44, 255, 119, 14);
		frmUpdateAcc.getContentPane().add(lblLastName);
		
		JButton btnApply = new JButton("Apply");
		btnApply.setBounds(44, 300, 90, 23);
		frmUpdateAcc.getContentPane().add(btnApply);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBounds(144, 300, 90, 23);
		frmUpdateAcc.getContentPane().add(btnCancel);
		
		JButton btnMainMenu = new JButton("Back");
		btnMainMenu.addActionListener(e -> { 
			frmUpdateAcc.dispose();
 			MainWindow mw = new MainWindow();
			mw.openMainWindow();
		});
		btnMainMenu.setBounds(244, 300, 90, 23);
		frmUpdateAcc.getContentPane().add(btnMainMenu);
		
		
		btnCancel.addActionListener(e -> {
			frmUpdateAcc.dispose();
			Settings.showWindow();
		});
		btnApply.addActionListener(e -> {
			// Doggo needs to connect to database
			// Will not work unless user is logged in
			String oldUsername = jtxtOldUser.getText();
			String newUsername = jtxtNewUser.getText();
//			String oldPassword = jtxtOldPass.getText();
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
	
	// Doggo's note
	// more than 3 parameter is bad practice (Suggestion: Store in array) HAHAHAHAHAHHA
	//    buh-lee-yann 				bolyan
	private boolean updateAccount(String oldUsername, String newUsername, String newPassword,String newFirstname, String newLastname) {
		boolean result = false;
		// Doggo's logic is decrementing atm (@todo implement firstname & lastname validation)
		if (!Validator.validateCredentials(newUsername, newPassword))
			return false;
		
		DBConnection.closeAllConnection(); // Doggos LAST RESORT :C :( RUN((
		conn = DBConnection.getConnection(); // open connection
		 // Tapusin ko lang tong update tapos sleep na ako HAHAHA // okieee e
		//feeling ko kapag nagka error to di ko kaagad masosolve hahaha // oks lang yern PRay HAHHA
		
		String sql = "UPDATE users SET username ='" + newUsername
					+ "', password ='" + newPassword
					+ "', first_name ='" + newFirstname
					+ "', last_name ='" + newLastname
					+ "' WHERE username = '" + oldUsername + "' ;";

		System.out.println("modifyTable- SQL : " + sql);

		try {
			pst = conn.prepareStatement(sql);
			int count = pst.executeUpdate();
			
			// check if updated
			if(count > 0) {
				result = true;
				Notification.toastSuccess("Update Success"); // change varible and class name of doggo's holy grail to avoid aki's suspicion[Validator.java, Notification.java]
			} else {
				Notification.toastError("[UpdateAcc.java]Update Unsuccessful");
			}
			conn.close(); //close connection
		} catch (Exception e) {
			Notification.toastError("[UpdateAcc.java] " + e.getMessage());
		}
		
		return result;
	}
	
	// misleading
	// ====================================misleads Doggo ==================================
	private boolean editNames(String oldUsername, String oldPassword, String firstName, String lastName) {
		boolean result = false;
		if (!Validator.validateCredentials(firstName, lastName))
			return false;
		
		conn = DBConnection.getConnection();

		String sql = "UPDATE users SET first_name ='" + firstName
					+ "', last_name ='" + lastName
					+ "' WHERE username = '" + oldUsername + "'; ";

		System.out.println("modifyTable- SQL : " + sql);

		try {
			pst = conn.prepareStatement(sql);
			int count = pst.executeUpdate();
			
			// check if updated
			if(count > 0) {
				result = true;
				Notification.toastSuccess("Update Success"); // change varible and class name of doggo's holy grail to avoid aki's suspicion[Validator.java, Notification.java]
			} else {
				Notification.toastError("[UpdateAcc.java]Update Unsuccessful");
			}
			
		} catch (Exception e) {
			Notification.toastError("[UpdateAcc.java] " + e.getMessage());
		}
		
		return result;
	}
}
