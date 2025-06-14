package order;

import api.OrderApi;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import model.order.OrderData;
import model.order.TrackId;
import org.junit.Before;
import org.junit.Test;
import util.OrderGenerator;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.Matchers.notNullValue;
import static util.OrderGenerator.*;

public class CreateOrderTest {
    private static OrderData order;
    private static OrderApi orderApi;
    private static TrackId trackId;
    OrderGenerator orderGenerator;

    @Before
    public void init() {
        orderGenerator = new OrderGenerator();
        orderApi = new OrderApi();
        trackId = new TrackId();
    }

    @DisplayName("The order can be created without color")
    @Test
    public void orderCanBeCreatedWithoutColorTest() {
        order = getRandomOrderWithoutColor();
        ValidatableResponse createOrderResponse = orderApi.createOrder(order);
        createOrderResponse.log()
                .all()
                .assertThat()
                .statusCode(SC_CREATED)
                .body("track", notNullValue());
        trackId.setId(createOrderResponse.extract()
                .jsonPath()
                .getString("track"));
    }

    @DisplayName("The order can be created with one BLACK color")
    @Test
    public void orderCanBeCreatedWithBlackColorTest() {
        order = getRandomOrderWithBlackColor();
        ValidatableResponse createOrderResponse = orderApi.createOrder(order);
        createOrderResponse.log()
                .all()
                .assertThat()
                .statusCode(SC_CREATED)
                .body("track", notNullValue());
        trackId.setId(createOrderResponse.extract()
                .jsonPath()
                .getString("track"));
    }

    @DisplayName("The order can be created with BLACK and GREY color")
    @Test
    public void orderCanBeCreatedWithBlackAndGreyColorTest() {
        order = getRandomOrderWithBlackAndGreyColor();
        ValidatableResponse createOrderResponse = orderApi.createOrder(order);
        createOrderResponse.log()
                .all()
                .assertThat()
                .statusCode(SC_CREATED)
                .body("track", notNullValue());
        trackId.setId(createOrderResponse.extract()
                .jsonPath()
                .getString("track"));
    }
}
