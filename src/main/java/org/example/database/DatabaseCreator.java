package org.example.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseCreator {

	public static void initDatabase(){
				try (Connection con = DataSource.getInstance().getConnection()) {
					Statement stmt = con.createStatement();

					String tableSql = "CREATE TABLE Bank ("
							+ "    id SERIAL PRIMARY KEY,"
							+ "    name VARCHAR(255) NOT NULL,"
							+ "    individual_commission DECIMAL(10, 2),"
							+ "    company_commission DECIMAL(10, 2)"
							+ ");"
							+ "CREATE TABLE Client ("
							+ "    id SERIAL PRIMARY KEY,"
							+ "    name VARCHAR(255) NOT NULL,"
							+ "    type VARCHAR(20) NOT NULL"
							+ ");"
							+ "CREATE TABLE Currency ("
							+ "    id VARCHAR PRIMARY KEY,"
							+ "    rate NUMERIC NOT NULL"
							+ "); "
							+ "CREATE TABLE Account ("
							+ "    id SERIAL PRIMARY KEY,"
							+ "    currency VARCHAR(10) NOT NULL,"
							+ "    balance DECIMAL(10, 2) DEFAULT 0.0,"
							+ "    client_id INT NOT NULL,"
							+ "	   bank_id INT NOT NULL,"
							+ "    FOREIGN KEY (client_id) REFERENCES Client(id) ON DELETE CASCADE,"
							+ "	   FOREIGN KEY (bank_id) REFERENCES Bank(id) ON DELETE CASCADE,"
							+ "	   FOREIGN KEY (currency) REFERENCES Currency(id)"
							+ ");"
							+ "CREATE TABLE Transaction ("
							+ "    id SERIAL PRIMARY KEY,"
							+ "    amount DECIMAL(10, 2) NOT NULL,"
							+ "    transaction_date TIMESTAMP NOT NULL,"
							+ "    source_account_id INT,"
							+ "    target_account_id INT,"
							+ "    FOREIGN KEY (source_account_id) REFERENCES Account(id) ON DELETE CASCADE ,"
							+ "    FOREIGN KEY (target_account_id) REFERENCES Account(id) ON DELETE CASCADE "
							+ ");"
							+ "INSERT INTO bank (id, name, individual_commission,company_commission)"
							+ "VALUES "
							+ "(1, 'Alfa', 5, 10),"
							+ "(2, 'Beta', 10, 10),"
							+ "(3, 'Prior', 50, 10);"
							+ "INSERT INTO public.client (id, name, type) VALUES (1, 'Alex', 'individual');"
							+ "INSERT INTO public.client (id, name, type) VALUES (2, 'Apple', 'company');"
							+ "INSERT INTO public.client (id, name, type) VALUES (3, 'Ivan', 'individual');"
							+ "INSERT INTO public.currency (id, rate) VALUES ('USD', 1);"
							+ "INSERT INTO public.currency (id, rate) VALUES ('BYN', 3);"
							+ "INSERT INTO public.currency (id, rate) VALUES ('EUR', 2);"
							+ "INSERT INTO public.account (id, currency, balance, client_id, bank_id) VALUES (2, 'USD', 50.00, 1, 1);"
							+ "INSERT INTO public.account (id, currency, balance, client_id, bank_id) VALUES (1, 'USD', 450.00, 1, 1);"
							+ "INSERT INTO public.account (id, currency, balance, client_id, bank_id) VALUES (4, 'USD', 45.00, 3, 2);"
							+ "INSERT INTO public.account (id, currency, balance, client_id, bank_id) VALUES (3, 'USD', 1700.00, 2, 3);";
					stmt.execute(tableSql);
					System.out.println("Hello World!");

				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
	}

}
