package finals;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

public class Login {

	private JFrame frmLogin;
	private JTextField jtxtUsername;
	private JPasswordField jtxtPassword;
	
	public static void showWindow() {
		Login window = new Login();
		window.frmLogin.setVisible(true);
	}
	
	public static void main(String[] args) {
		Login window = new Login();
		window.frmLogin.setVisible(true);
	}

	public Login() {
		frmLogin = new JFrame();
		frmLogin.setTitle("Login");
		frmLogin.setBounds(100, 100, 315, 179);
		frmLogin.setLayout(null);
		frmLogin.setLocationRelativeTo(null);
		
		JLabel lblUsername = new JLabel("Username");
		JLabel lblPassword = new JLabel("Password");
		
		jtxtUsername = new JTextField();
		jtxtPassword = new JPasswordField();
		
		JButton btnLogin = new JButton("Login");
		JButton btnRegister = new JButton("Register");
		
		lblUsername.setBounds(30, 29, 81, 14);
		lblPassword.setBounds(30, 65, 81, 14);
		
		jtxtUsername.setBounds(121, 24, 150, 25);
		jtxtPassword.setBounds(121, 60, 150, 25);
		
		btnLogin.setBounds(54, 97, 89, 23);
		btnRegister.setBounds(155, 97, 89, 23);
		
		frmLogin.add(lblUsername);
		frmLogin.add(jtxtUsername);		
		frmLogin.add(lblPassword);
		frmLogin.add(jtxtPassword);
				
		frmLogin.add(btnLogin);
		frmLogin.add(btnRegister);
		
		btnLogin.addActionListener(e -> {
			String username = jtxtUsername.getText();
			@SuppressWarnings("deprecation")
			String password = jtxtPassword.getText();
			
			if(!Validator.validateCredentials(username, password)) {
				Validator.displayError();
				return;
			}
		
			if(!AdminData.authenticate(username, password)) return;
			
			Notification.Success("Login successuful");
			
			frmLogin.dispose(); // to close the login system
			MainWindow mw = new MainWindow();
			mw.openMainWindow();
		});
		
	}
	
}
