package finals;

public class Checker {
    protected static String errorMsg = "";
    
    // validate registration fields
    public static boolean validateRegistration(String username, String password, String firstname, String lastname) {
    	if(!validateCredentials(username, password)) return false;
    	if(!validateFirstname(firstname)) return false;
    	if(!validateLastname(lastname)) return false;
    	// RUN BRIELLE RUN!
    	return true;
    }
    
    public static boolean validateFirstname(String name) {
    	if(!isAlpha(name)) {
    		setErrorMsg("Firstname must be alpha characters only.");
    		return false; // must contain alpha characters only
    	}
    	
    	if(!isNotEmpty(name)) {
    		setErrorMsg("Firstname must be alphanumeric");
    		return false; // check if not empty
    	}
    	
    	if(!isValidLength(name, 3)) {
    		setErrorMsg("First name should be 3 or more"); 
    		return false; // minimum allowed characters
    	}
    	
    	return true;
    }
    
    public static boolean validateLastname(String name) {
    	if(!isAlpha(name)) {
    		setErrorMsg("Lastname must be alpha characters only.");
    		return false; // must contain alpha characters only
    	}
    	
    	if(!isNotEmpty(name)) {
    		setErrorMsg("Lastname must be alphanumeric");
    		return false; // check if not empty
    	}
    	
    	if(!isValidLength(name, 3)) {
    		setErrorMsg("Lastname should be 3 or more"); 
    		return false; // minimum allowed characters
    	}
    	
    	return true;
    }
    
    public static boolean validateCredentials(String username, String password) {
        if (!validateUsername(username)) return false;
        if (!validatePassword(password)) return false;

        return true;
    }
    
    public static boolean validateUsername(String username) {
    	if(!isAlphaNumeric(username)) {
            setErrorMsg("Username must be alphanumeric");
            return false;
        }

        if(!isValidLength(username, 3)) {
            setErrorMsg("Username must be at least 3 characters!");
            return false;
        }
    	return true;
    }
    
    public static boolean validatePassword(String password) {
        if(password.length() < 5) {
            setErrorMsg("Password must be at least 5 characters!");
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
        Alert.Error(errorMsg);
    }

    public static void setErrorMsg(String msg) {
        errorMsg = msg;
    }

    public static String getErrorMsg() {
        return errorMsg;
    }

    public static void clearErrorMsg() {
        errorMsg = "";
    }
}
