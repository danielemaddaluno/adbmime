package it.adbmime.adb;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public static RemoteInput fromCommand(String command){
        try {
            for(RemoteInputType type: values()){
                Matcher matcher = type.getRegexPattern().matcher(command);
                if (matcher.find()) {
                    if(type.clazz.isEnum()){
                        return type.clazz.getEnumConstants()[Integer.valueOf(matcher.group(1))];
                    } else {
                        Constructor<? extends RemoteInput> constructor = type.clazz.getDeclaredConstructor(type.parameterTypes);
                        constructor.setAccessible(true);

                        Object[] parameters = new Object[matcher.groupCount()];
                        for(int i=0; i<type.parameterTypes.length; i++){
                            Class<?> parameterClass = type.parameterTypes[i];
                            if(parameterClass == int.class){
                                parameters[i] = Integer.valueOf(matcher.group(i+1));
                            } else {
                                parameters[i] = matcher.group(i+1);
                            }
                        }

                        RemoteInput result = constructor.newInstance(parameters);
                        return result;
                    }
                }
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
