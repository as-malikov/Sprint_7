package util;

import model.courier.CourierIdLombok;
import model.order.OrderParamLombok;

public class OrderParamGenerator {
    public static OrderParamLombok getRandomOrderParam(CourierIdLombok courierId) {
        return new OrderParamLombok(courierId.getId(), null);
    }
}
