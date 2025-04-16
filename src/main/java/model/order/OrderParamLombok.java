package model.order;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class OrderParamLombok {
    @NonNull
    private int courierId;
    private String nearestStation;
    private int limit;
    private int page;
}
