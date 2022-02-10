package it.adbmime.adb;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class PhysicalTouch implements AdbStreamResult {

    protected static final String GET_ABS_MT_POSITION = "adb shell getevent -l | grep ABS_MT_POSITION";

    // https://regex101.com/
    private static final String REGEX_X = "^.+: EV_ABS       ABS_MT_POSITION_X    (\\S+)?";
    private static final Pattern REGEX_PATTERN_X = Pattern.compile(REGEX_X);
    private static final String REGEX_Y = "^.+: EV_ABS       ABS_MT_POSITION_Y    (\\S+)?";
    private static final Pattern REGEX_PATTERN_Y = Pattern.compile(REGEX_Y);

    private Integer x;
    private Integer y;

    /**
     * This is used to instantiate it with reflection
     */
    protected PhysicalTouch(){
    }

    public static PhysicalTouch newInstance() {
        return AdbHelper.runForAdbStreamResult(GET_ABS_MT_POSITION, PhysicalTouch.class);
    }

    public boolean isReady(String adbRow){
        if(x == null){
            x = PhysicalTouch.getX(adbRow);
        }
        if(y == null){
            y = PhysicalTouch.getY(adbRow);
        }
        return x != null && y != null;
    }

    private static Integer matchXY(Pattern p, String adbResponseRow){
        Matcher m = p.matcher(adbResponseRow);
        if (m.find()) {
            String hex = m.group(1);
            return Integer.parseInt(hex,16);
        } else {
            return null;
        }
    }

    private static Integer getX(String adbResponseRow){
        return matchXY(REGEX_PATTERN_X, adbResponseRow);
    }

    private static Integer getY(String adbResponseRow){
        return matchXY(REGEX_PATTERN_Y, adbResponseRow);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getX(PhysicalSize size) {
        return x*size.getWidth()/size.getMaxX();
    }

    public int getY(PhysicalSize size) {
        return y*size.getHeight()/size.getMaxY();
    }

    public String getHexX() {
        return String.format("%08X", x);
    }

    public String getHexY() {
        return String.format("%08X", y);
    }

    @Override
    public String toString() {
        return "PhysicalTouch{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public String toString(PhysicalSize size) {
        return "PhysicalTouch{" +
                "x=" + getX(size) +
                ", y=" + getY(size) +
                '}';
    }

}
