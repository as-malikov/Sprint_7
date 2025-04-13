package model;

public class CourierCredantials {
    private String login;
    private String password;

    public CourierCredantials(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public static CourierCredantials getCourierCredantials(CourierData courierData) {
        return new CourierCredantials(courierData.getLogin(), courierData.getPassword());
    }
}
