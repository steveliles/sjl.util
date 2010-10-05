package com.sjl.util.collections;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;

public class LRUMap<K, V> {

	private Map<K, LRUEntry> delegate;
	
	private int maxSize;
	private AtomicInteger size;
	
	private Object queueLock = new Object();
	
	private LRUEntry oldest;
	private LRUEntry newest;
	
	public LRUMap(int aMaxSize) {
		delegate = new ConcurrentHashMap<K, LRUEntry>(aMaxSize);
		maxSize = aMaxSize;
		size = new AtomicInteger(0);
	}
	
	public V get(K aKey) {
		LRUEntry _e = delegate.get(aKey);
		if (_e == null) 
			return null;
		
		synchronized(queueLock) {
			_e.touch(newest);			
		}				
		return _e.value;		
	}
	
	public V put(K aKey, V aValue) {
		LRUEntry _new = null;
		synchronized(queueLock) {
			_new = new LRUEntry(aKey, aValue, newest);
			newest = _new;
			
			if (oldest == null)
				oldest = newest;			
		}
		LRUEntry _old = delegate.put(aKey, _new);
		
		if (_old != null) {
			synchronized(queueLock) {
				_old.remove();
				return _old.value;
			}
		} else {
			int _size = size.incrementAndGet();
			if (_size > maxSize) {
				K _oldKey;								
				boolean _expectRemove = false;
				V _oldest = null;
				do {
					synchronized(queueLock) {
						_oldKey = oldest.key;
						oldest.remove(); 
						_expectRemove = _oldKey != null;
					}				
					_oldest = remove(_oldKey);
				} while (_expectRemove && _oldest == null);
				
				onRemoveOldest(_oldKey, _oldest);				
			}			
		}
		return null;
	}
	
	public V remove(K aKey) {
		LRUEntry _removed = delegate.remove(aKey);
		if (_removed == null) 
			return null;
		
		size.decrementAndGet();
		synchronized(queueLock) {
			_removed.remove();
		}			
		return _removed.value;
	}
	
	public void onRemoveOldest(K aKey, V aValue) {
		// override me if you're interested		
	}	
	
	public int size() {
		return size.get();
	}
	
	public String toString() {
		return size + " ... " + delegate.toString();
	}
		
	private class LRUEntry {
		
		K key;
		V value;
		
		LRUEntry newer;
		LRUEntry older;
		
		public LRUEntry(K aKey, V aValue, LRUEntry aNewest) {
			key = aKey;
			value = aValue;
			if (aNewest != null) {
				older = aNewest;						
				aNewest.newer = this;			
			}
		}
		
		public void touch(LRUEntry aNewest) {
			if (this == aNewest) return;
			
			remove();			
			if ((older == null) && (newer != null)) 				
				oldest = newer;
			
			older = aNewest;
			aNewest.newer = this;
			newest = this;
		}		
		
		public void remove() {
			if (newer != null) {
				newer.older = older;
			} else {
				newest = older;
			}
			if (older != null) {
				older.newer = newer;
			} else {
				oldest = newer;
			}
		}
		
		public String toString() {
			return key + "";
		}
	
	}
}
