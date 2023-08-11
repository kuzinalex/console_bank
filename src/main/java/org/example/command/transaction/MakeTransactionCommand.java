package org.example.command.transaction;

import org.example.command.Command;
import org.example.dao.TransactionDao;
import org.example.entity.Transaction;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class MakeTransactionCommand implements Command {

	private static MakeTransactionCommand INSTANCE;

	private final TransactionDao transactionDao;

	private MakeTransactionCommand() {

		this.transactionDao = TransactionDao.getInstance();
	}

	public static MakeTransactionCommand getInstance(){
		if (INSTANCE == null) {
			INSTANCE = new MakeTransactionCommand();
		}

		return INSTANCE;
	}

	@Override
	public String command() {

		return "makeTransaction";
	}

	@Override
	public String description() {

		return "makeTransaction <id счета отправителя> <id счета получателя> <сумма> --- ПЕРЕВЕСТИ ДЕНЬГИ";
	}

	@Override
	public void handle(String... args) throws SQLException {

		Transaction transaction=new Transaction();
		transaction.setSourceAccountId(Integer.parseInt(args[1]));
		transaction.setTargetAccountId(Integer.parseInt(args[2]));
		transaction.setAmount(new BigDecimal(args[3]));
		transaction.setTransactionDate(LocalDateTime.now());
		transactionDao.createTransaction(transaction);
	}
}
