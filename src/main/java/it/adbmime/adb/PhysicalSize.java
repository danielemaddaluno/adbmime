package it.adbmime.adb;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class PhysicalSize {
    private static final String WM_SIZE = "adb shell wm size";
    private static final String WM_SIZE_MAX = "adb shell getevent -il /dev/input/event2 | grep ABS_MT_POSITION";
    private static String REGEX_WM_SIZE = "^.*Physical size: (\\d+)x(\\d+)?";
    private static String REGEX_WM_SIZE_MAX = "^.+ABS_MT_POSITION_X     :.+, max (\\d+),.+\\n.+ABS_MT_POSITION_Y     :.+, max (\\d+),.+?";
    private static final Pattern REGEX_PATTERN = Pattern.compile(REGEX_WM_SIZE, Pattern.MULTILINE);
    private static final Pattern REGEX_PATTERN_MAX = Pattern.compile(REGEX_WM_SIZE_MAX, Pattern.MULTILINE);

    private final int width;
    private final int height;
    private final int maxX;
    private final int maxY;

    PhysicalSize() {
        this(AdbHelper.run(WM_SIZE), AdbHelper.run(WM_SIZE_MAX));
    }

    /**
     *
     * @param adb1 the response from an "adb shell wm size" execution
     * @param adb2 the response from an "adb shell getevent -il /dev/input/event2 | grep ABS_MT_POSITION" execution
     */
    PhysicalSize(String adb1, String adb2) {
        Matcher m1 = REGEX_PATTERN.matcher(adb1);
        if (m1.find()) {
            this.width = Integer.valueOf(m1.group(1));
            this.height = Integer.valueOf(m1.group(2));
        } else {
            throw new IllegalArgumentException("adb response does not match the regex pattern " + REGEX_WM_SIZE);
        }

        Matcher m2 = REGEX_PATTERN_MAX.matcher(adb2);
        if (m2.find()) {
            this.maxX = Integer.valueOf(m2.group(1)) + 1;
            this.maxY = Integer.valueOf(m2.group(2)) + 1;
        } else {
            throw new IllegalArgumentException("adb response does not match the regex pattern " + REGEX_WM_SIZE_MAX);
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getMaxX() {
        return maxX;
    }

    public int getMaxY() {
        return maxY;
    }

//    public PhysicalTouch getRealTouch(PhysicalTouch pt){
//        return PhysicalTouch
//    }

    @Override
    public String toString() {
        return "PhysicalSize{" +
                "width=" + width +
                ", height=" + height +
                ", maxX=" + maxX +
                ", maxY=" + maxY +
                '}';
    }
}
