package org.fuchss.xmlobjectmapper;

import org.fuchss.xmlobjectmapper.annotation.XMLList;
import org.fuchss.xmlobjectmapper.annotation.XMLReference;
import org.fuchss.xmlobjectmapper.annotation.XMLValue;
import org.fuchss.xmlobjectmapper.util.ReflectUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.fuchss.xmlobjectmapper.XMLExceptionGenerator.*;
import static org.fuchss.xmlobjectmapper.util.CommonUtils.*;

public final class XML2Object extends XMLRegistry {
	private static final DocumentBuilderFactory factory = getDocumentBuilderFactory();

	public XML2Object() {
		super();
	}

	public <E> E parseXML(InputStream xml, Class<E> target) throws IOException, ParserConfigurationException, SAXException {
		var builder = factory.newDocumentBuilder();
		Document doc = builder.parse(xml);
		doc.getDocumentElement().normalize();

		Object rootNode = classToConstructor.get(target).get();
		parseXML(rootNode, classToName.get(target), doc.getDocumentElement());
		return target.cast(rootNode);
	}

	private void parseXML(Object targetElement, String tagName, Node currentNode) {
		if (tagName == null) {
			classNotRegistered(targetElement.getClass());
		}
		if (!Objects.equals(currentNode.getNodeName(), tagName)) {
			wrongTag(tagName, currentNode);
		}

		for (var field : targetElement.getClass().getDeclaredFields()) {
			var value = field.getDeclaredAnnotation(XMLValue.class);
			var reference = field.getDeclaredAnnotation(XMLReference.class);
			var list = field.getDeclaredAnnotation(XMLList.class);

			if (value == null && reference == null && list == null) {
				continue;
			}

			if (!uniqueNotNull(value, reference, list)) {
				multipleAnnotations(field, value, reference, list);
			}

			handleValue(targetElement, field, value, currentNode.getAttributes());
			handleReference(targetElement, field, reference, currentNode.getChildNodes());
			handleList(targetElement, field, list, currentNode.getChildNodes());
		}
	}

	private void handleList(Object targetElement, Field field, XMLList list, NodeList childNodes) {
		if (list == null)
			return;
		if (field.getType() != List.class)
			notAList(field);
		if (!classToConstructor.containsKey(list.elementType()))
			classNotRegistered(list.elementType());

		setXMLList(targetElement, field, list, childNodes);
	}

	private void handleReference(Object targetElement, Field field, XMLReference reference, NodeList childNodes) {
		if (reference == null)
			return;
		if (!classToConstructor.containsKey(field.getType()))
			classNotRegistered(field.getType());

		setXMLReference(targetElement, field, reference, childNodes);
	}

	private void handleValue(Object targetElement, Field field, XMLValue value, NamedNodeMap attributes) {
		if (value == null)
			return;

		setXMLValue(targetElement, field, value, attributes);
	}

	private void setXMLReference(Object target, Field field, XMLReference reference, NodeList childNodes) {
		var type = field.getType();
		var xmlName = reference.name().isBlank() ? field.getName() : reference.name();
		var node = findSingleNode(childNodes, xmlName);

		if (node == null && reference.mandatory())
			mandatory(field);

		if (node == null)
			return;

		Object value = classToConstructor.get(type).get();
		ReflectUtils.setField(target, field, value);

		XMLMapper mapper = reference.mapper() != XMLMapper.class //
				? ReflectUtils.toConstructor(ReflectUtils.getReflectiveConstructor(reference.mapper())).get() //
				: this::parseXML;
		mapper.parseXML(value, xmlName, node);
	}

	private void setXMLList(Object target, Field field, XMLList list, NodeList childNodes) {
		var xmlName = list.name().isBlank() ? field.getName() : list.name();
		var nodes = findMultipleNodes(childNodes, xmlName);

		List<Object> targetList = new ArrayList<>();
		ReflectUtils.setField(target, field, targetList);

		if (nodes.isEmpty())
			return;

		var valueConstructor = classToConstructor.get(list.elementType());
		XMLMapper mapper = list.elementMapper() != XMLMapper.class //
				? ReflectUtils.toConstructor(ReflectUtils.getReflectiveConstructor(list.elementMapper())).get() //
				: this::parseXML;

		for (var node : nodes) {
			var newValue = valueConstructor.get();
			mapper.parseXML(newValue, xmlName, node);
			targetList.add(newValue);
		}
	}

	private void setXMLValue(Object target, Field field, XMLValue value, NamedNodeMap attributes) {
		var key = value.name().isBlank() ? field.getName() : value.name();
		var node = attributes.getNamedItem(key);

		if (node == null && value.mandatory()) {
			mandatory(field);
		}
		if (node == null) {
			return;
		}

		String data = node.getNodeValue();
		setValue(target, field, data);
	}

	private void setValue(Object target, Field field, String value) {
		var type = field.getType();
		if (!isPrimitiveSupportedValue(type))
			throw new IllegalArgumentException("Unsupported Value Type. Use @" + XMLReference.class.getName() + " instead.");

		if (type == String.class) {
			ReflectUtils.setField(target, field, value);
		} else if (type == Integer.class || type == Integer.TYPE) {
			ReflectUtils.setField(target, field, Integer.parseInt(value));
		} else if (type == Double.class || type == Double.TYPE) {
			ReflectUtils.setField(target, field, Double.parseDouble(value));
		}
	}

}
