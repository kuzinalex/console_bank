package org.example.entity;

public class Client {
	private int id;
	private String name;
	private String type;
	private int bankId;

	public Client(int id, String name, String type, int bankId) {

		this.id = id;
		this.name = name;
		this.type = type;
		this.bankId = bankId;
	}

	public Client() {


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

	public String getType() {

		return type;
	}

	public void setType(String type) {

		this.type = type;
	}

	public int getBankId() {

		return bankId;
	}

	public void setBankId(int bankId) {

		this.bankId = bankId;
	}

	@Override
	public String toString() {

		return "Client{" +
				"id=" + id +
				", name='" + name + '\'' +
				", type='" + type + '\'' +
				", bankId=" + bankId +
				'}';
	}
}