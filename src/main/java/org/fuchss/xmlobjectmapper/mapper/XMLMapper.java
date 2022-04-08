package org.fuchss.xmlobjectmapper.mapper;

import org.fuchss.xmlobjectmapper.XMLException;
import org.w3c.dom.Node;

@FunctionalInterface
public interface XMLMapper {
	void parseXML(Object currentObject, String currentExpectedTag, Node currentNode) throws XMLException;
}
