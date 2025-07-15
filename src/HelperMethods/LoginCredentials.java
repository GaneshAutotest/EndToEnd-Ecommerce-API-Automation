package HelperMethods;

import org.testng.annotations.Test;

public class LoginCredentials {

	@Test
	public static String LoginCred() {
		
		return ("{\r\n"
				+ "    \"userEmail\": \"ganeshnaik638@gmail.com\",\r\n"
				+ "    \"userPassword\": \"Kiran@1210\"\r\n"
				+ "}");
		
	}
	
}
