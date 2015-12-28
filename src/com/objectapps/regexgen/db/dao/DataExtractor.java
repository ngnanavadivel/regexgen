package com.objectapps.regexgen.db.dao;

import java.util.List;

public interface DataExtractor {
	List<String> getTableNames();
	List<String> getColumnNames(String tableName);
	List<String> getColumnValues(String tableName, String columnName, long rowLimit);
}
