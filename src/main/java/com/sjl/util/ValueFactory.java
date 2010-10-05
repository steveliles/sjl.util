package com.sjl.util;

public interface ValueFactory<K, V> {

	V create(K aKey);
	
}
