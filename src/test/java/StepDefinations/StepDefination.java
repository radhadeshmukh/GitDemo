package StepDefinations;
import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

import Resources.APIResources;
import Resources.TestDataBuild;
import Resources.Utils;


import java.io.IOException;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class StepDefination extends Utils{
	
	RequestSpecification res;
	Response response;
	 ResponseSpecification respec;
	String Placeid;
	  JsonPath js;
	 static String  place_id;
	 TestDataBuild Data = new TestDataBuild();
	 @Given("Add place Payload with {string} {string} {string}")
	 public void add_place_payload_with(String name, String Language, String address) throws IOException {
		
       
		  res =given().spec(requestSpecification()).body(Data.addplacePayload(name,Language,address));
	}
	 @When("user call {string} with {string} http request")
	 public void user_call_with_http_request(String resource, String httpmethod) {
		
		 
		 //Constructor will called with value of resource which you pass from enum APIResources class
		APIResources ressourceAPI = APIResources.valueOf(resource);
		System.out.println(ressourceAPI.getResource());
		
		
		respec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		
		if(httpmethod.equalsIgnoreCase("POST"))
		  response= res.when().post(ressourceAPI.getResource());
		else if(httpmethod.equalsIgnoreCase("GET"))
	        response= res.when().get(ressourceAPI.getResource());	
		//.then().spec(respec).extract().response();
		
	}
	@Then("API call got success with status {int}")
	public void api_call_got_success_with_status(Integer int1) {

		assertEquals(response.getStatusCode(),200);
	   
	}
	@Then("{string} in response body is {string}")
	public void in_response_body_is(String keyvalue, String Expectedvalue) {

	   assertEquals(getJsonPath(response,keyvalue),Expectedvalue);
	  
	  
	}
	@Then("Verify place_id created that maps to {string} using {string}")
	public void verify_place_id_created_that_maps_to_using_get_place_api(String Expectedname,String resource) throws IOException {
		  place_id=getJsonPath(response,"place_id");
		  res =given().spec(requestSpecification()).queryParam("place_id",place_id);
		  user_call_with_http_request(resource, "GET");
		  String Actualname=getJsonPath(response,"name");
		  assertEquals(Actualname,Expectedname);
	}

	@Given("DeletePlace payload")
	public void delete_place_payload() throws IOException {
		 res =given().spec(requestSpecification()).body(Data.deletePlayload(place_id));
	}

}
