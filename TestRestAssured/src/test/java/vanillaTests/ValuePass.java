package vanillaTests;

import io.qameta.allure.testng.AllureTestNg;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@Listeners({AllureTestNg.class})
public class ValuePass {

    private final String BASE_URL = "https://jsonplaceholder.typicode.com";

    @Test
    public void getTitleAndUseInPostRequest() {
        System.out.println("Running test...");

        Response getResponse = given()
                .baseUri(BASE_URL)
                .when()
                .get("/posts/1");
        // Print full response
//        System.out.println("Status Code: " + getResponse.getStatusCode());
        System.out.println("Body:\n" + getResponse.getBody().prettyPrint());

        String extractedTitle = getResponse.jsonPath().getString("title");
        System.out.println("Extracted title: " + extractedTitle);


        // Step 2: Use that title in a POST request
        JSONObject requestBody = new JSONObject();
        requestBody.put("title", extractedTitle);
        requestBody.put("body", "This is the reused title post");
        requestBody.put("userId", 99);

        Response postResponse = given()
                .baseUri(BASE_URL)
                .header("Content-type", "application/json")
                .body(requestBody.toString())
                .log().all()
                .when()
                .post("/posts")
                .then()
                .log().all()
                .statusCode(201)
                .body("title", equalTo(extractedTitle))
                .extract().response();

        System.out.println("POST response ID: " + postResponse.jsonPath().getInt("id"));
    }
}
