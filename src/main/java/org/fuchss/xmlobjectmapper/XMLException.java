package org.fuchss.xmlobjectmapper;

/**
 * This exception wraps all kinds of other exceptions during serialization or deserialization that belong to XML processing.
 *
 * @author Dominik Fuchss
 */
public final class XMLException extends RuntimeException {
	/**
	 * Wrap another exception.
	 *
	 * @param cause the cause for this exception
	 */
	public XMLException(Throwable cause) {
		super(cause.getMessage(), cause);
	}
}
