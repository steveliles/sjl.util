package com.sjl.util.collections;

import java.util.*;

public class Lists {

	public static <E> FuncList<E> wrap(List<E> aList) {
		return FuncList.wrap(aList);
	}
	
	public static <E> FuncList<E> newList(List<E> aList) {
		return new FuncList<E>(aList);
	}
	
	public static <E> FuncList<E> newList() {
		return newList(new ArrayList<E>());
	}
	
	public static <E> FuncList<E> newList(E... anEntries) {
		FuncList<E> _list = newList();
		for (E _e : anEntries)
			_list.add(_e);
		return _list;
	}
	
	public static <E> FuncList<E> newList(Iterable<E> anIterable) {
		FuncList<E> _list = newList();
		for (E _e : anIterable)
			_list.add(_e);
		return _list;
	}
	
}
