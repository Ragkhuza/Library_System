package finals;

import javax.swing.JOptionPane;

public final class Notification {
// wait lang bawal mag copy paste? galing sa akin?
	// ang alam ko pwede
	
	// doggos holy grail part 
    static void toastMessage(String title, String msg) {
        JOptionPane.showMessageDialog(null, msg, title, JOptionPane.INFORMATION_MESSAGE);
    }

    static void toastSuccess(String msg) {
        JOptionPane.showMessageDialog(null, msg, "Success!", JOptionPane.INFORMATION_MESSAGE);
    }

    static void toastWarning(String msg) {
        JOptionPane.showMessageDialog(null, msg, "Warning!", JOptionPane.WARNING_MESSAGE);
    }

    static void toastError(String msg) {
        JOptionPane.showMessageDialog(null, msg, "ERROR!", JOptionPane.ERROR_MESSAGE);
    }

    static void toastError(String title, String msg) {
        JOptionPane.showMessageDialog(null, msg, title, JOptionPane.ERROR_MESSAGE);
    }
	

}
