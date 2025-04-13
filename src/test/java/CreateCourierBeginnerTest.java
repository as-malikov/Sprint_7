import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import model.CourierData;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.is;

public class CreateCourierBeginnerTest {
    @Before
    public void init() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @Test
    public void courierCanBeCreatedTest() {
        CourierData courierData = new CourierData("asdasdddjj", "ads", "asda");

        ValidatableResponse response =
                given()
                        .log().all()
                        .header("Content-type", "Application/json")
                        .and()
                        .body(courierData)
                        .when()
                        .post("/api/v1/courier")
                        .then();

        response.assertThat()
                .statusCode(SC_OK)
                .body("ok", is(true));
    }
}
