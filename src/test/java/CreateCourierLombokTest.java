import api.CourierApi;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import model.CourierData;
import model.CourierDataLombok;
import model.CourierId;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static model.CourierGenerator.getRandomCourier;
import static model.CourierGenerator.getRandomCourierLombok;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class CreateCourierLombokTest {
    private static CourierDataLombok courierDataLomboka;
    private static CourierApi courierApi;
    private static ValidatableResponse response;
    private static CourierId courierId;

    @BeforeClass
    public static void init() {
        courierDataLomboka = getRandomCourierLombok();
        courierApi = new CourierApi();
        courierId = new CourierId();
    }

    @DisplayName("Check courier can be created")
    @Test
    public void courierCanBeCreatedTest() {
        response = courierApi.createCourierLombok(courierDataLomboka);

        response.log().all()
                .assertThat()
                .statusCode(SC_CREATED)
                .body("ok", is(true));

//        response.log().all()
//                .assertThat()
//                .statusCode(SC_CONFLICT)
//                .body("message", is("Недостаточно данных для удаления курьера"));

        response.log().all()
                .assertThat()
                .statusCode(SC_OK)
                .body(notNullValue());
    }

//    @DisplayName("Check courier can be logged in")
//    @Test
//    public void courierCanBeLoggedIn() {
//        response = courierApi.loginCourier(courierDataLomboka);
//
//        response.log().all()
//                .assertThat()
//                .statusCode(SC_OK)
//                .body("id", notNullValue());
//
//        courierId.setId(response.extract().jsonPath().getString("id"));
//    }
//
//    //    @DisplayName("Check courier can be deleted")
//    @AfterClass
//    public static void deleteCourier() {
//        response = courierApi.deleteCourier(courierId);
//
//        response.log().all()
//                .assertThat()
//                .statusCode(SC_OK)
//                .body("ok", is(true));
//    }
}
