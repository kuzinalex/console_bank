package org.example.util;

import org.example.entity.Account;
import org.example.entity.Bank;
import org.example.entity.Client;
import org.example.entity.Transaction;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultMapper {

	public static Account mapResultSetToAccount(ResultSet resultSet) throws SQLException {
		Account account = new Account();
		account.setId(resultSet.getInt("id"));
		account.setCurrency(resultSet.getString("currency"));
		account.setBalance(resultSet.getBigDecimal("balance"));
		account.setClientId(resultSet.getInt("client_id"));
		account.setBankId(resultSet.getInt("bank_id"));
		return account;
	}

	public static Bank mapResultSetToBank(ResultSet resultSet) throws SQLException {

		Bank bank = new Bank();
		bank.setId(resultSet.getInt("id"));
		bank.setName(resultSet.getString("name"));
		bank.setIndividualCommission(resultSet.getBigDecimal("individual_commission"));
		bank.setCompanyCommission(resultSet.getBigDecimal("company_commission"));
		return bank;
	}

	public static Client mapResultSetToClient(ResultSet resultSet) throws SQLException {
		Client client = new Client();
		client.setId(resultSet.getInt("id"));
		client.setName(resultSet.getString("name"));
		client.setType(resultSet.getString("type"));
		return client;
	}

	public static Transaction mapResultSetToTransaction(ResultSet resultSet) throws SQLException {
		Transaction transaction = new Transaction();
		transaction.setId(resultSet.getInt("id"));
		transaction.setAmount(resultSet.getBigDecimal("amount"));
		transaction.setTransactionDate(resultSet.getTimestamp("transaction_date").toLocalDateTime());
		transaction.setSourceAccountId(resultSet.getInt("source_account_id"));
		transaction.setTargetAccountId(resultSet.getInt("target_account_id"));
		return transaction;
	}

}
