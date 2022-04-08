package org.fuchss.xmlobjectmapper.annotation;

import org.fuchss.xmlobjectmapper.mapper.XMLMapper;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface XMLList {
	Class<?> elementType();

	String name() default "";

	Class<? extends XMLMapper> elementMapper() default XMLMapper.class;
}
