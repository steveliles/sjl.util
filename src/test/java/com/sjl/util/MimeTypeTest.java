package com.sjl.util;

import static org.junit.Assert.*;

import org.junit.*;

public class MimeTypeTest {

	@Test
	public void testParsesMimeStringsToMimeTypeObjects() {
		assertEquals(new MimeType("test", "type"), MimeType.parse("test/type"));			
	}
	
	@Test
	public void testNormalizesMimeComponentsToLowerCase() {
		assertEquals(new MimeType("test", "type"), MimeType.parse("TeSt/TyPe"));
	}
	
	@Test
	public void testCachesMimeTypeObjectsForReuse() {
		assertSame(MimeType.parse("test/type"), MimeType.parse("test/type"));
		assertSame(MimeType.get("test", "type"), MimeType.parse("test/type"));
	}
	
	@Test
	public void testThrowsRuntimeExceptionWhenAskedToParseBadMimeString() {
		try {
			MimeType.parse("bad");
			fail("Expected an exception!");
		} catch(RuntimeException anExc) {
			// yippee
		}
	}
	
}
