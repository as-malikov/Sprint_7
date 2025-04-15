package model.courier;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourierCredentialLombok {
    private String login;
    private String password;

    public static CourierCredentialLombok getCourierCredentials(CourierDataLombok courierData) {
        return new CourierCredentialLombok(courierData.getLogin(), courierData.getPassword());
    }
}
