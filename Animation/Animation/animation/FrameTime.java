package animation;

import settings.Settings;

public class FrameTime {

    public static float getIncTime() {
        return incTime;
    }

    public static void setIncTime(float inctime) {
        incTime = inctime;
    }

    private static float incTime = Settings.INC_TIME;

}


