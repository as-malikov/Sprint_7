package util;

import io.qameta.allure.Step;
import model.courier.CourierId;
import model.order.OrderParam;

public class OrderParamGenerator {

    @Step("Generate order parameters by courier id")
    public static OrderParam getOrderParamByCourierId(CourierId courierId) {
        return new OrderParam(courierId.getId());
    }
}
