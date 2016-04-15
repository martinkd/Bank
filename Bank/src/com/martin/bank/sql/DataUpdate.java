package com.martin.bank.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.martin.bank.accounts.Account;
import com.martin.bank.customers.Customer;
import com.martin.bank.managers.Manager;

public class DataUpdate {
	private String user = "student";
	private String pass = "student";
	private PreparedStatement prepSt;
	private DataAccess accessDataBase = new DataAccess(user, pass);

	public void addManager() throws SQLException {
		Manager manager = new Manager("BIG BOSS");
		String sql = "INSERT INTO manager (name) VALUES (?)";
		prepSt = accessDataBase.getMyConn().prepareStatement(sql);
		prepSt.setString(1, manager.getName());
		prepSt.executeUpdate();
	}
	
	public void addCustomer(Customer customer, Manager manager) throws SQLException {
		String sql = "INSERT INTO customers (name, managerId) VALUES (?, ?)";
		prepSt = accessDataBase.getMyConn().prepareStatement(sql);
		prepSt.setString(1, customer.getName());
		prepSt.setInt(2, manager.getId());
		prepSt.executeUpdate();
	}
	
	public void removeCustomer(int id) throws SQLException {
		String sql = "DELETE FROM customers WHERE id = ?";
		prepSt = accessDataBase.getMyConn().prepareStatement(sql);
		prepSt.setInt(1, id);
		prepSt.executeUpdate();
	}
	
	public void addAccount(Account account, int customerId) throws SQLException {
		String sql = "INSERT INTO accounts (type, amount, interest, customerId)"
				+ "VALUES (?, ?, ?, ?)";
		prepSt = accessDataBase.getMyConn().prepareStatement(sql);
		prepSt.setString(1, account.getType());
		prepSt.setDouble(2, account.getAmount());
		prepSt.setDouble(3, account.getInterset());
		prepSt.setInt(4, customerId);
		prepSt.executeUpdate();
	}
	
	public void removeAccount(int accountId) throws SQLException {
		String sql = "DELETE FROM accounts WHERE id = ?";
		prepSt = accessDataBase.getMyConn().prepareStatement(sql);
		prepSt.setInt(1, accountId);
		prepSt.executeUpdate();
	}
	
}
