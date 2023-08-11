package org.example.command.client;

import org.example.command.Command;
import org.example.dao.ClientDao;
import org.example.entity.Client;

import java.sql.SQLException;
import java.util.List;

public class ListBankClientsCommand implements Command {

	private static ListBankClientsCommand INSTANCE;
	private final ClientDao clientDao;

	private ListBankClientsCommand() {

		this.clientDao = ClientDao.getInstance();
	}

	public static ListBankClientsCommand getInstance() {

		if (INSTANCE == null) {
			INSTANCE = new ListBankClientsCommand();
		}

		return INSTANCE;
	}

	@Override
	public String command() {

		return "listClients";
	}

	@Override
	public String description() {

		return "listClients <id банка> --- СПИСОК КЛИЕНТОВ БАНКА";
	}

	@Override
	public void handle(String... args) throws SQLException, IndexOutOfBoundsException, NumberFormatException, NullPointerException {

		List<Client> clients=clientDao.getClientsByBankId(Integer.parseInt(args[1]));

		System.out.println("Список клиентов банка");
		clients.forEach(System.out::println);
	}
}
