import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JSeparator;

public class WindowUpdateAccount {
	Connection conn = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	private JFrame frmUpdateAcc;
	private JTextField updateAccTxts[];

	String[] updateFormStrings = {
			"username",
			"email",
			"userpassword",
			"suffix",
			"firstName",
			"middleName",
			"lastName",
			"houseNum",
			"street",
			"barangay",
			"city",
			"postalCode"
	};

	public WindowUpdateAccount() {
		// frame
		frmUpdateAcc = new JFrame();
		frmUpdateAcc.setTitle("Settings - Update Account");
		frmUpdateAcc.setBounds(100, 100, 387, 382);
		frmUpdateAcc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmUpdateAcc.setLayout(new GridLayout(14,1));
		frmUpdateAcc.setLocationRelativeTo(null);
		frmUpdateAcc.setResizable(false);
		frmUpdateAcc.setVisible(true);
		
		// declaration of elements
		JLabel updateAccLbls[] = new JLabel[updateFormStrings.length];
		updateAccTxts = new JTextField[updateFormStrings.length];

		for (int i = 0; i < updateFormStrings.length; i++) {
			updateAccLbls[i] = new JLabel(updateFormStrings[i]);
			updateAccTxts[i] = new JTextField(CredentialData.getField(updateFormStrings[i]));
			frmUpdateAcc.add(updateAccLbls[i]);
			frmUpdateAcc.add(updateAccTxts[i]);
		}

		JButton btnUpdate = new JButton("Update");
		JButton btnCancel = new JButton("Cancel");
		JButton btnMainMenu = new JButton("Home");

		frmUpdateAcc.add(btnUpdate);
		frmUpdateAcc.add(btnCancel);
		frmUpdateAcc.add(btnMainMenu);
		
		
		btnMainMenu.addActionListener(e -> { 
			frmUpdateAcc.dispose();
 			new WindowLibrarian();
		});
		
		btnCancel.addActionListener(e -> {
			frmUpdateAcc.dispose();
			new WindowSettings();
		});
		
		btnUpdate.addActionListener(e -> {
			updateAccount();
		});
	}

	private boolean updateAccount() {
		boolean result = false;

		// @TODO ADD THIS VALIDATOR LATER ON
		/*if (!Validator.validateCredentials(newUsername, newPassword))
			return false;*/

		conn = DBConnection.getConnection(); // open new connection
		
		String sql = "UPDATE LIBRARYUSERS SET ";

		for (int i = 0; i < updateFormStrings.length; i++) {
			if (!updateFormStrings[i].toLowerCase().equals("username"))
				if (i < (updateFormStrings.length - 1))
					sql += updateFormStrings[i] + " ='" + updateAccTxts[i].getText() + "', ";
				else
					sql += updateFormStrings[i] + " ='" + updateAccTxts[i].getText() + "' ";
		}

		sql += "WHERE USERNAME = '" + updateAccTxts[0].getText() + "'";
		System.out.println("modifyTable- SQL : " + sql);

		try {
			pst = conn.prepareStatement(sql);

			int count = pst.executeUpdate();
			/*@TODO DOGGO UPDATE CREDENTIAL MANAGER AFTER UPDATING THE DATABASE*/

			if(count > 0) {
				result = true;
				NotificationManager.Success("Update success!");
			} else {
				NotificationManager.Error("[WindowUpdateAccount.java]Update Unsuccessful");
			}
		} catch (Exception e) {
			NotificationManager.Error("[WindowUpdateAccount.java] " + e.getMessage());
		}
		
		return result;
	}
}
