package com.martin.bank.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.martin.bank.managers.Manager;

public class ManagerDao {
	private static String user = "student";
	private static String pass = "student";
	private DataAccess accessDataBase;
	private Statement myStmt;
	private PreparedStatement prepSt;
	private ResultSet myRs;
	private static final int BOSS_ID = 1;

	public ManagerDao() {
		accessDataBase = new DataAccess(user, pass);
	}
	
	@SuppressWarnings("null")
	public List<Manager> findAll() throws SQLException {
		List<Manager> managers = null;
		try {
			String sql = "SELECT * FROM manager;";
			myStmt = accessDataBase.getConnection().createStatement();
			myRs = myStmt.executeQuery(sql);
			while (myRs.next()) {
				managers.add(convertRowToManager(myRs));
			}
			return managers;
		} finally {
			accessDataBase.closeConnection();
		}
	}

	private Manager convertRowToManager(ResultSet myRs) throws SQLException {
		Manager manager = new Manager();
		manager.setId(myRs.getInt("id"));
		manager.setName(myRs.getString("name"));
		return manager;
	}

	public Manager findById(int id) throws SQLException {
		Manager manager = null;
		try {
			String sql = "SELECT * FROM manager WHERE id = ?;";
			prepSt = accessDataBase.getConnection().prepareStatement(sql);
			prepSt.setInt(1, id);
			myRs = prepSt.executeQuery();
			if (myRs.next()) {
				manager = convertRowToManager(myRs);
			}
			return manager;
		} finally {
			accessDataBase.closeConnection();
		}
	}

	public void add(Manager manager) throws SQLException {
		try {
			String sql = "INSERT INTO manager (name) VALUES (?);";
			prepSt = accessDataBase.getConnection().prepareStatement(sql);
			prepSt.setString(1, manager.getName());
			prepSt.executeUpdate();
		} finally {
			accessDataBase.closeConnection();
		}
	}

	public void update(Manager manager, Manager newManager) throws SQLException {
		try {
			String sql = "UPDATE manager SET name = ? WHERE id = ?;";
			prepSt = accessDataBase.getConnection().prepareStatement(sql);
			prepSt.setString(1, newManager.getName());
			prepSt.setInt(2, manager.getId());
			prepSt.executeUpdate();
		} finally {
			accessDataBase.closeConnection();
		}
	}

	public void delete(int id) throws SQLException {
		try {
			String sql = "DELETE FROM manager WHERE id = ?;";
			prepSt = accessDataBase.getConnection().prepareStatement(sql);
			prepSt.setInt(1, id);
			prepSt.executeUpdate();
		} finally {
			accessDataBase.closeConnection();
		}
	}

	public Manager getBigBoss() throws SQLException {
		return findById(BOSS_ID);
	}
	
}
