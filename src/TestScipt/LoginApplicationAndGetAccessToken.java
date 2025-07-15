package TestScipt;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.File;
import java.util.ArrayList;

import org.testng.Assert;

import HelperMethods.Jpath;
import HelperMethods.LoginCredentials;
import HelperMethods.SpecBuilderClass;
import PojoClasses.CreateOrderPOJO;
import PojoClasses.LoginResponsePOJO;
import PojoClasses.OrdersPOJO;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class LoginApplicationAndGetAccessToken {

	public static void main(String[] args) {

		
		System.out.println("******************************** Login Application ***********************************");
		
		LoginResponsePOJO response = given().spec(SpecBuilderClass.RequestClass()).body(LoginCredentials.LoginCred())
				.when().post("api/ecom/auth/login")
				.then().spec(SpecBuilderClass.ResponseClass()).body("message", equalTo("Login Successfully")).extract().response().as(LoginResponsePOJO.class);

		String Token=response.getToken();
		String UserId=response.getUserId();

		System.out.println("Token :-" +Token);
		System.out.println("UserId :-" +UserId);
		
		System.out.println("******************************** Create Product ***********************************");
		
		RequestSpecification req=new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com/").addHeader("Authorization",Token).build();
		String respon = given().spec(req)
		.param("productName", "qwerty")
		.param("productAddedBy",UserId)
		.param("productCategory", "fashion")
		.param("productSubCategory", "shirts")
		.param("productPrice", "11500")
		.param("productDescription", "Addias Originals")
		.param("productFor", "women")
		.multiPart("productImage", new File("D:\\GaneshNaik\\Qwerty-min.png"))
		
		.when().post("api/ecom/product/add-product")
		.then().log().all().statusCode(201).body("message", equalTo("Product Added Successfully")).extract().response().asString();
		
		JsonPath JS01 = Jpath.JP(respon);
		String productId = JS01.get("productId");
		System.out.println("productId :-"+productId);
		
		System.out.println("******************************** Create Order ***********************************");
		
		OrdersPOJO op=new OrdersPOJO();
		op.setCountry("India");
		op.setProductOrderedId(productId);
		
		ArrayList<OrdersPOJO> OrderDetails=new ArrayList<OrdersPOJO>();
		OrderDetails.add(op);
		
		CreateOrderPOJO CRorder=new CreateOrderPOJO();
		CRorder.setOrders(OrderDetails);
		
		 RequestSpecification req01 = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com/").addHeader("Authorization",Token).setContentType(ContentType.JSON).build();
		
		 RequestSpecification req001 = given().spec(req01).body(CRorder);
		
		String CreateOrderResponse = req001.when().post("api/ecom/order/create-order").then().log().all().statusCode(201).body("message", equalTo("Order Placed Successfully")).extract().response().asString();
		
		JsonPath JS = Jpath.JP(CreateOrderResponse);
		String OrderId = JS.get("orders[0]");
		
		System.out.println("OrderId : "+OrderId);
		
		System.out.println("******************************** View Order ***********************************");
	
		 RequestSpecification req011 = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com/").addHeader("Authorization",Token).build();

		String ViewOrderRes = given().spec(req011).queryParam("id", OrderId)
		.when().get("api/ecom/order/get-orders-details")
		.then().log().all().statusCode(200).body("message", equalTo("Orders fetched for customer Successfully")).extract().response().asString();
		
		JsonPath JS012 = Jpath.JP(ViewOrderRes);
		String productOrderedId=JS012.get("data.productOrderedId");
		System.out.println("productOrderedId : "+productOrderedId);
		
		System.out.println("******************************** Delete Order ***********************************");
		
		 RequestSpecification req012 = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com/").addHeader("Authorization",Token).build();

		String DeleteResp = given().spec(req012).pathParam("productOrderedId", productOrderedId)
		
		.when().delete("api/ecom/product/delete-product/{productOrderedId}")
		
		.then().log().all().statusCode(200).extract().response().asString();
		
		JsonPath JS0123 = Jpath.JP(DeleteResp);
		String DeleteMsg=JS0123.get("message");
		
		Assert.assertEquals("Product Deleted Successfully", DeleteMsg);
		
		System.out.println(DeleteMsg);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
