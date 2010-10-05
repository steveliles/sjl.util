package com.sjl.util.strings;

import static org.junit.Assert.*;

import org.junit.*;

public class StringTokenIteratorTest {

	@Test
	public void testTokenizesStringsUsingRegularExpressions() throws Exception {
		
		StringTokenIterator _i = new StringTokenIterator("abc 123 abc 123", "[0-9]+");

		assertTrue(_i.hasNext());
		
		StringTokenIterator.Token _t = _i.next();
		assertFalse(_t.isMatch());
		assertEquals("abc ", _t.getString());
		
		assertTrue(_i.hasNext());
		_t = _i.next();
		assertTrue(_t.isMatch());
		assertEquals("123", _t.getString());
		
		assertTrue(_i.hasNext());
		_t = _i.next();
		assertFalse(_t.isMatch());
		assertEquals(" abc ", _t.getString());
		
		assertTrue(_i.hasNext());
		_t = _i.next();
		assertTrue(_t.isMatch());
		assertEquals("123", _t.getString());
		
		assertFalse(_i.hasNext());
	}

	@Test
	public void testReturnsEntireInputStringIfNoMatches() throws Exception {
		StringTokenIterator _i = new StringTokenIterator("steve is nice", "[0-9]+");
		assertTrue(_i.hasNext());
		assertEquals("steve is nice", _i.next().getString());
		assertFalse(_i.hasNext());
	}

	@Test
	public void testPlaysNicelyWithEmptyStrings() throws Exception {
		StringTokenIterator _i = new StringTokenIterator("", "");
		assertTrue(_i.hasNext());
		assertEquals("", _i.next().toString());
		assertFalse(_i.hasNext());
	}

	@Test
	public void testWorksWhenEntireInputMatches() {
		StringTokenIterator _i = new StringTokenIterator("123", "[0-9]+");
		assertTrue(_i.hasNext());
		assertEquals("123", _i.next().toString());
		assertFalse(_i.hasNext());
	}

	@Test
	public void testWorksWhenInputConsistsOfOnlyMatches() {
		StringTokenIterator _i = new StringTokenIterator("123123123", "[0-9]{3}");
		assertTrue(_i.hasNext());
		assertEquals("123", _i.next().toString());
		assertTrue(_i.hasNext());
		assertEquals("123", _i.next().toString());
		assertTrue(_i.hasNext());
		assertEquals("123", _i.next().toString());
		assertFalse(_i.hasNext());
	}

}
