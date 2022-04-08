package org.fuchss.xmlobjectmapper;

import org.w3c.dom.Node;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * This class helps to create some default exceptions.
 *
 * @author Dominik Fuchss
 */
final class XMLExceptionGenerator {
	private XMLExceptionGenerator() {
		throw new IllegalAccessError();
	}

	/**
	 * A class was not registered to the {@link XMLRegistry}.
	 *
	 * @param clazz the class
	 */
	public static void classNotRegistered(Class<?> clazz) {
		throw new XMLParserException("Class " + clazz.getName() + " is not registered");
	}

	/**
	 * The tag in an XML string has not matched the expected tag.
	 *
	 * @param expected    the expected tag
	 * @param currentNode the node with the wrong tag
	 */
	public static void wrongTag(String expected, Node currentNode) {
		throw new XMLParserException("Got wrong type of node. Expected: " + expected + " Actual: " + currentNode.getNodeName());
	}

	/**
	 * Multiple XML annotations on a field has been detected but only one was allowed.
	 *
	 * @param field       the field
	 * @param annotations the found annotations
	 */
	public static void multipleAnnotations(Field field, Object... annotations) {
		throw new XMLParserException("Found multiple active annotations for field " + field + ": " + Arrays.stream(annotations).filter(Objects::nonNull).toList());
	}

	/**
	 * A field was marked with {@link org.fuchss.xmlobjectmapper.annotation.XMLList} but has not the type {@link List}.
	 *
	 * @param field the field
	 */
	public static void notAList(Field field) {
		throw new XMLParserException("Expected a field of type " + List.class + " but got " + field.getType());
	}

	/**
	 * A field is marked as mandatory but its value is not present
	 *
	 * @param field the field
	 */
	public static void mandatory(Field field) {
		throw new XMLParserException("Field " + field + " is marked as mandatory, but no data has been found");
	}
}
