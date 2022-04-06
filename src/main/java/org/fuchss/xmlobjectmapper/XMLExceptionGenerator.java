package org.fuchss.xmlobjectmapper;

import org.w3c.dom.Node;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public final class XMLExceptionGenerator {
	private XMLExceptionGenerator() {
		throw new IllegalAccessError();
	}

	public static void classNotRegistered(Class<?> clazz) {
		throw new XMLParserException("Class " + clazz.getName() + " is not registered");
	}

	public static void wrongTag(String expected, Node currentNode) {
		throw new XMLParserException("Got wrong type of node. Expected: " + expected + " Actual: " + currentNode.getNodeName());
	}

	public static void multipleAnnotations(Field field, Object... annotations) {
		throw new XMLParserException("Found multiple active annotations for field " + field + ": " + Arrays.toString(annotations));
	}

	public static void notAList(Field field) {
		throw new XMLParserException("Expected a field of type " + List.class + " but got " + field.getType());
	}

	public static void mandatory(Field f) {
		throw new XMLParserException("Field " + f + " is marked as mandatory, but no data has been found");
	}
}
