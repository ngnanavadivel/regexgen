package com.objectapps.regexgen.db.util;

public interface MetadataQueryBuilder {
	String getQueryToListAllTables();
	String getQueryToListAllColumns(String tableName);
	String getQueryToSelectColumnValues(String tableName, String columnName, long rowLimit);
}
