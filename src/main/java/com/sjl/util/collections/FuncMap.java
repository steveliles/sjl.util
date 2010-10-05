package com.sjl.util.collections;

import java.util.*;

import com.sjl.util.*;

public class FuncMap<K, V> 
implements Map<K, V>{

	public static final <K, V> FuncMap<K, V> wrap(Map<K, V> aMap) {
		return new FuncMap<K, V>(aMap, true);
	}
	
	public static final <K, V> FuncMap<K, V> wrap(Map<K, V> aMap, ValueFactory<K, V> aValueFactory) {
		FuncMap<K, V> _result = new FuncMap<K, V>(aMap, true);
		_result.valueFactory = aValueFactory;
		return _result;
	}
	
	private Map<K, V> delegate;
	private ValueFactory<K, V> valueFactory;

	private FuncMap(Map<K, V> aDelegate, boolean aWrap) {
		if (aWrap) {
			delegate = aDelegate;
		} else {
			delegate = new HashMap<K, V>(aDelegate);
		}
	}
	
	public FuncMap() {
		this(new HashMap<K, V>(), true);
	}
	
	public FuncMap(int anInitialSize) {
		this(new HashMap<K, V>(anInitialSize), true);
	}
	
	public FuncMap(ValueFactory<K, V> aValueFactory) {
		this(new HashMap<K, V>(), true);
		valueFactory = aValueFactory;
	}

	public FuncMap(Map<K, V> aMap) {
		this(new HashMap<K, V>(aMap), true);
	}
	
	public <R> R eachKey(Task<K, R> aTask) {
		for (K _k : keySet()) {
			aTask.perform(_k);
		}
		return aTask.getResult();
	}
	
	public <R> R eachValue(Task<V, R> aTask) {
		for (V _v : values()) {
			aTask.perform(_v);
		}
		return aTask.getResult();
	}
	
	public <R> R each(Task<Map.Entry<K, V>, R> aTask) {
		for (Map.Entry<K, V> _e : entrySet()) {
			aTask.perform(_e);
		}
		return aTask.getResult();
	}
	
	public Map.Entry<K, V> find(Matcher<Map.Entry<K, V>> aMatcher) {
		for (Map.Entry<K, V> _e : entrySet()) {
			if (aMatcher.matches(_e)) {
				return _e;
			}
		}
		return null;
	}
	
	public FuncMap<K, V> collect(final Matcher<Map.Entry<K, V>> aMatcher) {
		return each(new Task<Entry<K, V>, FuncMap<K, V>>(new FuncMap<K, V>()) {
			public void perform(Entry<K, V> anEntry) {
				if (aMatcher.matches(anEntry)) {
					result.put(anEntry.getKey(), anEntry.getValue());
				}
			}
		});
	}
	
	public FuncList<K> collectKeys(final Matcher<Map.Entry<K, V>> aMatcher) {
		return each(new Task<Entry<K,V>, FuncList<K>>(new FuncList<K>()) {
			public void perform(Entry<K, V> anEntry) {
				if (aMatcher.matches(anEntry)) {
					result.add(anEntry.getKey());
				}
			}
		});
	}
	
	public FuncList<V> collectValues(final Matcher<Map.Entry<K, V>> aMatcher) {
		return each(new Task<Entry<K,V>, FuncList<V>>(new FuncList<V>()) {
			public void perform(Entry<K, V> anEntry) {
				if (aMatcher.matches(anEntry)) {
					result.add(anEntry.getValue());
				}
			}
		});
	}
	
	public FuncMap<K, V> filter(Matcher<Map.Entry<K, V>> aMatcher) {
		FuncMap<K, V> _result = new FuncMap<K, V>();
		for (Map.Entry<K, V> _e : entrySet()) {
			if (!aMatcher.matches(_e)) {
				_result.put(_e.getKey(), _e.getValue());
			}
		}
		return _result;
	}
	
	public FuncMap<K, V> filterKeys(Matcher<K> aMatcher) {
		FuncMap<K, V> _result = new FuncMap<K, V>();
		for (Map.Entry<K, V> _e : entrySet()) {
			if (!aMatcher.matches(_e.getKey())) {
				_result.put(_e.getKey(), _e.getValue());
			}
		}
		return _result;
	}
	
	public FuncMap<K, V> filterValues(Matcher<V> aMatcher) {
		FuncMap<K, V> _result = new FuncMap<K, V>();
		for (Map.Entry<K, V> _e : entrySet()) {
			if (!aMatcher.matches(_e.getValue())) {
				_result.put(_e.getKey(), _e.getValue());
			}
		}
		return _result;
	}

	/**
	 * fetch a value from the map by key ... if the value is currently null, generate a new
	 * value using the given factory.
	 *  
	 * @param aKey
	 * @param aBuilder
	 * @return the value, whether newly created by the factory, or got existing from map.
	 */
	public V retrieve(K aKey, ValueFactory<K, V> aFactory) {
		V _value = get(aKey);
		if (_value == null && aFactory != null) {
			_value = aFactory.create(aKey);
			put(aKey, _value);
		}
		return _value;
	}
	
	public V retrieve(K aKey) {
		return retrieve(aKey, valueFactory);
	}
	
	public void clear() {
		delegate.clear();
	}

	public boolean containsKey(Object anKey) {
		return delegate.containsKey(anKey);
	}

	public boolean containsValue(Object anValue) {
		return delegate.containsValue(anValue);
	}

	public Set<Entry<K, V>> entrySet() {
		return delegate.entrySet();
	}

	public boolean equals(Object anO) {
		return delegate.equals(anO);
	}

	public V get(Object anKey) {
		return delegate.get(anKey);
	}

	public int hashCode() {
		return delegate.hashCode();
	}

	public boolean isEmpty() {
		return delegate.isEmpty();
	}

	public Set<K> keySet() {
		return delegate.keySet();
	}

	public V put(K anKey, V anValue) {
		return delegate.put(anKey, anValue);
	}

	public void putAll(Map<? extends K, ? extends V> anM) {
		delegate.putAll(anM);
	}

	public V remove(Object anKey) {
		return delegate.remove(anKey);
	}

	public int size() {
		return delegate.size();
	}

	public Collection<V> values() {
		return delegate.values();
	}
			
	public String toString() {
		return delegate.toString();
	}
}
