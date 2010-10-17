package com.sjl.util;

import java.util.*;

import com.sjl.util.collections.*;
import com.sjl.util.strings.*;

public class Utils {
	
	public static <T> T firstNonNull(T... aTs) {
		if (aTs == null) return null;
		for (T _t : aTs) {
			if (_t != null) return _t;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static <T> T[] compact(T... aTs) {
		if (isEmpty(aTs)) 
			return aTs;
		
		FuncList<T> _list = Lists.newList(aTs).compact();
		
		return (T[]) _list.toArray();
	}
	
	public static <T> boolean containsAnyNulls(T... aTs) {
		if (isEmpty(aTs)) return false;
		for (T _t : aTs) { 
			if (_t == null) return true;
		}
		return false;
	}
	
	public static <T> boolean contains(T[] aTs, T anElement) {
		for (T _t : aTs)
			if (_t.equals(anElement)) return true; 
		
		return false;
	}
	
	public static <T> boolean isEmpty(T... anArray) {
		return ((anArray == null) || (anArray.length == 0));
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T[] splice(T[] anArray, T... anElements) {
		if (anElements.length == 0)
			return anArray;		
		if (anArray.length == 0)
			return anElements;
		
		List<T> _list = Lists.newList(anArray);
		_list.addAll(Lists.newList(anElements));
		
		return (T[]) _list.toArray();
	}
	
	public static String firstNonEmpty(String... aStrings) {
		return Strings.firstNonEmpty(aStrings);
	}

	public static interface Transformer<F, T> {
		
		public T transform(F aFrom);
		
	}
	
	public static <F, T> FuncList<T> transform(Iterable<F> aFrom, Transformer<F, T> aTransformer) {
		FuncList<T> _result = Lists.newList();
		for (F _f : aFrom) {
			_result.add(aTransformer.transform(_f));
		}
		return _result;
	}
}
