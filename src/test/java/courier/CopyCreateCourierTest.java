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
import static util.CourierGenerator.NUMBER_OF_CHARACTERS;
import static util.CourierGenerator.getRandomCourier;

@RunWith(Parameterized.class)
public class CopyCreateCourierTest {
    private final CourierData courierData;
    private final int expectedStatus;
    private final boolean expectedBody;

    public CopyCreateCourierTest(CourierData courierData, int expectedStatus, boolean expectedBody) {
        this.courierData = courierData;
        this.expectedStatus = expectedStatus;
        this.expectedBody = expectedBody;
    }

    private static CourierApi courierApi;
    private static ValidatableResponse createCourierResponse;

    @Before
    public void init() {
        courierApi = new CourierApi();
    }

    @Parameterized.Parameters
    public static Collection<Object[]> canBeCreated() {
        return Arrays.asList(
                new Object[]{getRandomCourier(), SC_CREATED, true},
                new Object[]{getRandomCourier(), SC_CREATED, true},
                new Object[]{getRandomCourier(), SC_CREATED, true},
                new Object[]{getRandomCourier(), SC_CREATED, true},
                new Object[]{getRandomCourier(), SC_CREATED, true},
                new Object[]{getRandomCourier(), SC_CREATED, true}
                );
    }

    @DisplayName("Check courier can be created")
    @Test
    public void courierCanBeCreatedTest() {
        createCourierResponse = courierApi.createCourierLombok(courierData);
        createCourierResponse.log()
                .all()
                .assertThat()
                .statusCode(expectedStatus)
                .body("ok", is(expectedBody));
    }

    @DisplayName("Check courier cannot be created without password")
    @Test
    public void courierCanNotCreatedWithoutPasswordTest() {
        CourierData courierWithoutPassword = new CourierData(RandomStringUtils.randomAlphabetic(NUMBER_OF_CHARACTERS),
                null, null);
        createCourierResponse = courierApi.createCourierLombok(courierWithoutPassword);
        createCourierResponse.log()
                .all()
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .body("message", is("Недостаточно данных для создания учетной записи"));
    }

    @DisplayName("The courier cannot be created, if courier name is not unique.")
    @Test
    public void courierCanNotBeCreatedIfNameNotUniqueTest() {
        createCourierResponse = courierApi.createCourierLombok(courierData);
        ValidatableResponse createIdenticalCourierResponse = courierApi.createCourierLombok(courierData);
        createIdenticalCourierResponse.log()
                .all()
                .assertThat()
                .statusCode(SC_CONFLICT)
                .body("message", is("Этот логин уже используется. Попробуйте другой."));
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
