package it.adbmime.adb;

import java.io.File;
import java.util.regex.Pattern;

// Test regex at: https://regex101.com/
public enum RemoteInputType {
    KEY(RemoteInputKey.class, "adb shell input keyevent " + RemoteInputKey.LONG_PRESS + "%d", "^.+ input keyevent(.*) (\\d+)?", boolean.class, int.class),
    SWIPE(RemoteInputSwipe.class, "adb shell input swipe %d %d %d %d", "^.+ input swipe (\\d+) (\\d+) (\\d+) (\\d+)?", int.class, int.class, int.class, int.class),
    TAP(RemoteInputTap.class, "adb shell input tap %d %d", "^.+ input tap (\\d+) (\\d+)?", int.class, int.class),
    TEXT(RemoteInputText.class, "adb shell input text '%s'", "^.+ input text '(.*?)'", String.class),
    UNINSTALL(RemoteInputAppUnInstall.class, "adb uninstall %s", "^.+ uninstall (.+)?", String.class),
    INSTALL(RemoteInputAppInstall.class, "adb install -r %s", "^.+ install -r (.+)?", File.class),
    OPEN(RemoteInputAppOpen.class, "adb shell monkey --pct-syskeys 0 -p %s 1", "^.+ monkey --pct-syskeys 0 -p (.+) 1?", String.class),
    HIDE(RemoteInputAppHide.class, "adb shell pm hide %s", "^.+ pm hide (.+)?", String.class),
    UNHIDE(RemoteInputAppUnHide.class, "adb shell pm unhide %s", "^.+ pm unhide (.+)?", String.class),
    COMMENT(RemoteInputComment.class, "#%s", "^\\s*#(.*)?", String.class),
    SLEEP(RemoteInputSleep.class, "sleep %d", "\\s*sleep (\\d+)?", int.class),
    EMPTY(RemoteInputEmpty.class, "", "^\\s*$"),
    UNKNOWN(RemoteInputUnknown.class, "%s", "^(.*)?", String.class);

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
