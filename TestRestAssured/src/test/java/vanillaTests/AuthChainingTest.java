package vanillaTests;

import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class AuthChainingTest {
    private final String BASE_URL = "https://reqres.in/api";

    @Test(enabled = false)
    public void loginAndUseTokenForPost() {
        // Step 1: Login and extract token
        JSONObject loginBody = new JSONObject();
        loginBody.put("email", "eve.holt@reqres.in");
        loginBody.put("password", "cityslicka");

        Response loginResponse = given()
                .baseUri(BASE_URL)
                .header("Content-Type", "application/json")
                .body(loginBody.toString())
                .when()
                .post("/login")
                .then()
                .log().all()
                .statusCode(200)
                .extract().response();

        String token = loginResponse.jsonPath().getString("token");
        System.out.println("🔐 Extracted Token: " + token);

        // Step 2: Use token in POST /users
        JSONObject userBody = new JSONObject();
        userBody.put("name", "Somu QA");
        userBody.put("job", "Test Architect");

        Response userResponse = given()
                .baseUri(BASE_URL)
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token) // simulate token usage
                .body(userBody.toString())
                .when()
                .post("/users")
                .then()
                .log().all()
                .statusCode(201)
                .body("name", equalTo("Somu QA"))
                .body("job", equalTo("Test Architect"))
                .extract().response();

        String userId = userResponse.jsonPath().getString("id");
        System.out.println("👤 User created with ID: " + userId);
    }
}
