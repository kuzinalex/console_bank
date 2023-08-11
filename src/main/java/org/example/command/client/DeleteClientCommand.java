package org.example.command.client;

import org.example.command.Command;
import org.example.dao.ClientDao;

import java.sql.SQLException;

public class DeleteClientCommand implements Command {

	private static DeleteClientCommand INSTANCE;

	private final ClientDao clientDao;

	private DeleteClientCommand() {

		this.clientDao = ClientDao.getInstance();
	}

	public static DeleteClientCommand getInstance(){
		if (INSTANCE == null) {
			INSTANCE = new DeleteClientCommand();
		}

		return INSTANCE;
	}
	@Override
	public String command() {

		return "deleteClient";
	}

	@Override
	public String description() {

		return "deleteClient <id> --- УДАЛИТЬ КЛИЕНТА";
	}

	@Override
	public void handle(String... args) throws SQLException {

		clientDao.deleteClient(Integer.parseInt(args[1]));

		System.out.println("Клиент удален");

	}
}
