package com.sjl.util.math;

public final class Percentage {

	public static final Percentage ONE_HUNDRED = new Percentage(1, 1);

	private Double percentage;

	public Percentage(double aNumerator, double aDenominator) {
		percentage = (aNumerator / aDenominator) * 100;
	}
	
	public Percentage(double aPercentage) {
		percentage = aPercentage;
	}

	public double getDoubleValue() {
		return percentage;
	}

	public int getIntValue() {
		return percentage.intValue();
	}

	public String toString() {
		return getIntValue() + "%";
	}

	public boolean equals(Object anObject) {
		if (anObject instanceof Percentage) {
			Percentage _other = (Percentage) anObject;
			return percentage == _other.percentage;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return getClass().hashCode() ^ new Double(percentage).hashCode();
	}
}
