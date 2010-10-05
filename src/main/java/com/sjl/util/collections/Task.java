package com.sjl.util.collections;

public abstract class Task<E, R> {

	protected R result;

	public Task() {
	}
	
	public Task(R aResult) {
		result = aResult;
	}
	
	public abstract void perform(E anE);

	public R getResult() {
		return result;
	}
	
}
