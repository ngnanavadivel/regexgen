package com.objectapps.regexgen.db.transformers;

import java.sql.ResultSet;

/**
 * @author Nataraj Gnanavadivel
 *
 * @param <T>
 */
public interface ResultTransformer<T> {
   T getResults();

   void transform(ResultSet rs);
}
