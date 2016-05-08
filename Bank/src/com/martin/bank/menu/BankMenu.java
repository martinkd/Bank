package com.martin.bank.menu;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.martin.bank.accounts.Account;
import com.martin.bank.accounts.Rate;
import com.martin.bank.customers.Customer;
import com.martin.bank.managers.Manager;
import com.martin.bank.sql.ManagerDao;
import com.martin.bank.util.BankUtils;

public class BankMenu {

	private static final int NULL_ACC = 0;
	private static final int GET = 1;
	private static final int ADD = 2;
	private static final int DELETE = 3;
	private static final int UPDATE = 4;
	private static final int ADD_CUSTOMER = 1;
	private static final int ADD_ACCOUNT = 2;
	private static final int ADD_PAYMENT = 1;
	private static final int ADD_CREDIT = 2;
	private static final int ADD_SAVINGS = 3;
	private static final int GET_ALL_CUSTOMERS = 1;
	private static final int FIND_CUSTOMER_BY_ID = 2;
	private static final int GET_ALL_ACCOUNTS = 3;
	private static final int GET_CUSTOMER_ACCOUNTS = 4;
	private static final int FIND_ACCOUNT_BY_ID = 5;
	private static final int UPDATE_CUSTOMER = 1;
	private static final int UPDATE_NAME = 1;
	private static final int UPDATE_MANAGER = 2;
	private static final int UPDATE_ACCOUNT = 2;
	private static final int CHARGE = 1;
	private static final int PULL = 2;
	private static final int TRANSFER = 3;
	private static final int DELETE_CUSTOMER = 1;
	private static final int DELETE_ACCOUNT = 2;
	private static final int RETURN = 0;
	private static Scanner input = new Scanner(System.in);
	private ManagerDao mDao = new ManagerDao();
	String accountId = "Enter account id: ";

	public void RegManager(String name) throws SQLException {
		Manager manager = new Manager();
		manager.setName(name);
		mDao.add(manager);
	}

	public void logIn() throws SQLException {
		System.out.print("Enter ID to log in: ");
		int managerId = BankUtils.getValidInteger(input);
		Manager manager = mDao.findById(managerId);
		if (manager == null) {
			System.err.println("\nWrong ID");
			logIn();
		} else {
			System.out.println("\nWelcome, " + manager.getName() + "!");
			mainMenu(manager);
		}
	}

	private void mainMenu(Manager manager) throws SQLException {
		System.out.println("\tMAIN MENU");
		System.out.println("1. Get\n2. Add\n3. Delete\n4. Update");
		System.out.println("0. Log out");
		int option = BankUtils.getValidInteger(input);
		switch (option) {
		case GET:
			readMenu(manager);
			break;
		case ADD:
			addMenu(manager);
			break;
		case DELETE:
			deleteMenu(manager);
			break;
		case UPDATE:
			updateMenu(manager);
			break;
		case RETURN:
			logIn();
			break;
		default:
			System.out.println("INVALID OPTION");
			mainMenu(manager);
			break;
		}
	}

	private void returnReadMenu(Manager manager) throws SQLException {
		while (true) {
			System.out.println("Press \"0\" (zero) to return");
			if (BankUtils.getValidInteger(input) == RETURN) {
				readMenu(manager);
				break;
			}
		}
	}

	private void readMenu(Manager manager) throws SQLException {
		String command = "1. Get all customers\n" + "2. Find customer by ID\n" + "3. Get all accounts\n"
				+ "4. Get customer accounts\n" + "5. Find account by ID\n" + "0. RETURN";
		System.out.println(command);
		int option = BankUtils.getValidInteger(input);
		switch (option) {
		case GET_ALL_CUSTOMERS:
			System.out.println(manager.getCustomers());
			returnReadMenu(manager);
			break;
		case FIND_CUSTOMER_BY_ID:
			findCustomerMenu(manager);
			returnReadMenu(manager);
			break;
		case GET_ALL_ACCOUNTS:
			System.out.println(manager.getAllAccounts());
			returnReadMenu(manager);
			break;
		case GET_CUSTOMER_ACCOUNTS:
			getCustomerAccountsMenu(manager);
			returnReadMenu(manager);
			break;
		case FIND_ACCOUNT_BY_ID:
			findAccountMenu(manager);
			returnReadMenu(manager);
			break;
		case RETURN:
			mainMenu(manager);
		default:
			System.out.println("INVALID OPTION");
			readMenu(manager);
			break;
		}
	}

	private void findCustomerMenu(Manager manager) throws SQLException {
		Customer foundCustomer = findCustomer(manager);
		if (foundCustomer != null) {
			System.out.println(foundCustomer);
		} else {
			System.out.println("No such customer");
		}
	}

	private void getCustomerAccountsMenu(Manager manager) throws SQLException {
		Customer foundCustomer = findCustomer(manager);
		if (foundCustomer != null) {
			System.out.println(manager.getCustomerAccounts(foundCustomer));
		} else {
			System.out.println("No such customer");
		}
	}

	private Customer findCustomer(Manager manager) throws SQLException {
		System.out.println("Enter ID of customer: ");
		int id = BankUtils.getValidInteger(input);
		return manager.getCustomer(id);
	}

	private void findAccountMenu(Manager manager) throws SQLException {
		System.out.println("Enter account ID: ");
		int id = BankUtils.getValidInteger(input);
		Account foundAccount = manager.getAccount(id);
		if (foundAccount.getId() == NULL_ACC) {
			System.out.println("No such account");
		} else {
			System.out.println(foundAccount);
		}
	}

	private void addMenu(Manager manager) throws SQLException {
		String command = "Enter option\n1. Add customer\n2. Add account\n0. RETURN";
		System.out.println(command);
		int option = BankUtils.getValidInteger(input);
		switch (option) {
		case ADD_CUSTOMER:
			add_cust_menu(manager);
			addMenu(manager);
			break;
		case ADD_ACCOUNT:
			add_acc_menu(manager);
			addMenu(manager);
			break;
		case RETURN:
			mainMenu(manager);
			break;
		default:
			System.out.println("INVALID OPTION");
			addMenu(manager);
			break;
		}
	}

	private void add_cust_menu(Manager manager) throws SQLException {
		System.out.println("Enter name: ");
		String name = input.nextLine();
		manager.addCustomer(name);
		List<Customer> customers = manager.getCustomers();
		int lastCustomer = customers.size() - 1;
		int currentCustomerId = customers.get(lastCustomer).getId();
		Customer customer = manager.getCustomer(currentCustomerId);
		System.out.println(customer);
		System.out.println("Added successfully\n");
	}

	private void add_acc_menu(Manager manager) throws SQLException {
		String command = "Enter customer id: ";
		System.out.println(command);
		int customerId = BankUtils.getValidInteger(input);
		Customer customer = manager.getCustomer(customerId);
		if (customer == null) {
			System.out.println("No customer with such id");
		} else {
			String type = "Select type:\n1. PAYMENT\n2. CREDIT\n3. SAVINGS";
			System.out.println(type);
			int option = BankUtils.getValidInteger(input);
			switch (option) {
			case ADD_PAYMENT:
				manager.addPaymentAcc(customer);
				System.out.println("Payment added");
				break;
			case ADD_CREDIT:
				manager.addCreditAcc(customer, Rate.CREDIT_12_MONTHS);
				System.out.println("Credit added");
				break;
			case ADD_SAVINGS:
				manager.addSavingsAcc(customer, Rate.SAVINGS_12_MONTHS);
				System.out.println("Savings added");
				break;
			default:
				System.out.println("INVALID OPTION");
				break;
			}
		}
	}

	private void updateMenu(Manager manager) throws SQLException {
		String command = "1. Update customer\n2. Update account\n0. RETURN";
		System.out.println(command);
		int option = BankUtils.getValidInteger(input);
		switch (option) {
		case UPDATE_CUSTOMER:
			updateCustomerMenu(manager);
			break;
		case UPDATE_ACCOUNT:
			updateAccountMenu(manager);
			break;
		case RETURN:
			mainMenu(manager);
			break;
		default:
			System.out.println("INVALID OPTION");
			updateMenu(manager);
			break;
		}
	}

	private void updateCustomerMenu(Manager manager) throws SQLException {
		String command = "Enter ID: ";
		System.out.println(command);
		int id = BankUtils.getValidInteger(input);
		Customer customer = manager.getCustomer(id);
		if (customer == null) {
			System.out.println("No customer with ID: " + id);
			updateMenu(manager);
		} else {
			updateCustomer(manager, customer);
		}
	}

	private void updateCustomer(Manager manager, Customer customer) throws SQLException {
		System.out.println(customer);
		String command = "1. Update name\n2. Update manager\n0. RETURN";
		System.out.println(command);
		int option = BankUtils.getValidInteger(input);
		switch (option) {
		case UPDATE_NAME:
			System.out.println("Enter new name: ");
			String name = input.nextLine();
			Customer newCustomer = customer;
			newCustomer.setName(name);
			if (manager.updateCustomer(customer, newCustomer)) {
				System.out.println("UPDATED SUCCESSFULLY");
			} else {
				System.out.println("UPDATE NOT COMPLETE");
			}
			updateMenu(manager);
			break;
		case UPDATE_MANAGER:
			System.out.println("Enter new manager ID: ");
			int id = BankUtils.getValidInteger(input);
			Manager newManager = mDao.findById(id);
			if (newManager != null) {
				Customer newCustomer1 = customer;
				newCustomer1.setManagerId(id);
				manager.updateCustomer(customer, newCustomer1);
				System.out.println("UPDATED SUCCESSFULLY");
			} else {
				System.out.println("UPDATE NOT COMPLETE");
			}
			updateMenu(manager);
			break;
		case RETURN:
			updateMenu(manager);
			break;
		default:
			System.out.println("INVALID OPTION");
			updateCustomer(manager, customer);
			break;
		}
	}

	private void updateAccountMenu(Manager manager) throws SQLException {
		String command = "Enter ID of account: ";
		System.out.println(command);
		int id = BankUtils.getValidInteger(input);
		Account account = manager.getAccount(id);
		if (account.getId() == NULL_ACC) {
			System.out.println("There isn't account with ID: " + id);
			updateMenu(manager);
		} else {
			updateAccount(manager, account);
		}
	}

	private void updateAccount(Manager manager, Account account) throws SQLException {
		String command = "1. Charge\n2. Pull\n3. Transfer\n0. RETURN";
		System.out.println(command);
		int option = BankUtils.getValidInteger(input);
		switch (option) {
		case CHARGE:
			System.out.println("Enter amount to charge: ");
			int chargeAmount = BankUtils.getValidInteger(input);
			manager.charge(account, chargeAmount);
			System.out.println("CHARGED SUCCESSFULLY");
			updateMenu(manager);
			break;
		case PULL:
			System.out.println("Enter amount to pull: ");
			int pullAmount = BankUtils.getValidInteger(input);
			if (manager.pull(account, pullAmount)) {
				System.out.println("PULL SUCCESSFULLY");
			} else {
				System.out.println("NOT ENOUGH BALANCE");
			}
			updateMenu(manager);
			break;
		case TRANSFER:
			transferMenu(manager, account);
			updateMenu(manager);
			break;
		case RETURN:
			updateMenu(manager);
		default:
			System.out.println("INVALID OPTION");
			updateAccount(manager, account);
			break;
		}
	}

	private void transferMenu(Manager manager, Account sender) throws SQLException {
		String recieverId = "Enter reciever account ID: ";
		System.out.println(recieverId);
		int id = BankUtils.getValidInteger(input);
		Account reciever = manager.getAccount(id);
		if (reciever.getId() == NULL_ACC) {
			System.out.println("There isn't account with ID: " + id);
		} else {
			System.out.println("Enter amount: ");
			int amount = BankUtils.getValidInteger(input);
			if (manager.transfer(sender, reciever, amount)) {
				System.out.println("TRANSFER COMPLETED");
			} else {
				System.out.println("Not enough balance");
			}
		}
	}

	private void deleteMenu(Manager manager) throws SQLException {
		String command = "1. Delete customer by ID\n2. Delete account by ID\n0. RETURN";
		System.out.println(command);
		int option = BankUtils.getValidInteger(input);
		switch (option) {
		case DELETE_CUSTOMER:
			deleteCustomerMenu(manager);
			deleteMenu(manager);
			break;
		case DELETE_ACCOUNT:
			deleteAccountMenu(manager);
			deleteMenu(manager);
			break;
		case RETURN:
			mainMenu(manager);
			break;
		default:
			System.out.println("INVALID OPTION");
			deleteMenu(manager);
			break;
		}
	}

	private void deleteCustomerMenu(Manager manager) throws SQLException {
		System.out.println("Enter ID of customer: ");
		int id = BankUtils.getValidInteger(input);
		if (manager.deleteCustomer(id)) {
			System.out.println("Customer deleted");
		} else {
			System.err.println("No such customer with ID: " + id);
		}
	}

	private void deleteAccountMenu(Manager manager) throws SQLException {
		System.out.println("Enter ID of account: ");
		int id = BankUtils.getValidInteger(input);
		if (manager.deleteAccount(id)) {
			System.out.println("Account deleted");
		} else {
			System.err.println("No such account with ID: " + id);
		}
	}
}
