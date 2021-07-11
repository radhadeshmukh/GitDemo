package StepDefinations;

import java.io.IOException;

import io.cucumber.java.Before;

public class Hooks {
@Before("@Deleteplace")
 public  void beforeScenario() throws IOException
 {
	StepDefination stepd = new StepDefination();
	
	// as place_id is static we have to call using class name instead of object of the class
	if(StepDefination.place_id==null)
	{
	stepd.add_place_payload_with("shantanu", "French", "UK");
	stepd.user_call_with_http_request("AddPlaceAPI", "POST");
	stepd.verify_place_id_created_that_maps_to_using_get_place_api("shantanu", "getPlaceAPI");
 }
}
}
