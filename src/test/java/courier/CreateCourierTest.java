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
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static util.CourierGenerator.*;
import static util.CourierGenerator.getRandomCourier;

@RunWith(Parameterized.class)
public class CreateCourierTest {
    private static CourierApi courierApi;
    private final CourierData courierData;
    private final int expectedStatus;
    ValidatableResponse createCourierResponse;

    public CreateCourierTest(CourierData courierData, int expectedStatus) {
        this.courierData = courierData;
        this.expectedStatus = expectedStatus;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> canBeCreated() {
        CourierData courierDataDouble = getAllegraSilvanaCourier();
        return Arrays.asList(
                new Object[]{getRandomCourier(), SC_CREATED},
                new Object[]{getRandomCourier(), SC_CREATED},
                new Object[]{getRandomCourier(), SC_CREATED},
                new Object[]{getRandomCourier(), SC_CREATED},
                new Object[]{getRandomCourier(), SC_CREATED},
                new Object[]{getRandomCourier(), SC_CREATED},
                new Object[]{courierDataDouble, SC_CREATED},
                new Object[]{courierDataDouble, SC_CREATED},
                new Object[]{new CourierData(RandomStringUtils.randomAlphabetic(NUMBER_OF_CHARACTERS), null,
                        null), SC_CONFLICT});
    }

    @Before
    public void init() {
        courierApi = new CourierApi();
    }

    @DisplayName("Check courier can be created")
    @Test
    public void courierCanBeCreatedTest() {
        if (expectedStatus == SC_OK) {
            createCourierResponse = courierApi.createCourierLombok(courierData);
            createCourierResponse.log()
                    .all()
                    .assertThat()
                    .statusCode(expectedStatus)
                    .body("ok", is(true));
            courierApi.createCourierLombok(courierData)
                    .log()
                    .all()
                    .assertThat()
                    .statusCode(SC_CONFLICT)
                    .body("message", is("Этот логин уже используется. Попробуйте другой."));
        } else if (expectedStatus == SC_BAD_REQUEST) {
            createCourierResponse = courierApi.createCourierLombok(courierData)
                    .log()
                    .all()
                    .assertThat()
                    .statusCode(expectedStatus)
                    .body("message", is("Недостаточно данных для создания учетной записи"));
        }
    }

    @After
    @Step("Login for get id courier and delete him")
    public void deleteCourier() {
        CourierId courierId = new CourierId();

        ValidatableResponse loginCourierResponse = courierApi.loginCourier(courierData);
        if (loginCourierResponse.extract()
                .statusCode() == SC_OK)
        {
            loginCourierResponse.log()
                    .all()
                    .assertThat()
                    .body("id", notNullValue());
            courierId.setId(loginCourierResponse.extract()
                    .jsonPath()
                    .getInt("id"));

            ValidatableResponse deleteCourierResponse = courierApi.deleteCourier(courierId);
            deleteCourierResponse.log()
                    .all()
                    .assertThat()
                    .statusCode(SC_OK)
                    .body("ok", is(true));
        }
    }
}
