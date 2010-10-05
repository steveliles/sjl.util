package com.sjl.util;

public interface ExceptionHandler<T extends Throwable> {

	public void onException(T anExc);
	
}
