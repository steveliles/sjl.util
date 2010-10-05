package com.sjl.util.strings;

import static junit.framework.Assert.*;

import java.util.regex.*;

import org.junit.*;

import com.sjl.util.*;
import com.sjl.util.collections.*;
import com.sjl.util.testing.*;
import com.sjl.util.testing.ThroughputComparison.*;

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
	
	@Test
	public void providesFasterRepeatIterationsOfMatches()
	throws Exception {		
		
		ThroughputComparison _comparison = new ThroughputComparison();
		
		Throughput _t1 = _comparison.add(new Worker("Strings"){
			public void performOneIteration() {
				Strings.matches("steve is cool", "[s,t,e,v]+\\s[i,s]+\\s[c,o,l]+");				
			}
		});

		Throughput _t2 = _comparison.add(new Worker("Pattern"){
			public void performOneIteration() {
				Pattern.matches("steve is cool", "[s,t,e,v]+\\s[i,s]+\\s[c,o,l]+");	
			}			
		});

		_comparison.testConcurrentlyFor(500);
		
//		System.out.println(_t1);
//		System.out.println(_t2);
		
		assertTrue(_t1.getCompletedIterationCount() > _t2.getCompletedIterationCount());
	}
	
	@Test
	public void testMapsStringValuesUsingRegexSplit() {
		FuncMap<String, String> _map = Strings.splitAsMap("steve is cool", " ", "steve is cool".split(" "));
		assertEquals("steve", _map.get("steve"));
		assertEquals("is", _map.get("is"));
		assertEquals("cool", _map.get("cool"));
	}

	@Test
	public void testMapsOnlyThoseValuesForWhichWeProvideSufficientKeys() {
		FuncMap<String, String> _map = Strings.splitAsMap("steve is cool", " ", "steve is".split(" "));
		assertEquals("steve", _map.get("steve"));
		assertEquals("is", _map.get("is"));
		assertNull(_map.get("cool"));
	}
	
	@Test
	public void testDoesNotMapKeysForWhichNoValueIsAvailable() {
		FuncMap<String, String> _map = Strings.splitAsMap("steve is", " ", "steve is cool".split(" "));
		assertEquals("steve", _map.get("steve"));
		assertEquals("is", _map.get("is"));
		assertFalse(_map.containsKey("cool"));
	}	

	@Test
	public void testMapsStringValuesUsingRegexCapture() {
		FuncMap<String, String> _map = Strings.captureAsMap("steve is cool", "([a-z]*) ([a-z]*) ([a-z]*)", "steve is cool".split(" "));
		assertEquals("steve", _map.get("steve"));
		assertEquals("is", _map.get("is"));
		assertEquals("cool", _map.get("cool"));
	}

	@Test
	public void testCapturesOnlyThoseValuesForWhichWeProvideSufficientKeys() {
		FuncMap<String, String> _map = Strings.captureAsMap("steve is cool", "([a-z]*) ([a-z]*) ([a-z]*)", "steve is".split(" "));
		assertEquals("steve", _map.get("steve"));
		assertEquals("is", _map.get("is"));
		assertNull(_map.get("cool"));
	}	
	
	@Test
	public void testUsesValueFactoryToCreateValuesWhereMissing() {
		ValueFactory<String, String> _vf = new ValueFactory<String, String>(){
			public String create(String aKey) {
				return aKey;
			}			
		};
		FuncMap<String, String> _map = Strings.captureAsMap("steve is", "([a-z]*) ([a-z]*)", _vf, "steve is cool".split(" "));
		assertEquals("steve", _map.get("steve"));
		assertEquals("is", _map.get("is"));
		assertEquals("cool", _map.get("cool"));
	}
}
