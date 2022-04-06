package org.fuchss.xmlobjectmapper.util;

import org.fuchss.xmlobjectmapper.annotation.XMLList;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.util.ArrayList;
import java.util.List;

public final class CommonUtils {
	private CommonUtils() {
		throw new IllegalAccessError();
	}

	public static boolean isPrimitiveSupportedValue(Class<?> type) {
		return List.of(Integer.class, Integer.TYPE, Double.class, Double.TYPE, String.class).contains(type);
	}

	public static DocumentBuilderFactory getFactory() {
		try {
			var factory = DocumentBuilderFactory.newInstance();
			factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
			return factory;
		} catch (ParserConfigurationException e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	public static List<Node> findMultipleNodes(NodeList childNodes, String tagName) {
		var nodes = new ArrayList<Node>();
		for (int i = 0; i < childNodes.getLength(); i++) {
			var node = childNodes.item(i);
			if (node.getNodeName().equals(tagName))
				nodes.add(node);
		}
		return nodes;
	}

	public static Node findSingleNode(NodeList childNodes, String tagName) {
		var nodes = findMultipleNodes(childNodes, tagName);

		if (nodes.size() > 1) {
			throw new IllegalArgumentException("Found multiple nodes matching " + tagName + " use " + XMLList.class.getSimpleName() + " instead");
		}
		return nodes.isEmpty() ? null : nodes.get(0);
	}

	public static boolean uniqueNotNull(Object... values) {
		boolean notNull = false;
		for (var o : values) {
			if (o != null && notNull)
				return false;
			if (o != null)
				notNull = true;
		}
		return true;
	}
}
