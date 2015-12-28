package com.objectapps.regexgen.db.util;

public class MySqlMetadataQueryBuilder implements MetadataQueryBuilder {

	@Override
	public String getQueryToListAllTables() {
		return "SHOW  TABLES";
	}

	@Override
	public String getQueryToListAllColumns(String tableName) {
		return "SHOW COLUMNS FROM " + tableName;
	}

	@Override
	public String getQueryToSelectColumnValues(String tableName, String columnName, long rowLimit) {
		return "SELECT " + columnName + " FROM " + tableName + " LIMIT " + rowLimit;
	}

}
