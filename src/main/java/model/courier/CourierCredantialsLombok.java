package model.courier;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.CourierData;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourierCredantialsLombok {
    private String login;
    private String password;

//    public CourierCredantials(String login, String password) {
//        this.login = login;
//        this.password = password;
//    }

    public static CourierCredantialsLombok getCourierCredantials(CourierDataLombok courierData) {
        return new CourierCredantialsLombok(courierData.getLogin(), courierData.getPassword());
    }
}
