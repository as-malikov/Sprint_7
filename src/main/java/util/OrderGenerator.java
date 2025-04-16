package util;

import io.qameta.allure.Step;
import model.order.OrderDataLombok;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

public class OrderGenerator {
    public static final int NINE_NUMBER = 9;
    public static final String FIRST_NAME = "firstName_";
    public static final String LAST_NAME = "lastName_";
    public static final String ADDRESS = "address_";
    public static final String METRO_STATION = "metroStation_";
    public static final int THIRTY_DAYS = 30;
    public static final int ONE_DAY = 1;
    public static final String COMMENT = "comment_";
    public static final String BLACK_COLOR = "BLACK";
    public static final String GREY_COLOR = "GREY";
    public static final int FOUR_BOUND = 1000;
    public static final int TWO_BOUND = 100;

    @Step("Generate random order default without color")
    public static OrderDataLombok getRandomOrderWithoutColor() {
        return templateOrderGenerate(genetateColorList());
    }

    @Step("Generate random order default without Black color")
    public static OrderDataLombok getRandomOrderWithBlackColor() {
        return templateOrderGenerate(genetateColorList(BLACK_COLOR));
    }

    @Step("Generate random order default with Black and Grey color")
    public static OrderDataLombok getRandomOrderWithBlackAndGreyColor() {
        List<String> colors = new ArrayList<>();
        colors.add(BLACK_COLOR);
        colors.add(GREY_COLOR);
        return templateOrderGenerate(genetateColorList(colors));
    }

    public static OrderDataLombok templateOrderGenerate(List<String> color) {
        String login = FIRST_NAME + randomAlphabetic(NINE_NUMBER);
        String lastName = LAST_NAME + randomAlphabetic(NINE_NUMBER);
        String address = ADDRESS + randomAlphabetic(NINE_NUMBER);
        String metroStation = METRO_STATION + randomAlphabetic(NINE_NUMBER);
        String phone = generatePhoneNumber();
        int rentTime = generateRentTime();
        String deliveryDate = generateDeliveryDay();
        String comment = COMMENT + randomAlphabetic(NINE_NUMBER);
        return new OrderDataLombok(login, lastName, address, metroStation, phone, rentTime, deliveryDate, comment,
                color);
    }

    public static List<String> genetateColorList() {
        return null;
    }

    public static List<String> genetateColorList(String color) {
        return List.of(color);
    }

    public static List<String> genetateColorList(List<String> colors) {
        return colors;
    }

    public static int generateRentTime() {
        Random random = new Random();
        return random.nextInt(TWO_BOUND);
    }

    public static String generatePhoneNumber() {
        Random random = new Random();
        String part1 = String.format("%03d", random.nextInt(FOUR_BOUND)); // 3 цифры
        String part2 = String.format("%02d", random.nextInt(TWO_BOUND));  // 2 цифры
        String part3 = String.format("%02d", random.nextInt(TWO_BOUND));  // 2 цифры
        return "+7 800 " + part1 + " " + part2 + " " + part3;
    }

    public static String generateDeliveryDay() {
        return LocalDate.now()
                .plusDays(NINE_NUMBER)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
