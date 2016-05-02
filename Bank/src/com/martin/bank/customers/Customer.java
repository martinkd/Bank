package com.martin.bank.customers;

import java.util.HashMap;
import java.util.Map;

import com.martin.bank.accounts.Account;

public class Customer {
	private int id;
	private String name;
	private int managerId;
	private Map<Integer, Account> accounts = new HashMap<Integer, Account>();
	
	public Map<Integer, Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(Map<Integer, Account> accounts) {
		this.accounts = accounts;
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

	public int getManagerId() {
		return managerId;
	}

	public void setManagerId(int managerId) {
		this.managerId = managerId;
	}
	
	@Override
	public String toString() {
		return String.format("CUSTOMER: %d%n%s%n", getId(), getName());
	}
}
