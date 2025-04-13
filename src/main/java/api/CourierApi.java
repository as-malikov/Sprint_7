package api;

import io.restassured.response.ValidatableResponse;
import model.CourierData;

import static io.restassured.RestAssured.given;

public class CourierApi extends RestApi {

    public static final String API_V1_COURIER = "/api/v1/courier";
    public static final String API_V_1_COURIER_LOGIN = "/api/v1/courier/login";

    public ValidatableResponse createCourier(CourierData courier) {
        return given()
                .spec(requestSpecification())
                .and()
                .body(courier)
                .when()
                .post(API_V1_COURIER)
                .then();
    }

    public ValidatableResponse loginCourier(CourierData courier) {
        return given()
                .log().all()
                .header("Content-type", "Application/json")
                .and()
                .body(courier)
                .when()
                .post(API_V_1_COURIER_LOGIN)
                .then();
    }

    public ValidatableResponse deleteCourier(CourierData courier, String id) {
        return given()
                .log().all()
                .header("Content-type", "Application/json")
                .and()
                .body(courier)
                .when()
                .post(API_V1_COURIER + id)
                .then();
    }
}
