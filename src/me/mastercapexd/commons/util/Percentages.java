package me.mastercapexd.commons.util;

public class Percentages {

	public static <T> PercentageSet<T> newHashPercentageSet() {
		return new HashPercentageSet<>();
	}
	
	private Percentages() {
		throw new UnsupportedOperationException("This class cannot be instantiated!");
	}
	
	public static void main(String[] args) {
		
		PercentageSet<String> percentageSet = Percentages.newHashPercentageSet();
		percentageSet.put("Entry1", 30);
		percentageSet.put("Entry2", 9);
		percentageSet.put("Entry3", 9);
		percentageSet.put("Entry4", 22);
		percentageSet.put("Entry5", 50);
		
		for (int i = 0; i <= 50; i++)
			System.out.println(percentageSet.get());
		
	}
}