package com.objectapps.regexgen.model;

import java.util.List;

public class Response {
	private String						regex;
	private String						logs;
	private List<TableAnalysisResult>	results;

	public String getRegex() {
		return regex;
	}

	public void setRegex(String regex) {
		this.regex = regex;
	}

	public String getLogs() {
		return logs;
	}

	public void setLogs(String logs) {
		this.logs = logs;
	}

	public List<TableAnalysisResult> getResults() {
		return results;
	}

	public void setResults(List<TableAnalysisResult> results) {
		this.results = results;
	}
}
