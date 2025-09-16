package org.ecommercapp.ecommerce.shared.util;

import java.time.LocalDateTime;
import java.util.Random;

public class CodeGenerator {

    private static String generateCode(String prefix) {
        LocalDateTime now = LocalDateTime.now();
        int randomNumber = generateRandomNumber(10000, 99999);
        return String.format("%s-%04d-%02d-%02d-%02d-%02d-%02d-%05d",
                prefix, now.getYear(), now.getMonthValue(), now.getDayOfMonth(),
                now.getHour(), now.getMinute(), now.getSecond(),
                randomNumber);
    }

    private static int generateRandomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }

    public static String generateOrderCode() {
        return generateCode("ORDER");
    }

    public static String generateOrderItemCode() {
        return generateCode("ITEM");
    }

}
