package org.fuchss.xmlobjectmapper.util;

import org.fuchss.xmlobjectmapper.annotation.XMLList;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerFactory;
import java.util.ArrayList;
import java.util.List;

/**
 * This class contains some common utilities.
 *
 * @author Dominik Fuchss
 */
public final class CommonUtils {
	private CommonUtils() {
		throw new IllegalAccessError();
	}

	/**
	 * Check whether a type belongs to the supported primitive types for {@link org.fuchss.xmlobjectmapper.annotation.XMLValue}
	 *
	 * @param type the type
	 * @return indicator whether supported
	 */
	public static boolean isPrimitiveSupportedValue(Class<?> type) {
		return List.of(Integer.class, Integer.TYPE, Double.class, Double.TYPE, String.class).contains(type);
	}

	/**
	 * Create a new DocumentBuilderFactory.
	 *
	 * @return a new DocumentBuilderFactory
	 */
	public static DocumentBuilderFactory getDocumentBuilderFactory() {
		try {
			var factory = DocumentBuilderFactory.newInstance();
			factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
			return factory;
		} catch (ParserConfigurationException e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	/**
	 * Create a new TransformerFactory.
	 *
	 * @return a new TransformerFactory
	 */
	public static TransformerFactory getTransformerFactory() {
		var factory = TransformerFactory.newInstance();
		factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
		factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, "");
		return factory;
	}

	/**
	 * Find all nodes with a certain tag.
	 *
	 * @param childNodes the list of nodes
	 * @param tagName    the tag
	 * @return all nodes with the certain tag
	 */
	public static List<Node> findMultipleNodes(NodeList childNodes, String tagName) {
		var nodes = new ArrayList<Node>();
		for (int i = 0; i < childNodes.getLength(); i++) {
			var node = childNodes.item(i);
			if (node.getNodeName().equals(tagName))
				nodes.add(node);
		}
		return nodes;
	}

	/**
	 * Find the one node wit a certain tag.
	 *
	 * @param childNodes the list of nodes
	 * @param tagName    the tag
	 * @return the found node or null if none found
	 * @throws IllegalArgumentException iff multiple nodes matches the tag
	 */
	public static Node findSingleNode(NodeList childNodes, String tagName) {
		var nodes = findMultipleNodes(childNodes, tagName);

		if (nodes.size() > 1) {
			throw new IllegalArgumentException("Found multiple nodes matching " + tagName + " use " + XMLList.class.getSimpleName() + " instead");
		}
		return nodes.isEmpty() ? null : nodes.get(0);
	}

	/**
	 * Check that exactly one value is null in a list of objects.
	 *
	 * @param values the objects to check
	 * @return indicator, that exactly one element is null
	 */
	public static boolean uniqueNotNull(Object... values) {
		boolean notNull = false;
		for (var o : values) {
			if (o != null && notNull)
				return false;
			if (o != null)
				notNull = true;
		}
		return notNull;
	}
}
