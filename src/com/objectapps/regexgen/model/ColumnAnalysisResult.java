package com.objectapps.regexgen.model;

public class ColumnAnalysisResult {
	private String	columnName;
	private boolean	isMatching;
	private double	matchPercentage;

	public double getMatchPercentage() {
		return matchPercentage;
	}

	public void setMatchPercentage(double matchPercentage) {
		this.matchPercentage = matchPercentage;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public boolean isMatching() {
		return isMatching;
	}

	public void setMatching(boolean isMatching) {
		this.isMatching = isMatching;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ColumnAnalysisResult [columnName=");
		builder.append(columnName);
		builder.append(", isMatching=");
		builder.append(isMatching);
		builder.append(", matchPercentage=");
		builder.append(matchPercentage);
		builder.append("]");
		return builder.toString();
	}

}
