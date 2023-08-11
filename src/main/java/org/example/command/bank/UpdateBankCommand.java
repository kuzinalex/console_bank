package org.example.command.bank;

import org.example.command.Command;
import org.example.dao.BankDao;
import org.example.entity.Bank;

import java.math.BigDecimal;
import java.sql.SQLException;

public class UpdateBankCommand implements Command {

	private static UpdateBankCommand INSTANCE;
	private final BankDao bankDao;

	private UpdateBankCommand() {

		this.bankDao =BankDao.getInstance();
	}

	public static UpdateBankCommand getInstance(){
		if (INSTANCE == null) {
			INSTANCE = new UpdateBankCommand();
		}

		return INSTANCE;
	}

	@Override
	public String command() {

		return "updateBank";
	}

	@Override
	public String description() {

		return "updateBank <id> <название> <комиссия для физ лиц> <комиссия для юр лиц> --- ОБНОВИТЬ БАНК";
	}

	@Override
	public void handle(String... args) throws SQLException {

		Bank bank=new Bank(Integer.parseInt(args[1]),args[2], new BigDecimal(args[3]), new BigDecimal(args[4]));
		bankDao.updateBank(bank);
		System.out.println("Банк обновлен");
	}
}
