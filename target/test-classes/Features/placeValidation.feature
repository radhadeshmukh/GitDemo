Feature: Validating place APIs
@Addplace @Regression
Scenario Outline: Verify if place is being succesfully added usig AddPlaceAPI
Given Add place Payload with "<name>" "<Language>" "<address>"
When user call "AddPlaceAPI" with "POST" http request
Then API call got success with status 200
And "status" in response body is "OK"
And "scope" in response body is "APP"
And Verify place_id created that maps to "<name>" using "getPlaceAPI"

Examples:
    |name|Language|address|
    |RDhouse|Marathi|UK|
    
@Deleteplace @Regression
Scenario: Verify if Delete Place functionality is working
 Given DeletePlace payload
 When user call "deletePlaceAPI" with "POST" http request
 Then API call got success with status 200
 And "status" in response body is "OK"