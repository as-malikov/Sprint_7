package api;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import model.courier.CourierCredantialsLombok;
import model.CourierData;
import model.courier.CourierDataLombok;
import model.courier.CourierIdLombok;

import static io.restassured.RestAssured.given;
import static model.courier.CourierCredantialsLombok.getCourierCredantials;

public class CourierApi extends RestApi {

    public static final String API_V1_COURIER = "/api/v1/courier";
    public static final String API_V_1_COURIER_LOGIN = "/api/v1/courier/login";
    public static final String SLASH = "/";

    @Step("Create courier")
    public ValidatableResponse createCourierLombok(CourierDataLombok courier) {
        return given()
                .spec(requestSpecification())
                .and()
                .body(courier)
                .when()
                .post(API_V1_COURIER)
                .then();
    }

    @Step("Login courier")
    public ValidatableResponse loginCourier(CourierDataLombok courier) {
        CourierCredantialsLombok courierCredantialsLombok = getCourierCredantials(courier);
        return given()
                .spec(requestSpecification())
                .and()
                .body(courierCredantialsLombok)
                .when()
                .post(API_V_1_COURIER_LOGIN)
                .then();
    }

    @Step("Delete courier")
    public ValidatableResponse deleteCourier(CourierIdLombok courierIdLombok) {
        return given()
                .spec(requestSpecification())
                .and()
                .body(courierIdLombok)
                .when()
                .delete(API_V1_COURIER + SLASH + courierIdLombok.getId())
                .then();
    }
}
