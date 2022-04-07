package org.fuchss.xmlobjectmapper.example;

import org.fuchss.xmlobjectmapper.annotation.XMLClass;
import org.fuchss.xmlobjectmapper.annotation.XMLList;
import org.fuchss.xmlobjectmapper.annotation.XMLReference;
import org.fuchss.xmlobjectmapper.annotation.XMLValue;

import java.util.List;
import java.util.Objects;

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

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		IntegrationObject that = (IntegrationObject) o;
		return Objects.equals(a, that.a) && Objects.equals(b, that.b) && Objects.equals(complex, that.complex) && Objects.equals(cpx, that.cpx);
	}

	@Override
	public int hashCode() {
		return Objects.hash(a, b, complex, cpx);
	}

	@XMLClass(name = "complex")
	public final static class Complex {
		@XMLValue(mandatory = false)
		public String c;
		@XMLReference(mandatory = false)
		public Complex myComplex;

		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (o == null || getClass() != o.getClass())
				return false;
			Complex complex = (Complex) o;
			return Objects.equals(c, complex.c) && Objects.equals(myComplex, complex.myComplex);
		}

		@Override
		public int hashCode() {
			return Objects.hash(c, myComplex);
		}

		@Override
		public String toString() {
			return "Complex{" + "c='" + c + '\'' + ", myComplex=" + myComplex + '}';
		}
	}
}
