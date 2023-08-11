package org.example.command.account;

import org.example.command.Command;
import org.example.command.client.DeleteClientCommand;
import org.example.dao.AccountDao;
import org.example.dao.BankDao;

import java.sql.SQLException;

public class DeleteAccountCommand implements Command {

	private static DeleteAccountCommand INSTANCE;

	private final AccountDao accountDao;

	private DeleteAccountCommand() {

		this.accountDao =AccountDao.getInstance();
	}

	public static DeleteAccountCommand getInstance(){
		if (INSTANCE == null) {
			INSTANCE = new DeleteAccountCommand();
		}

		return INSTANCE;
	}

	@Override
	public String command() {

		return "deleteAccount";
	}

	@Override
	public String description() {

		return "deleteAccount <id> --- УДАЛИТЬ СЧЕТ";
	}

	@Override
	public void handle(String... args) throws SQLException {

		accountDao.deleteAccount(Integer.parseInt(args[1]));
	}
}
