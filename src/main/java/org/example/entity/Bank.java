package org.example.entity;

import java.math.BigDecimal;

public class Bank {
	private int id;
	private String name;
	private BigDecimal individualCommission;
	private BigDecimal companyCommission;

	public Bank(int id, String name, BigDecimal individualCommission, BigDecimal companyCommission) {

		this.id = id;
		this.name = name;
		this.individualCommission = individualCommission;
		this.companyCommission = companyCommission;
	}

	public Bank() {

	}

	public int getId() {

		return id;
	}

	public void setId(int id) {

		this.id = id;
	}

	public String getName() {

		return name;
	}

	public void setName(String name) {

		this.name = name;
	}

	public BigDecimal getIndividualCommission() {

		return individualCommission;
	}

	public void setIndividualCommission(BigDecimal individualCommission) {

		this.individualCommission = individualCommission;
	}

	public BigDecimal getCompanyCommission() {

		return companyCommission;
	}

	public void setCompanyCommission(BigDecimal companyCommission) {

		this.companyCommission = companyCommission;
	}

	@Override
	public String toString() {

		return "Bank{" +
				"id=" + id +
				", name='" + name + '\'' +
				", individualCommission=" + individualCommission +
				", entityCommission=" + companyCommission +
				'}';
	}
}
