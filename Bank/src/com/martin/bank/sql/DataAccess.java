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

	private void createTables() {
		try {
			connectDb();
			createManagerTable();
			createCustomersTable();
			createAccountsTable();
			System.out.println("Tables OK");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void connectDb() throws SQLException{
		myConn = DriverManager.getConnection(dbUrl, user, pass);
		myStmt = myConn.createStatement();
	}
	
	private void createManagerTable() throws SQLException{
		String sql = "CREATE TABLE IF NOT EXISTS manager ("
				+ "id INT,"
				+ "name VARCHAR(45),"
				+ "PRIMARY KEY(id)"
				+ ");";
		myStmt.execute(sql);
	}
	
	private void createCustomersTable() throws SQLException {
		String sql = "CREATE TABLE IF NOT EXISTS customers ("
				+ "id INT,"
				+ "name VARCHAR(45),"
				+ "managerId INT,"
				+ "PRIMARY KEY(id),"
				+ "FOREIGN KEY(managerId) REFERENCES manager(id)"
				+ ");";
		myStmt.execute(sql);
	}
	
	private void createAccountsTable() throws SQLException {
		String sql = "CREATE TABLE IF NOT EXISTS accounts ("
				+ "id INT,"
				+ "type VARCHAR(45),"
				+ "amount DOUBLE,"
				+ "interest DOUBLE,"
				+ "customerId INT,"
				+ "PRIMARY KEY(id),"
				+ "FOREIGN KEY(customerId) REFERENCES customers(id)"
				+ ");";
		myStmt.execute(sql);
	}
}

