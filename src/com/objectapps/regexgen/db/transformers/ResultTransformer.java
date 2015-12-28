package com.objectapps.regexgen.db.transformers;

import java.sql.ResultSet;

public interface ResultTransformer<T> {
	void transform(ResultSet rs);
	T getResults();
}
