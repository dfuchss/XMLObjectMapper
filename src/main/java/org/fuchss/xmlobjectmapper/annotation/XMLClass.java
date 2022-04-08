package org.fuchss.xmlobjectmapper.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used to mark classes a potential root elements of a xml file.
 *
 * @author Dominik Fuchss
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface XMLClass {
	/**
	 * The name of the tag if a class is used as root element. By default, the simple class name of the annotated class.
	 *
	 * @return the name of the tag
	 */
	String name() default "";
}
