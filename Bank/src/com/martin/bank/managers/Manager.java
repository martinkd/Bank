package com.martin.bank.managers;

import java.util.HashMap;
import java.util.Map;

import com.martin.bank.accounts.Account;
import com.martin.bank.accounts.Payment;
import com.martin.bank.customers.Customer;

public class Manager {
	int id;
	String name;
	static Map<Integer, Customer> customers = new HashMap<Integer, Customer>();;
	Map<Integer, Account> accounts;

	public Customer addCustomer() {
		Customer customer = new Customer();
		String name = null;
		customer.setName(name);
		customers.put(customer.getId(), customer);
		return customer;
	}

	public boolean removeCustomer(int customerId) {
		boolean canRemove = customers.containsKey(customerId);
		if (canRemove) {
			customers.remove(customerId);
		}
		return canRemove;
	}

	public Account addAccount(int customerId) {
		Customer customer = customers.get(customerId);
		Account paymentAccount = new Payment();
		accounts = customer.getAccounts();
		accounts.put(paymentAccount.getId(), paymentAccount);
		customer.setAccounts(accounts);
		return paymentAccount;
	}

	public boolean removeAccount(int customerId, int accountId) {
		Customer customer = customers.get(customerId);
		accounts = customer.getAccounts();
		boolean canRemove = accounts.containsKey(accountId);
		if (canRemove) {
			accounts.remove(accountId);
			customer.setAccounts(accounts);
		}
		return canRemove;
	}

	public void clearCustomers() {
		customers.clear();
	}
}
