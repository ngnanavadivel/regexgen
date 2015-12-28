package com.objectapps.regexgen.model;

import java.util.List;

public class Response {
	private String						logs;
	private String						regex;
	private List<TableAnalysisResult>	results;

	public String getLogs() {
		return logs;
	}

	public String getRegex() {
		return regex;
	}

	public List<TableAnalysisResult> getResults() {
		return results;
	}

	public void setLogs(String logs) {
		this.logs = logs;
	}

	public void setRegex(String regex) {
		this.regex = regex;
	}

	public void setResults(List<TableAnalysisResult> results) {
		this.results = results;
	}
}
