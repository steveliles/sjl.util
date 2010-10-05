package com.sjl.util.strings;

import java.util.*;
import java.util.regex.*;

public class StringTokenIterator
implements Iterator<StringTokenIterator.Token>, Iterable<StringTokenIterator.Token> {
	
	private Matcher matcher;
	private String string;
	
	private int index;
	private boolean more;
	
	public StringTokenIterator(String aString, String aRegex) {
		this(aString, Strings.getCachedPattern(aRegex));
	}
	
	public StringTokenIterator(String aString, Pattern aPattern) {
		matcher = aPattern.matcher(aString);
		string = aString;
		more = (string != null);
	}
	
	public boolean hasNext() {
		return more; 
	}
	
	public Token next() {
		int _start = index;
		boolean _isMatch = false;
		if (matcher.find(_start)) {
			_isMatch = (matcher.start() == _start);
			index = _isMatch ? matcher.end() : matcher.start();
		    more = index != string.length();
		} else {
			_isMatch = false;
			more = false;
			index = string.length();
		}
		return new Token(string.substring(_start, index), _isMatch);
	}
	
	public void remove() {
		throw new UnsupportedOperationException();
	}	

	public Iterator<Token> iterator() {
		return this;
	}

	public static class Token {
		private String string;
		private boolean isMatch;
		
		public Token(String aString, boolean anIsMatch) {
			string = aString;
			isMatch = anIsMatch;
		}
		
		public String getString() {
			return string;
		}
		
		public String toString() {
			return string;
		}
		
		public boolean isMatch() {
			return isMatch;
		}
	}
}

