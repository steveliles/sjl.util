package com.sjl.util.collections;

import java.util.*;

public class Sets {

	public static <E> FuncSet<E> wrap(Set<E> aSet) {
		return FuncSet.wrap(aSet);
	}
	
	public static <E> FuncSet<E> newSet(Set<E> aSet) {
		return new FuncSet<E>(aSet); 
	}
	
	public static <E> FuncSet<E> newSet() {
		return newSet(new HashSet<E>());
	}
	
	public static <E> FuncSet<E> newSet(E... anEntries) {
		FuncSet<E> _set = newSet();
		for (E _e : anEntries)
			_set.add(_e);
		return _set;
	}
	
	public static <E> FuncSet<E> newSet(Iterable<E> anIterable) {
		FuncSet<E> _set = newSet();
		for (E _e : anIterable)
			_set.add(_e);
		return _set;
	}		
	
}
