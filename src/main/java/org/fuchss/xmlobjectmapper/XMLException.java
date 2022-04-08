package org.fuchss.xmlobjectmapper;

public final class XMLException extends RuntimeException {
	public XMLException(Throwable cause) {
		super(cause.getMessage(), cause);
	}
}
