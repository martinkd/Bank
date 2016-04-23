package com.martin.bank.managers;

import java.sql.SQLException;
import java.util.List;

import com.martin.bank.accounts.Account;
import com.martin.bank.customers.Customer;
import com.martin.bank.sql.DataQuery;
import com.martin.bank.sql.DataUpdate;

public class Manager {
	private int id;
	private String name;
	private Customer customer;
	private DataUpdate updateData = new DataUpdate();
	private DataQuery queryData = new DataQuery();

	public Manager() {};

	public Manager(String name) {
		this.name = name;
	}

	private Manager bigBoss() throws SQLException {
		return queryData.getBigBoss();
	}

	public Customer addCustomer(String name) throws SQLException {
		customer = new Customer();
		customer.setName(name);
		updateData.addCustomer(customer, bigBoss());
		return customer;
	}

	public boolean removeCustomer(int customerId) throws SQLException {
		boolean canRemove = queryData.contains(customerId, "customers");
		if (canRemove) {
			updateData.removeCustomer(customerId);
		}
		return canRemove;
	}

	public boolean addAccount(int customerId) throws SQLException {
		boolean canAdd = queryData.contains(customerId, "customers");
		if (canAdd) {
			Account account = new Account();
			updateData.addAccount(account, customerId);
		}
		return canAdd;
	}

	public boolean removeAccount(int customerId, int accountId) throws SQLException {
		boolean canRemove = queryData.contains(customerId, "customers") && queryData.contains(accountId, "accounts");
		if (canRemove) {
			updateData.removeAccount(accountId);
		}
		return canRemove;
	}
	
	public List<Customer> getCustomers () throws SQLException {
		return queryData.getCustomers();
	}
	
	public Customer getCustomer(int customerId) throws SQLException {
		return queryData.getCustomer(customerId);
	}
	
	public Account getAccount (int customerId, int accountId) throws SQLException {
		return queryData.getAccount(customerId, accountId);
	}
	
	public List<Integer> getCustomerAccountIds(int customerId) throws SQLException {
		return queryData.getCustomerAccountIds(customerId);
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
}
