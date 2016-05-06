package com.martin.bank.menu;

import java.sql.SQLException;

import com.martin.bank.sql.DataAccess;

public class RunBank {
	public static void main(String[] args) throws SQLException {
		DataAccess da = new DataAccess("student", "student");
		da.deleteAllTables();
		BankMenu menu = new BankMenu();
		menu.RegManager("BOSS");
		menu.RegManager("4i4o");
		menu.logIn();
	}
}
