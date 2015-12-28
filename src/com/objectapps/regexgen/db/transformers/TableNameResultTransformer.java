package com.objectapps.regexgen.db.transformers;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TableNameResultTransformer implements ResultTransformer<List<String>> {

	List<String> results = new ArrayList<String>();

	@Override
	public List<String> getResults() {
		return results;
	}

	@Override
	public void transform(ResultSet rs) {
		try {
			if (rs != null) {
				while (rs.next()) {
					results.add(rs.getString(1));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
