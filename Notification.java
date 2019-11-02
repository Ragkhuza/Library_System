package finals;

import javax.swing.JOptionPane;

public final class Notification {
	
    static void Message(String title, String msg) {
        JOptionPane.showMessageDialog(null, msg, title, JOptionPane.INFORMATION_MESSAGE);
    }

    static void Success(String msg) {
        JOptionPane.showMessageDialog(null, msg, "Success!", JOptionPane.INFORMATION_MESSAGE);
    }

    static void Warning(String msg) {
        JOptionPane.showMessageDialog(null, msg, "Warning!", JOptionPane.WARNING_MESSAGE);
    }

    static void Error(String msg) {
        JOptionPane.showMessageDialog(null, msg, "ERROR!", JOptionPane.ERROR_MESSAGE);
    }

    static void Error(String title, String msg) {
        JOptionPane.showMessageDialog(null, msg, title, JOptionPane.ERROR_MESSAGE);
    }

}
