package order;

import api.CourierApi;
import api.OrderApi;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import model.courier.CourierData;
import model.courier.CourierId;
import model.order.OrderParam;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.notNullValue;
import static util.CourierGenerator.getRandomCourier;
import static util.OrderParamGenerator.getOrderParamByCourierId;

public class ListOrderTest {
    private static OrderApi orderApi;
    private OrderParam orderParam;


    @Before
    public void init() {
        CourierData courier = getRandomCourier();
        System.out.println(courier);
        CourierApi courierApi = new CourierApi();
        orderApi = new OrderApi();
        CourierId courierId = new CourierId();
        ValidatableResponse response = courierApi.createCourierLombok(courier);
        response.log()
                .all()
                .assertThat()
                .statusCode(SC_CREATED);

        ValidatableResponse loginCourierResponse = courierApi.loginCourier(courier);
        courierId.setId(loginCourierResponse.extract()
                .jsonPath()
                .getInt("id"));
        orderParam = getOrderParamByCourierId(courierId);
    }

    @DisplayName("Order List have be returned")
    @Test
    public void ordersListHaveBeReturnedTest() {
        ValidatableResponse orderListResponse = orderApi.getOrdersList(orderParam);
        orderListResponse.log()
                .all()
                .assertThat()
                .statusCode(SC_OK)
                .body("orders", notNullValue());
    }
}