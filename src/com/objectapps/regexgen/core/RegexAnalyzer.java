package com.objectapps.regexgen.core;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.objectapps.regexgen.util.Logger;

public class RegexAnalyzer {

	private String[] samples = null;

	public RegexAnalyzer(String[] samples) {
		this.samples = samples;
	}

	public Set<String> generateRegex() {
		Set<String> expressions = new LinkedHashSet<String>();

		if (samples != null && samples.length > 0) {
			StringBuilder pattern = new StringBuilder();

			// check if all the samples are of same length.
			boolean sameSize = checkForSameSize(samples);

			// check whether all the sample values represent Numbers.
			NumberTraits traits = checkForNumbers(samples);
			if (traits.allAreNumbers) {

				// Accommodate Signed numbers. (Negative values).
				if (traits.startsWithPlusOrMinus) {
					pattern.append("-?");
				}

				// Check if any of the samples(numbers) have '.' in them.
				if (traits.isDecimated) {
					// Check if all the samples have '.' in them.
					if (traits.allAreDecimated) {
						// Check the value of decimalIndex. If it is anything other than -1,
						// which indicates that all the samples have the '.' in the same index.
						if (traits.decimalIndex != -1) {
							// Though all samples have a '.' in the same index,
							// only if all of them are of same size, we could KEEP the '.' as part of the Regex.
							if (sameSize) {
								// '.' is now part of the Regex.
								// The real number's Characteristic and Mantissa parts are arrived as follows:
								// Characteristic Part's Length = decimalIndex (When numbers are unsigned) otherwise
								// decimalIndex - 1 (If Signed.)
								// Mantissa Part's Length = Length of Sample - decimalIndex - 1 (As all dots are at same
								// Index, we can ignore Signed Number's Impact.)
								pattern.append(String.format("\\d{%d}\\.\\d{%d}", (traits.startsWithPlusOrMinus
																												? traits.decimalIndex
																													- 1
																												: traits.decimalIndex),
										samples[0].length() - traits.decimalIndex - 1));
							} else {
								// all samples are decimated at the same place
								// but mantissa size varies.
								pattern.append(String.format("\\d{%d}\\.\\d+", (traits.startsWithPlusOrMinus
																												? traits.decimalIndex
																													- 1
																												: traits.decimalIndex)));
							}
						} else {
							// all samples are decimated but at random
							// positions.
							pattern.append("\\d+\\.\\d+");
						}

					} else {
						// only some samples are decimated and hence [.] is
						// optional.
						pattern.append("\\d+\\.?\\d+");
					}
				} else {
					// None of them are decimated.
					pattern.append("\\d" + (sameSize
														? String.format("{%d}", (traits.startsWithPlusOrMinus
																												? samples[0]
																														.length()
																													- 1
																												: samples[0]
																														.length()))
														: "+"));
				}
				expressions.add(pattern.toString());
				return expressions;
			} 
			// Processing samples for Numbers is done.
			// If we are here, then few of the samples are NOT Numbers.
			List<Integer> indicesToRetain = findMatchingCharsInAllSamples(samples);

			for (String sample : samples) {
				expressions.add(new RegexGenerator(sample).deriveRegex(indicesToRetain));
			}
		}
		return expressions;
	}

	private List<Integer> findMatchingCharsInAllSamples(String[] samples) {
		List<Integer> matchingIndices = new ArrayList<Integer>();
		int length = getSizeOfSmallest(samples);
		if (length != -1) {
			for (int j = 0; j < length; ++j) {
				boolean isAPatternBreak = false;
				for (int i = 1; i < samples.length; ++i) {
					if (samples[i].charAt(j) != samples[0].charAt(j)) {
						isAPatternBreak = true;
						break;
					}
				}
				if (!isAPatternBreak) {
					matchingIndices.add(j);
				}
			}
		}
		Logger.instance().log("Matching Indices : " + matchingIndices);
		return matchingIndices;
	}

	private int getSizeOfSmallest(String[] samples) {
		int length = -1;
		if (samples != null && samples.length > 0) {
			length = samples[0].length();
			for (int i = 1; i < samples.length; ++i) {
				int currSampleLength = samples[i].length();
				if (currSampleLength < length) {
					length = currSampleLength;
				}
			}
		}
		Logger.instance().log("Smallest Sample's Size : " + length);
		return length;
	}

	private boolean checkForSameSize(String[] samples) {

		if (samples != null && samples.length > 0) {
			int length = samples[0].length();
			for (int i = 1; i < samples.length; ++i) {
				if (samples[i].length() != length) {
					return false;
				}
			}
			return true;
		} else {
			throw new RuntimeException("Samples are empty!");
		}
	}

	private NumberTraits checkForNumbers(String[] samples) {
		NumberTraits traits = new NumberTraits();
		if (samples != null && samples.length > 0) {
			Set<Integer> dotIndices = new HashSet<Integer>();
			int numDecimatedSamples = 0;
			for (String sample : samples) {
				try {
					new BigDecimal(sample);
				} catch (Exception e) {
					traits.allAreNumbers = false;
					break;
				}
				if (sample.startsWith("+") || sample.startsWith("-")) {
					traits.startsWithPlusOrMinus = true;
				}
				int index = sample.indexOf(".");
				if (index > -1) {
					traits.isDecimated = true;
					numDecimatedSamples++;
					dotIndices.add(index);
				}
			}
			if (samples.length == numDecimatedSamples) {
				traits.allAreDecimated = true;
				if (dotIndices.size() == 1) {
					traits.decimalIndex = dotIndices.iterator().next();
				}
			}
		} else {
			throw new RuntimeException("Samples are empty!");
		}

		return traits;
	}

	class NumberTraits {
		public boolean	allAreNumbers	= true;
		public boolean	startsWithPlusOrMinus;
		public boolean	isDecimated;
		public boolean	allAreDecimated;
		public int		decimalIndex	= -1;
	}

	public static void main(String[] args) {
		String[] samples = { "(123)103-1202", "(143)120-1289", "(673)124-1262", "(189)127-7602", "(147)125-1282" };
		System.out.println("\n\n" + Arrays.toString(samples));
		System.out.println(new RegexAnalyzer(samples).generateRegex());

		samples = new String[] { "A12321S12", "A58293S23", "A13439S12", "A09182S78", "N12309S82" };
		System.out.println("\n\n" + Arrays.toString(samples));
		System.out.println(new RegexAnalyzer(samples).generateRegex());

		samples = new String[] { "-20.29", "-13.42", "-0.56", "-123.41", "1234.56" };
		System.out.println("\n\n" + Arrays.toString(samples));
		System.out.println(new RegexAnalyzer(samples).generateRegex());

		samples = new String[] { "$213,124.56", "$1,232.45", "$1,412,213", "$23,499.78", "$1,123,123,428" };
		System.out.println("\n\n" + Arrays.toString(samples));
		System.out.println(new RegexAnalyzer(samples).generateRegex());

		samples = new String[] { "102983129", "014210290", "191820392", "238472998", "293842930" };
		System.out.println("\n\n" + Arrays.toString(samples));
		System.out.println(new RegexAnalyzer(samples).generateRegex());

		samples = new String[] { "\"102983129", "\"014210290" };
		System.out.println("\n\n" + Arrays.toString(samples));
		System.out.println(new RegexAnalyzer(samples).generateRegex());
	}
}
