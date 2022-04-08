package org.fuchss.xmlobjectmapper.mapper;

import org.fuchss.xmlobjectmapper.XMLException;
import org.w3c.dom.Node;

/**
 * Defines the necessary function to create an object from an XML node.
 *
 * @author Dominik Fuchss
 */
@FunctionalInterface
public interface XMLParser {
	/**
	 * Parse information from the current xml node to the current object.
	 *
	 * @param currentObject      the destination object
	 * @param currentExpectedTag the expected tag in the xml file
	 * @param currentNode        the current node with all information on the object
	 * @throws XMLException if parse error occurs
	 */
	void parseXML(Object currentObject, String currentExpectedTag, Node currentNode) throws XMLException;
}
