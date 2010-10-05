package com.sjl.util.strings;

import java.util.regex.*;
import java.util.regex.Matcher;

import com.sjl.util.*;
import com.sjl.util.collections.*;

public class Strings {

	private static ThreadLocal<PatternCache> PATTERN_CACHE = new ThreadLocal<PatternCache>() {
		@Override
		protected PatternCache initialValue() {
			return new PatternCache();
		}		
	};
	
	public static String nullToString(String aString) {
		return (aString == null) ? "" : aString; 
	}		
	
	public static boolean isEmpty(String aString) {
		if (aString == null)
			return true;
		
		return (aString.trim().length() == 0);
	}
	
	public static boolean isNotEmpty(String aString) {
		return !isEmpty(aString);
	}
	
	public static String firstNonEmpty(String... aStrings) {
		for (String _s : aStrings) {
			if (!isEmpty(_s))
				return _s;
		}
		return null;
	}
	
	public static boolean matches(String aString, String aRegex) {
		return matches(aString, getCachedPattern(aRegex));
	}
	
	public static boolean matches(String aString, Pattern aPattern) {
		return aPattern.matcher(aString).matches();
	}				
	
	public static <T> String join(Iterable<T> aTerms) {
		return join(", ", aTerms);
	}
	
	public static <T> String join(String aDelimiter, Iterable<T> aTerms) {
		StringBuilder _sb = new StringBuilder();
		
		for (T _t : aTerms) {
			_sb.append(_t);
			_sb.append(aDelimiter);
		}
		
		if (_sb.length() > aDelimiter.length())
			_sb.setLength(_sb.length() - aDelimiter.length());
		
		return _sb.toString();
	}
	
	public static String join(String... aTerms) {
		return joinWithDelimiter(", ", aTerms);
	}
	
	public static String joinWithDelimiter(String aDelimiter, String... aTerms) {
		if (aTerms.length == 0)
			return "";				
		
		StringBuilder _sb = new StringBuilder(aTerms.length * 10);
		boolean _needsDelimiter = false;
		for (int i=0; i<aTerms.length; i++) {			
			if (aTerms[i] != null) {		
				if (_needsDelimiter) {
					_sb.append(aDelimiter);					
				}
				_sb.append(aTerms[i]);	
				_needsDelimiter = true;
			}
		}
		
		return _sb.toString();
	}
	
	public static Pattern getCachedPattern(String aRegex) {
		return getCachedPattern(aRegex, 0);
	}
	
	public static Pattern getCachedPattern(String aRegex, int aFlags) { 
		return PATTERN_CACHE.get().get(aRegex, aFlags);
	}
	
	public static FuncSet<String> splitAsSet(String aString, String aRegex) {
		return splitAsSet(aString, Pattern.compile(aRegex));
	}
	
	public static FuncSet<String> splitAsSet(String aString, Pattern aPattern) {
		return Sets.newSet(aPattern.split(aString));
	}	
	
	public static FuncSet<String> captureAsSet(String aString, String aCapturingRegex) {
		return captureAsSet(aString, Pattern.compile(aCapturingRegex));
	}
	
	public static FuncSet<String> captureAsSet(String aString, Pattern aCapturingPattern) {
		Matcher _matcher = aCapturingPattern.matcher(aString);
		FuncSet<String> _result = Sets.newSet();
		
		if (!_matcher.matches())
			return _result;
		 
		for (int i=1; i<=_matcher.groupCount(); i++) {
			_result.add(_matcher.group(i));					
		}		
		
		return _result;
	}
	
	public static FuncList<String> splitAsList(String aString, String aRegex) {
		return splitAsList(aString, Pattern.compile(aRegex));
	}
	
	public static FuncList<String> splitAsList(String aString, Pattern aPattern) {
		return Lists.newList(aPattern.split(aString));
	}	
	
	public static FuncList<String> captureAsList(String aString, String aCapturingRegex) {
		return captureAsList(aString, Pattern.compile(aCapturingRegex));
	}
	
	public static FuncList<String> captureAsList(String aString, Pattern aCapturingPattern) {
		Matcher _matcher = aCapturingPattern.matcher(aString);
		FuncList<String> _result = Lists.newList();
		
		if (!_matcher.matches())
			return _result;
		 
		for (int i=1; i<=_matcher.groupCount(); i++) {
			_result.add(_matcher.group(i));					
		}		
		
		return _result;
	}
	
	public static <K> FuncMap<K, String> captureAsMap(String aString, String aRegex, K... aKeys) {
        return captureAsMap(aString, Pattern.compile(aRegex), aKeys);
    }
    
    public static <K> FuncMap<K, String> captureAsMap(String aString, String aRegex, ValueFactory<K, String> aValueFactory, K... aKeys) {
        return captureAsMap(aString, Pattern.compile(aRegex), aValueFactory, aKeys);
    }
    
    public static <K> FuncMap<K, String> captureAsMap(String aString, Pattern aPattern, K... aKeys) {
        return captureAsMap(aString, aPattern, null, aKeys);
    }
    
    public static <K> FuncMap<K, String> captureAsMap(String aString, Pattern aPattern, ValueFactory<K, String> aFactory, K... aKeys) {
        return captureAsMap(aString, aPattern, aFactory, Lists.newList(aKeys));
    }
    
	public static <K> FuncMap<K, String> captureAsMap(String aString, String aRegex, Iterable<K> aKeys) {	
		return captureAsMap(aString, aRegex, null, aKeys);
	}	
	
	public static <K> FuncMap<K, String> captureAsMap(String aString, String aRegex, ValueFactory<K, String> aValueFactory, Iterable<K> aKeys) {	
		return captureAsMap(aString, Strings.getCachedPattern(aRegex), aValueFactory, aKeys);
	}
	
	public static <K> FuncMap<K, String> captureAsMap(String aString, Pattern aPattern, Iterable<K> aKeys) {
		return captureAsMap(aString, aPattern, null, aKeys);
	}		
	
	public static <K> FuncMap<K, String> captureAsMap(String aString, Pattern aPattern, ValueFactory<K, String> aValueFactory, Iterable<K> aKeys) {
		Matcher _matcher = aPattern.matcher(aString);
		FuncMap<K, String> _result = Maps.newMap();
		
		if (!_matcher.matches())
			return _result; // TODO: should populate with defaults?
		 
		int i=1;
		for (K _k : aKeys) {				
			if (_matcher.groupCount() < i) {				
				if (aValueFactory == null) { 
					break;
				} else {
					_result.put(_k, aValueFactory.create(_k));					
				}
			} else {				
				_result.put(_k, _matcher.group(i++));
			}
		}
		
		return _result;
	}

	public static <K> FuncMap<K, String> splitAsMap(String aString, String aRegex, K... aKeys) {
	    return splitAsMap(aString, aRegex, null, aKeys);
	}

	public static <K> FuncMap<K, String> splitAsMap(String aString, String aRegex, ValueFactory<K, String> aValueFactory, K... aKeys) {
	    return splitAsMap(aString, Pattern.compile(aRegex), aValueFactory, aKeys);
	}	
	
    public static <K> FuncMap<K, String> splitAsMap(String aString, Pattern aPattern, K... aKeys) {
        return splitAsMap(aString, aPattern, null, aKeys);
    }   
    
    public static <K> FuncMap<K, String> splitAsMap(String aString, Pattern aPattern, ValueFactory<K, String> aValueFactory, K... aKeys) {
        return splitAsMap(aString, aPattern, aValueFactory, Lists.newList(aKeys));
    }

	public static <K> FuncMap<K, String> splitAsMap(String aString, String aRegex, Iterable<K> aKeys) {
		return splitAsMap(aString, aRegex, null, aKeys);
	}
	
	public static <K> FuncMap<K, String> splitAsMap(String aString, String aRegex, ValueFactory<K, String> aValueFactory, Iterable<K> aKeys) {
		return splitAsMap(aString, Strings.getCachedPattern(aRegex), aValueFactory, aKeys);
	}
	
	public static <K> FuncMap<K, String> splitAsMap(String aString, Pattern aPattern, Iterable<K> aKeys) {
		return splitAsMap(aString, aPattern, null, aKeys);
	}
	
	public static <K> FuncMap<K, String> splitAsMap(String aString, Pattern aPattern, ValueFactory<K, String> aValueFactory, Iterable<K> aKeys) {		
		String[] _values = aPattern.split(aString);
		FuncMap<K, String> _result = Maps.newMap();
		int i = 0;
		for (K _k : aKeys) {
			if (_values.length > i) {
				_result.put(_k, _values[i++]);
			} else {
				if (aValueFactory == null)
					break;
				_result.put(_k, aValueFactory.create(_k));
			}
		}	
		return _result;
	}
}
