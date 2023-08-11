package org.example.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSource {

	static final String DB_URL = "jdbc:postgresql://localhost:5432/intexsoft";
	static final String USER = "postgres";
	static final String PASS = "postgres";

	private static DataSource INSTANCE;

	private DataSource() {
	}

	public static DataSource getInstance() {

		if (INSTANCE == null) {
			INSTANCE = new DataSource();
		}

		return INSTANCE;
	}

	public Connection getConnection(){

		try {
			return DriverManager.getConnection(DB_URL,USER,PASS);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
