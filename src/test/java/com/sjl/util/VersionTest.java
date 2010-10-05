package com.sjl.util;

import static junit.framework.Assert.*;

import org.junit.*;

public class VersionTest {

	@Test
	public void testParsesVersionStrings() {
		Version _v = new Version("0.1.2.3 (fluffy-ferret)", Version.FULL_VERSION_PATTERN);
		assertEquals("0", _v.getMajorVersion());
		assertEquals("1", _v.getMinorVersion());
		assertEquals("2", _v.getMaintenanceVersion());
		assertEquals("3", _v.getBuildNumber());
		assertEquals("fluffy-ferret", _v.getCodeName());
		
		_v = new Version("0.1");
		assertEquals("0", _v.getMajorVersion());
		assertEquals("1", _v.getMinorVersion());		
	}
	
	@Test
	public void testToStringsPrettily() {
		Version _v = new Version("0", "1", "2", "3", "silly-billy");
		assertEquals("0.1.2.3 (silly-billy)", _v.toString());
		
		_v = new Version("1.0");
		assertEquals("1.0", _v.toString());
	}
	
}
