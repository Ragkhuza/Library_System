public final class CredentialData {
	private static int userLoginID;
	private static String username;
	private static String email;
	private static String password;
	private static String suffix;
	private static String firstName;
	private static String middleName;
	private static String lastName;
	private static String houseNum;
	private static String street;
	private static String barangay;
	private static String city;
	private static String postalCode;
	private static String userType;

	public static String getField(String field) {
		if (field.toLowerCase().equals("username"))
			return getUsername();
		 else if (field.toLowerCase().equals("email"))
			return getEmail();
		 else if (field.toLowerCase().equals("userpassword"))
			return getPassword();
		 else if (field.toLowerCase().equals("suffix"))
			return getSuffix();
		 else if (field.toLowerCase().equals("firstname"))
			return getFirstName();
		 else if (field.toLowerCase().equals("middlename"))
			return getMiddleName();
		 else if (field.toLowerCase().equals("lastname"))
			return getLastName();
		 else if (field.toLowerCase().equals("housenum"))
			return getHouseNum();
		 else if (field.toLowerCase().equals("street"))
			return getStreet();
		 else if (field.toLowerCase().equals("barangay"))
			return getBarangay();
		 else if (field.toLowerCase().equals("city"))
			return getCity();
		 else if (field.toLowerCase().equals("postalcode"))
			return getPostalCode();
		 else
			return "[CREDENTIALDATA.JAVA] ERROR";
	}

	public static int getUserLoginID() {
		return userLoginID;
	}

	public static void setUserLoginID(int userLoginID) {
		CredentialData.userLoginID = userLoginID;
	}

	public static String getUserType() {
		return userType;
	}

	public static void setUserType(String userType) {
		CredentialData.userType = userType;
	}

	public static String getUsername() {
		return username;
	}

	public static void setUsername(String username) {
		CredentialData.username = username;
	}

	public static String getEmail() {
		return email;
	}

	public static void setEmail(String email) {
		CredentialData.email = email;
	}

	public static String getPassword() {
		return password;
	}

	public static void setPassword(String password) {
		CredentialData.password = password;
	}

	public static String getSuffix() {
		return suffix;
	}

	public static void setSuffix(String suffix) {
		CredentialData.suffix = suffix;
	}

	public static String getFirstName() {
		return firstName;
	}

	public static void setFirstName(String firstName) {
		CredentialData.firstName = firstName;
	}

	public static String getMiddleName() {
		return middleName;
	}

	public static void setMiddleName(String middleName) {
		CredentialData.middleName = middleName;
	}

	public static String getLastName() {
		return lastName;
	}

	public static void setLastName(String lastName) {
		CredentialData.lastName = lastName;
	}

	public static String getHouseNum() {
		return houseNum;
	}

	public static void setHouseNum(String houseNum) {
		CredentialData.houseNum = houseNum;
	}

	public static String getStreet() {
		return street;
	}

	public static void setStreet(String street) {
		CredentialData.street = street;
	}

	public static String getBarangay() {
		return barangay;
	}

	public static void setBarangay(String barangay) {
		CredentialData.barangay = barangay;
	}

	public static String getCity() {
		return city;
	}

	public static void setCity(String city) {
		CredentialData.city = city;
	}

	public static String getPostalCode() {
		return postalCode;
	}

	public static void setPostalCode(String postalCode) {
		CredentialData.postalCode = postalCode;
	}
}
