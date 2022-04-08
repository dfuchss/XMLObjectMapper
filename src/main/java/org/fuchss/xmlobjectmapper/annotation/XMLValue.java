package org.fuchss.xmlobjectmapper.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used to mark a primitive attribute of a class to be serialized as an attribute in the surrounding xml element.
 *
 * @author Dominik Fuchss
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface XMLValue {
	/**
	 * The name of the attribute. Defaults to the attribute name in java.
	 *
	 * @return the name of the attribute in the xml file
	 */
	String name() default "";

	/**
	 * Indicates whether this attribute is mandatory (not null).
	 *
	 * @return indicator, defaults to true
	 */
	boolean mandatory() default true;
}
