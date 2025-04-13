import api.CourierApi;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import model.CourierData;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.is;

public class CreateCourierBeginnerTest {
    @Before
    public void init() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @Test
    public void courierCanBeCreatedTest() {
        CourierData courierData = new CourierData("asdasdddjjsaqda", "ads", "asda");
        CourierApi courierApi = new CourierApi();

        ValidatableResponse response = courierApi.createCourier(courierData);

        response.log().all()
                .assertThat()
                .statusCode(SC_CREATED)
                .body("ok", is(true));
    }

     @After
    public void deleteCourier() {
        // удалить курьера
    }
}
