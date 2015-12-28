package com.objectapps.regexgen.model;

import java.util.List;

public class TableAnalysisResult {
	private String						tableName;
	private List<ColumnAnalysisResult>	columnResults;

	public TableAnalysisResult(String table, List<ColumnAnalysisResult> columnResults) {
		this.tableName = table;
		this.columnResults = columnResults;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public List<ColumnAnalysisResult> getColumnResults() {
		return columnResults;
	}

	public void setColumnResults(List<ColumnAnalysisResult> columnResults) {
		this.columnResults = columnResults;
	}
}
