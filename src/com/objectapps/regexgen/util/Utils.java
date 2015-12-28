package com.objectapps.regexgen.util;

public class Utils {
	public static String convertToValidJavaString(String regex) {
		if (regex != null) {
			regex = regex.replaceAll("\"", "\\\"");
			return regex.replaceAll("\\", "\\\\");

		}
		return regex;
	}
}
