package com.martin.bank.managers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

import com.martin.bank.customers.Customer;
import com.martin.bank.sql.DataAccess;

public class ManagerTest {
	private static final int INVALID_ID = 0;
	private static final int FIRST_CUSTOMER_ID = 1;
	private static final int FIRST_ACCOUNT_ID = 1;
	private static final int SECOND_ACCOUNT_ID = 2;


	@Before
	public void clearDataBase() throws SQLException {
		DataAccess da = new DataAccess("student", "student");
		da.deleteAllTables();
	}

	@Test
	public void testAddCustomer() throws SQLException {
		Manager boss = new Manager("THE BOSS");
		boss.addCustomer("Gosho");
		assertEquals("should have only 1 customer", 1, boss.getCustomers().size());
		boss.addCustomer("Neli");
		assertEquals("should have 2 customers", 2, boss.getCustomers().size());

	}

	@Test
	public void testDeleteCustomer() throws SQLException {
		Manager boss = new Manager("THE BOSS");
		assertFalse("should not have any customers to delete", boss.deleteCustomer(1));
		boss.addCustomer("Gosho");
		assertTrue("should delete customer with id 1", boss.deleteCustomer(FIRST_CUSTOMER_ID));
		assertFalse("should not delete same customer twice", boss.deleteCustomer(FIRST_CUSTOMER_ID));
	}

	@Test
	public void testAddPaymentAcc() throws SQLException {
		Manager boss = new Manager("THE BOSS");
		assertFalse("should not add because no customers of the bank", boss.addPaymentAcc(new Customer()));
		boss.addCustomer("Gosho");
		Customer gosho = boss.getCustomer(FIRST_CUSTOMER_ID);
		assertTrue("should be added payment account", boss.addPaymentAcc(gosho));
	}
	
	@Test
	public void testDeletePaymentAcc() throws SQLException {
		Manager boss = new Manager("THE BOSS");
		assertFalse("should not delete because no accounts of the bank", boss.deleteAccount(FIRST_ACCOUNT_ID));
		boss.addCustomer("Gosho");
		Customer gosho = boss.getCustomer(FIRST_CUSTOMER_ID);
		boss.addPaymentAcc(gosho);
		boss.addPaymentAcc(gosho);
		assertTrue("should delete account with id 1", boss.deleteAccount(FIRST_ACCOUNT_ID));
		assertEquals("should be left one account", 1, boss.getCustomerAccounts(FIRST_CUSTOMER_ID).size());
		assertFalse("should not delete twice same account", boss.deleteAccount(FIRST_ACCOUNT_ID));
		assertTrue("should delete account with id 2", boss.deleteAccount(SECOND_ACCOUNT_ID));
		assertTrue("should be empty", boss.getCustomerAccounts(FIRST_CUSTOMER_ID).isEmpty());
	}

	@Test
	public void testGetCustomers() throws SQLException {
		Manager boss = new Manager("THE BOSS");
		assertTrue("should be empty",boss.getCustomers().isEmpty());
		boss.addCustomer("Gosho");
		assertEquals("should have 1 customer", 1, boss.getCustomers().size());
		boss.addCustomer("Neli");
		assertEquals("should have 2 customers", 2, boss.getCustomers().size());

	}

	@Test
	public void testGetCustomer() throws SQLException {
		Manager boss = new Manager("THE BOSS");
		assertEquals("should not have any customers to get",
				INVALID_ID, boss.getCustomer(FIRST_CUSTOMER_ID).getId());
		boss.addCustomer("Gosho");
		assertEquals("first customer id should be 1", 1, 
				boss.getCustomer(FIRST_CUSTOMER_ID).getId());
		assertEquals("first customer name should be Gosho", "Gosho", 
				boss.getCustomer(FIRST_CUSTOMER_ID).getName());

	}
	
	@Test
	public void testGetAccount() throws SQLException {
		Manager boss = new Manager("THE BOSS");
		assertEquals("shoud not have any accounts to get", 
				INVALID_ID, boss.getAccount(FIRST_ACCOUNT_ID).getId());
		boss.addCustomer("Gosho");
		Customer gosho = boss.getCustomer(FIRST_CUSTOMER_ID);
		boss.addPaymentAcc(gosho);
		boss.addPaymentAcc(gosho);
		assertEquals("should get first account", 1, boss.getAccount(FIRST_ACCOUNT_ID).getId());
		assertEquals("should get second account", 2, boss.getAccount(SECOND_ACCOUNT_ID).getId());

	}
	
}
