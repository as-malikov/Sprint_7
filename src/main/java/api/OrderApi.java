package api;

import io.restassured.response.ValidatableResponse;
import model.order.OrderDataLombok;

import static io.restassured.RestAssured.given;

public class OrderApi extends RestApi {

    public static final String API_V_1_ORDERS = "/api/v1/orders";

    public ValidatableResponse createOrder(OrderDataLombok order) {
        return given()
                .spec(requestSpecification())
                .and()
                .body(order)
                .when()
                .post(API_V_1_ORDERS)
                .then();
    }

    public ValidatableResponse cancelOrder() {
        return null;
    }

    public ValidatableResponse getList() {
        return null;
    }
}
