package com.martin.bank.managers;

import java.sql.SQLException;
import java.util.List;

import com.martin.bank.accounts.Account;
import com.martin.bank.accounts.Credit;
import com.martin.bank.accounts.Payment;
import com.martin.bank.accounts.Savings;
import com.martin.bank.customers.Customer;
import com.martin.bank.sql.AccountDao;
import com.martin.bank.sql.CustomerDao;

public class Manager {
	private int id;
	private String name;
	private CustomerDao cDao = new CustomerDao();
	private AccountDao aDao = new AccountDao();
	private static final int INVALID_ID = 0;

	public Manager() {
	}

	public Manager(String name) throws SQLException {
		this.name = name;
	}

	public Customer addCustomer(String name) throws SQLException {
		Customer customer = new Customer();
		customer.setName(name);
		customer.setManagerId(getId());
		cDao.add(customer);
		return customer;
	}

	public boolean deleteCustomer(int customerId) throws SQLException {
		boolean canDelete = cDao.findById(customerId).getId() != INVALID_ID;
		if (canDelete) {
			cDao.delete(customerId);
		}
		return canDelete;
	}

	public boolean addPaymentAcc(Customer customer) throws SQLException {
		return addAccountIfCan(customer, new Payment());
	}

	public boolean addCreditAcc(Customer customer) throws SQLException {
		return addAccountIfCan(customer, new Credit());
	}

	public boolean addSavingsAcc(Customer customer) throws SQLException {
		return addAccountIfCan(customer, new Savings());
	}

	private boolean addAccountIfCan(Customer customer, Account account) throws SQLException {
		boolean canAdd = cDao.findById(customer.getId()).getId() != INVALID_ID;
		if (canAdd) {
			account.setCustomerId(customer.getId());
			aDao.add(account);
		}
		return canAdd;
	}

	public boolean deleteAccount(int accountId) throws SQLException {
		boolean canDelete = aDao.findById(accountId).getId() != INVALID_ID;
		if (canDelete) {
			aDao.delete(accountId);
		}
		return canDelete;
	}

	public List<Customer> getCustomers() throws SQLException {
		return cDao.findAll();
	}

	public Customer getCustomer(int id) throws SQLException {
		return cDao.findById(id);
	}

	public Account getAccount(int accountId) throws SQLException {
		return aDao.findById(accountId);
	}

	public List<Account> getCustomerAccounts(int customerId) throws SQLException {
		return aDao.findAllCustomerAccounts(customerId);
	}

	public void charge(Account account, double cashFlow) throws SQLException {
		Account currentAccount = aDao.findById(account.getId());
		double amount = currentAccount.getAmount();
		amount += cashFlow;
		currentAccount.setAmount(amount);
		aDao.update(account, currentAccount);
	}

	public boolean pull(Account account, double cashFlow) throws SQLException {
		Account currentAccount = aDao.findById(account.getId());
		double amount = currentAccount.getAmount();
		boolean isEnoughBalance = cashFlow <= amount;
		if (isEnoughBalance) {
			amount -= cashFlow;
			currentAccount.setAmount(amount);
			aDao.update(account, currentAccount);
		}
		return isEnoughBalance;
	}
	
	public void transfer(Account sender, Account reciever, double amount) throws SQLException {
		if (pull(sender, amount)) {
			charge(reciever, amount);
		}
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

	@Override
	public String toString() {
		return String.format("MANAGER: %d%n%s%n", getId(), getName());
	}
}
