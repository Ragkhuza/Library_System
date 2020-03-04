package finals;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Register {
	// for calling on other classes
	public static void showWindow() {
		Register window = new Register();
		window.frmRegister.setVisible(true);
	}
	
	// main
	public static void main(String[] args) {
		Register window = new Register();
		window.frmRegister.setVisible(true);
	}
	
	Connection conn = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	private JFrame frmRegister;
	private JTextField jtxtUsername;
	private JTextField jtxtFirstName;
	private JTextField jtxtLastName;
	private JPasswordField jtxtPassword;

	public Register() {
		
		frmRegister = new JFrame();
		frmRegister.setTitle("Register");
		frmRegister.setBounds(100, 100, 295, 210);
		frmRegister.setLayout(null);
		frmRegister.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmRegister.setLocationRelativeTo(null);
		frmRegister.setResizable(false);
		
		JLabel lblUsername = new JLabel("Username");
		JLabel lblPassword = new JLabel("Password");
		JLabel lblFirstName = new JLabel("First Name");
		JLabel lblLastName = new JLabel("Last Name");
		
		jtxtUsername = new JTextField();
		jtxtPassword = new JPasswordField();
		jtxtFirstName = new JTextField();
		jtxtLastName = new JTextField();
		
		JButton btnSignUp = new JButton("Sign Up");
		JButton btnCancel = new JButton("Cancel");
		
		lblUsername.setBounds(10, 13, 98, 14);
		lblPassword.setBounds(10, 44, 98, 14);
		lblFirstName.setBounds(10, 74, 98, 14);
		lblLastName.setBounds(10, 104, 98, 14);

		jtxtUsername.setBounds(118, 8, 150, 25);
		jtxtPassword.setBounds(118, 39, 150, 25);
		jtxtFirstName.setBounds(118, 69, 150, 25);
		jtxtLastName.setBounds(118, 99, 150, 25);
		
		btnSignUp.setBounds(39, 135, 89, 23);
		btnCancel.setBounds(147, 135, 89, 23);
		
		
		frmRegister.add(lblUsername);
		frmRegister.add(jtxtUsername);
		frmRegister.add(lblPassword);
		frmRegister.add(jtxtPassword);
		frmRegister.add(lblFirstName);
		frmRegister.add(jtxtFirstName);
		frmRegister.add(lblLastName);
		frmRegister.add(jtxtLastName);
		
		frmRegister.add(btnSignUp);
		frmRegister.add(btnCancel);
		
		// sign up button
		btnSignUp.addActionListener(e -> {
			register();
			
			 // close Register window
			
			// Go back to Login page
			
		});
		
		
		// cancel button
		
		btnCancel.addActionListener(e -> {
			frmRegister.dispose(); // close Register window
			
			// Go back to Login page
			Login.showWindow();
		});
	}
	
	public boolean register(){
		// @DOGGO HAHAHA no validation
		String username = jtxtUsername.getText();
		@SuppressWarnings("deprecation")
		String password = jtxtPassword.getText();
		String first_name = jtxtFirstName.getText();
		String last_name = jtxtLastName.getText();
		
		// @DOGGOS this is the magic
		if( !Checker.validateRegistration(username, password, first_name, last_name) ) {
			Checker.displayError();
			return false;
		}
		// RUN BRIEL RUN
		
		jtxtUsername.setText(username);
		jtxtPassword.setText(password);
		jtxtFirstName.setText(first_name);
		jtxtLastName.setText(last_name);
		
		boolean success = false;

		conn = DBConnection.getConnection();

		if(conn != null) {
			String sql = "INSERT INTO Users (Username, Password, First_name, Last_name) ";
			sql += "VALUES (";
			sql += "'" + username + "',";
			sql += "'" + password + "',";
			sql += "'" + first_name + "',";
			sql += "'" + last_name + "'";
			sql += ")";

			System.out.println("register- SQL : " + sql);
 
			try {
				pst = conn.prepareStatement(sql);
				int count = pst.executeUpdate();
				
				// check if updated
				if(count > 0) {
					success = true;
					Alert.Success("Registered succesfully!");
					
					frmRegister.dispose();
					Login.showWindow();
					
				} else {
					Alert.Error("[UpdateAcc.java]Register unsuccessful");
				}
				conn.close(); //close connection
			} catch (Exception e) {
				Alert.Error("[UpdateAcc.java] " + e.getMessage());
			}
		}
		
		return success;
		};
	
}
