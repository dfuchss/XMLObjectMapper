package org.fuchss.xmlobjectmapper;

import org.w3c.dom.Node;

@FunctionalInterface
public interface XMLMapper {
	void parseXML(Object currentObject, String currentExpectedTag, Node currentNode);
}
