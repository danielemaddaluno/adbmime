package it.adbmime.utils;

import java.lang.reflect.*;
import java.util.Arrays;
import java.util.List;

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

    public static <T> T getValueFromObject(Object object, String fieldName, Class<T> returnClazz) {
        return getValueFromObject(object, object.getClass(), fieldName, returnClazz);
    }

    private static <T> T getValueFromObject(Object object, Class<?> declaredFieldClass, String fieldName,
                                            Class<T> returnClazz) {
        try {
            Field field = declaredFieldClass.getDeclaredField(fieldName);
            field.setAccessible(true);
            Object value = field.get(object);
            return returnClazz.cast(value);
        } catch (NoSuchFieldException e) {
            Class<?> superClass = declaredFieldClass.getSuperclass();
            if (superClass != null) {
                return getValueFromObject(object, superClass, fieldName, returnClazz);
            }
        } catch (IllegalAccessException e) {
        }
        return null;
    }

    public static void setValueOnObject(Object object, String fieldName, Object value) {
        setValueOnObject(object, object.getClass(), fieldName, value);
    }

    private static void setValueOnObject(Object object, Class<?> declaredFieldClass, String fieldName, Object value) {
        try {
            Field field = declaredFieldClass.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(object, value);
        } catch (NoSuchFieldException e) {
            Class<?> superClass = declaredFieldClass.getSuperclass();
            if (superClass != null) {
                setValueOnObject(object, superClass, fieldName, value);
                return;
            }
        } catch (IllegalAccessException e) {
        }
    }

    public static Object genericInvokeMethod(Object obj, String methodName, List<Class<?>> classes, List<Object> args) {
        int paramCount = classes.size();
        Method method;
        Object requiredObj = null;
        Class<?>[] classArray = new Class<?>[paramCount];
        Object[] argsArray = new Object[paramCount];
        for (int i = 0; i < paramCount; i++) {
            Class<?> clazz = classes.get(i);
            Object arg = args.get(i);
            classArray[i] = clazz;
            argsArray[i] = arg;
        }
        try {
            method = obj.getClass().getDeclaredMethod(methodName, classArray);
            method.setAccessible(true);
            requiredObj = method.invoke(obj, argsArray);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return requiredObj;
    }

    /**
     * Use to call a simple method with no arguments
     * @param <T>
     * @param obj
     * @param methodName
     * @param returnClass
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T simpleInvokeMethod(Object obj, String methodName, Class<T> returnClass) {
        try {
            Method method = obj.getClass().getMethod(methodName);
            method.setAccessible(true);
            T value = (T) method.invoke(obj);
            return value;
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> Class<T[]> getArrayClass(Class<T> clazz) {
        return (Class<T[]>) Array.newInstance(clazz, 0).getClass();
    }
}
