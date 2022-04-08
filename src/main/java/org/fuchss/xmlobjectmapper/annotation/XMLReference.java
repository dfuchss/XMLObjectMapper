package org.fuchss.xmlobjectmapper.annotation;

import org.fuchss.xmlobjectmapper.mapper.XMLMapper;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used to mark attributes to be serialized as sub elements of the current object.
 *
 * @author Dominik Fuchss
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface XMLReference {
	/**
	 * Indicates whether this attribute is mandatory for serialization (not null).
	 *
	 * @return indicator, defaults to true
	 */
	boolean mandatory() default true;

	/**
	 * Defines the tag name of the serialized attribute. Defaults to the simple class name of the attribute.
	 *
	 * @return the tag name of the serialized attribute
	 */
	String name() default "";

	/**
	 * Define a custom mapper for serialize and deserialize the field. By default, this will be done using the fields of the attribute
	 *
	 * @return a custom mapper
	 */
	Class<? extends XMLMapper> mapper() default XMLMapper.class;
}
