package org.fuchss.xmlobjectmapper;

import org.fuchss.xmlobjectmapper.annotation.XMLList;
import org.fuchss.xmlobjectmapper.annotation.XMLReference;
import org.fuchss.xmlobjectmapper.annotation.XMLValue;
import org.fuchss.xmlobjectmapper.util.ReflectUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.fuchss.xmlobjectmapper.XMLExceptionGenerator.*;
import static org.fuchss.xmlobjectmapper.util.CommonUtils.*;

public final class Object2XML extends XMLRegistry {
	private static final DocumentBuilderFactory factory = getDocumentBuilderFactory();

	public Object2XML() {
		super();
	}

	public String serializeObject(Object o) throws ParserConfigurationException, TransformerException {
		if (!classToConstructor.containsKey(o.getClass()))
			classNotRegistered(o.getClass());

		var document = factory.newDocumentBuilder().newDocument();
		var root = document.createElement(classToName.get(o.getClass()));
		document.appendChild(root);
		serializeObject(document, o, root);
		document.normalize();

		Transformer transformer = getTransformerFactory().newTransformer();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		Result result = new StreamResult(bos);
		transformer.transform(new DOMSource(document), result);
		return bos.toString(StandardCharsets.UTF_8);
	}

	private void serializeObject(Document document, Object currentObject, Element currentNode) {
		if (!classToConstructor.containsKey(currentObject.getClass()))
			classNotRegistered(currentObject.getClass());

		for (Field field : currentObject.getClass().getDeclaredFields()) {
			var value = field.getDeclaredAnnotation(XMLValue.class);
			var reference = field.getDeclaredAnnotation(XMLReference.class);
			var list = field.getDeclaredAnnotation(XMLList.class);

			if (value == null && reference == null && list == null) {
				continue;
			}

			if (!uniqueNotNull(value, reference, list)) {
				multipleAnnotations(field, value, reference, list);
			}

			handleValue(currentObject, field, value, currentNode);
			handleReference(document, currentObject, field, reference, currentNode);
			handleList(document, currentObject, field, list, currentNode);
		}
	}

	private void handleValue(Object currentObject, Field field, XMLValue value, Element currentNode) {
		if (value == null)
			return;
		var type = field.getType();
		if (!isPrimitiveSupportedValue(type))
			throw new IllegalArgumentException("Unsupported Value Type. Use @" + XMLReference.class.getName() + " instead.");

		var key = value.name().isBlank() ? field.getName() : value.name();
		var objectValue = ReflectUtils.getValue(currentObject, field);
		if (objectValue == null)
			return;
		currentNode.setAttribute(key, objectValue);
	}

	private void handleReference(Document document, Object currentObject, Field field, XMLReference reference, Element currentNode) {
		if (reference == null)
			return;
		if (!classToConstructor.containsKey(field.getType()))
			classNotRegistered(field.getType());
		var xmlName = reference.name().isBlank() ? field.getName() : reference.name();

		var value = ReflectUtils.getField(currentObject, field, Object.class);
		if (value == null)
			return;

		var node = document.createElement(xmlName);
		currentNode.appendChild(node);
		serializeObject(document, value, node);
	}

	private void handleList(Document document, Object currentObject, Field field, XMLList list, Element currentNode) {
		if (list == null)
			return;
		if (field.getType() != List.class)
			notAList(field);
		if (!classToConstructor.containsKey(list.elementType()))
			classNotRegistered(list.elementType());

		List<?> listValue = ReflectUtils.getField(currentObject, field, List.class);
		if (listValue == null)
			return;

		var xmlName = list.name().isBlank() ? field.getName() : list.name();
		for (var object : listValue) {
			var node = document.createElement(xmlName);
			currentNode.appendChild(node);
			serializeObject(document, object, node);
		}

	}
}
