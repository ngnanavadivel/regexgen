package com.objectapps.regexgen.db.dao;

import java.util.List;

/**
 * @author Nataraj Gnanavadivel
 *
 */
public interface DataExtractor {
   List<String> getColumnNames(String tableName);

   List<String> getColumnValues(String tableName, String columnName, long rowLimit);

   List<String> getTableNames();
}
