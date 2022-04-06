package org.fuchss.xmlobjectmapper;

import org.fuchss.xmlobjectmapper.annotation.XMLClass;
import org.fuchss.xmlobjectmapper.util.ReflectUtils;

import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

sealed class XMLRegistry permits XMLObjectMapper {
	protected final Map<Class<?>, String> classToName;
	protected final Map<Class<?>, Supplier<Object>> classToConstructor;

	protected XMLRegistry() {
		classToName = new HashMap<>();
		classToConstructor = new HashMap<>();
	}

	public void registerClasses(Class<?>... classes) throws ReflectiveOperationException {
		for (Class<?> clazz : classes) {
			registerClass(clazz);
		}
	}

	public void registerClass(Class<?> clazz) throws ReflectiveOperationException {
		var annotation = clazz.getDeclaredAnnotation(XMLClass.class);
		if (annotation == null)
			throw new IllegalArgumentException("You have to declare " + XMLClass.class);
		if (!Modifier.isFinal(clazz.getModifiers())) {
			throw new IllegalArgumentException("XMLClasses have to be final. " + clazz + " is not final.");
		}
		var constructor = clazz.getDeclaredConstructor();
		if (!Modifier.isPublic(constructor.getModifiers())) {
			throw new IllegalArgumentException("XMLClasses need a public default constructor");
		}
		var name = annotation.name().isBlank() ? clazz.getName() : annotation.name();
		classToName.put(clazz, name);
		classToConstructor.put(clazz, ReflectUtils.toConstructor(constructor::newInstance));
	}
}
