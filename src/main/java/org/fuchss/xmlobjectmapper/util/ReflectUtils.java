package org.fuchss.xmlobjectmapper.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.function.Supplier;

/**
 * This class contains some utilities for reflection.
 *
 * @author Dominik Fuchss
 */
@SuppressWarnings("java:S3011")
public final class ReflectUtils {
    private ReflectUtils() {
        throw new IllegalAccessError();
    }

    /**
     * Get the default constructor of a class.
     *
     * @param clazz the class
     * @param <S>   the type of the class
     * @return the constructor
     */
    public static <S> Constructor<S> getReflectiveConstructor(Class<S> clazz) {
        try {
            return clazz.getDeclaredConstructor();
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException(clazz + " needs a default constructor", e);
        }
    }

    /**
     * Get a default constructor without exceptions for a certain class. The constructor may throw an {@link IllegalStateException} if an exception occur during invoke.
     *
     * @param constructor the constructor with exceptions
     * @param <S>         the type of the object to be created
     * @return the constructor without exceptions
     */
    public static <S> Supplier<S> toConstructor(Constructor<S> constructor) {
        return () -> {
            try {
                constructor.setAccessible(true);
                return constructor.newInstance();
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        };
    }

    /**
     * Get a default constructor without exceptions for a certain class. The constructor may throw an {@link IllegalStateException} if an exception occur during invoke.
     *
     * @param constructor the constructor with exceptions
     * @return the constructor without exceptions
     */
    public static Supplier<Object> toRawConstructor(Constructor<?> constructor) {
        return () -> {
            try {
                constructor.setAccessible(true);
                return constructor.newInstance();
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        };
    }

    /**
     * Set a field of an object.
     *
     * @param target the object
     * @param field  the field
     * @param value  the value to set
     * @throws IllegalArgumentException if any exception occurs
     */
    public static void setField(Object target, Field field, Object value) {
        try {
            field.setAccessible(true);
            field.set(target, value);
        } catch (ReflectiveOperationException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

    /**
     * Get the value of a field of an object.
     *
     * @param target     the object
     * @param field      the field
     * @param resultType the return type
     * @param <T>        the return type
     * @return the value of the field
     * @throws IllegalArgumentException if any exception occurs
     */
    public static <T> T getField(Object target, Field field, Class<T> resultType) {
        try {
            field.setAccessible(true);
            var value = field.get(target);
            return value == null ? null : resultType.cast(value);
        } catch (ReflectiveOperationException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

    /**
     * Get the value of a field of an object as string.
     *
     * @param target the object
     * @param field  the field
     * @return the value as string
     * @throws IllegalArgumentException if any exception occurs
     */
    public static String getValue(Object target, Field field) {
        var value = getField(target, field, Object.class);
        return value == null ? null : String.format("%s", value);
    }
}

