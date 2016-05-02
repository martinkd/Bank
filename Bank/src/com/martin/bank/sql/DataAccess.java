package com.martin.bank.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataAccess {
	private String dbUrl = "jdbc:mysql://localhost:3306/bank?useSSL=false";
	private String user;
	private String pass;

	private Connection myConn;
	private Statement myStmt;

	public DataAccess(String user, String pass) {
		this.user = user;
		this.pass = pass;
		createTables();
	}

	public Connection getConnection() throws SQLException {
		return myConn = DriverManager.getConnection(dbUrl, user, pass);
	}

	private void createTables() {
		try {
			createStatement();
			createManagerTable();
			createCustomersTable();
			createAccountsTable();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void createStatement() throws SQLException{
		myStmt = getConnection().createStatement();
	}
	
	private void createManagerTable() throws SQLException{
		String sql = "CREATE TABLE IF NOT EXISTS manager ("
				+ "id INT NOT NULL AUTO_INCREMENT,"
				+ "name VARCHAR(45),"
				+ "PRIMARY KEY(id)"
				+ ");";
		myStmt.execute(sql);
	}
	
	private void createCustomersTable() throws SQLException {
		String sql = "CREATE TABLE IF NOT EXISTS customers ("
				+ "id INT NOT NULL AUTO_INCREMENT,"
				+ "name VARCHAR(45),"
				+ "managerId INT,"
				+ "PRIMARY KEY(id)"
				+ ");";
		myStmt.execute(sql);
	}
	
	private void createAccountsTable() throws SQLException {
		String sql = "CREATE TABLE IF NOT EXISTS accounts ("
				+ "id INT NOT NULL AUTO_INCREMENT,"
				+ "type VARCHAR(45),"
				+ "amount DOUBLE,"
				+ "interest DOUBLE,"
				+ "customerId INT,"
				+ "PRIMARY KEY(id)"
				+ ");";
		myStmt.execute(sql);
	}
	
	public void closeConnection() throws SQLException {
		if (myConn != null) {
			myConn.close();
		}
	}
	
	public void deleteAllTables() throws SQLException {
		createStatement();
		dropAccounts();
		dropCustomers();
		dropManager();
	}
	
	private void dropAccounts() throws SQLException {
		myStmt.executeUpdate("DROP TABLE accounts;");
	}
	private void dropCustomers() throws SQLException {
		myStmt.executeUpdate("DROP TABLE customers;");
	}
	private void dropManager() throws SQLException {
		myStmt.executeUpdate("DROP TABLE manager;");
	}
}

