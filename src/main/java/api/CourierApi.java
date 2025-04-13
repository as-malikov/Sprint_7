package api;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import model.CourierCredantials;
import model.CourierData;
import model.CourierDataLombok;
import model.CourierId;

import static io.restassured.RestAssured.given;
import static model.CourierCredantials.getCourierCredantials;

public class CourierApi extends RestApi {

    public static final String API_V1_COURIER = "/api/v1/courier";
    public static final String API_V_1_COURIER_LOGIN = "/api/v1/courier/login";
    public static final String SLASH = "/";

    @Step("Create courier")
    public ValidatableResponse createCourier(CourierData courier) {
        return given()
                .spec(requestSpecification())
                .and()
                .body(courier)
                .when()
                .post(API_V1_COURIER)
                .then();
    }
    @Step("Create courier lombok")
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
    public ValidatableResponse loginCourier(CourierData courier) {
        CourierCredantials courierCredantials = getCourierCredantials(courier);
        return given()
                .spec(requestSpecification())
                .and()
                .body(courierCredantials)
                .when()
                .post(API_V_1_COURIER_LOGIN)
                .then();
    }

    @Step("Delete courier")
    public ValidatableResponse deleteCourier(CourierId courierId) {
        return given()
                .spec(requestSpecification())
                .and()
                .body(courierId)
                .when()
                .delete(API_V1_COURIER + SLASH + courierId.getId())
                .then();
    }
}
