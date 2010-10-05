package com.sjl.util.collections;

public class Matchers {

	public static <E> Matcher<E> newNullMatcher() {
		return new Matcher<E>() {
			@Override
			public boolean matches(E anE) {
				return anE == null;
			}			
		};
	}
	
}
