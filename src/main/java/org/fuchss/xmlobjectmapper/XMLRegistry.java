package org.fuchss.xmlobjectmapper;

import org.fuchss.xmlobjectmapper.annotation.XMLClass;
import org.fuchss.xmlobjectmapper.util.ReflectUtils;

import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * This class handles custom classes that have to be serialized or deserialized.
 *
 * @author Dominik Fuchss
 */
sealed class XMLRegistry permits Object2XML, XML2Object {
	/**
	 * A mapping from registered classes to their tag.
	 */
	protected final Map<Class<?>, String> classToTag;
	/**
	 * A mapping from registered classes to their default constructor.
	 */
	protected final Map<Class<?>, Supplier<Object>> classToConstructor;

	/**
	 * Create the new registry.
	 */
	protected XMLRegistry() {
		classToTag = new HashMap<>();
		classToConstructor = new HashMap<>();
	}

	/**
	 * Register several classes for serialization or deserialization. Registered classes need a public default constructor and have to be final.
	 *
	 * @param classes the classes
	 * @throws ReflectiveOperationException if regarding reflection fails
	 * @see #registerClass(Class)
	 */
	public void registerClasses(Class<?>... classes) throws ReflectiveOperationException {
		for (Class<?> clazz : classes) {
			registerClass(clazz);
		}
	}

	/**
	 * Register a new class serialization or deserialization. Registered classes need a public default constructor and have to be final.
	 *
	 * @param clazz the class
	 * @throws ReflectiveOperationException if regarding reflection fails
	 */
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
		classToTag.put(clazz, name);
		classToConstructor.put(clazz, ReflectUtils.toConstructor(constructor::newInstance));
	}
}
