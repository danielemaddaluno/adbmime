package it.adbmime.adb;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class PhysicalTouch implements AdbStreamResult {

    protected static final String GET_ABS_MT_POSITION = "adb shell getevent -l | grep ABS_MT_POSITION";
    protected static final String INPUT_TAP = "adb shell input tap %d %d";

    // https://regex101.com/
    private static final String REGEX_X = "^.+: EV_ABS       ABS_MT_POSITION_X    (\\S+)?";
    private static final Pattern REGEX_PATTERN_X = Pattern.compile(REGEX_X);
    private static final String REGEX_Y = "^.+: EV_ABS       ABS_MT_POSITION_Y    (\\S+)?";
    private static final Pattern REGEX_PATTERN_Y = Pattern.compile(REGEX_Y);

    private PhysicalSize size;
    private Integer x;
    private Integer y;

    /**
     * This is used to instantiate it with reflection
     */
    protected PhysicalTouch(){
    }

    protected PhysicalTouch(int x, int y){
        this.x = x;
        this.y = y;
    }
    public static PhysicalTouch newInstance(int x, int y) {
        return new PhysicalTouch(x, y);
    }

    public static PhysicalTouch newInstance(PhysicalSize physicalSize) {
        PhysicalTouch physicalTouch =  AdbHelper.runForAdbStreamResult(GET_ABS_MT_POSITION, PhysicalTouch.class);
        physicalTouch.setSize(physicalSize);
        return  physicalTouch;
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

    /**
     *
     * @return
     */
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

    public void setSize(PhysicalSize size) {
        this.size = size;
    }

    public void tap() {
        String command = String.format(INPUT_TAP, getX(), getY());
        AdbHelper.run(command);
    }

    @Override
    public String toString() {
        return "PhysicalTouch{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

}
