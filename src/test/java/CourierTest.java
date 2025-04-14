//import api.CourierApi;
//import io.qameta.allure.junit4.DisplayName;
//import io.restassured.response.ValidatableResponse;
//import model.CourierData;
//import model.courier.CourierIdLombok;
//import org.junit.*;
//
//import static util.CourierGenerator.getRandomCourier;
//import static org.apache.http.HttpStatus.SC_CREATED;
//import static org.apache.http.HttpStatus.SC_OK;
//import static org.hamcrest.Matchers.is;
//import static org.hamcrest.Matchers.notNullValue;
//
//public class CourierTest {
//    private static CourierData courierData;
//    private static CourierApi courierApi;
//    private static ValidatableResponse response;
//    private static CourierIdLombok courierIdLombok;
//
//    @BeforeClass
//    public static void init() {
//        courierData = getRandomCourier();
//        courierApi = new CourierApi();
//        courierIdLombok = new CourierIdLombok();
//    }
//
//    @DisplayName("Check courier can be created")
//    @Test
//    public void courierCanBeCreatedTest() {
//        response = courierApi.createCourier(courierData);
//
//        response.log().all()
//                .assertThat()
//                .statusCode(SC_CREATED)
//                .body("ok", is(true));
//    }
//
//    @DisplayName("Check courier can be logged in")
//    @Test
//    public void courierCanBeLoggedIn() {
//        response = courierApi.loginCourier(courierData);
//
//        response.log().all()
//                .assertThat()
//                .statusCode(SC_OK)
//                .body("id", notNullValue());
//
//        courierIdLombok.setId(response.extract().jsonPath().getString("id"));
//    }
//
////    @DisplayName("Check courier can be deleted")
//    @AfterClass
//    public static void deleteCourier() {
//        response = courierApi.deleteCourier(courierIdLombok);
//
//        response.log().all()
//                .assertThat()
//                .statusCode(SC_OK)
//                .body("ok", is(true));
//    }
//}