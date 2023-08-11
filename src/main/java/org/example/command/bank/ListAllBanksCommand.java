package org.example.command.bank;

import org.example.command.Command;
import org.example.dao.BankDao;
import org.example.entity.Bank;

import java.sql.SQLException;
import java.util.List;

public class ListAllBanksCommand implements Command {

	private static ListAllBanksCommand INSTANCE;
	private final BankDao bankDao;

	private ListAllBanksCommand() {

		this.bankDao = BankDao.getInstance();
	}

	public static ListAllBanksCommand getInstance() {

		if (INSTANCE == null) {
			INSTANCE = new ListAllBanksCommand();
		}

		return INSTANCE;
	}

	@Override
	public String command() {

		return "listBanks";
	}

	@Override
	public String description() {

		return "listBanks --- ВЫВЕСТИ СПИСОК БАНКОВ";
	}

	@Override
	public void handle(String... args) throws SQLException {

		List<Bank> banks = bankDao.getAllBanks();
		if (banks.isEmpty()) {
			System.out.println("Ничего не найдено");
		} else {
			System.out.println("Список банков");
			banks.forEach(System.out::println);
		}

	}
}
