package courier;

import api.CourierApi;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import model.courier.CourierData;
import model.courier.CourierId;
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
    private static CourierData courierData;
    private static CourierApi courierApi;
    private static CourierId courierId;

    @Before
    public void init() {
        courierData = getRandomCourierLombok();
        courierApi = new CourierApi();
        courierId = new CourierId();
        courierApi.createCourierLombok(courierData);
    }

    @DisplayName("Check courier can be login")
    @Test
    public void courierCanBeLoggedIn() {
        ValidatableResponse loginCourierResponse = courierApi.loginCourier(courierData);

        loginCourierResponse.log().all()
                .assertThat()
                .statusCode(SC_OK)
                .body("id", notNullValue());

        courierId.setId(loginCourierResponse.extract().jsonPath().getInt("id"));
    }

    @DisplayName("The courier cannot be login with incorrect name.")
    @Test
    public void courierCanNotBeLoginWithBadNameTest() {
        CourierData courierWithoutPassword =
                new CourierData(RandomStringUtils.randomAlphabetic(NUMBER_OF_CHARACTERS),
                        courierData.getLogin(),null);
        ValidatableResponse loginCourierWithoutPasswordResponse = courierApi.loginCourier(courierWithoutPassword);

        loginCourierWithoutPasswordResponse.log().all()
                .assertThat()
                .statusCode(SC_NOT_FOUND)
                .body("message", is("Учетная запись не найдена"));
    }

    @DisplayName("The courier cannot be login with incorrect password.")
    @Test
    public void courierCanNotBeLoginWithBadPasswordTest() {
        CourierData courierWithoutPassword = new CourierData(courierData.getLogin(),
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
        CourierData courierWithoutPassword = new CourierData(
                courierData.getLogin(), null,null);
        ValidatableResponse loginCourierWithoutPasswordResponse = courierApi.loginCourier(courierWithoutPassword);

        loginCourierWithoutPasswordResponse.log().all()
                .assertThat()
                .statusCode(SC_GATEWAY_TIMEOUT);
    }

    @After
    @Step("Login for get id courier and delete him")
    public void deleteCourier() {
        CourierId courierId = new CourierId();

        ValidatableResponse loginCourierResponse = courierApi.loginCourier(courierData);
        if (loginCourierResponse.extract().statusCode() == SC_OK) {
            loginCourierResponse.log().all()
                    .assertThat()
                    .body("id", notNullValue());
            courierId.setId(loginCourierResponse.extract().jsonPath().getInt("id"));

            ValidatableResponse deleteCourierResponse = courierApi.deleteCourier(courierId);
            deleteCourierResponse.log().all()
                    .assertThat()
                    .statusCode(SC_OK)
                    .body("ok", is(true));
        }
    }
}
