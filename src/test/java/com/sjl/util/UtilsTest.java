package com.sjl.util;

import static org.junit.Assert.*;

import org.junit.*;

public class UtilsTest {

	@Test
	public void testCanSelectFirstNonNullObject() {
		assertEquals("steve", Utils.firstNonNull(null, null, null, "steve", "foo"));
	    assertEquals("steve", Utils.firstNonNull("steve", "foo"));
	    assertEquals("foo", Utils.firstNonNull("foo"));
	}
	
	@Test
	public void testCompact() {
		assertArrayEquals(new String[]{"steve", "is", "cool"}, Utils.compact("steve", null, null, "is", null, "cool", null));
	}

	@Test
	public void testContainsAnyNulls() {
		assertFalse(Utils.containsAnyNulls(new String[]{"steve"}));
		assertFalse(Utils.containsAnyNulls(new String[]{"steve", "is"}));
		assertFalse(Utils.containsAnyNulls(new String[]{"steve", "is", "cool"}));
		assertFalse(Utils.containsAnyNulls(new String[]{}));
		
		assertTrue(Utils.containsAnyNulls(new String[]{null, "is", "cool"}));
		assertTrue(Utils.containsAnyNulls(new String[]{"steve", null, "cool"}));
		assertTrue(Utils.containsAnyNulls(new String[]{"steve", "is", null}));
		assertTrue(Utils.containsAnyNulls(new String[]{null}));
	}

	
}
