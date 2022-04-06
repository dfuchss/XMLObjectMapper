package org.fuchss.xmlobjectmapper;

import org.fuchss.xmlobjectmapper.example.IntegrationObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class XMLObjectMapperTest {
	@Test
	void integrationTest() throws Exception {
		XMLObjectMapper xom = new XMLObjectMapper();
		xom.registerClasses(IntegrationObject.class, IntegrationObject.Complex.class);
		IntegrationObject data = xom.parseXML(XMLObjectMapperTest.class.getResourceAsStream("/simple.xml"), IntegrationObject.class);
		System.out.println(data);

		Assertions.assertEquals("a", data.a);
		Assertions.assertEquals("bc", data.b);
		Assertions.assertEquals("MySpecialValue", data.complex.c);
		Assertions.assertNull(data.complex.myComplex);
		Assertions.assertEquals(2, data.cpx.size());
		Assertions.assertEquals("x", data.cpx.get(0).c);
		Assertions.assertEquals("abcd", data.cpx.get(0).myComplex.c);
		Assertions.assertEquals("y", data.cpx.get(1).c);
		Assertions.assertNull(data.cpx.get(1).myComplex);
	}
}