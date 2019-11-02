package finals;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JButton;

public class Settings {

	private JFrame frmSettings;
	
	public static void showWindow() {
		Settings window = new Settings();
		window.frmSettings.setVisible(true);
	}
	
	// hala asan yung chatttt
// OH NO
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Settings window = new Settings();
					window.frmSettings.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Settings() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSettings = new JFrame();
		frmSettings.setTitle("Settings");
		frmSettings.setBounds(100, 100, 257, 212);
		frmSettings.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSettings.getContentPane().setLayout(null);
		
		JButton btnUpdateAcc = new JButton("Update account");
		// Anonymous/lambda function search mo na lang haha basta function siya na walang variable name
		btnUpdateAcc.addActionListener(e -> { // magsusulat pa sana ako dito HAHAHA
 			frmSettings.dispose();
			UpdateAcc.showWindow();			/// / hEhe OHH HAHAHA Ieexplain ko kasi iyong arrow function HAHAHA di pa ako tapos HAHAHAH
		});
		btnUpdateAcc.setBounds(36, 30, 164, 23);
		frmSettings.getContentPane().add(btnUpdateAcc);
		
		JButton btnDeleteAcc = new JButton("Delete account");
		btnDeleteAcc.addActionListener(e -> { 
// 			frmSettings.dispose();
			//delete
		});
		btnDeleteAcc.setBounds(36, 59, 164, 23);
		frmSettings.getContentPane().add(btnDeleteAcc);
		
		
		JButton btnBack = new JButton("Back to main menu");
		btnBack.addActionListener(e -> { 
 			frmSettings.dispose();
 			MainWindow mw = new MainWindow();
			mw.openMainWindow();
		});
		btnBack.setBounds(36, 88, 164, 23);
		frmSettings.getContentPane().add(btnBack);
		
		JButton btnLogOut = new JButton("Log out");
		// di ko tanda iyong pagka hindi lambda HAHAHA oh no
		btnLogOut.addActionListener(e -> {
					frmSettings.dispose();
			Notification.toastMessage("Message", "Logging out!");
			//logout
			
			Login login = new Login();
			login.setVisible(true);
			login.setSize(400,200);
			login.setLocationRelativeTo(null);
		});
		btnLogOut.setBounds(36, 117, 164, 23);
		frmSettings.getContentPane().add(btnLogOut);

	}
}
