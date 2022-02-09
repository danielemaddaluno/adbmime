package it.adbmime.adb;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class PhysicalTouch {
    private static String REGEX = "^/dev/input/event2: EV_ABS       ABS_MT_POSITION_X    (\\S+)\n/dev/input/event2: EV_ABS       ABS_MT_POSITION_Y    (\\S+)?";
    private static final Pattern REGEX_PATTERN = Pattern.compile(REGEX);

    private final String hexX;
    private final String hexY;
    private final int x;
    private final int y;

    PhysicalTouch(int x, int y) {
        this.hexX = String.format("%08X", x);
        this.hexY = String.format("%08X", y);
        this.x = x;
        this.y = y;
    }

    /**
     *
     * @param adbResponse the response from the first two rows of an "adb shell getevent -l | grep ABS_MT_POSITION" execution
     */
    PhysicalTouch(String adbResponse) {
        Matcher m = REGEX_PATTERN.matcher(adbResponse);
        if (m.find()) {
            this.hexX = m.group(1);
            this.hexY = m.group(2);
            this.x = Integer.parseInt(hexX,16);
            this.y = Integer.parseInt(hexY,16);
        } else {
            throw new IllegalArgumentException("adbResponse does not match the regex pattern " + REGEX);
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "PhysicalTouch{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

}
