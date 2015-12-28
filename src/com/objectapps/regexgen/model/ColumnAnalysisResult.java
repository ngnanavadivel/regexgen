package com.objectapps.regexgen.model;

public class ColumnAnalysisResult {
   private String  columnName;
   private boolean isMatching;
   private double  matchPercentage;

   public String getColumnName() {
      return columnName;
   }

   public double getMatchPercentage() {
      return matchPercentage;
   }

   public boolean isMatching() {
      return isMatching;
   }

   public void setColumnName(String columnName) {
      this.columnName = columnName;
   }

   public void setMatching(boolean isMatching) {
      this.isMatching = isMatching;
   }

   public void setMatchPercentage(double matchPercentage) {
      this.matchPercentage = matchPercentage;
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
