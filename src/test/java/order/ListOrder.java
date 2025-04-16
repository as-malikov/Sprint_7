package order;

import api.CourierApi;
import io.restassured.response.ValidatableResponse;
import model.courier.CourierDataLombok;
import model.courier.CourierIdLombok;
import model.order.OrderParamLombok;
import org.junit.Before;
import org.junit.Test;

import static util.CourierGenerator.getRandomCourierLombok;

public class ListOrder {
    private CourierIdLombok courierId;
    private OrderParamLombok orderParamLombok;
    CourierApi courierApi;


    @Before
    public void init() {
        CourierDataLombok courier = getRandomCourierLombok();
        courierApi = new CourierApi();
        courierId = new CourierIdLombok();
        courierApi.createCourierLombok(courier);

        ValidatableResponse loginCourierResponse = courierApi.loginCourier(courier);
        courierId.setId(loginCourierResponse.extract().jsonPath().getString("id"));
    }

    @Test
    public void ordersListHaveToBeReturn() {
        
    }


}
