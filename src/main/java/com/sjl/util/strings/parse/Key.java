package com.sjl.util.strings.parse;

import java.util.*;

public abstract class Key<T> {
	
	private String name;
	private T defaultValue;

	public Key(String aName) {
		this(aName, null);
	}

	public Key(String aName, T aDefaultValue) {
		name = aName;
		defaultValue = aDefaultValue;
	}

	public T get(Map<Key<?>, String> aMap) {
		String _value = aMap.get(this);
		return (_value != null) ? convertOrDefault(_value) : defaultValue;
	}

	private T convertOrDefault(String aValue) {
		T _result = convert(aValue);
		return (_result != null) ? _result : defaultValue;
	}

	/**
	 * @param aString
	 * @return T a T created from the given string
	 */
	protected abstract T convert(String aString);

	@Override
	public int hashCode() {
		return defaultValue == null ? 
			getClass().hashCode() ^ name.hashCode() :
			getClass().hashCode() ^ name.hashCode() ^ defaultValue.hashCode();
	}
	
	public boolean equals(Object anObject) {
		if (getClass().isAssignableFrom(anObject.getClass())) {
			Key<?> _k = (Key<?>) anObject;
			return (defaultValue == null) ?
				_k.defaultValue == null && name.equals(_k.name) :
				name.equals(_k.name) && defaultValue.equals(_k.defaultValue);
		}
		return false;
	}
	
	@Override
	public String toString() {
		return (defaultValue == null) ?
			String.format("%s[%s]", getClass().getSimpleName(), name) :
		    String.format("%s[%s/%s]", getClass().getSimpleName(), name, defaultValue);
	}
}