package vanillaTests;

import io.qameta.allure.testng.AllureTestNg;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertEquals;

@Listeners({AllureTestNg.class})
public class UsersTest {

    private final String BASE_URL = "https://jsonplaceholder.typicode.com";

    //@Test
    public void getAllUsers() {
        given()
                .baseUri(BASE_URL)
                .when()
                .get("/users")
                .then()
                .assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("size()", equalTo(10));
    }

    @Test
    public void getUserById() {
        given()
                .baseUri(BASE_URL)
                .when()
                .get("/users/1")
                .then()
                .assertThat()
                .statusCode(200)
                .body("id", equalTo(1));
    }

    @Test
    public void getUserNotFound() {
        given()
                .baseUri(BASE_URL)
                .when()
                .get("/users/999")
                .then()
                .assertThat()
                .statusCode(404);
    }

    @Test
    public void getUserWithLogging() {
        given()
                .baseUri(BASE_URL)
                .log().all() // Log request
                .when()
                .get("/users/1")
                .then()
                .log().all() // Log response
                .statusCode(200);
    }

    @Test
    public void getUserByIdPrint() {
        Response response = given()
                .baseUri(BASE_URL)
                .when()
                .get("/users/1");

        // Print full response
        System.out.println("Status Code: " + response.getStatusCode());
        System.out.println("Headers:\n" + response.getHeaders());
        System.out.println("Body:\n" + response.getBody().prettyPrint());

        // Basic assertions
        response.then()
                .statusCode(200)
                .body("id", equalTo(1));
    }

    @Test
    public void assertIndividualFields() {
        given()
                .baseUri(BASE_URL)
                .when()
                .get("/users/1")
                .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("name", equalTo("Leanne Graham"))
                .body("username", equalTo("Bret"))
                .body("email", equalTo("Sincere@april.biz"))
                .body("address.city", equalTo("Gwenborough"))
                .body("address.geo.lat", equalTo("-37.3159"))
                .body("company.name", equalTo("Romaguera-Crona"));
    }

    @Test
    public void assertUsingJsonPath() {
        Response response = given()
                .baseUri(BASE_URL)
                .when()
                .get("/users/1");
        JsonPath jsonPath = response.jsonPath();

        assertEquals(jsonPath.getInt("id"), 1);
        assertEquals(jsonPath.getString("email"), "Sincere@april.biz");
        assertEquals(jsonPath.getString("address.geo.lat"), "-37.3159");

    }
}
