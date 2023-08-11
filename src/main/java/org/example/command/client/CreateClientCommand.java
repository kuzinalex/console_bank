package org.example.command.client;

import org.example.command.Command;
import org.example.command.bank.UpdateBankCommand;
import org.example.dao.ClientDao;
import org.example.entity.Client;

import java.sql.SQLException;

public class CreateClientCommand implements Command {

	private static CreateClientCommand INSTANCE;
	private final ClientDao clientDao;

	private CreateClientCommand() {

		this.clientDao = ClientDao.getInstance();
	}

	public static CreateClientCommand getInstance() {

		if (INSTANCE == null) {
			INSTANCE = new CreateClientCommand();
		}

		return INSTANCE;
	}

	@Override
	public String command() {

		return "createClient";
	}

	@Override
	public String description() {

		return "createClient <имя> <тип company|individual> <id банка> --- СОЗДАТЬ КЛИЕНТА";
	}

	@Override
	public void handle(String... args) throws SQLException {

		Client client = new Client();
		client.setName(args[1]);
		client.setType(args[2]);
		client.setBankId(Integer.parseInt(args[3]));
		if (client.getType().equals("company") || client.getType().equals("individual")) {
			clientDao.createClient(client);
		} else {
			throw new NumberFormatException();
		}

		System.out.println("Клиент "+ client.getName()+" зарегистрирован");
	}
}
