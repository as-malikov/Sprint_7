import api.CourierApi;
import io.restassured.response.ValidatableResponse;
import model.CourierData;
import model.CourierId;
import org.junit.*;

import static model.CourierGenerator.getRandomCourier;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class CourierTest {
    private static CourierData courierData;
    private static CourierApi courierApi;
    private static ValidatableResponse response;
    private static CourierId courierId;

    @BeforeClass
    public static void init() {
        courierData = getRandomCourier();
        courierApi = new CourierApi();
        courierId = new CourierId();
    }

    @Test
    public void courierCanBeCreatedTest() {
        response = courierApi.createCourier(courierData);

        response.log().all()
                .assertThat()
                .statusCode(SC_CREATED)
                .body("ok", is(true));
    }

    @Test
    public void courierCanBeLoggedIn() {
        response = courierApi.loginCourier(courierData);

        response.log().all()
                .assertThat()
                .statusCode(SC_OK)
                .body("id", notNullValue());

        courierId.setId(response.extract().jsonPath().getString("id"));
    }

    @AfterClass
    public static void deleteCourier() {
        response = courierApi.deleteCourier(courierId);

        response.log().all()
                .assertThat()
                .statusCode(SC_OK)
                .body("ok", is(true));
    }
}