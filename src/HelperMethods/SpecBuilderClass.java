package HelperMethods;

import org.testng.annotations.Test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class SpecBuilderClass {

	
	@Test
	public static RequestSpecification RequestClass() {
		
		RequestSpecification req=new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com/").setContentType(ContentType.JSON).build();
		return req;
	}
	
	
	public static ResponseSpecification ResponseClass() {
		
		ResponseSpecification res = new ResponseSpecBuilder().expectStatusCode(200).build();
		return res;
	}
	
	
	
	
	
}
