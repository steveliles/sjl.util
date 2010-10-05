package com.sjl.util.strings.parse;

import java.util.*;
import java.util.regex.*;

import com.sjl.util.collections.*;
import com.sjl.util.strings.*;

public abstract class StringDigester<T> {
    
    private Pattern pattern;
    private List<Key<?>> keys;
    
    public StringDigester(String aRegex, Key<?>... aKeys) {
        this(Pattern.compile(aRegex), aKeys);
    }                
    
    public StringDigester(Pattern aPattern, Key<?>...aKeys) {
        pattern = aPattern;
        keys = Lists.newList(aKeys);
    }
    
    public StringDigester(String aRegex, List<Key<?>> aKeys) {
        this(Pattern.compile(aRegex), aKeys);
    }
    
    public StringDigester(Pattern aPattern, List<Key<?>> aKeys) {
        pattern = aPattern;
        keys = Lists.newList(aKeys);
    }
    
    /**
     * @param aMappedValues
     * @return A class parameterised with the data in the map provided.
     */
    protected abstract T digest(Map<Key<?>, String> aMappedValues);

    public void add(Key<?> aKey) {
    	keys.add(aKey);
    }
    
    public T digest(String aString) {            
        return digest(Strings.captureAsMap(aString, pattern, keys));
    }        
}
