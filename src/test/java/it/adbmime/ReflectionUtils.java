package it.adbmime;

import it.adbmime.adb.DeviceScreenSize;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class ReflectionUtils {
    public static <T> T build(Class<T> clazz, Object... params)  {
        try {
            Class<?>[] classes = Arrays.stream(params).map(Object::getClass).toArray(Class[]::new);
            Constructor<T> constructor = clazz.getDeclaredConstructor(classes);
            constructor.setAccessible(true);
            return constructor.newInstance(params);
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
