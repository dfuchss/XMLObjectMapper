package org.fuchss.xmlobjectmapper;

import org.fuchss.xmlobjectmapper.example.IntegrationObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

class XMLObjectTest {

	@Test
	@DisplayName("Check whether Parse works")
	void parseTest() throws Exception {
		XML2Object xom = new XML2Object();
		xom.registerClasses(IntegrationObject.class, IntegrationObject.Complex.class);
		IntegrationObject data = xom.parseXML(XMLObjectTest.class.getResourceAsStream("/simple.xml"), IntegrationObject.class);
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

	@Test
	@DisplayName("Check whether Serialize works")
	void serializeTest() throws Exception {
		IntegrationObject data = new IntegrationObject();
		data.a = "X";
		data.b = "Y";
		data.complex = new IntegrationObject.Complex();
		data.complex.c = "CC";
		data.cpx = List.of(new IntegrationObject.Complex(), new IntegrationObject.Complex());

		Object2XML o2x = new Object2XML();
		o2x.registerClasses(IntegrationObject.class, IntegrationObject.Complex.class);
		var result = o2x.serializeObject(data);
		Assertions.assertNotNull(result);
	}

	@Test
	@DisplayName("Check whether Parse(Serialize(X)) == X")
	void serializeAndDeserializeTest() throws Exception {
		IntegrationObject data = new IntegrationObject();
		data.a = "X";
		data.b = "Y";
		data.complex = new IntegrationObject.Complex();
		data.complex.c = "CC";
		var complex = new IntegrationObject.Complex();
		complex.c = "434";
		complex.myComplex = data.complex;
		data.cpx = List.of(complex, new IntegrationObject.Complex());
		System.out.println(data);

		Object2XML o2x = new Object2XML();
		o2x.registerClasses(IntegrationObject.class, IntegrationObject.Complex.class);
		var result = o2x.serializeObject(data);
		System.out.println(result);

		XML2Object x2o = new XML2Object();
		x2o.registerClasses(IntegrationObject.class, IntegrationObject.Complex.class);
		IntegrationObject dataCopy = x2o.parseXML(new ByteArrayInputStream(result.getBytes(StandardCharsets.UTF_8)), IntegrationObject.class);
		System.out.println(dataCopy);

		Assertions.assertEquals(data, dataCopy);
	}
}