package com.sjl.util.strings.parse;

public class LongKey extends Key<Long> {

	public LongKey(String aName) {
        super(aName);
    }

    public LongKey(String aName, Long aDefaultValue) { 
        super(aName, aDefaultValue);
    }

    @Override
    protected Long convert(String aString) {
        return new Long(aString);
    }
}
