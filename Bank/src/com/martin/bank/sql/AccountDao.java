package com.martin.bank.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.martin.bank.accounts.Account;
import com.martin.bank.accounts.Type;
import com.martin.bank.customers.Customer;

public class AccountDao {
	private static String user = "student";
	private static String pass = "student";
	private DataAccess accessDataBase;
	private Statement myStmt;
	private PreparedStatement prepSt;
	private ResultSet myRs;

	public AccountDao() {
		accessDataBase  = new DataAccess(user, pass);
	}
	
	public List<Account> findAll() throws SQLException {
		List<Account> accounts = new ArrayList<Account>();
		try {
			String sql = "SELECT * FROM accounts;";
			myStmt = accessDataBase.getConnection().createStatement();
			myRs = myStmt.executeQuery(sql);
			while (myRs.next()) {
				accounts.add(convertRowToAccount(myRs));
			}
			return accounts;
		} finally {
			accessDataBase.closeConnection();
		}
	}
	
	public List<Account> findAllCustomerAccounts(Customer customer) throws SQLException {
		List<Account> accounts = new ArrayList<Account>();
		try {
			String sql = "SELECT * FROM accounts WHERE customerId = ?;";
			prepSt = accessDataBase.getConnection().prepareStatement(sql);
			prepSt.setInt(1, customer.getId());
			myRs = prepSt.executeQuery();
			while (myRs.next()) {
				accounts.add(convertRowToAccount(myRs));
			}
			return accounts;
		} finally {
			accessDataBase.closeConnection();
		}
	}

	private Account convertRowToAccount(ResultSet myRs) throws SQLException {
		Account account = new Account();
		account.setId(myRs.getInt("id"));
		account.setType(Type.valueOf(myRs.getString("type")));
		account.setRate(myRs.getDouble("rate"));
		account.setAmount(myRs.getDouble("amount"));
		account.setInterest(myRs.getDouble("interest"));
		account.setCustomerId(myRs.getInt("customerId"));
		return account;
	}

	public Account findById(int id) throws SQLException {
		Account account = new Account();
		try {
			String sql = "SELECT * FROM accounts WHERE id = ?;";
			prepSt = accessDataBase.getConnection().prepareStatement(sql);
			prepSt.setInt(1, id);
			myRs = prepSt.executeQuery();
			if (myRs.next()) {
				account = convertRowToAccount(myRs);
			}
			return account;
		} finally {
			accessDataBase.closeConnection();
		}
	}

	public void add(Account account) throws SQLException {
		try {
			String sql = "INSERT INTO accounts (type, amount, rate, interest, customerId)" 
					+ " VALUES (?, ?, ?, ?, ?);";
			prepSt = accessDataBase.getConnection().prepareStatement(sql);
			prepSt.setString(1, account.getType().toString());
			prepSt.setDouble(2, account.getAmount());
			prepSt.setDouble(3, account.getRate());
			prepSt.setDouble(4, account.getInterest());
			prepSt.setInt(5, account.getCustomerId());
			prepSt.executeUpdate();
		} finally {
			accessDataBase.closeConnection();
		}
	}

	public void update(Account account, Account newAccount) throws SQLException {
		try {
			String sql = "UPDATE accounts SET amount = ?, interest = ?  WHERE id = ?;";
			prepSt = accessDataBase.getConnection().prepareStatement(sql);
			prepSt.setDouble(1, newAccount.getAmount());
			prepSt.setDouble(2, newAccount.getInterest());
			prepSt.setInt(3, account.getId());
			prepSt.executeUpdate();
		} finally {
			accessDataBase.closeConnection();
		}
	}

	public void delete(int id) throws SQLException {
		try {
			String sql = "DELETE FROM accounts WHERE id = ?;";
			prepSt = accessDataBase.getConnection().prepareStatement(sql);
			prepSt.setInt(1, id);
			prepSt.executeUpdate();
		} finally {
			accessDataBase.closeConnection();
		}
	}
}
