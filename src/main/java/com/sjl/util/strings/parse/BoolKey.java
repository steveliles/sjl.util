package com.sjl.util.strings.parse;

public class BoolKey extends Key<Boolean>{

	public BoolKey(String aName) {
		super(aName, false);
	}
	
	public BoolKey(String aName, Boolean aDefaultValue) {
		super(aName, aDefaultValue);
	}
	
	@Override
	protected Boolean convert(String aString) {
		return Boolean.valueOf(aString);
	}

}
