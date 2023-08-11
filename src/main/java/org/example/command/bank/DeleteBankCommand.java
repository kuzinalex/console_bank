package org.example.command.bank;

import org.example.command.Command;
import org.example.dao.BankDao;

import java.sql.SQLException;

public class DeleteBankCommand implements Command {

	private static DeleteBankCommand INSTANCE;
	private final BankDao bankDao;

	private DeleteBankCommand() {

		this.bankDao =BankDao.getInstance();
	}

	public static DeleteBankCommand getInstance(){
		if (INSTANCE == null) {
			INSTANCE = new DeleteBankCommand();
		}

		return INSTANCE;
	}

	@Override
	public String command() {

		return "deleteBank";
	}

	@Override
	public String description() {

		return "deleteBank <id> --- УДАЛИТЬ БАНК";
	}

	@Override
	public void handle(String... args) throws SQLException {

		bankDao.deleteBank(Integer.parseInt(args[1]));

		System.out.println("Банк удален");
	}
}
