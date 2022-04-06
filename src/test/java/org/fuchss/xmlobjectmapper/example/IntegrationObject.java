package org.fuchss.xmlobjectmapper.example;

import org.fuchss.xmlobjectmapper.annotation.XMLClass;
import org.fuchss.xmlobjectmapper.annotation.XMLList;
import org.fuchss.xmlobjectmapper.annotation.XMLReference;
import org.fuchss.xmlobjectmapper.annotation.XMLValue;

import java.util.List;

@XMLClass(name = "simple")
public final class IntegrationObject {
	@XMLValue
	public String a;
	@XMLValue
	public String b;

	@XMLReference
	public Complex complex;

	@XMLList(elementType = Complex.class)
	public List<Complex> cpx;

	@Override
	public String toString() {
		return "SimpleAttributes{" + "a='" + a + '\'' + ", b='" + b + '\'' + ", complex=" + complex + ", cpx=" + cpx + '}';
	}

	@XMLClass(name = "complex")
	public final static class Complex {
		@XMLValue(mandatory = false)
		public String c;
		@XMLReference(mandatory = false)
		public Complex myComplex;

		@Override
		public String
		toString() {
			return "Complex{" + "c='" + c + '\'' + ", myComplex=" + myComplex + '}';
		}
	}
}
