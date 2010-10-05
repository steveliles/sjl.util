package com.sjl.util.collections;

import java.util.*;

public abstract class FuncCollection<E> 
implements Collection<E> {

	protected abstract <T> FuncCollection<T> newInstance();
		
	public <R> R each(Task<E, R> aTask) {
		for (E _e : this) {
			aTask.perform(_e);
		}
		return aTask.getResult();
	}
	
	public E find(Matcher<E> aMatcher) {
		for (E _e : this) {
			if (aMatcher.matches(_e)) {
				return _e;
			}
		}
		return null;
	}
	
	protected FuncCollection<E> collect(Matcher<E> aMatcher) {
		FuncCollection<E> _c = newInstance();
		for (E _e : this) {
			if (aMatcher.matches(_e)) {
				_c.add(_e);
			}
		}
		return _c;
	}
	
	protected FuncCollection<E> filter(Matcher<E> aMatcher) {
		FuncCollection<E> _c = newInstance();
		for (E _e : this) {
			if (!aMatcher.matches(_e)) {
				_c.add(_e);
			}
		}
		return _c;
	}
	
	protected <T> FuncCollection<T> transform(Transformer<E, T> aTransformer) {
		FuncCollection<T> _c = newInstance();
		for (E _e : this) {
			_c.add(aTransformer.apply(_e));
		}
		return _c;
	}
	
	protected FuncCollection<E> compact() {
		Matcher<E> _matcher = Matchers.newNullMatcher();
		return filter(_matcher);
	}
}
