package com.objectapps.regexgen.db.transformers;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ColumnValueResultTransformer implements ResultTransformer<List<String>> {

   private List<String> values = new ArrayList<String>();

   @Override
   public List<String> getResults() {
      return values;
   }

   @Override
   public void transform(ResultSet rs) {
      try {
         if (rs != null) {
            while (rs.next()) {
               values.add(rs.getString(1));
            }
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

}
