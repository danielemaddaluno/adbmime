package it.adbmime.input;

import it.adbmime.AdbHelper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

/**
 * TODO when using adb shell, specify the device:
 * (https://stackoverflow.com/a/14655015/3138238)
 * adb -s 7f1c864e shell
 * or
 * adb -s 192.168.56.101:5555 shell
 *
 */
public abstract class RemoteInput {
    public abstract RemoteInputType type();
    public abstract String command();

    public RemoteInput send(){
        AdbHelper.run(command());
        return this;
    }

    public RemoteInput send(String deviceId) {
        String command = command();
        if(command.startsWith("adb ")){
            AdbHelper.run(command.replace("adb ", "adb -s " + deviceId + " "));
        }
        return this;
    }

    public static List<RemoteInput> fromCommands(File file) {
        List<RemoteInput> remoteInputs = new ArrayList<>();
        try {
            Path path = Paths.get(file.toURI());
            List<String> lines = Files.readAllLines(path);
            for(String line: lines){
                RemoteInput remoteInput = RemoteInput.fromCommand(line);
                if(remoteInput != null){
                    remoteInputs.add(remoteInput);
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }

        return remoteInputs;
    }

    public static RemoteInput fromCommand(String command){
        try {
            for(RemoteInputType type: RemoteInputType.values()){
                Matcher matcher = type.getRegexPattern().matcher(command);
                if (matcher.find()) {
                    if(type.getClazz().isEnum()){
                        return type.getClazz().getEnumConstants()[Integer.valueOf(matcher.group(1))];
                    } else {
                        Constructor<? extends RemoteInput> constructor = type.getClazz().getDeclaredConstructor(type.getParameterTypes());
                        constructor.setAccessible(true);

                        Object[] parameters = new Object[matcher.groupCount()];
                        for(int i=0; i<type.getParameterTypes().length; i++){
                            Class<?> parameterClass = type.getParameterTypes()[i];
                            if(parameterClass == int.class){
                                parameters[i] = Integer.valueOf(matcher.group(i+1));
                            } else if(parameterClass == boolean.class){
                                parameters[i] = !matcher.group(i+1).isEmpty();
                            } else if(parameterClass == File.class){
                                parameters[i] = new File(matcher.group(i+1));
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

    public static void toCommands(List<RemoteInput> remoteInputs, File file) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for(RemoteInput row: remoteInputs){
                writer.write(row.command());
                writer.write("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static RemoteInputKey key(boolean longPress, int keycode) {
        return RemoteInputKey.newInstance(longPress, keycode);
    }

    public static RemoteInputText text(String text) {
        return RemoteInputText.newInstance(text);
    }

    public static RemoteInputTap tap(int x, int y){
        return RemoteInputTap.newInstance(x, y);
    }

    public static RemoteInputTap tap(RemotePoint p){
        return RemoteInputTap.newInstance(p);
    }

    public static RemoteInputSwipe swipe(int x0, int y0, int x1, int y1){
        return RemoteInputSwipe.newInstance(x0, y0, x1, y1);
    }

    public static RemoteInputSwipe swipe(RemotePoint p0, RemotePoint p1){
        return RemoteInputSwipe.newInstance(p0, p1);
    }

    public static RemoteInputAppInstall install(File apk){
        return RemoteInputAppInstall.newInstance(apk);
    }

    public static RemoteInputAppUnInstall uninstall(String packageName){
        return RemoteInputAppUnInstall.newInstance(packageName);
    }

    public static RemoteInputAppOpen open(String packageName){
        return RemoteInputAppOpen.newInstance(packageName);
    }

    public static RemoteInputAppHide hide(String packageName){
        return RemoteInputAppHide.newInstance(packageName);
    }

    public static RemoteInputAppUnHide unhide(String packageName){
        return RemoteInputAppUnHide.newInstance(packageName);
    }

}
