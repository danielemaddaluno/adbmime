package it.adbmime.adb;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class PhysicalTouch implements AdbStreamResult {
    private static final String REGEX_X = "^/dev/input/event2: EV_ABS       ABS_MT_POSITION_X    (\\S+)?";
    private static final Pattern REGEX_PATTERN_X = Pattern.compile(REGEX_X);
    private static final String REGEX_Y = "^/dev/input/event2: EV_ABS       ABS_MT_POSITION_Y    (\\S+)?";
    private static final Pattern REGEX_PATTERN_Y = Pattern.compile(REGEX_Y);

    private String hexX;
    private String hexY;
    private Integer x;
    private Integer y;

    PhysicalTouch(){
    }

    PhysicalTouch(int x, int y) {
        this.hexX = String.format("%08X", x);
        this.hexY = String.format("%08X", y);
        this.x = x;
        this.y = y;
    }

    public boolean isReady(String adbResponseRow){
        if(x == null){
            hexX = PhysicalTouch.getX(adbResponseRow);
            x = Integer.parseInt(hexX,16);
        }
        if(y == null){
            hexY = PhysicalTouch.getX(adbResponseRow);
            y = Integer.parseInt(hexY,16);
        }
        return x != null && y != null;
    }

    private static String matchXY(Pattern p, String adbResponseRow){
        Matcher m = p.matcher(adbResponseRow);
        if (m.find()) {
            String hex = m.group(1);
            return hex;
        } else {
            return null;
        }
    }

    private static String getX(String adbResponseRow){
        return matchXY(REGEX_PATTERN_X, adbResponseRow);
    }

    private static String getY(String adbResponseRow){
        return matchXY(REGEX_PATTERN_Y, adbResponseRow);
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
