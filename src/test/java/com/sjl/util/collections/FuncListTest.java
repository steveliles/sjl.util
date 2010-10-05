package com.sjl.util.collections;

import static com.sjl.util.collections.Lists.*;
import static org.junit.Assert.*;

import java.util.*;

import org.junit.*;

public class FuncListTest {

	@Test
	public void testCanApplyTaskToEachEntry() {
		FuncList<String> _l = newList("steve", "is", "cool");
		
 		assertEquals(_l, _l.each(new Task<String, List<String>>(new FuncList<String>()) {
			public void perform(String anE) {
				result.add(anE);
			}
		}));
	}
	
	@Test
	public void testCanFindFirstMatchingEntry() {
		FuncList<String> _l = newList("steve", "is", "cool", "init!");
		String _beginsWithI = _l.find(new Matcher<String>(){
			public boolean matches(String anE) {
				return anE.startsWith("i");
			}
		});
		assertEquals("is", _beginsWithI);
	}
	
	@Test
	public void testCanCollectAllMatchingEntries() {
		FuncList<String> _l = newList("steve", "is", "cool", "init!");
		FuncCollection<String> _r = _l.collect(new Matcher<String>() {
			public boolean matches(String anE) {
				return anE.startsWith("i");
			}
		});
		assertEquals(newList("is", "init!"), _r);
 	}
	
	@Test
	public void testCanDiscardAllMatchingEntries() {
		FuncList<String> _l = newList("steve", "is", "cool", "init!");
		
		assertEquals(newList("steve", "cool"), _l.filter(new Matcher<String>() {
			public boolean matches(String anE) {
				return anE.startsWith("i");
			}
		}));
 	}
}
