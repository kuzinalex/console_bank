package org.example.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaction {
	private int id;
	private BigDecimal amount;
	private LocalDateTime transactionDate;
	private int sourceAccountId;
	private int targetAccountId;

	public Transaction(int id, BigDecimal amount, LocalDateTime transactionDate, int sourceAccountId, int targetAccountId) {

		this.id = id;
		this.amount = amount;
		this.transactionDate = transactionDate;
		this.sourceAccountId = sourceAccountId;
		this.targetAccountId = targetAccountId;
	}

	public Transaction() {


	}

	public int getId() {

		return id;
	}

	public void setId(int id) {

		this.id = id;
	}

	public BigDecimal getAmount() {

		return amount;
	}

	public void setAmount(BigDecimal amount) {

		this.amount = amount;
	}

	public LocalDateTime getTransactionDate() {

		return transactionDate;
	}

	public void setTransactionDate(LocalDateTime transactionDate) {

		this.transactionDate = transactionDate;
	}

	public int getSourceAccountId() {

		return sourceAccountId;
	}

	public void setSourceAccountId(int sourceAccountId) {

		this.sourceAccountId = sourceAccountId;
	}

	public int getTargetAccountId() {

		return targetAccountId;
	}

	public void setTargetAccountId(int targetAccountId) {

		this.targetAccountId = targetAccountId;
	}

	@Override
	public String toString() {

		return "Transaction{" +
				"id=" + id +
				", amount=" + amount +
				", transactionDate=" + transactionDate +
				", sourceAccountId=" + sourceAccountId +
				", targetAccountId=" + targetAccountId +
				'}';
	}
}
