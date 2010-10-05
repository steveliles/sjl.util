package com.sjl.util.collections;

import java.util.*;

public class FuncSet<E>
extends FuncCollection<E>
implements Set<E> {

	public static final <E> FuncSet<E> wrap(Set<E> aDelegate) { 
		return new FuncSet<E>(aDelegate, true);
	}
	
	private Set<E> delegate;
	
	private FuncSet(Set<E> aDelegate, boolean aWrap) {
		if (aWrap) {
			delegate = aDelegate;
		} else {
			delegate = new HashSet<E>(aDelegate);
		}
	}
	
	public FuncSet(Set<E> aDelegate) {
		this(aDelegate, false);
	}
	
	public FuncSet() {
		this(new HashSet<E>(), true);
	}
	
	protected <T> FuncCollection<T> newInstance() {
		return new FuncSet<T>(new HashSet<T>());
	}
	
	public boolean add(E anE) {
		return delegate.add(anE);
	}

	public boolean addAll(Collection<? extends E> anC) {
		return delegate.addAll(anC);
	}

	public void clear() {
		delegate.clear();
	}

	public boolean contains(Object anO) {
		return delegate.contains(anO);
	}

	public boolean containsAll(Collection<?> anC) {
		return delegate.containsAll(anC);
	}

	public boolean equals(Object anO) {
		return delegate.equals(anO);
	}

	public int hashCode() {
		return delegate.hashCode();
	}

	public boolean isEmpty() {
		return delegate.isEmpty();
	}

	public Iterator<E> iterator() {
		return delegate.iterator();
	}

	public boolean remove(Object anO) {
		return delegate.remove(anO);
	}

	public boolean removeAll(Collection<?> anC) {
		return delegate.removeAll(anC);
	}	

	public boolean retainAll(Collection<?> anC) {
		return delegate.retainAll(anC);
	}

	public int size() {
		return delegate.size();
	}

	public Object[] toArray() {
		return delegate.toArray();
	}

	public <T> T[] toArray(T[] anA) {
		return delegate.toArray(anA);
	}
	
	public String toString() {
		return delegate.toString();
	}

	public FuncSet<E> collect(Matcher<E> aMatcher) {
		return (FuncSet<E>) super.collect(aMatcher);
	}

	public FuncSet<E> compact() {
		return (FuncSet<E>) super.compact();
	}

	public FuncSet<E> filter(Matcher<E> aMatcher) {
		return (FuncSet<E>) super.filter(aMatcher);
	}

	public <T> FuncSet<T> transform(Transformer<E, T> aTransformer) {
		return (FuncSet<T>) super.transform(aTransformer);
	}
	
}
