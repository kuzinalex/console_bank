package org.example.command;

import java.sql.SQLException;

public interface Command {

	String command();

	String description();

	void handle(String... args) throws SQLException, IndexOutOfBoundsException, NumberFormatException, NullPointerException;

	default boolean supports(String command) {

		return command.equals(command());
	}
}
