package com.sjl.util.strings.parse;

public class StringKey extends Key<String> {
	
    public StringKey(String aName) {
        super(aName);
    }
    
    public StringKey(String aName, String aDefaultValue) {
        super(aName, aDefaultValue);
    }

    @Override
    protected String convert(String aString) {
        return aString;
    }
    
}
