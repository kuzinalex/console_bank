package org.example.entity;

import java.math.BigDecimal;

public class Account {
	private int id;
	private String currency;
	private BigDecimal balance;
	private int clientId;

	private int bankId;

	public Account(int id, String currency, BigDecimal balance, int clientId, int bankId) {

		this.id = id;
		this.currency = currency;
		this.balance = balance;
		this.clientId = clientId;
		this.bankId = bankId;
	}

	public Account() {

	}

	public int getId() {

		return id;
	}

	public void setId(int id) {

		this.id = id;
	}

	public String getCurrency() {

		return currency;
	}

	public void setCurrency(String currency) {

		this.currency = currency;
	}

	public BigDecimal getBalance() {

		return balance;
	}

	public void setBalance(BigDecimal balance) {

		this.balance = balance;
	}

	public int getClientId() {

		return clientId;
	}

	public void setClientId(int clientId) {

		this.clientId = clientId;
	}

	public int getBankId() {

		return bankId;
	}

	public void setBankId(int bankId) {

		this.bankId = bankId;
	}

	@Override
	public String toString() {

		return "Account {" +
				"id=" + id +
				", currency='" + currency + '\'' +
				", balance=" + balance +
				", clientId=" + clientId +
				", bankId=" + bankId +
				'}';
	}
}

