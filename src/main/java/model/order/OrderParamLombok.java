package model.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor()
public class OrderParamLombok {
    private int courierId;
    private String nearestStation;
    private int limit;
    private int page;
}
