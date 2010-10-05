package com.sjl.util.strings;

import java.util.*;
import java.util.regex.*;

public class PatternCache {
	
	private Map<String, Pattern> cache = new HashMap<String, Pattern>();
	
	public Pattern get(String aRegex) {
		return get(aRegex, 0);
	}
	
	public Pattern get(String aRegex, int aFlags) {
		String _key = (aFlags != 0) ? aFlags + ".." + aRegex : aRegex;
		Pattern _p = cache.get(_key);
		if (_p == null) {
			_p = Pattern.compile(aRegex, aFlags);
			cache.put(_key, _p);
		}
		return _p;
	}
	
}
