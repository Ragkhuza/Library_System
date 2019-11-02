package finals;

// doggos holy grail part 2
public class Validator {
    protected static String errorMsg = "";
    
    public static boolean validateCredentials(String username, String password) {
        if (!validateUsername(username)) return false;
        if (!validatePassword(password)) return false;

        return true;
    }
    
    public static boolean validateUsername(String username) {
    	if(!isAlphaNumeric(username)) {
            setErrorMsg("Username must be alphanumeric!");
            return false;
        }

        if(!isValidLength(username, 3)) {
            setErrorMsg("User Name must be at least 3 characters");
            return false;
        }
    	return true;
    }
    
    public static boolean validatePassword(String password) {
        if(password.length() < 5) {
            setErrorMsg("Password must be at least 5 characters");
            return false;
        }
        return true;
    }

    public static boolean isValidLength(String str, int minLength) {
        return str.length() >= minLength;
    }

    public static boolean isNotEmpty(String str) {
        return !str.isEmpty();
    }

    public static boolean isAlpha(String str) {
        return str.matches("^[a-zA-Z]+$");
    }

    public static boolean isAlphaNumeric(String str) {
        return str.matches("^[a-zA-Z0-9]+$");
    }

    public static boolean isNumeric(String str) {
        return str.matches("^[0-9]+$");
    }

    public static void displayError() {
        Notification.toastError(errorMsg);
        
        // CJ LILIGO LANG AKO AHAHAHAHAHAHAHAHAHA
        // EWWWW DI KA PAPALA NALILIG
//         YUCKKK
//         HOY NALIGO AKO
//         STRESSED LANG
//         KAILANGAN KO ULIT AHHAHAHAHAHA
        // WOW NAHIYA TULOY AKO BIGLA HAHAH
        // JOKE LANG GO ON AHAHHAHAHA
        // I WATCH U
        // Dont worry hahaha alam ko namang HAHAHAHA sige
    }

    public static void setErrorMsg(String msg) {
        errorMsg = msg;
    }

    public static  String getErrorMsg() {
        return errorMsg;
    }

    public static void clearErrorMsg() {
        errorMsg = "";
    }
}
