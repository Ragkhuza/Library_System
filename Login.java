package finals;

import javax.swing.JFrame;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

public class Login {
	// for calling on other classes
	public static void showWindow() {
		Login window = new Login();
		window.frmLogin.setVisible(true);
	}
	
	// main
	public static void main(String[] args) {
		Login window = new Login();
		window.frmLogin.setVisible(true); // shiternet
		window.frmLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // @BRIELLE Lagay mo to sa lahat ng nag oopen ng JFrame para mawal iyong lock :) okiii
		// may code na pang close wait lang tinitingnan ko iyong fcode ko
		// may code to na pang exit
		// PAANO YUNG THING PARA AUTOMATIC MAGCLOSE
		// NAIINIS NA AKO SA LOCKING
	}
	
	Connection conn = null;
	PreparedStatement pst = null;
	ResultSet rs = null;	

	private JFrame frmLogin;
	private JTextField jtxtUsername;
	private JPasswordField jtxtPassword;

	public Login() {
		
		refreshTable();
		frmLogin = new JFrame();
		frmLogin.setTitle("Login");
		frmLogin.setBounds(100, 100, 315, 179);
		frmLogin.setLayout(null);
		frmLogin.setLocationRelativeTo(null);
		frmLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
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
			
			if(!Checker.validateCredentials(username, password)) {
				Checker.displayError();
				return;
			}
		
			if(!AdminData.authenticate(username, password)) return;
			
			Alert.Success("Login successful");
			
			frmLogin.dispose(); // to close the login system
			MainWindow mw = new MainWindow();
			mw.openMainWindow();
		});
		
		btnRegister.addActionListener(e -> { 
			frmLogin.dispose();
 			Register.showWindow();
		});
		
	}
	
	public void refreshTable() {
//		conn = DBConnection.getConnection();

		if(conn != null) {

			String sql = "SELECT username, password, First_name, last_name FROM Users";
			System.out.println("refreshTable- SQL : " + sql);

			try {
				pst = conn.prepareStatement(sql);
				rs = pst.executeQuery();
			}

			catch (Exception e) {
				Alert.Warning("[refreshTable] " + e.getMessage());
			}
		}

	}
	
}
