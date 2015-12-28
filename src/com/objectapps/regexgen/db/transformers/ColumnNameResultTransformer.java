package com.objectapps.regexgen.db.transformers;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ColumnNameResultTransformer implements ResultTransformer<List<String>> {
	
	List<String> columns = new ArrayList<String>();

	@Override
	public List<String> getResults() {
		return columns;
	}

	@Override
	public void transform(ResultSet rs) {
		try {
			if (rs != null) {
				while (rs.next()) {
					columns.add(rs.getString("field"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
