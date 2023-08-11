package org.example.command.account;

import org.example.command.Command;
import org.example.dao.AccountDao;
import org.example.entity.Account;

import java.sql.SQLException;
import java.util.List;

public class ListClientAccountsCommand implements Command {

	private static ListClientAccountsCommand INSTANCE;
	private final AccountDao accountDao;

	private ListClientAccountsCommand() {

		this.accountDao = AccountDao.getInstance();
	}

	public static ListClientAccountsCommand getInstance(){
		if (INSTANCE == null) {
			INSTANCE = new ListClientAccountsCommand();
		}

		return INSTANCE;
	}

	@Override
	public String command() {

		return "listAccounts";
	}

	@Override
	public String description() {

		return "listAccounts <id клиента> --- СПИСОК СЧЕТОВ КЛИЕНТА";
	}

	@Override
	public void handle(String... args) throws SQLException {

		List<Account> accounts=accountDao.getAccountsByClientId(Integer.parseInt(args[1]));

		System.out.println("Список счетов");
		accounts.forEach(System.out::println);

	}
}
