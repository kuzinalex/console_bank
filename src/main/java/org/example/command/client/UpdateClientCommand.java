package org.example.command.client;

import org.example.command.Command;
import org.example.dao.ClientDao;
import org.example.entity.Client;

import java.sql.SQLException;

public class UpdateClientCommand implements Command {

	private static UpdateClientCommand INSTANCE;
	private final ClientDao clientDao;

	private UpdateClientCommand() {

		this.clientDao = ClientDao.getInstance();
	}

	public static UpdateClientCommand getInstance() {

		if (INSTANCE == null) {
			INSTANCE = new UpdateClientCommand();
		}

		return INSTANCE;
	}

	@Override
	public String command() {

		return "updateClient";
	}

	@Override
	public String description() {

		return "updateClient <id> <имя> <тип company|individual> --- ОБНОВИТЬ КЛИЕНТА";
	}

	@Override
	public void handle(String... args) throws SQLException, IndexOutOfBoundsException, NumberFormatException, NullPointerException {
		Client client = new Client();
		client.setId(Integer.parseInt(args[1]));
		client.setName(args[2]);
		client.setType(args[3]);
		if (client.getType().equals("company") || client.getType().equals("individual")) {
			clientDao.updateClient(client);
		} else {
			throw new NumberFormatException();
		}

		System.out.println("Клиент обновлен");

	}
}
