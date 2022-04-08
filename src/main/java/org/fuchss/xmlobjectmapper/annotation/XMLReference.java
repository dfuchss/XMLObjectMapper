package org.fuchss.xmlobjectmapper.annotation;

import org.fuchss.xmlobjectmapper.mapper.XMLMapper;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface XMLReference {
	boolean mandatory() default true;

	String name() default "";

	Class<? extends XMLMapper> mapper() default XMLMapper.class;
}
