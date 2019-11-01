package me.mastercapexd.commons.util;

public final class NumberValidator {

	public static boolean isEven(int number) {
		return (number & 1) == 0;
	}
	
	public static boolean isByte(String str) {
		try {
			Byte.parseByte(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	public static boolean isShort(String str) {
		try {
			Short.parseShort(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	public static boolean isInteger(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	public static boolean isLong(String str) {
		try {
			Long.parseLong(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	public static boolean isFloat(String str) {
		try {
			Float.parseFloat(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	public static boolean isDouble(String str) {
		try {
			Double.parseDouble(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}