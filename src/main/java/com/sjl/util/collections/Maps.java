package com.sjl.util.collections;

import java.util.*;

import com.sjl.util.*;

/**
 * See also "StringMapper" for some nice ways to map values from String's into Map's.
 * 
 * @author steve
 */
public class Maps {

	public static <K, V> FuncMap<K, V> wrap(Map<K, V> aMap) {  
		return FuncMap.wrap(aMap);
	}
	
	public static <K, V> FuncMap<K, V> wrap(Map<K, V> aMap, ValueFactory<K, V> aFactory) {
		return FuncMap.wrap(aMap, aFactory);
	}
	
	public static <K,V> FuncMap<K, V> newMap() {
		return new FuncMap<K, V>(); 
	}
	
	public static <K,V> FuncMap<K, V> newMap(ValueFactory<K,V> aFactory) {
		return new FuncMap<K, V>(aFactory);
	}
	
	public static <K, V> FuncMap<K, V> newMap(K[] aKeys, V... aValues) {
		return newMap(Arrays.asList(aKeys), Arrays.asList(aValues));
	}	
	
	public static <K, V> FuncMap<K, V> newMap(Iterable<K> aKeys, Iterable<V> aValues) {
	    Iterator<V> _values = aValues.iterator();
	    FuncMap<K, V> _result = newMap();
		for (K _k : aKeys) {
	    	if (_values.hasNext()) {
	    		_result.put(_k, _values.next());
	    	}
	    }
		return _result;
	}
	
	public static <K, V> FuncMap<K, V> newMap(Map<K, V> aMap) {
		return new FuncMap<K, V>(aMap);
	}
	
}
