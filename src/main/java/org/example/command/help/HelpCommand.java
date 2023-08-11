package org.example.command.help;

import org.example.command.Command;
import org.example.command.account.CreateAccountCommand;
import org.example.command.account.DeleteAccountCommand;
import org.example.command.account.ListClientAccountsCommand;
import org.example.command.bank.CreateBankCommand;
import org.example.command.bank.DeleteBankCommand;
import org.example.command.bank.ListAllBanksCommand;
import org.example.command.bank.UpdateBankCommand;
import org.example.command.client.CreateClientCommand;
import org.example.command.client.DeleteClientCommand;
import org.example.command.client.ListBankClientsCommand;
import org.example.command.client.UpdateClientCommand;
import org.example.command.transaction.ListClientTransactions;
import org.example.command.transaction.MakeTransactionCommand;

import java.util.ArrayList;
import java.util.List;

public class HelpCommand implements Command {

	private static HelpCommand INSTANCE;

	private List<Command> commands;

	public List<Command> getCommands() {

		return commands;
	}

	private HelpCommand() {

		this.commands = new ArrayList<>();
		this.commands.add(CreateBankCommand.getInstance());
		this.commands.add(ListAllBanksCommand.getInstance());
		this.commands.add(UpdateBankCommand.getInstance());
		this.commands.add(DeleteBankCommand.getInstance());

		this.commands.add(CreateAccountCommand.getInstance());
		this.commands.add(DeleteAccountCommand.getInstance());
		this.commands.add(ListClientAccountsCommand.getInstance());


		this.commands.add(CreateClientCommand.getInstance());
		this.commands.add( DeleteClientCommand.getInstance());
		this.commands.add(ListBankClientsCommand.getInstance());
		this.commands.add(UpdateClientCommand.getInstance());

		this.commands.add(MakeTransactionCommand.getInstance());
		this.commands.add(ListClientTransactions.getInstance());
	}

	public static HelpCommand getInstance(){
		if (INSTANCE == null) {
			INSTANCE = new HelpCommand();
		}

		return INSTANCE;
	}

	@Override
	public String command() {

		return "help";
	}

	@Override
	public String description() {

		return "help";
	}

	@Override
	public void handle(String... args) {

		for (Command command : commands) {
			System.out.println(command.description());
		}
	}
}
