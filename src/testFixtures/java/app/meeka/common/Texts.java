package app.meeka.common;

import static app.meeka.common.Randoms.aRandomSuffix;

public class Texts {

    public static String aPostTitle() {
        return "A post title for test - %s".formatted(aRandomSuffix());
    }

    public static String aPostContent() {
        return "A post content for test - %s".formatted(aRandomSuffix());
    }
}
