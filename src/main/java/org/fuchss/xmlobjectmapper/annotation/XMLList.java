package org.fuchss.xmlobjectmapper.annotation;

import org.fuchss.xmlobjectmapper.mapper.XMLMapper;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used to mark a field in a class as serialized as list of elements.<br>
 * <b>Important:</b> The annotated field must have the type {@code java.util.List&lt;T&gt;}
 *
 * @author Dominik Fuchss
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface XMLList {
	/**
	 * The content type of the annotated list.
	 *
	 * @return the content type of the list
	 */
	Class<?> elementType();

	/**
	 * The name of the tag used for list elements. By default, the simple name of the element type.
	 *
	 * @return the tag used for the list elements
	 */
	String name() default "";

	/**
	 * Here you can specify an arbitrary element mapper to serialize or deserialize the list contents. By default, this is done by iteration over the fields.
	 *
	 * @return a custom mapper
	 */
	Class<? extends XMLMapper> elementMapper() default XMLMapper.class;
}
