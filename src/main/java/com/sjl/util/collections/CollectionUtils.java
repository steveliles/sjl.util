package com.sjl.util.collections;

import java.util.*;

import com.sjl.util.*;

public class CollectionUtils {

	/** @deprecated - use Maps.newMap instead */
	@Deprecated
	public static <K,V> FuncMap<K, V> newMap() {
		return Maps.newMap(); 
	}
	
	/** @deprecated - use Maps.newMap instead */
	@Deprecated
	public static <K,V> FuncMap<K, V> newMap(ValueFactory<K,V> aFactory) {
		return Maps.newMap(aFactory);
	}
	
	/** @deprecated - use Maps.functionalize instead */
	@Deprecated
	public static <K, V> FuncMap<K, V> newMap(Map<K, V> aMap) {
		return Maps.newMap(aMap);
	}
	
	/** @deprecated - use Maps.n instead */
	@Deprecated
	public static <K, V> FuncMap<K, V> newMap(K[] aKeys, V... aValues) {
		return Maps.newMap(aKeys, aValues);
	}
	
	/** @deprecated - use Maps.newMap instead */
	@Deprecated
	public static <K, V> FuncMap<K, V> newMap(Iterable<K> aKeys, Iterable<V> aValues) {
	    return Maps.newMap(aKeys, aValues);
	}
	
	
	/** @deprecated - use Lists.functionalize instead */
	@Deprecated
	public static <E> FuncList<E> newList(List<E> aList) {
		return Lists.newList(aList);
	}
	
	/** @deprecated - use Lists.newList instead */
	@Deprecated
	public static <E> FuncList<E> newList() {
		return Lists.newList();
	}
	
	/** @deprecated - use Lists.newList instead */
	@Deprecated	
	public static <E> FuncList<E> newList(E... anEntries) {
		return Lists.newList(anEntries);
	}
	
	/** @deprecated - use Lists.newList instead */
	@Deprecated	
	public static <E> FuncList<E> newList(Iterable<E> anIterable) {
		return Lists.newList(anIterable);
	}
	
	/** @deprecated - use Sets.functionalize instead */
	@Deprecated
	public static <E> FuncSet<E> functionalize(Set<E> aSet) {
		return Sets.newSet(aSet); 
	}
	
	/** @deprecated - use Sets.newSet instead */
	@Deprecated
	public static <E> FuncSet<E> newSet() {
		return Sets.newSet();
	}
	
	/** @deprecated - use Sets.newSet instead */
	@Deprecated
	public static <E> FuncSet<E> newSet(E... anEntries) {
		return Sets.newSet(anEntries);
	}
	
	/** @deprecated - use Sets.newSet instead */
	@Deprecated
	public static <E> FuncSet<E> newSet(Iterable<E> anIterable) {
		return Sets.newSet(anIterable);
	}

	/** @deprecated - use Matchers.newNullMatcher */
	@Deprecated
	public static <E> Matcher<E> newNullMatcher() {
		return Matchers.newNullMatcher();
	}
	
	public static <T> Iterator<T> emptyIterator() {
		return new Iterator<T>(){
			@Override
			public boolean hasNext() {
				return false;
			}

			@Override
			public T next() {
				return null;
			}

			@Override
			public void remove() {
			}
		};
	}
}
