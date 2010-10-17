package com.sjl.util.strings;

import static junit.framework.Assert.*;

import org.junit.*;

public class StringsTest {

	@Test
	public void joinConcatenatesOnlyNonNullValues()
	throws Exception {
		String _result = Strings.joinWithDelimiter(".", "steve", "is", "cool", null, null, "and", null, "nice", null);
		assertEquals("steve.is.cool.and.nice", _result);
		
		_result = Strings.joinWithDelimiter(".", null, null, "steve", null, "is", "cool", null, null, "and", null, "nice");
		assertEquals("steve.is.cool.and.nice", _result);
	}
	
	@Test
	public void isEmptyChecksForNonNullAndNonEmptyStrings()
	throws Exception {
		assertTrue(Strings.isEmpty(null));
		assertTrue(Strings.isEmpty(" "));
		assertTrue(Strings.isEmpty("       "));
		assertFalse(Strings.isEmpty(" ! "));
	}
	
	@Test
	public void findsFirstNonEmptyStringInGivenArray()
	throws Exception {
		assertEquals("steve", Strings.firstNonEmpty(null, "", "", "steve", "hello", null));
		assertEquals("steve", Strings.firstNonEmpty("steve"));
		assertEquals("steve", Strings.firstNonEmpty("", "steve", ""));
	}
	
}
