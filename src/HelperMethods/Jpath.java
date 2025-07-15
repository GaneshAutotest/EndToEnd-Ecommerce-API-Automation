package HelperMethods;

import io.restassured.path.json.JsonPath;

public class Jpath {

	public static JsonPath JP(String Response) {
		
		return new JsonPath(Response);
	}
	
}
