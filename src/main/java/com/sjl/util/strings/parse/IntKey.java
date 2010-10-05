package com.sjl.util.strings.parse;

public class IntKey extends Key<Integer> {
 
	public IntKey(String aName) { 
        super(aName);
    }
    
    public IntKey(String aName, Integer aDefaultValue) {
        super(aName, aDefaultValue);
    }   
    
    @Override
    protected Integer convert(String aString) {    	
        return new Integer(aString);
    }        
}