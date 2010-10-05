package com.sjl.util.collections;

public interface Transformer<F, T> {

	public T apply(F aFrom);
	
}
