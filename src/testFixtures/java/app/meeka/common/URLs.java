package app.meeka.common;

import static app.meeka.common.Randoms.aRandomText;

public class URLs {

    public static String aRandomURL() {
        return "https://" + aRandomText(10) + "/" + aRandomText(3) + ".jpg";
    }
}
