package org.example.dao;

import org.example.database.DataSource;
import org.example.entity.Bank;
import org.example.util.ResultMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BankDao {

	private static BankDao INSTANCE;

	private Connection connection;

	private BankDao() {

		this.connection = DataSource.getInstance().getConnection();
	}

	public static BankDao getInstance(){
		if (INSTANCE == null) {
			INSTANCE = new BankDao();
		}

		return INSTANCE;
	}

	public void createBank(Bank bank) throws SQLException {

		String query = "INSERT INTO Bank (name, individual_commission, company_commission) VALUES (?, ?,?)";
		try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
			statement.setString(1, bank.getName());
			statement.setBigDecimal(2, bank.getIndividualCommission());
			statement.setBigDecimal(3, bank.getCompanyCommission());
			statement.executeUpdate();

			try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					bank.setId(generatedKeys.getInt(1));
				}
			}
		}
	}

	public List<Bank> getAllBanks() throws SQLException {

		List<Bank> banks = new ArrayList<>();
		String query = "SELECT * FROM Bank";
		try (PreparedStatement statement = connection.prepareStatement(query);
				ResultSet resultSet = statement.executeQuery()) {
			while (resultSet.next()) {
				banks.add(ResultMapper.mapResultSetToBank(resultSet));
			}
		}
		return banks;
	}

	public void updateBank(Bank bank) throws SQLException {

		String query = "UPDATE Bank SET name = ?, individual_commission = ?, company_commission=? WHERE id = ?";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, bank.getName());
			statement.setBigDecimal(2, bank.getIndividualCommission());
			statement.setBigDecimal(3, bank.getCompanyCommission());
			statement.setInt(4, bank.getId());
			statement.executeUpdate();
		}
	}

	public void deleteBank(int id) throws SQLException {

		String query = "DELETE FROM Bank WHERE id = ?";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, id);
			statement.executeUpdate();
		}
	}

}
