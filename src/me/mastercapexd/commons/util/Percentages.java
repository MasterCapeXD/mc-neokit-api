package me.mastercapexd.commons.util;

import java.util.Collection;

public class Percentages {

	public static <T> PercentageSet<T> newHashPercentageSet() {
		return new HashPercentageSet<>();
	}
	
	public static <T> PercentageSet<T> newHashPercentageSet(Collection<T> collection) {
		PercentageSet<T> set = new HashPercentageSet<>();
		set.addAll(collection);
		return set;
	}
	
	private Percentages() {
		throw new UnsupportedOperationException("This class cannot be instantiated!");
	}
}