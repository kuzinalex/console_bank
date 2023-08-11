package org.example.dao;

import org.example.database.DataSource;
import org.example.entity.Account;
import org.example.entity.Transaction;
import org.example.util.ResultMapper;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class TransactionDao {

	private static  TransactionDao INSTANCE;
	private Connection connection;

	private TransactionDao() {

		this.connection = DataSource.getInstance().getConnection();
	}

	public static TransactionDao getInstance(){
		if (INSTANCE == null) {
			INSTANCE = new TransactionDao();
		}

		return INSTANCE;
	}

	public void createTransaction(Transaction transaction) throws SQLException {

		connection.setAutoCommit(false);

		Account sourceAccount;
		String sourceClientType;
		int sourceCurrency;

		Account targetAccount;
		int targetCurrency;

		int individualCommission;
		int companyCommission;

		BigDecimal commission= BigDecimal.valueOf(0);

		String accountQuery = "select account.id, account.currency, account.balance,"
				+ "account.client_id, account.bank_id, client.type,"
				+ "bank.individual_commission, bank.company_commission, currency.rate "
				+ "FROM account "
				+ "JOIN client ON client.id=account.client_id "
				+ "JOIN bank ON bank.id=account.bank_id "
				+ "JOIN currency ON currency.id=account.currency "
				+ "WHERE account.id=?";
		try (PreparedStatement statement = connection.prepareStatement(accountQuery)) {
			statement.setInt(1, transaction.getSourceAccountId());
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					 sourceAccount=ResultMapper.mapResultSetToAccount(resultSet);
					 sourceClientType=resultSet.getString("type");
					 individualCommission=resultSet.getInt("individual_commission");
					companyCommission=resultSet.getInt("company_commission");
					sourceCurrency=resultSet.getInt("rate");
				}else {
					System.out.println("Неверные данные отправителя");
					return;
				}
			}
		}
		if (sourceAccount.getBalance().intValue()<transaction.getAmount().intValue()){
			System.out.println("Недостаточно средств для перевода");
			return;
		}

		try (PreparedStatement statement = connection.prepareStatement(accountQuery)) {
			statement.setInt(1, transaction.getTargetAccountId());
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					targetAccount=ResultMapper.mapResultSetToAccount(resultSet);
					targetCurrency=resultSet.getInt("rate");
				}else {
					System.out.println("Неверные данные получателя");
					return;
				}
			}
		}

		if (sourceAccount.getBankId()!=targetAccount.getBankId()){
			if (sourceClientType.equals("individual")){
				commission=transaction.getAmount().divide(BigDecimal.valueOf(100)).multiply(BigDecimal.valueOf(individualCommission));
			}else {
				commission=transaction.getAmount().divide(BigDecimal.valueOf(100)).multiply(BigDecimal.valueOf(companyCommission));
			}
		}

		String query = "UPDATE Account SET   balance = ? WHERE id = ?";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			BigDecimal amount=transaction.getAmount().add(commission);
			statement.setBigDecimal(1, sourceAccount.getBalance().subtract(amount));
			statement.setInt(2, transaction.getSourceAccountId());

			statement.executeUpdate();
		}

		try (PreparedStatement statement = connection.prepareStatement(query)) {
			BigDecimal newAmount=transaction.getAmount().divide(BigDecimal.valueOf(sourceCurrency)).multiply(BigDecimal.valueOf(targetCurrency));
			statement.setBigDecimal(1, targetAccount.getBalance().add(newAmount));
			statement.setInt(2, transaction.getTargetAccountId());

			statement.executeUpdate();
		}



		String transactionQuery = "INSERT INTO Transaction (amount, transaction_date, source_account_id, target_account_id) VALUES (?, ?, ?, ?)";
		try (PreparedStatement statement = connection.prepareStatement(transactionQuery, Statement.RETURN_GENERATED_KEYS)) {
			statement.setBigDecimal(1, transaction.getAmount());
			statement.setTimestamp(2, Timestamp.valueOf(transaction.getTransactionDate()));
			statement.setInt(3, transaction.getSourceAccountId());
			statement.setInt(4, transaction.getTargetAccountId());
			statement.executeUpdate();

			try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					transaction.setId(generatedKeys.getInt(1));
				}
			}
		}

		System.out.println("Транзакция проведена успешно. Комиссия = "+commission +" "+ sourceAccount.getCurrency());

		connection.commit();
	}

	public List<Transaction> getTransactionsByClientId(int id) throws SQLException {
		List<Transaction> transactions = new ArrayList<>();
		String query = "SELECT * FROM Transaction "
				+ "WHERE source_account_id IN "
				+ "(SELECT account.id FROM account WHERE account.client_id=?) "
				+ "OR target_account_id IN "
				+ "(SELECT account.id FROM account WHERE account.client_id=?) ";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, id);
			statement.setInt(2, id);
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					transactions.add(ResultMapper.mapResultSetToTransaction(resultSet));
				}
			}
		}
		return transactions;
	}

}
