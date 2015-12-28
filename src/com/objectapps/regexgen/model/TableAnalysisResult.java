package com.objectapps.regexgen.model;

import java.util.List;

public class TableAnalysisResult {
   private List<ColumnAnalysisResult> columnResults;
   private String                     tableName;

   public TableAnalysisResult(String table, List<ColumnAnalysisResult> columnResults) {
      this.tableName = table;
      this.columnResults = columnResults;
   }

   public List<ColumnAnalysisResult> getColumnResults() {
      return columnResults;
   }

   public String getTableName() {
      return tableName;
   }

   public void setColumnResults(List<ColumnAnalysisResult> columnResults) {
      this.columnResults = columnResults;
   }

   public void setTableName(String tableName) {
      this.tableName = tableName;
   }
}
