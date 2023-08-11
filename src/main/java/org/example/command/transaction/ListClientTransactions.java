package org.example.command.transaction;

import org.example.command.Command;
import org.example.dao.TransactionDao;
import org.example.entity.Transaction;

import java.sql.SQLException;
import java.util.List;

public class ListClientTransactions implements Command {

	private static ListClientTransactions INSTANCE;

	private final TransactionDao transactionDao;

	private ListClientTransactions() {

		this.transactionDao = TransactionDao.getInstance();
	}

	public static ListClientTransactions getInstance(){
		if (INSTANCE == null) {
			INSTANCE = new ListClientTransactions();
		}

		return INSTANCE;
	}

	@Override
	public String command() {

		return "listTransactions";
	}

	@Override
	public String description() {

		return "listTransactions <id клиента> --- СПИСОК ТРАНЗАКЦИЙ КЛИЕНТА";
	}

	@Override
	public void handle(String... args) throws SQLException {

		List<Transaction> transactions=transactionDao.getTransactionsByClientId(Integer.parseInt(args[1]));

		System.out.println("Список транзакций");
		transactions.forEach(System.out::println);
	}
}
