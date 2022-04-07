package org.fuchss.xmlobjectmapper.util;

import java.lang.reflect.Field;
import java.util.function.Supplier;

@SuppressWarnings("java:S3011")
public final class ReflectUtils {
	private ReflectUtils() {
		throw new IllegalAccessError();
	}

	public static <S> ReflectiveConstructor<S> getReflectiveConstructor(Class<S> clazz) {
		return () -> clazz.getDeclaredConstructor().newInstance();
	}

	public static <S> Supplier<S> toConstructor(ReflectiveConstructor<S> constructor) {
		return () -> {
			try {
				return constructor.get();
			} catch (Exception e) {
				throw new IllegalStateException(e);
			}
		};

	}

	public static void setField(Object target, Field field, Object value) {
		try {
			field.setAccessible(true);
			field.set(target, value);
		} catch (ReflectiveOperationException e) {
			throw new IllegalArgumentException(e.getMessage(), e);
		}
	}

	public static <T> T getField(Object target, Field field, Class<T> resultType) {
		try {
			field.setAccessible(true);
			var value = field.get(target);
			return value == null ? null : resultType.cast(value);
		} catch (ReflectiveOperationException e) {
			throw new IllegalArgumentException(e.getMessage(), e);
		}
	}

	public static String getValue(Object target, Field field) {
		var value = getField(target, field, Object.class);
		return value == null ? null : String.format("%s", value);
	}

	@FunctionalInterface
	public interface ReflectiveConstructor<S> {
		S get() throws ReflectiveOperationException;
	}
}

