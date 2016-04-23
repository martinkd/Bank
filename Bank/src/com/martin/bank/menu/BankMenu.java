package com.martin.bank.menu;

import java.sql.SQLException;

import com.martin.bank.customers.Customer;
import com.martin.bank.managers.Manager;
import com.martin.bank.sql.DataAccess;


public class BankMenu {

	public static void main(String[] args) throws SQLException {
		DataAccess da = new DataAccess("student", "student");
		da.deleteAllTables();
		Manager manager = new Manager();
		manager.addCustomer("Gosho Petkanov");
		manager.addCustomer("Ilcho Grozdanov");
		for (Customer customer : manager.getCustomers()) {
			System.out.println(customer);
		}
		
		Customer gosho = manager.getCustomer(1);
		manager.addPaymentAcc(gosho.getId());
		manager.addCreditAcc(gosho.getId());
		manager.addSavingsAcc(gosho.getId());
		
		Customer ilcho = manager.getCustomer(2);
		manager.addSavingsAcc(ilcho.getId());
		
		System.out.println(manager.getCustomerAccountIds(gosho.getId()));
		System.out.println(manager.getAccount(gosho.getId(), 3));
	
	}
}
