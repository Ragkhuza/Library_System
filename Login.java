package finals;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Login extends JFrame implements ActionListener {

	public static void main(String args[]) {
		Login login = new Login();
		login.setVisible(true);
		login.setSize(400,200);
		login.setLocationRelativeTo(null);
	}

	JLabel usernameLabel, passwordLabel;
	JTextField usernameField;
	JPasswordField passwordField;

	JButton loginButton;
	JButton cancelButton;
	AdminData admindata = new AdminData();

	public Login() {
		super("Login");
		setLayout(new GridLayout(3,2));

		usernameLabel = new JLabel("Username:     ");
		passwordLabel = new JLabel("Password:     ");

		usernameField = new JTextField();
		passwordField = new JPasswordField();

		loginButton = new JButton("LOGIN");
		cancelButton = new JButton("CANCEL");

		add(usernameLabel);
		usernameLabel.setHorizontalAlignment(JLabel.RIGHT);
		add(usernameField);

		add(passwordLabel);
		passwordLabel.setHorizontalAlignment(JLabel.RIGHT);
		add(passwordField);

		add(loginButton);
		add(cancelButton);

		loginButton.addActionListener(this);
		cancelButton.addActionListener(this);

	}

	public static boolean checkCharacters(String p) {

		String specialCharacters = " !#$%'()*+,-./:;<=>?@[]^_`{|}~";
		String[] charArray = new String[p.length()];
		for(int i = 0; i < p.length(); i++) {
			charArray[i] = Character
					.toString(p.charAt(i));
		}

		int count = 0;
		for(int i = 0; i < charArray.length; i++) {
			if(specialCharacters.contains(charArray[i])) {
				count++;
			}
		}

		if(p != null && count == 0)
			return false;
		else
			return true;

	}


	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == cancelButton) {
			int response = JOptionPane.showConfirmDialog(rootPane,
					"Are you sure you want to exit the program?",
					"Confirm",
					JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE);

			if(response == 0) {
				this.dispose();
			}

		}

		else {
			if(e.getSource() == loginButton) {
				String username = usernameField.getText();
				@SuppressWarnings("deprecation")
				String password = passwordField.getText();

				@SuppressWarnings("deprecation")
				int passLength = passwordField.getText().length();
				
				if(!Validator.validateCredentials(username, password)) {
					Validator.displayError();
					return;
				}
				
				// OMG DOGGO NEVER RUN THE CODE
				// @DOGGO did waste time
				// AHAHAHAHA it oki doggoRUN RUN RUN
				// may error paaaa
			
				if(!AdminData.authenticate(username, password)) return;
				
				Notification.toastSuccess("magic of doggo");
				
				this.dispose(); // to close the login system
				MainWindow mw = new MainWindow();
				mw.openMainWindow();
				
			}
		}

	}
}
