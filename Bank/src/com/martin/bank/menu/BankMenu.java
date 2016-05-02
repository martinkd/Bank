package com.martin.bank.menu;

import java.sql.SQLException;

import com.martin.bank.customers.Customer;
import com.martin.bank.managers.Manager;
import com.martin.bank.sql.DataAccess;


public class BankMenu {

	public static void main(String[] args) throws SQLException {
		DataAccess da = new DataAccess("student", "student");
		da.deleteAllTables();
		Manager manager = new Manager("THE BOSS");
		manager.addCustomer("gosho");
		manager.addCustomer("martin");
		for (Customer customer : manager.getCustomers()) {
			System.out.println(customer);
		}
		Customer gosho = manager.getCustomer(1);
		System.out.println(gosho);
		
		Customer martin = manager.getCustomer(2);
		System.out.println(martin);
		
		manager.addPaymentAcc(gosho);
		manager.addCreditAcc(gosho);
		manager.addSavingsAcc(gosho);
		
		System.out.println(manager.getAccount(1));
		System.out.println(manager.getAccount(2));
		System.out.println(manager.getAccount(3));
		
		manager.addPaymentAcc(martin);
		
		System.out.println(manager.getAccount(4));
		System.out.println(manager.getAccount(5));
		
		System.out.println(manager.getCustomerAccounts(martin.getId()));
		System.out.println(manager.getCustomerAccounts(gosho.getId()));


	}
}
