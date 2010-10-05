package com.sjl.util.collections;

import java.util.*;

public class FuncList<E>
extends FuncCollection<E>
implements List<E> {

	public static final <E> FuncList<E> wrap(List<E> aList) {
		return new FuncList<E>(aList, true);
	}
	
	private List<E> delegate;		
	
	private FuncList(List<E> aDelegate, boolean aWrap) {
		if (aWrap) {
			delegate = aDelegate;
		} else {
			delegate = new ArrayList<E>(aDelegate);
		}
	}
	
	public FuncList(List<E> aList) {
		this(aList, false);
	}
	
	public FuncList() {
		this(new ArrayList<E>(), true);
	}

	protected <T> FuncCollection<T> newInstance() {
		return new FuncList<T>(new ArrayList<T>());
	}
	
	public boolean add(E anE) {
		return delegate.add(anE);
	}

	public void add(int anIndex, E anElement) {
		delegate.add(anIndex, anElement);
	}

	public boolean addAll(Collection<? extends E> anC) {
		return delegate.addAll(anC);
	}

	public boolean addAll(int anIndex, Collection<? extends E> anC) {
		return delegate.addAll(anIndex, anC);
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

	public E get(int anIndex) {
		return delegate.get(anIndex);
	}

	public int hashCode() {
		return delegate.hashCode();
	}

	public int indexOf(Object anO) {
		return delegate.indexOf(anO);
	}

	public boolean isEmpty() {
		return delegate.isEmpty();
	}

	public Iterator<E> iterator() {
		return delegate.iterator();
	}

	public int lastIndexOf(Object anO) {
		return delegate.lastIndexOf(anO);
	}

	public ListIterator<E> listIterator() {
		return delegate.listIterator();
	}

	public ListIterator<E> listIterator(int anIndex) {
		return delegate.listIterator(anIndex);
	}

	public E remove(int anIndex) {
		return delegate.remove(anIndex);
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

	public E set(int anIndex, E anElement) {
		return delegate.set(anIndex, anElement);
	}

	public int size() {
		return delegate.size();
	}

	public List<E> subList(int anFromIndex, int anToIndex) {
		return delegate.subList(anFromIndex, anToIndex);
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

	public FuncList<E> collect(Matcher<E> aMatcher) {
		return (FuncList<E>) super.collect(aMatcher);
	}

	public FuncList<E> compact() {
		return (FuncList<E>) super.compact();
	}

	public FuncList<E> filter(Matcher<E> aMatcher) {
		return (FuncList<E>) super.filter(aMatcher);
	}

	public <T> FuncList<T> transform(Transformer<E, T> aTransformer) {
		return (FuncList<T>) super.transform(aTransformer);
	}	
}
