package util;

import io.qameta.allure.Step;
import model.courier.CourierData;
import org.apache.commons.lang3.RandomStringUtils;

public class CourierGenerator {

    public static final String LOGIN = "login_";
    public static final String PASSWORD = "password_";
    public static final String FIRST_NAME = "firstName_";
    public static final String UNDERLINE = "_";
    public static final int NUMBER_OF_CHARACTERS = 8;

    @Step("Generate random courier default")
    public static CourierData getRandomCourierLombok() {
        String login = LOGIN + RandomStringUtils.randomAlphabetic(NUMBER_OF_CHARACTERS);
        String password = PASSWORD + RandomStringUtils.randomAlphabetic(NUMBER_OF_CHARACTERS);
        String firstName = FIRST_NAME + RandomStringUtils.randomAlphabetic(NUMBER_OF_CHARACTERS);

        return new CourierData(login, password, firstName);
    }

    @Step("Generate random courier by data")
    public static CourierData getRandomCourier(String loginParam, String passwordParam, String firstNameParam) {
        String login = loginParam + UNDERLINE + RandomStringUtils.randomAlphabetic(NUMBER_OF_CHARACTERS);
        String password = passwordParam + UNDERLINE + RandomStringUtils.randomAlphabetic(NUMBER_OF_CHARACTERS);
        String firstName = firstNameParam + UNDERLINE + RandomStringUtils.randomAlphabetic(NUMBER_OF_CHARACTERS);

        return new CourierData(login, password, firstName);
    }
}
