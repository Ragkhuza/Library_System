import javax.swing.JFrame;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

public class Login {
	Connection conn = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	private JFrame frmLogin;
	private JTextField jtxtUsername;
	private JPasswordField jtxtPassword;

	// main
	public static void main(String[] args) {
		new Login();
	}

	public Login() {
		
		refreshTable();
		frmLogin = new JFrame();
		frmLogin.setTitle("Login");
		frmLogin.setBounds(100, 100, 315, 179);
		frmLogin.setLayout(null);
		frmLogin.setLocationRelativeTo(null);
		frmLogin.setResizable(false);
		frmLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// declaration of elements
		JLabel lblUsername = new JLabel("Username");
		JLabel lblPassword = new JLabel("Password");
		
		jtxtUsername = new JTextField();
		jtxtPassword = new JPasswordField();
		
		JButton btnLogin = new JButton("Login");
		JButton btnRegister = new JButton("Register");
		
		// positioning of elements
		lblUsername.setBounds(30, 29, 81, 14);
		lblPassword.setBounds(30, 65, 81, 14);
		
		jtxtUsername.setBounds(121, 24, 150, 25);
		jtxtPassword.setBounds(121, 60, 150, 25);
		
		btnLogin.setBounds(54, 97, 89, 23);
		btnRegister.setBounds(155, 97, 89, 23);
		
		// displaying of elements
		frmLogin.add(lblUsername);
		frmLogin.add(jtxtUsername);		
		frmLogin.add(lblPassword);
		frmLogin.add(jtxtPassword);
				
		frmLogin.add(btnLogin);
		frmLogin.add(btnRegister);
		frmLogin.setVisible(true);
		
		// button functions!
		
		btnLogin.addActionListener(e -> { // login button

			/* @TODO @DOGGO DISABLE LOGIN AUTHENTICATION FOR NOW*/
			/* @TODO DONT DELETE THIS BLOCK */
			/*String username = jtxtUsername.getText();
			@SuppressWarnings("deprecation")
			String password = jtxtPassword.getText();
			
			if(!Validator.validateCredentials(username, password)) {
				Validator.displayError();
				return;
			}
		
			if(!AdminData.authenticate(username, password)) return;
			
			NotificationManager.Success("Login successful");*/
			
			frmLogin.dispose(); // to close the login system
			new MainWindow(); // Start the main jFrame
		});

		
		btnRegister.addActionListener(e -> { // register button
			frmLogin.dispose();
 			new Register();
		});
		
	}
	
	public void refreshTable() {
		if(conn != null) {

			String sql = "SELECT username, password, First_name, last_name FROM Users";
			System.out.println("refreshTable- SQL : " + sql);

			try {
				pst = conn.prepareStatement(sql);
				rs = pst.executeQuery();
			} catch (Exception e) {
				NotificationManager.Warning("[refreshTable] " + e.getMessage());
			}
		}

	}
	
}
