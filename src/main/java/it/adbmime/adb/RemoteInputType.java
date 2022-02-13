package it.adbmime.adb;

import java.util.regex.Pattern;

// Test regex at: https://regex101.com/
public enum RemoteInputType {
    KEY(RemoteInputKey.class, "adb shell input keyevent %d", "^.+ input keyevent (\\d+)?"),
    SWIPE(RemoteInputSwipe.class, "adb shell input swipe %d %d %d %d", "^.+ input swipe (\\d+) (\\d+) (\\d+) (\\d+)?", int.class, int.class, int.class, int.class),
    TAP(RemoteInputTap.class, "adb shell input tap %d %d", "^.+ input tap (\\d+) (\\d+)?", int.class, int.class),
    TEXT(RemoteInputText.class, "adb shell input text '%s'", "^.+ input text '(.*?)'", String.class);

    private final Class<? extends RemoteInput> clazz;
    private final String command;
    private final String regex;
    private final Class<?>[] parameterTypes;

    RemoteInputType(Class<? extends RemoteInput> clazz, String command, String regex, Class<?>... parameterTypes) {
        this.clazz = clazz;
        this.command = command;
        this.regex = regex;
        this.parameterTypes = parameterTypes;
    }

    public String getCommand() {
        return command;
    }

    public Pattern getRegexPattern() {
        return Pattern.compile(regex);
    }

    public Class<? extends RemoteInput> getClazz() {
        return clazz;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }
}
