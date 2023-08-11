package org.example;

import org.example.processor.ConsoleProcessor;

import java.sql.SQLException;


public class App {

	public static void main(String[] args) throws SQLException {

		new ConsoleProcessor().run();
	}
}
