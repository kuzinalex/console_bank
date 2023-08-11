package org.example.processor;

import org.example.command.Command;
import org.example.command.help.HelpCommand;
import org.example.database.DatabaseCreator;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConsoleProcessor {

	private List<Command> commands;

	private Scanner scanner;

	public ConsoleProcessor() {

		this.commands = new ArrayList<>();
		this.commands.add(HelpCommand.getInstance());
		this.commands.addAll(HelpCommand.getInstance().getCommands());
		this.scanner = new Scanner(System.in);
	}

	public void run() throws SQLException {

		// раскоментить если нет бд
		//DatabaseCreator.initDatabase();

		while (true) {
			System.out.println("help --- список команд");
			System.out.println("exit --- выход");
			String input = scanner.nextLine();

			if (input.equals("exit")) {
				break;
			} else {
				String[] strings = input.split(" ");
				for (Command command : commands) {
					if (command.supports(strings[0])) {
						try {
							command.handle(strings);
						} catch (IndexOutOfBoundsException | NumberFormatException | NullPointerException | SQLException e) {
							System.out.println("Неверные аргументы команды");
							System.out.println(command.description());
						}
						break;
					}
				}
			}
		}

	}

}
