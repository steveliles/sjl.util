package com.sjl.util.collections;

import static com.sjl.util.collections.Sets.*;
import static org.junit.Assert.*;

import java.util.*;

import org.junit.*;

public class FuncSetTest {

	@Test
	public void testCanApplyTaskToEachEntry() {
		FuncSet<String> _l = newSet("steve", "is", "cool");
		
 		assertEquals(_l, _l.each(new Task<String, Set<String>>(new FuncSet<String>()) {
 			public void perform(String anE) {
				result.add(anE);
			}
		}));
	}
	
	@Test
	public void testCanFindAMatchingEntry() {
		FuncSet<String> _l = newSet("steve", "is", "cool");
		String _beginsWithI = _l.find(new Matcher<String>(){
			public boolean matches(String anE) {
				return anE.startsWith("i");
			}
		});
		assertEquals("is", _beginsWithI);
	}
	
	@Test
	public void testCanCollectAllMatchingEntries() {
		FuncSet<String> _l = newSet("steve", "is", "cool", "init!");
		FuncCollection<String> _r = _l.collect(new Matcher<String>() {
			public boolean matches(String anE) {
				return anE.startsWith("i");
			}
		});
		assertEquals(newSet("is", "init!"), _r);
 	}
	
	@Test
	public void testCanDiscardAllMatchingEntries() {
		FuncSet<String> _s = newSet("steve", "is", "cool", "init!");
		FuncCollection<String> _result = _s.filter(new Matcher<String>() {
			public boolean matches(String anE) {
				return anE.startsWith("i");
			}
		});
		assertEquals(newSet("steve", "cool"), _result);
 	}
}
