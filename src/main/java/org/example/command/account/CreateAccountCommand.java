package org.example.command.account;

import org.example.command.Command;
import org.example.dao.AccountDao;
import org.example.entity.Account;

import java.math.BigDecimal;
import java.sql.SQLException;

public class CreateAccountCommand implements Command {

	private static CreateAccountCommand INSTANCE;

	private final AccountDao accountDao;

	private CreateAccountCommand() {

		this.accountDao = AccountDao.getInstance();
	}

	public static CreateAccountCommand getInstance(){
		if (INSTANCE == null) {
			INSTANCE = new CreateAccountCommand();
		}

		return INSTANCE;
	}

	@Override
	public String command() {

		return "createAccount";
	}

	@Override
	public String description() {

		return "createAccount <id клиента> <id банка> <валюта> --- СОЗДАТЬ СЧЕТ";
	}

	@Override
	public void handle(String... args) throws SQLException, IndexOutOfBoundsException, NumberFormatException, NullPointerException {

		Account account=new Account();
		account.setBalance(new BigDecimal(0));
		account.setClientId(Integer.parseInt(args[1]));
		account.setBankId(Integer.parseInt(args[2]));
		account.setCurrency(args[3]);

		accountDao.createAccount(account);

	}
}
