public final class CredentialData {
	private static String username;
	private static String password;
	private static String firstname;
	private static String lastname;
	private static String role;

	public static String getUsername() {
		return username;
	}

	public static void setUsername(String username) {
		CredentialData.username = username;
	}

	public static String getPassword() {
		return password;
	}

	public static void setPassword(String password) {
		CredentialData.password = password;
	}

	public static String getFirstname() {
		return firstname;
	}

	public static void setFirstname(String firstname) {
		CredentialData.firstname = firstname;
	}

	public static String getLastname() {
		return lastname;
	}

	public static void setLastname(String lastname) {
		CredentialData.lastname = lastname;
	}

	public static String getRole() {
		return role;
	}

	public static void setRole(String role) {
		CredentialData.role = role;
	}
}
