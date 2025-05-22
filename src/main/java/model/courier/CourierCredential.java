package model.courier;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourierCredential {
    private String login;
    private String password;

    public static CourierCredential getCourierCredentials(CourierData courierData) {
        return new CourierCredential(courierData.getLogin(), courierData.getPassword());
    }
}
