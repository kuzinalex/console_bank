package org.example.dao;

import org.example.database.DataSource;
import org.example.entity.Account;
import org.example.util.ResultMapper;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDao {

	private static AccountDao INSTANCE;
	private Connection connection;

	private AccountDao() {

		this.connection = DataSource.getInstance().getConnection();
	}

	public static AccountDao getInstance(){
		if (INSTANCE == null) {
			INSTANCE = new AccountDao();
		}

		return INSTANCE;
	}

	public void createAccount(Account account) throws SQLException {
		String query = "INSERT INTO Account (currency, balance, client_id, bank_id) VALUES (?, ?, ?, ?)";
		try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
			statement.setString(1, account.getCurrency());
			statement.setBigDecimal(2, account.getBalance());
			statement.setInt(3, account.getClientId());
			statement.setInt(4, account.getBankId());
			statement.executeUpdate();

			try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					account.setId(generatedKeys.getInt(1));
				}
			}
		}
	}

	public Account getAccountById(int id) throws SQLException {
		String query = "SELECT * FROM Account WHERE id = ?";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, id);
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					return ResultMapper.mapResultSetToAccount(resultSet);
				}
			}
		}
		return null;
	}

	public List<Account> getAccountsByClientId(int clientId) throws SQLException {
		List<Account> accounts = new ArrayList<>();
		String query = "SELECT * FROM Account WHERE client_id = ?";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, clientId);
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					accounts.add( ResultMapper.mapResultSetToAccount(resultSet));
				}
			}
		}
		return accounts;
	}

	public void updateAccount(Account account) throws SQLException {
		String query = "UPDATE Account SET  currency = ?, balance = ?, client_id = ? WHERE id = ?";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, account.getCurrency());
			statement.setBigDecimal(2, account.getBalance());
			statement.setInt(3, account.getClientId());
			statement.setInt(4, account.getId());
			statement.executeUpdate();
		}
	}

	public void deleteAccount(int id) throws SQLException {
		String query = "DELETE FROM Account WHERE id = ?";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, id);
			statement.executeUpdate();
		}
	}

}
