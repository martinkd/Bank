package com.martin.bank.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.martin.bank.customers.Customer;

public class CustomerDao {
	private static String user = "student";
	private static String pass = "student";
	private DataAccess accessDataBase;
	private Statement myStmt;
	private PreparedStatement prepSt;
	private ResultSet myRs;

	public CustomerDao() {
		accessDataBase = new DataAccess(user, pass);
	}

	public List<Customer> findAll() throws SQLException {
		List<Customer> customers = new ArrayList<Customer>();
		try {
			String sql = "SELECT * FROM customers;";
			myStmt = accessDataBase.getConnection().createStatement();
			myRs = myStmt.executeQuery(sql);
			while (myRs.next()) {
				customers.add(convertRowToCustomer(myRs));
			}
			return customers;
		} finally {
			accessDataBase.closeConnection();
		}
	}

	private Customer convertRowToCustomer(ResultSet myRs) throws SQLException {
		Customer customer = new Customer();
		customer.setId(myRs.getInt("id"));
		customer.setName(myRs.getString("name"));
		customer.setManagerId(myRs.getInt("managerId"));
		return customer;
	}

	public Customer findById(int id) throws SQLException {
		Customer customer = null;
		try {
			String sql = "SELECT * FROM customers WHERE id = ?;";
			prepSt = accessDataBase.getConnection().prepareStatement(sql);
			prepSt.setInt(1, id);
			myRs = prepSt.executeQuery();
			if (myRs.next()) {
				customer = convertRowToCustomer(myRs);
			}
			return customer;
		} finally {
			accessDataBase.closeConnection();
		}
	}

	public void add(Customer customer) throws SQLException {
		try {
			String sql = "INSERT INTO customers (name, managerId) VALUES (?, ?);";
			prepSt = accessDataBase.getConnection().prepareStatement(sql);
			prepSt.setString(1, customer.getName());
			prepSt.setInt(2, customer.getManagerId());
			prepSt.executeUpdate();
		} finally {
			accessDataBase.closeConnection();
		}
	}

	public void update(Customer customer, Customer newCustomer) throws SQLException {
		try {
			String sql = "UPDATE customer SET name = ?, managerId = ? WHERE id = ?;";
			prepSt = accessDataBase.getConnection().prepareStatement(sql);
			prepSt.setString(1, newCustomer.getName());
			prepSt.setInt(2, newCustomer.getManagerId());
			prepSt.setInt(3, customer.getId());
			prepSt.executeUpdate();
		} finally {
			accessDataBase.closeConnection();
		}
	}

	public void delete(int id) throws SQLException {
		try {
			String sql = "DELETE FROM customers WHERE id = ?;";
			prepSt = accessDataBase.getConnection().prepareStatement(sql);
			prepSt.setInt(1, id);
			prepSt.executeUpdate();
		} finally {
			accessDataBase.closeConnection();
		}
	}
}
