package com.sjl.util.strings;


public class Strings {
	
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
	
}
