package com.objectapps.regexgen.db.transformers;

import java.sql.ResultSet;

public interface ResultTransformer<T> {
   T getResults();

   void transform(ResultSet rs);
}
