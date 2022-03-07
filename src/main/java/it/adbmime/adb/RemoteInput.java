package it.adbmime.adb;

import javafx.scene.input.MouseEvent;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.regex.Matcher;

/**
 * TODO when using adb shell, specify the device:
 * (https://stackoverflow.com/a/14655015/3138238)
 * adb -s 7f1c864e shell
 * or
 * adb -s 192.168.56.101:5555 shell
 *
 */
public interface RemoteInput {
    RemoteInputType type();
    String command();
    RemoteInput send();
    static RemoteInput fromCommand(String command){
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

    static RemoteInputKey key(boolean longPress, int keycode) {
        return RemoteInputKey.newInstance(longPress, keycode);
    }

    static RemoteInputText text(String text) {
        return RemoteInputText.newInstance(text);
    }

    static RemoteInputTap tap(int x, int y){
        return RemoteInputTap.newInstance(x, y);
    }

    static RemoteInputTap tap(RemotePoint p){
        return RemoteInputTap.newInstance(p);
    }

    static RemoteInputTap tap(MouseEvent e){
        return RemoteInputTap.newInstance(e);
    }

    static RemoteInputSwipe swipe(int x0, int y0, int x1, int y1){
        return RemoteInputSwipe.newInstance(x0, y0, x1, y1);
    }

    static RemoteInputSwipe swipe(RemotePoint p0, RemotePoint p1){
        return RemoteInputSwipe.newInstance(p0, p1);
    }

    static RemoteInputSwipe swipe(MouseEvent e0, MouseEvent e1){
        return RemoteInputSwipe.newInstance(e0, e1);
    }

    static RemoteInputAppInstall install(File apk){
        return RemoteInputAppInstall.newInstance(apk);
    }

    static RemoteInputAppUnInstall uninstall(String packageName){
        return RemoteInputAppUnInstall.newInstance(packageName);
    }

    static RemoteInputAppOpen open(String packageName){
        return RemoteInputAppOpen.newInstance(packageName);
    }

    static RemoteInputAppHide hide(String packageName){
        return RemoteInputAppHide.newInstance(packageName);
    }

    static RemoteInputAppUnHide unhide(String packageName){
        return RemoteInputAppUnHide.newInstance(packageName);
    }
}
