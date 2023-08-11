package org.example.dao;

import org.example.database.DataSource;
import org.example.entity.Bank;
import org.example.entity.Client;
import org.example.util.ResultMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDao {

	private static ClientDao INSTANCE;
	private Connection connection;

	private ClientDao() {

		this.connection = DataSource.getInstance().getConnection();
	}

	public static ClientDao getInstance(){
		if (INSTANCE == null) {
			INSTANCE = new ClientDao();
		}

		return INSTANCE;
	}
	public void createClient(Client client) throws SQLException {

		String clientQuery = "INSERT INTO Client (name, type) VALUES (?, ?)";
		String accountQuery = "INSERT INTO Account (currency, balance, client_id, bank_id) VALUES (?, ?, ?, ?)";
		String bankQuery = "SELECT * FROM Bank WHERE id = ?";
		int newClientid = 0;
		int bankId=0;
		connection.setAutoCommit(false);


			try (PreparedStatement statement = connection.prepareStatement(bankQuery)) {
				statement.setInt(1, client.getBankId());
				try (ResultSet resultSet = statement.executeQuery()) {
					if (resultSet.next()) {
						bankId= resultSet.getInt("id");
					}else {
						System.out.println("Ошибка добавления клиента, банка не существует");
						return;
					}
				}
			}catch (Exception e){
				System.out.println(e);
				connection.rollback();
			}


		try (PreparedStatement statement = connection.prepareStatement(clientQuery, Statement.RETURN_GENERATED_KEYS)) {
			statement.setString(1, client.getName());
			statement.setString(2, client.getType());
			statement.executeUpdate();

			try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					newClientid = generatedKeys.getInt(1);
					client.setId(newClientid);
				}
			}
		}catch (Exception e){
			System.out.println(e);
			connection.rollback();
		}
		try (PreparedStatement statement = connection.prepareStatement(accountQuery, Statement.RETURN_GENERATED_KEYS)) {
			statement.setString(1, "USD");
			statement.setInt(2, 0);
			statement.setInt(3, newClientid);
			statement.setInt(4, client.getBankId());
			statement.executeUpdate();
		}catch (Exception e){
			System.out.println(e);
			connection.rollback();
		}

		connection.commit();

	}

	public List<Client> getClientsByBankId(int bankId) throws SQLException {
		List<Client> clients = new ArrayList<>();
		String query = "SELECT DISTINCT client.id, client.name, client.type FROM Client "
				+ "JOIN account ON client.id=account.client_id "
				+ "JOIN bank ON bank.id=account.bank_id "
				+ "WHERE bank.id = ?";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, bankId);
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					clients.add(ResultMapper.mapResultSetToClient(resultSet));
				}
			}
		}
		return clients;
	}

	public void updateClient(Client client) throws SQLException {
		String query = "UPDATE Client SET name = ?, type = ? WHERE id = ?";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, client.getName());
			statement.setString(2, client.getType());
			statement.setInt(3, client.getId());
			statement.executeUpdate();
		}
	}

	public void deleteClient(int id) throws SQLException {
		String query = "DELETE FROM Client WHERE id = ?";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, id);
			statement.executeUpdate();
		}
	}




}

