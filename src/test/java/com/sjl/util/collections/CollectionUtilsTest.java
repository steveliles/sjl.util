package com.sjl.util.collections;

import static com.sjl.util.collections.Lists.*;
import static com.sjl.util.collections.Maps.*;
import static com.sjl.util.collections.Sets.*;
import static org.junit.Assert.*;

import java.util.*;

import org.junit.*;

import com.sjl.util.strings.*;

public class CollectionUtilsTest {

	@Test
	public void testMakesDeclaringGenericCollectionsEasier() {
		List<String> _l = newList();
		assertNotNull(_l);

		Set<String> _s = newSet();
		assertNotNull(_s);

		Map<String, String> _m = newMap();
		assertNotNull(_m);
	}

	@Test
	public void testAllowsSingleStepMapCreationAndPopulation() {
		// from arrays (and varargs)
		Map<String, String> _m1 = newMap(
			"noun,verb,adjective".split(","),
			"steve,runs,fast".split(",")
		);
		assertEquals("steve", _m1.get("noun"));
		assertEquals("runs", _m1.get("verb"));
		assertEquals("fast", _m1.get("adjective"));
	
	    // from Iterables
		Map<String, String> _m2 = Maps.newMap(
			Lists.newList("noun", "verb", "adjective"),
			Lists.newList("steve", "runs", "fast")
		);
		assertEquals(_m1, _m2);
	}

	@Test
	public void testAllowsSingleStepListCreationAndPopulation() {
		// from varargs
		List<String> _l1 = newList("steve", "is", "cool");
		assertEquals("steve", _l1.get(0));
		assertEquals("is", _l1.get(1));
		assertEquals("cool", _l1.get(2));
		
		// from arrays
		List<String> _l2 = newList("steve is cool".split(" "));
		assertEquals(_l1, _l2);
		
		// from iterables
		List<String> _l3 = newList(_l1);
		assertEquals(_l2, _l3);
	}
	
	@Test
	public void testAllowsSingleStepSetCreationAndPopulation() {
		// from varargs
		Set<String> _s1 = newSet("steve", "is", "cool");
		assertTrue(_s1.contains("steve"));
		assertTrue(_s1.contains("is"));
		assertTrue(_s1.contains("cool"));
		
		// from arrays
		Set<String> _s2 = newSet("steve is cool".split(" "));
		assertEquals(_s1, _s2);
		
		// from iterables
		Set<String> _s3 = newSet(_s1);
		assertEquals(_s2, _s3);
	}
	
	@Test
	public void testAllowsSetPopulationByStringCapture() {		
		Set<String> _s1 = Strings.captureAsSet("steve is cool", "(\\S*) (\\S*) (\\S*)");
		assertTrue(_s1.contains("steve"));
		assertTrue(_s1.contains("is"));
		assertTrue(_s1.contains("cool"));
	}
	
	@Test
	public void testAllowsSetPopulationByStringSplit() {		
		Set<String> _s1 = Strings.splitAsSet("steve is cool", "\\s+");
		assertTrue(_s1.contains("steve"));
		assertTrue(_s1.contains("is"));
		assertTrue(_s1.contains("cool"));
	}
}
