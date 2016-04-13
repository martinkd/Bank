package com.martin.bank.customers;

import java.util.HashMap;
import java.util.Map;

import com.martin.bank.accounts.Account;

public class Customer {
	static int id = 0;
	private String name;
	public static Map<Integer, Account> accounts = new HashMap<Integer, Account>();
	
	public Map<Integer, Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(Map<Integer, Account> accounts) {
		Customer.accounts = accounts;
	}
	
	public Customer() {
		id +=1;
	}
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
