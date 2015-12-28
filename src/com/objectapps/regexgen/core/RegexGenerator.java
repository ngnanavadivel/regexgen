package com.objectapps.regexgen.core;

import java.util.Arrays;
import java.util.List;

public class RegexGenerator {

	private static final List<String>	REGEX_RESERVED_CHARS	= Arrays
			.asList(new String[] { ".", "+", "*", "(", ")", "?", "{", "}", "\\", "-", "^", "$" });
	/*
	 * Flags to mark whether the current and last character parsed are a Digit |
	 * Alphabet | Whitespace | Special character
	 */
	private boolean						currentCharADigit		= false;
	private boolean						currentCharAnAlphabet	= false;
	private boolean						currentCharASpecial		= false;
	private boolean						currentCharAWhiteSpace	= false;
	private boolean						lastCharADigit			= false;
	private boolean						lastCharAnAlphabet		= false;
	private boolean						lastCharASpecial		= false;
	private boolean						lastCharAWhiteSpace		= false;

	/* Counter that holds repeating count of a particular character type. */
	private int repetitions = 0;

	/* Value from which Regular Expression is derived. */
	private String value = null;

	public RegexGenerator(String value) {
		this.value = value;
	}

	/* Derives a Regular Expression for the value */
	public String deriveRegex(List<Integer> indicesToRetain) {
		String regex = null;
		if (value != null) {
			StringBuilder pattern = new StringBuilder();
			int size = value.length();
			for (int i = 0; i < size; ++i) {

				char token = value.charAt(i);
				if (indicesToRetain  != null && indicesToRetain.contains(i)) {
					if (repetitions > 0) {
						pattern.append(String.format("{%d}", repetitions + 1));
						repetitions = 0;
					}
					if (isReservedMetaChar(token)) {
						pattern.append("\\" + token);
					} else {
						pattern.append(token);
					}
					resetCurrentCharFlags();
					resetLastCharFlags();
					continue;
				}

				updateCurrentCharFlags(token);

				boolean isARepeatingCandidate = (lastCharADigit && currentCharADigit)
												|| (lastCharAnAlphabet && currentCharAnAlphabet)
												|| (lastCharAWhiteSpace && currentCharAWhiteSpace)
												|| (lastCharASpecial && currentCharASpecial);
				if (isARepeatingCandidate) {
					repetitions++;
				} else {
					if (repetitions > 0) {
						pattern.append(String.format("{%d}", repetitions + 1));
					}
					pattern.append(deriveRegex(token));

					repetitions = 0;
				}

				lastCharADigit = currentCharADigit;
				lastCharAnAlphabet = currentCharAnAlphabet;
				lastCharAWhiteSpace = currentCharAWhiteSpace;
				lastCharASpecial = currentCharASpecial;

				resetCurrentCharFlags();
			}

			if (repetitions > 0) {
				pattern.append(String.format("{%d}", repetitions + 1));
			}

			regex = pattern.toString();
		}
		return regex;
	}

	/*
	 * Returns the Regular Expression for the current character based on whether
	 * it's a Digit or Alphabet or Whitespace or Neither of them
	 */
	private String deriveRegex(char value) {
		if (isDigit(value)) {
			return "\\d";
		} else if (isAlphabet(value)) {
			return "[a-zA-Z]";
		} else if (isWhiteSpace(value)) {
			return "\\s";
		} else if (isReservedMetaChar(value)) {
			return "\\" + value;
		} else {
			return String.valueOf(value);
		}
	}

	private void resetCurrentCharFlags() {
		currentCharADigit = false;
		currentCharAnAlphabet = false;
		currentCharAWhiteSpace = false;
		currentCharASpecial = false;
	}
	
	private void resetLastCharFlags() {
		lastCharADigit = false;
		lastCharAnAlphabet = false;
		lastCharAWhiteSpace = false;
		lastCharASpecial = false;
	}

	private void updateCurrentCharFlags(char token) {
		if (isDigit(token)) {
			currentCharADigit = true;
		} else if (isAlphabet(token)) {
			currentCharAnAlphabet = true;
		} else if (isWhiteSpace(token)) {
			currentCharAWhiteSpace = true;
		} else {
			currentCharASpecial = true;
		}
	}

	private static boolean isReservedMetaChar(char value) {
		return REGEX_RESERVED_CHARS.contains(String.valueOf(value));
	}

	private static boolean isAlphabet(char value) {
		return Character.isAlphabetic(value);
	}

	private static boolean isDigit(char value) {
		return Character.isDigit(value);
	}

	private static boolean isWhiteSpace(char value) {
		return Character.isWhitespace(value);
	}
	
	public static void main(String[] args) {
		String date1 = "12/Nov/2015";
		String date2 = "12/31/2015";
		String date3 = "October 12th 2015";
		String date4 = "12-Nov-2015";
		String date5 = "12-12-2015";
		String email1 = "nataraj.gnanavadivel@gmail.com";
		String email2 = "hariram.ananthasubramaniam@altisource.com";
		String phone1 = "6787045753";
		String phone2 = "(678)704-5753";
		String phone3 = "678.704.5753";
		String phone4 = "678 704 5753";

		System.out.println(String.format("%50s -> %s", date1, new RegexGenerator(date1).deriveRegex(null)));
		System.out.println(String.format("%50s -> %s", date2, new RegexGenerator(date2).deriveRegex(null)));
		System.out.println(String.format("%50s -> %s", date3, new RegexGenerator(date3).deriveRegex(null)));
		System.out.println(String.format("%50s -> %s", date4, new RegexGenerator(date4).deriveRegex(null)));
		System.out.println(String.format("%50s -> %s", date5, new RegexGenerator(date5).deriveRegex(null)));
		System.out.println(String.format("%50s -> %s", email1, new RegexGenerator(email1).deriveRegex(null)));
		System.out.println(String.format("%50s -> %s", email2, new RegexGenerator(email2).deriveRegex(null)));
		System.out.println(String.format("%50s -> %s", phone1, new RegexGenerator(phone1).deriveRegex(null)));
		System.out.println(String.format("%50s -> %s", phone2, new RegexGenerator(phone2).deriveRegex(null)));
		System.out.println(String.format("%50s -> %s", phone3, new RegexGenerator(phone3).deriveRegex(null)));
		System.out.println(String.format("%50s -> %s", phone4, new RegexGenerator(phone4).deriveRegex(null)));
	}

}
