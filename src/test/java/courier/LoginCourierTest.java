package courier;

import api.CourierApi;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import model.courier.CourierDataLombok;
import model.courier.CourierIdLombok;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static util.CourierGenerator.NUMBER_OF_CHARACTERS;
import static util.CourierGenerator.getRandomCourierLombok;

public class LoginCourierTest {
    private static CourierDataLombok courierDataLombok;
    private static CourierApi courierApi;
    private static CourierIdLombok courierIdLombok;

    @Before
    public void init() {
        courierDataLombok = getRandomCourierLombok();
        courierApi = new CourierApi();
        courierIdLombok = new CourierIdLombok();
        courierApi.createCourierLombok(courierDataLombok);
    }

    @DisplayName("Check courier can be login")
    @Test
    public void courierCanBeLoggedIn() {
        ValidatableResponse loginCourierResponse = courierApi.loginCourier(courierDataLombok);

        loginCourierResponse.log().all()
                .assertThat()
                .statusCode(SC_OK)
                .body("id", notNullValue());

        courierIdLombok.setId(loginCourierResponse.extract().jsonPath().getString("id"));
    }

    @DisplayName("The courier cannot be login with incorrect name.")
    @Test
    public void courierCanNotBeLoginWithBadNameTest() {
        CourierDataLombok courierWithoutPassword =
                new CourierDataLombok(RandomStringUtils.randomAlphabetic(NUMBER_OF_CHARACTERS),
                        courierDataLombok.getLogin(),null);
        ValidatableResponse loginCourierWithoutPasswordResponse = courierApi.loginCourier(courierWithoutPassword);

        loginCourierWithoutPasswordResponse.log().all()
                .assertThat()
                .statusCode(SC_NOT_FOUND)
                .body("message", is("Учетная запись не найдена"));
    }

    @DisplayName("The courier cannot be login with incorrect password.")
    @Test
    public void courierCanNotBeLoginWithBadPasswordTest() {
        CourierDataLombok courierWithoutPassword = new CourierDataLombok(courierDataLombok.getLogin(),
                RandomStringUtils.randomAlphabetic(NUMBER_OF_CHARACTERS),null);
        ValidatableResponse loginCourierWithoutPasswordResponse = courierApi.loginCourier(courierWithoutPassword);

        loginCourierWithoutPasswordResponse.log().all()
                .assertThat()
                .statusCode(SC_NOT_FOUND)
                .body("message", is("Учетная запись не найдена"));
    }

    @DisplayName("The courier cannot be login without password.")
    @Test
    public void courierCanNotBeLoginWithoutPasswordTest() {
        CourierDataLombok courierWithoutPassword = new CourierDataLombok(
                courierDataLombok.getLogin(), null,null);
        ValidatableResponse loginCourierWithoutPasswordResponse = courierApi.loginCourier(courierWithoutPassword);

        loginCourierWithoutPasswordResponse.log().all()
                .assertThat()
                .statusCode(SC_GATEWAY_TIMEOUT);
    }

    @After
    @Step("Login for get id courier and delete him")
    public void deleteCourier() {
        CourierIdLombok courierIdLombok = new CourierIdLombok();

        ValidatableResponse loginCourierResponse = courierApi.loginCourier(courierDataLombok);
        if (loginCourierResponse.extract().statusCode() == SC_OK) {
            loginCourierResponse.log().all()
                    .assertThat()
                    .body("id", notNullValue());
            courierIdLombok.setId(loginCourierResponse.extract().jsonPath().getString("id"));

            ValidatableResponse deleteCourierResponse = courierApi.deleteCourier(courierIdLombok);
            deleteCourierResponse.log().all()
                    .assertThat()
                    .statusCode(SC_OK)
                    .body("ok", is(true));
        }
    }
}
