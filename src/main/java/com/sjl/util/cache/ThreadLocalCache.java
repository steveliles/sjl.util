package com.sjl.util.cache;

import com.sjl.util.*;
import com.sjl.util.collections.*;

/**
 * A cache-per-thread, so that thread contention for cached objects is taken out of the equation.
 * Obviously this is expensive in terms of memory compared to a cache that is shared across threads, so
 * it only makes sense to use the ThreadLocalCache where several of the following hold true:
 * 
 * 1) the cached objects are not very large
 * 2) there are not a huge number of cached objects
 * 3) there are relatively few threads 
 * 
 * @author steve
 *
 * @param <K> The type for keys.
 * @param <V> The type for values.
 */
public class ThreadLocalCache<K, V> {
	
	private ThreadLocal<FuncMap<K, V>> cache;
	
	public ThreadLocalCache(final ValueFactory<K, V> aValueFactory) {
		cache =  new ThreadLocal<FuncMap<K, V>>() {
			@Override
			protected FuncMap<K, V> initialValue() {
				return Maps.newMap(aValueFactory);								
			} 		
		};
	}	

	public V get(K aKey) {
		return cache.get().get(aKey);
	}
	
	public V retrieve(K aKey) {
		return cache.get().retrieve(aKey);
	}
	
	public V put(K aKey, V aValue) {
		return cache.get().put(aKey, aValue);
	}
	
}
