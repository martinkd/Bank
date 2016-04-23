package com.martin.bank.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.martin.bank.accounts.Account;
import com.martin.bank.customers.Customer;
import com.martin.bank.managers.Manager;

public class DataQuery {
	private static String user = "student";
	private static String pass = "student";
	private Statement myStmt;
	private PreparedStatement prepSt;
	private DataAccess accessDataBase = new DataAccess(user, pass);
	private ResultSet myRs;

	public Manager getBigBoss() throws SQLException {
		String sql = "SELECT * FROM manager;";
		myStmt = accessDataBase.getMyConn().createStatement();
		myRs = myStmt.executeQuery(sql);
		Manager manager = new Manager();
		while (myRs.next()) {
			manager.setId(myRs.getInt("id"));
			manager.setName(myRs.getString("name"));
		}
		return manager;
	}

	public boolean contains(int id, String table) throws SQLException {
		String sql = "SELECT id FROM " + table + ";";
		java.util.List<Integer> ids = new ArrayList<Integer>();
		myStmt = accessDataBase.getMyConn().createStatement();
		myRs = myStmt.executeQuery(sql);
		while (myRs.next()) {
			ids.add(myRs.getInt("id"));
		}
		return ids.contains(id);
	}

	public List<Customer> getCustomers() throws SQLException {
		List<Customer> customers = new ArrayList<Customer>();
		String sql = "SELECT * FROM customers;";
		myStmt = accessDataBase.getMyConn().createStatement();
		myRs = myStmt.executeQuery(sql);
		while (myRs.next()) {
			customers.add(convertRowToCustomer(myRs));
		}
		return customers;
	}
	
	public Customer getCustomer(int customerId) throws SQLException {
		Customer customer = new Customer();
		String sql = "SELECT * FROM customers WHERE id = ?;";
		prepSt = accessDataBase.getMyConn().prepareStatement(sql);
		prepSt.setInt(1, customerId);
		myRs = prepSt.executeQuery();
		if (myRs.next()) {
			customer = convertRowToCustomer(myRs);
		}
		return customer;
	}

	private Customer convertRowToCustomer(ResultSet myRs) throws SQLException {
		Customer customer = new Customer();
		int id = myRs.getInt("id");
		String name = myRs.getString("name");
		int managerId = myRs.getInt("managerId");
		customer.setId(id);
		customer.setName(name);
		customer.setManagerId(managerId);
		return customer;
	}

	public List<Integer> getCustomerAccountIds(int customerId) throws SQLException {
		List<Integer> accountIds = new ArrayList<Integer>();
		if (contains(customerId, "customers")) {
			String sql = "SELECT id FROM accounts WHERE customerId = ?;";
			prepSt = accessDataBase.getMyConn().prepareStatement(sql);
			prepSt.setInt(1, customerId);
			myRs = prepSt.executeQuery();
			while (myRs.next()) {
				accountIds.add(myRs.getInt("id"));
			}
		}
		return accountIds;
	}

	public Account getAccount(int customerId, int accountId) throws SQLException {
		Account account = new Account();
		if (getCustomerAccountIds(customerId).contains(accountId)) {
			String sql = "SELECT * FROM accounts WHERE id = ?;";
			prepSt = accessDataBase.getMyConn().prepareStatement(sql);
			prepSt.setInt(1, accountId);
			myRs = prepSt.executeQuery();
			if (myRs.next()) {
				return account = convertRowToAccount(myRs);
			}
		}
		return account;
	}

	private Account convertRowToAccount(ResultSet myRs) throws SQLException {
		int id = myRs.getInt("id");
		String type = myRs.getString("type");
		Double amount = myRs.getDouble("amount");
		Double interest = myRs.getDouble("interest");
		int customerId = myRs.getInt("customerId");
		Account account = new Account();
		account.setId(id);
		account.setType(type);
		account.setAmount(amount);
		account.setInterest(interest);
		account.setCustomerId(customerId);
		return account;
	}

}
