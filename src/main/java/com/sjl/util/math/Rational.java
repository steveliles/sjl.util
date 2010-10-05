package com.sjl.util.math;

public class Rational {

	private int numerator;
	private int denominator;
	
	public Rational(int aNumerator) {
		this(aNumerator, 1);
	}
	
	public Rational(int aNumerator, int aDenominator) {
		numerator = aNumerator;
		denominator = aDenominator;
	}
	
	public int getNumerator() {
		return numerator;
	}
	
	public void add(int aNumber) {
		add(new Rational(aNumber));
	}
	
	public void add(Rational aRational) {
		if (denominator == aRational.denominator) {
			numerator += aRational.numerator;
		} else {
			numerator *= aRational.denominator;
			int _toAdd = aRational.numerator * denominator;
			numerator += _toAdd;
			denominator *= aRational.denominator;
		}
	}
	
	public int getDenominator() {
		return denominator;
	}
	
	public double asDouble() {
		return ((double)numerator)/((double)denominator);
	}
	
	public Percentage asPercentage() {
		return new Percentage(numerator, denominator);
	}
	
	public boolean isOne() {
		return numerator == denominator;
	}
	
	public String toString() {
		return String.format("%s/%s", numerator, denominator);
	}
}
