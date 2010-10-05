package com.sjl.util.collections;

import static com.sjl.util.collections.Maps.*;
import static com.sjl.util.collections.Sets.*;
import static org.junit.Assert.*;

import java.util.*;
import java.util.Map.*;

import org.junit.*;

import com.sjl.util.*;

public class FuncMapTest {

	private FuncMap<String, String> map;
	
	@Before
	public void setup() {
		map = newMap(
			"first,middle,last".split(","), 
			"steven,john,liles".split(",")
		);
	}
	
	@Test
	public void testCanApplyTasksToEntries() {
		assertEquals(newSet("first=steven,middle=john,last=liles".split(",")),
			map.each(new Task<Entry<String,String>, Set<String>>(new FuncSet<String>()) {
				public void perform(Entry<String,String> anEntry) {
					result.add(String.format("%s=%s", anEntry.getKey(), anEntry.getValue()));
				}
			}));
	}
	
	@Test
	public void testCanApplyTasksToKeys() {
		assertEquals(newSet("first,middle,last".split(",")), 
			map.eachKey(new Task<String, Set<String>>(new FuncSet<String>()) {
				public void perform(String aKey) {
					result.add(aKey);
				}
			}));
	}
	
	@Test
	public void testCanApplyTasksToValues() {
		assertEquals(newSet("steven,john,liles".split(",")), 
			map.eachValue(new Task<String, Set<String>>(new FuncSet<String>()) {
				public void perform(String aKey) {
					result.add(aKey);
				}
			}));
	}
	
	@Test
	public void testCanFindMatch() {	
		assertEquals("john", map.find(new Matcher<Entry<String, String>>() {
				public boolean matches(Entry<String, String> anEntry) {
					return "middle".equals(anEntry.getKey());
				}
			}).getValue());
	}
	
	public void testCanCollectMatches() {
		FuncMap<String,String> _expected = newMap(map);
		_expected.remove("last");
		
		// make sure we didn't alter "map" by changing "expected"
		assertEquals(2, _expected.size());
		assertEquals(3, map.size());
		
		assertEquals(_expected, 
			map.collect(new Matcher<Entry<String,String>>() {
				public boolean matches(Entry<String,String> anEntry) {
					return !("middle".equals(anEntry.getKey()));
				}
			}));
	}
	
	@Test
	public void testCanDiscardMatches() {
		FuncMap<String, String> _expect = newMap(
			"first,last".split(","),
			"steven,liles".split(",")
		);
		
		assertEquals(_expect, map.filter(new Matcher<Entry<String,String>>() {
			public boolean matches(Entry<String, String> anEntry) {
				return "middle".equals(anEntry.getKey());
			}
		}));
	}
	
	@Test
	public void testCanCreateMissingEntriesUsingASuppliedFactory() {
		FuncMap<String,String> _expected = newMap(map);
		_expected.put("title", "mr");
		
		assertNull(map.get("title"));
		
		assertEquals("mr", map.retrieve("title", new ValueFactory<String,String>() {
			public String create(String aKey) {
				return "mr";
			}
		}));
		
		assertEquals("mr", map.get("title"));
		assertEquals(_expected, map);
	}
}
