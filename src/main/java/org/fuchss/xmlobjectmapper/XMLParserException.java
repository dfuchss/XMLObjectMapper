package org.fuchss.xmlobjectmapper;

/**
 * The internal exception for errors during XML parsing.
 *
 * @author Dominik Fuchss
 */
final class XMLParserException extends RuntimeException {
	/**
	 * Create the exception with a message.
	 *
	 * @param message the message
	 */
	public XMLParserException(String message) {
		super(message);
	}
}
