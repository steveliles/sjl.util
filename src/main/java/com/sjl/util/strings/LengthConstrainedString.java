package com.sjl.util.strings;

public class LengthConstrainedString {

	private String str;
	
	public LengthConstrainedString(String aString, int aMinLength, int aMaxLength) {	
		if (aString == null)
			throw new IllegalArgumentException("String must not be null!");
		
		if (aString.length() >= aMaxLength) {
			String _msg = String.format("String \"%s\" exceeds max-length of %s characters.", aString, aMaxLength);
			throw new IllegalArgumentException(_msg);
		}
		
		if (aString.length() < aMinLength) {
			String _msg = String.format("String \"%s\" does not satisfy minimum-length of %s characters.", aString, aMinLength);
			throw new IllegalArgumentException(_msg);
		}
		
		str = aString;
	}
	
	public String toString() {
		return str;
	}
	
	public boolean equals(Object anOther) {
		if ((anOther instanceof String) || (anOther instanceof LengthConstrainedString)) {
			return (anOther.toString()).equals(str);
		}
		return false;
	}
	
	public int hashCode() {
		return str.hashCode();
	}
	
}
