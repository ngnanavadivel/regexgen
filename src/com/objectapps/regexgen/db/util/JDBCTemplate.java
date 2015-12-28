package com.objectapps.regexgen.db.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.objectapps.regexgen.db.transformers.ResultTransformer;

public class JDBCTemplate {
	private String		connectionUri;
	private String		userName;
	private String		password;
	private Connection	connection;
	private ResultSet	resultSet;

	public JDBCTemplate(String uri, String userName, String password) {
		this.connectionUri = uri;
		this.userName = userName;
		this.password = password;
	}

	public Connection openConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(connectionUri, userName, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}

	public ResultSet executeQuery(String query, ResultTransformer<?> transformer) {
		ResultSet records = null;
		if (connection != null) {
			Statement stmt = null;
			try {
				stmt = connection.createStatement();
				records = stmt.executeQuery(query);
				transformer.transform(records);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (stmt != null) {
					try {
						stmt.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return records;
	}

	public void closeResultSet() {
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void closeConnection() {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
