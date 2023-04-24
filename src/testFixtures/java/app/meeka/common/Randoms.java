package app.meeka.common;

import java.util.Random;

public class Randoms {

    private static final Random RANDOM = new Random();

    public static String aRandomText(int length) {
        var stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            stringBuilder.append(RANDOM.nextInt(10));
        }
        return stringBuilder.toString();
    }

    public static Long aRandomId() {
        return RANDOM.nextLong();
    }

    public static String aRandomSuffix() {
        return aRandomText(6);
    }
}
