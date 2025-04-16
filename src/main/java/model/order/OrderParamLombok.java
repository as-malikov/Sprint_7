package model.order;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class OrderParamLombok {
    @NonNull
    private Integer courierId;
    private String nearestStation;
    private int limit;
    private int page;
}
