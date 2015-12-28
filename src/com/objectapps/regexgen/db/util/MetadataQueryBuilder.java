package com.objectapps.regexgen.db.util;

/**
 * @author Nataraj Gnanavadivel
 *
 */
public interface MetadataQueryBuilder {
   String getQueryToListAllColumns(String tableName);

   String getQueryToListAllTables();

   String getQueryToSelectColumnValues(String tableName, String columnName, long rowLimit);
}
