package it.adbmime.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class ReflectionUtilsTest {
	public enum MyEnum {
	    A, B, C
	}
	
	@SuppressWarnings("unchecked")
	public static <T> Class<T[]> getArrayClassExpected(Class<T> clazz) throws ClassNotFoundException {
		Class<T[]> namedClass = (Class<T[]>) Class.forName("[L" + clazz.getName() + ";");
		return namedClass;
	}
	
	@Test
	public void test() throws ClassNotFoundException {
		Class<?> clazz1 = ReflectionUtils.getArrayClass(MyEnum.class);
		Class<?> clazz2 = getArrayClassExpected(MyEnum.class);
		Assertions.assertEquals(clazz2, clazz1);
	}
}
