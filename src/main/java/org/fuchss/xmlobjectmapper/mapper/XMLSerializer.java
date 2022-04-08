package org.fuchss.xmlobjectmapper.mapper;

import org.fuchss.xmlobjectmapper.XMLException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Defines the necessary function to create an XML node from an object.
 *
 * @author Dominik Fuchss
 */
@FunctionalInterface
public interface XMLSerializer {
	/**
	 * Serialize information from the current object to a xml node.
	 *
	 * @param document      the overall document (needed to create new nodes, elements and attributes)
	 * @param currentObject the object that shall be serialized
	 * @param currentNode   the destination node for serialization
	 * @throws XMLException if extraction of information failed
	 */
	void serializeObject(Document document, Object currentObject, Element currentNode) throws XMLException;
}
