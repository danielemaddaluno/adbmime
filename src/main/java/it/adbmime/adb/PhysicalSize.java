package it.adbmime.adb;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class PhysicalSize {
    private static String REGEX = "^Physical size: (\\d+)x(\\d+)?";
    private static final Pattern REGEX_PATTERN = Pattern.compile(REGEX);

    private final int width;
    private final int height;

    PhysicalSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /**
     *
     * @param adbResponse the response from an "adb shell wm size" execution
     */
    PhysicalSize(String adbResponse) {
        Matcher m = REGEX_PATTERN.matcher(adbResponse);
        if (m.find()) {
            this.width = Integer.valueOf(m.group(1));
            this.height = Integer.valueOf(m.group(2));
        } else {
            throw new IllegalArgumentException("adbResponse does not match the regex pattern " + REGEX);
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public String toString() {
        return "PhysicalSize{" +
                "width=" + width +
                ", height=" + height +
                '}';
    }
}
