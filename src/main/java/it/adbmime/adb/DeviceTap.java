package it.adbmime.adb;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class DeviceTap implements AdbStreamResult {
    protected static final String GET_ABS_MT_POSITION = "adb shell getevent -l | grep ABS_MT_POSITION";

    // https://regex101.com/
    private static final String REGEX_X = "^.+: EV_ABS       ABS_MT_POSITION_X    (\\S+)?";
    private static final Pattern REGEX_PATTERN_X = Pattern.compile(REGEX_X);
    private static final String REGEX_Y = "^.+: EV_ABS       ABS_MT_POSITION_Y    (\\S+)?";
    private static final Pattern REGEX_PATTERN_Y = Pattern.compile(REGEX_Y);

    private DeviceScreenSize size;
    private Integer x;
    private Integer y;

    /**
     * This is used to instantiate it with reflection
     */
    private DeviceTap(){
    }

    protected static DeviceTap newInstance(DeviceScreenSize deviceScreenSize) {
        DeviceTap deviceTap =  AdbHelper.runForAdbStreamResult(GET_ABS_MT_POSITION, DeviceTap.class);
        deviceTap.size = deviceScreenSize;
        return deviceTap;
    }

    public boolean isReady(String adbRow){
        if(x == null){
            x = DeviceTap.getX(adbRow);
        }
        if(y == null){
            y = DeviceTap.getY(adbRow);
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
        if(size != null){
            return x*size.getWidth()/size.getMaxX();
        } else {
            return x;
        }
    }

    public int getY() {
        if(size != null){
            return y*size.getHeight()/size.getMaxY();
        } else {
            return y;
        }
    }

    @Override
    public String toString() {
        return "PhysicalTouch{" +
                "x=" + getX() +
                ", y=" + getY() +
                '}';
    }

}
