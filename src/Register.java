import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Iterator;
import java.util.LinkedHashMap;

public class Register {
	// for calling on other classes
	Connection conn = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	private JFrame frmRegister;
	private JPasswordField jtxtPassword;
	private JTextField[] registerFormTxt;
	private LinkedHashMap fieldsMap = new LinkedHashMap();

	public Register() {
		String[] registerFromStrings = {
				"username",
				"email",
				"password",
				"suffix",
				"firstName",
				"middleName",
				"lastName",
				"houseNum",
				"street",
				"barangay",
				"city",
				"postalCode",
				"userType"
		};

		JLabel[] registerFormLabels = new JLabel[registerFromStrings.length];
		registerFormTxt = new JTextField[registerFromStrings.length];

		frmRegister = new JFrame();
		frmRegister.setTitle("Register");
		frmRegister.setLayout(new GridLayout(14, 1));
		frmRegister.setBounds(100, 100, 295, 400);
		frmRegister.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmRegister.setLocationRelativeTo(null);
		frmRegister.setResizable(false);
		frmRegister.setVisible(true);

		// Create Buttons
		for (int i = 0; i < registerFromStrings.length; i++) {
			// add labels
			registerFormLabels[i] = new JLabel(registerFromStrings[i]);
			frmRegister.add(registerFormLabels[i]);

			// if field is password
			if (registerFromStrings[i].toLowerCase().equals("password")) {
				jtxtPassword = new JPasswordField();
				frmRegister.add(jtxtPassword);
				fieldsMap.put(registerFromStrings[i], jtxtPassword);
			// else if field is normal JText
			} else {
				registerFormTxt[i] = new JTextField();
				frmRegister.add(registerFormTxt[i]);
				fieldsMap.put(registerFromStrings[i], registerFormTxt[i]);
			}
		}

		JButton btnSignUp = new JButton("Sign Up");
		JButton btnCancel = new JButton("Cancel");
		frmRegister.add(btnSignUp);
		frmRegister.add(btnCancel);

		// sign up button
		btnSignUp.addActionListener(e -> {
			// register user
			register();
		});

		// cancel button
		btnCancel.addActionListener(e -> {
			frmRegister.dispose(); // close Register window

			// Go back to Login page
			new Login();
		});
	}

	public boolean register(){
		// @DOGGOS this is the magic
		if( !Validator.validateRegistration(fieldsMap) ) {
			Validator.displayError();
			return false;
		}

		boolean success = false;

		conn = DBConnection.getConnection();

		if(conn != null) {
			String sql = "BEGIN addUser(seq_user_id.nextval,'";

			int i = 0;
			for (Object key : fieldsMap.keySet()) {
				i++;
				String k = (String)key;
				System.out.println("getting " + k + " from hashmap");
				if (!k.toLowerCase().equals("password")) {
					if (i < fieldsMap.size())
						sql += ((JTextField) fieldsMap.get(k)).getText() + "', '";
					else
						sql += ((JTextField) fieldsMap.get(k)).getText() + "'); end;";
				} else {
					sql += ((JPasswordField) fieldsMap.get(k)).getText() + "', '";
				}
			}

			System.out.println("register- SQL : " + sql);

			try {
				pst = conn.prepareStatement(sql);
				int count = pst.executeUpdate();

				// check if updated
				if(count > 0) {
					success = true;
					NotificationManager.Success("Registered succesfully!");

					frmRegister.dispose();
					new Login();

				} else {
					NotificationManager.Error("[UpdateAcc.java]Register unsuccessful");
				}
				conn.close(); //close connection
			} catch (Exception e) {
				NotificationManager.Error("[UpdateAcc.java] " + e.getMessage());
			}
		}

		return success;
		};

}
