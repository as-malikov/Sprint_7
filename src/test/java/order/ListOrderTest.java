package order;

import api.CourierApi;
import api.OrderApi;
import io.restassured.response.ValidatableResponse;
import model.courier.CourierDataLombok;
import model.courier.CourierIdLombok;
import model.order.OrderParamLombok;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.notNullValue;
import static util.CourierGenerator.getRandomCourierLombok;
import static util.OrderParamGenerator.getRandomOrderParam;

public class ListOrderTest {
    private CourierIdLombok courierId;
    private static OrderApi orderApi;
    private OrderParamLombok orderParamLombok;
    CourierApi courierApi;


    @Before
    public void init() {
        CourierDataLombok courier = getRandomCourierLombok();
        orderApi = new OrderApi();
        courierId = new CourierIdLombok();
        courierApi.createCourierLombok(courier);

        ValidatableResponse loginCourierResponse = courierApi.loginCourier(courier);
        courierId.setId(loginCourierResponse.extract().jsonPath().getInt("id"));
        orderParamLombok = getRandomOrderParam(courierId);
    }

    @Test
    public void ordersListHaveBeReturnedTest() {
        ValidatableResponse orderListResponse = orderApi.getOrdersList(orderParamLombok);
        orderListResponse.log().all()
                .assertThat()
                .statusCode(SC_OK)
                .body("orders", notNullValue());
    }
}