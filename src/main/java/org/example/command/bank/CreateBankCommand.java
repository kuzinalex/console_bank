package org.example.command.bank;

import org.example.command.Command;
import org.example.command.account.DeleteAccountCommand;
import org.example.dao.BankDao;
import org.example.entity.Bank;

import java.math.BigDecimal;
import java.sql.SQLException;

public class CreateBankCommand implements Command {

	private static CreateBankCommand INSTANCE;
	private final BankDao bankDao;

	private CreateBankCommand() {

		this.bankDao =BankDao.getInstance();
	}

	public static CreateBankCommand getInstance(){
		if (INSTANCE == null) {
			INSTANCE = new CreateBankCommand();
		}

		return INSTANCE;
	}

	@Override
	public String command() {

		return "createBank";
	}

	@Override
	public String description() {

		return "createBank <название> <комиссия для физ лиц> <комиссия для юр лиц> --- СОЗДАТЬ БАНК";
	}

	@Override
	public void handle(String... args) throws SQLException {

		Bank newBank=new Bank();
		newBank.setName(args[1]);
		newBank.setIndividualCommission(new BigDecimal(args[2]));
		newBank.setCompanyCommission(new BigDecimal(args[3]));

		bankDao.createBank(newBank);
		System.out.println(newBank.getName()+" успешно создан");

	}
}
