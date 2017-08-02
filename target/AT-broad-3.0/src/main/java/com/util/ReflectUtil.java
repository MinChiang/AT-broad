package com.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectUtil {

	@SuppressWarnings("unchecked")
	public static <T> T newInstance(Class<?> cls, Class<?>[] paramTypes, Object[] params) {
		T t = null;
		try {
			Constructor<?> constructor = cls.getConstructor(paramTypes);
			t = (T) constructor.newInstance(params);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return t;
	}

	public static <T> T newInstance(Class<?> cls) {
		return newInstance(cls, null, null);
	}

	public static Object invoke(Object object, String methodName, Class<?>[] paramTypes, Object[] params) {
		Class<?> cls = object.getClass();
		Object obj = null;
		try {
			Method method = cls.getDeclaredMethod(methodName, paramTypes);
			obj = method.invoke(object, params);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return obj;
	}

	public static void setParamByMethod(Object object, String param, Class<?> paramType, Object value) {
		String setMethod = "set" + Character.toUpperCase(param.charAt(0)) + param.substring(1);
		invoke(object, setMethod, new Class<?>[] { paramType }, new Object[] { value });
	}

	public static boolean setParamByField(Object object, String param, Object value) {
		boolean result = false;
		Class<?> cls = object.getClass();
		Field[] fields = cls.getDeclaredFields();
		for (Field field : fields) {
			if (field.getName().equals(param)) {
				boolean acce = field.isAccessible();
				field.setAccessible(true);
				try {
					field.set(object, value);
					result = true;
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
				field.setAccessible(acce);
			}
		}
		return result;
	}

	public static boolean setParamByField(Object object, String param, Class<?> paramType, Object value) {
		boolean result = false;
		Class<?> cls = object.getClass();
		Field[] fields = cls.getDeclaredFields();
		for (Field field : fields) {
			if (field.getName().equals(param) && field.getType().getName().equalsIgnoreCase(paramType.getName())) {
				boolean acce = field.isAccessible();
				field.setAccessible(true);
				try {
					field.set(object, value);
					result = true;
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
				field.setAccessible(acce);
			}
		}
		return result;
	}

	public static boolean isBaseType(Class<?> type) {
		if (type == String.class) {
			return true;
		} else if (type == int.class) {
			return true;
		} else if (type == double.class) {
			return true;
		} else if (type == long.class) {
			return true;
		} else if (type == short.class) {
			return true;
		} else if (type == boolean.class) {
			return true;
		} else if (type == float.class) {
			return true;
		} else if (type == char.class) {
			return true;
		}
		return false;
	}

	public static Object string2BaseType(String value, Class<?> type) {
		Object realValue = null;
		if (type == String.class) {
			realValue = value;
		} else if (type == int.class) {
			realValue = Integer.valueOf(value);
		} else if (type == double.class) {
			realValue = Double.valueOf(value);
		} else if (type == long.class) {
			realValue = Long.valueOf(value);
		} else if (type == short.class) {
			realValue = Short.valueOf(value);
		} else if (type == boolean.class) {
			realValue = Boolean.valueOf(value);
		} else if (type == float.class) {
			realValue = Float.valueOf(value);
		} else if (type == char.class) {
			realValue = value.charAt(0);
		} else if (type == char[].class) {
			realValue = value.toCharArray();
		}
		return realValue;
	}

	public static void setParamAutoFixBaseType(Object object, String param, String value) {
		Class<?> cls = object.getClass();
		try {
			Field field = cls.getDeclaredField(param);
			Class<?> fieldType = field.getType();
			Object realValue = string2BaseType(value, fieldType);
			setParamByMethod(object, param, fieldType, realValue);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
	}

}
