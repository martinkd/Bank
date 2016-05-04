package com.martin.bank.menu;

import java.sql.SQLException;

import com.martin.bank.accounts.Account;
import com.martin.bank.accounts.Rate;
import com.martin.bank.customers.Customer;
import com.martin.bank.managers.Manager;
import com.martin.bank.sql.DataAccess;
import com.martin.bank.sql.ManagerDao;


public class BankMenu {

	public static void main(String[] args) throws SQLException {
		DataAccess da = new DataAccess("student", "student");
		da.deleteAllTables();
		ManagerDao mDao = new ManagerDao();
		Manager manager = new Manager("THE BOSS");
		mDao.add(manager);
		manager = mDao.getBigBoss();
		manager.addCustomer("gosho");
		manager.addCustomer("martin");
		
		for (Customer customer : manager.getCustomers()) {
			System.out.println(customer);
		}
		Customer gosho = manager.getCustomer(1);
		Customer martin = manager.getCustomer(2);
		
		manager.addPaymentAcc(gosho);
		manager.addCreditAcc(gosho, Rate.CREDIT_12_MONTHS);
		manager.addSavingsAcc(gosho, Rate.SAVINGS_12_MONTHS);
		
		manager.addPaymentAcc(martin);
		
		Account goshoPaymAcc = manager.getAccount(1);
		manager.charge(goshoPaymAcc, 5000);
		Account goshoCreditAcc = manager.getAccount(2);
		manager.charge(goshoCreditAcc, 100);
		Account goshoSavingsAcc = manager.getAccount(3);
		manager.charge(goshoSavingsAcc, 50);

		Account martinPaymAcc = manager.getAccount(4);
		manager.charge(martinPaymAcc, 2000);
		
		System.out.println(manager.getCustomerAccounts(gosho.getId()));
		System.out.println(manager.getCustomerAccounts(martin.getId()));

	}
}
