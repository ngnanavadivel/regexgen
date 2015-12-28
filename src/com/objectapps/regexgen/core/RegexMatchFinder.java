package com.objectapps.regexgen.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.objectapps.regexgen.db.dao.MySqlDataExtractor;
import com.objectapps.regexgen.model.ColumnAnalysisResult;
import com.objectapps.regexgen.model.TableAnalysisResult;
import com.objectapps.regexgen.util.Logger;

public class RegexMatchFinder {
	public ColumnAnalysisResult analyzeColumn(List<String> columnValues, double thresholdPercentage, String regex) {
		ColumnAnalysisResult result = new ColumnAnalysisResult();

		if (columnValues != null) {
			int recordCount = columnValues.size();

			int countOfMatches = 0;
			Pattern pattern = Pattern.compile(regex);
			for (int i = 0; i < recordCount; ++i) {
				String value = columnValues.get(i);
				Matcher matcher = pattern.matcher(value);
				if (matcher.matches()) {
					Logger.instance().log(value + " is a match!");
					countOfMatches++;
				}
			}
			double matchingPercentage = (countOfMatches * 100.0 / recordCount);
			result.setMatching(matchingPercentage >= thresholdPercentage);
			result.setMatchPercentage(matchingPercentage);

		}
		return result;
	}

	public List<TableAnalysisResult> analyze(	String connectionUri,
												String userName,
												String password,
												long numRowsToCheck,
												double thresholdPercentage,
												String regex) {
		List<TableAnalysisResult> result = new ArrayList<TableAnalysisResult>();

		MySqlDataExtractor dao = new MySqlDataExtractor(connectionUri, userName, password);

		List<String> tableNames = dao.getTableNames();
		Logger.instance().log("Tables : " + tableNames);

		if (tableNames != null) {
			for (String table : tableNames) {
				Logger.instance().log("Columns of " + table);

				List<String> columnNames = dao.getColumnNames(table);
				Logger.instance().log(columnNames.toString());
				List<ColumnAnalysisResult> columnResults = new ArrayList<ColumnAnalysisResult>();
				for (String column : columnNames) {
					List<String> columnValues = dao.getColumnValues(table, column, numRowsToCheck);
					Logger.instance().log("Values of Column [" + column + "] : " + columnValues);
					ColumnAnalysisResult analysisResult = analyzeColumn(columnValues, thresholdPercentage, regex);
					analysisResult.setColumnName(column);
					columnResults.add(analysisResult);
				}
				result.add(new TableAnalysisResult(table, columnResults));
			}
		}

		return result;
	}

	public static void main(String[] args) {
		String[] samples = new String[] { "45913", "12345" };
		Set<String> expressions = new RegexAnalyzer(samples).generateRegex();
		if (expressions != null && expressions.size() == 1) {
			String regex = expressions.iterator().next();
			System.out.println(new RegexMatchFinder().analyze("jdbc:mysql://localhost:3306/customer?useSSL=false",
					"admin", "admin", 1000, 80.0, regex).toString());
		}

	}
}
