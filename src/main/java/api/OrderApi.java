package api;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import model.order.OrderData;
import model.order.OrderParam;

import static io.restassured.RestAssured.given;

public class OrderApi extends RestApi {

    public static final String API_V_1_ORDERS = "/api/v1/orders";

    @Step("Create order")
    public ValidatableResponse createOrder(OrderData order) {
        return given().spec(requestSpecification())
                .and()
                .body(order)
                .when()
                .post(API_V_1_ORDERS)
                .then();
    }

    @Step("Get order List")
    public ValidatableResponse getOrdersList(OrderParam orderParam) {
        return given().spec(requestSpecification())
                .and()
                .body(orderParam)
                .when()
                .get(API_V_1_ORDERS)
                .then();
    }
}