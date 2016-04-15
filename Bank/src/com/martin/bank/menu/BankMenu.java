package com.martin.bank.menu;

import java.sql.SQLException;

import com.martin.bank.customers.Customer;
import com.martin.bank.managers.Manager;
import com.martin.bank.sql.DataQuery;
import com.martin.bank.sql.DataUpdate;


public class BankMenu {

	public static void main(String[] args) throws SQLException {
		DataUpdate update = new DataUpdate();
		DataQuery query = new DataQuery();
		update.addManager();
		Manager boss = query.getBigBoss();
		update.addCustomer(new Customer(),boss);
	}
}
