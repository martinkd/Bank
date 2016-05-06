package com.martin.bank.managers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

import com.martin.bank.accounts.Account;
import com.martin.bank.accounts.Rate;
import com.martin.bank.customers.Customer;
import com.martin.bank.sql.DataAccess;
import com.martin.bank.sql.ManagerDao;

public class ManagerTest {
	private static final int INVALID_ID = 0;
	private static final int FIRST_CUSTOMER_ID = 1;
	private static final int SECOND_CUSTOMER_ID = 2;
	private static final int FIRST_ACCOUNT_ID = 1;
	private static final int SECOND_ACCOUNT_ID = 2;
	private Manager boss;
	private ManagerDao mDao;

	@Before
	public void clearDataBase() throws SQLException {
		DataAccess da = new DataAccess("student", "student");
		da.deleteAllTables();
		boss = new Manager("BOSS");
		mDao = new ManagerDao();
		mDao.add(boss);
	}

	@Test
	public void testAddCustomer() throws SQLException {
		boss = mDao.getBigBoss();
		boss.addCustomer("Gosho");
		assertEquals("should have only 1 customer", 1, boss.getCustomers().size());
		boss.addCustomer("Neli");
		assertEquals("should have 2 customers", 2, boss.getCustomers().size());

	}

	@Test
	public void testDeleteCustomer() throws SQLException {
		boss = mDao.getBigBoss();
		assertFalse("should not have any customers to delete", boss.deleteCustomer(1));
		boss.addCustomer("Gosho");
		assertTrue("should delete customer with id 1", boss.deleteCustomer(FIRST_CUSTOMER_ID));
		assertFalse("should not delete same customer twice", boss.deleteCustomer(FIRST_CUSTOMER_ID));
	}

	@Test
	public void testAddPaymentAcc() throws SQLException {
		boss = mDao.getBigBoss();
		assertFalse("should not add because no customers of the bank", boss.addPaymentAcc(new Customer()));
		boss.addCustomer("Gosho");
		Customer gosho = boss.getCustomer(FIRST_CUSTOMER_ID);
		assertTrue("should be added payment account", boss.addPaymentAcc(gosho));
	}

	@Test
	public void testDeletePaymentAcc() throws SQLException {
		boss = mDao.getBigBoss();
		assertFalse("should not delete because no accounts of the bank", boss.deleteAccount(FIRST_ACCOUNT_ID));
		boss.addCustomer("Gosho");
		Customer gosho = boss.getCustomer(FIRST_CUSTOMER_ID);
		boss.addPaymentAcc(gosho);
		boss.addPaymentAcc(gosho);
		assertTrue("should delete account with id 1", boss.deleteAccount(FIRST_ACCOUNT_ID));
		assertEquals("should be left one account", 1, boss.getCustomerAccounts(gosho).size());
		assertFalse("should not delete twice same account", boss.deleteAccount(FIRST_ACCOUNT_ID));
		assertTrue("should delete account with id 2", boss.deleteAccount(SECOND_ACCOUNT_ID));
		assertTrue("should be empty", boss.getCustomerAccounts(gosho).isEmpty());
	}

	@Test
	public void testGetCustomers() throws SQLException {
		boss = mDao.getBigBoss();
		assertTrue("should be empty", boss.getCustomers().isEmpty());
		boss.addCustomer("Gosho");
		assertEquals("should have 1 customer", 1, boss.getCustomers().size());
		boss.addCustomer("Neli");
		assertEquals("should have 2 customers", 2, boss.getCustomers().size());

	}

	@Test
	public void testGetCustomer() throws SQLException {
		boss = mDao.getBigBoss();
		assertNull("should not have any customers to get", boss.getCustomer(FIRST_CUSTOMER_ID));
		boss.addCustomer("Gosho");
		assertEquals("first customer id should be 1", 1, boss.getCustomer(FIRST_CUSTOMER_ID).getId());
		assertEquals("first customer name should be Gosho", "Gosho", boss.getCustomer(FIRST_CUSTOMER_ID).getName());

	}

	@Test
	public void testGetAccount() throws SQLException {
		boss = mDao.getBigBoss();
		assertEquals("shoud not have any accounts to get", INVALID_ID, boss.getAccount(FIRST_ACCOUNT_ID).getId());
		boss.addCustomer("Gosho");
		Customer gosho = boss.getCustomer(FIRST_CUSTOMER_ID);
		boss.addPaymentAcc(gosho);
		boss.addPaymentAcc(gosho);
		assertEquals("should get first account", 1, boss.getAccount(FIRST_ACCOUNT_ID).getId());
		assertEquals("should get second account", 2, boss.getAccount(SECOND_ACCOUNT_ID).getId());

	}

	@Test
	public void testGetCustomerAccounts() throws SQLException {
		boss = mDao.getBigBoss();
		boss.addCustomer("Gosho");
		boss.addCustomer("Martin");
		Customer gosho = boss.getCustomer(FIRST_CUSTOMER_ID);
		Customer martin = boss.getCustomer(SECOND_CUSTOMER_ID);
		boss.addPaymentAcc(gosho);
		boss.addCreditAcc(gosho, Rate.CREDIT_12_MONTHS);
		boss.addSavingsAcc(martin, Rate.SAVINGS_12_MONTHS);
		assertEquals("Gosho should have 2 accounts", 2, boss.getCustomerAccounts(gosho).size());
		assertEquals("Martin should have 1 account", 1, boss.getCustomerAccounts(martin).size());

	}

	@Test
	public void testCharge() throws SQLException {
		boss = mDao.getBigBoss();
		boss.addCustomer("Gosho");
		Customer gosho = boss.getCustomer(FIRST_CUSTOMER_ID);
		boss.addPaymentAcc(gosho);
		Account goshoAccount = boss.getAccount(FIRST_ACCOUNT_ID);
		assertEquals("shoul be 0", 0, goshoAccount.getAmount(), 0.01);
		boss.charge(goshoAccount, 100);
		goshoAccount = boss.getAccount(FIRST_ACCOUNT_ID);
		assertEquals("should be 100", 100, goshoAccount.getAmount(), 0.01);
	}

	@Test
	public void testPull() throws SQLException {
		boss = mDao.getBigBoss();
		boss.addCustomer("Gosho");
		Customer gosho = boss.getCustomer(FIRST_CUSTOMER_ID);
		boss.addPaymentAcc(gosho);
		Account goshoAccount = boss.getAccount(FIRST_ACCOUNT_ID);
		assertFalse("should not have money to pull", boss.pull(goshoAccount, 100));
		boss.charge(goshoAccount, 150);
		assertTrue("should pull OK", boss.pull(goshoAccount, 100));
		goshoAccount = boss.getAccount(FIRST_ACCOUNT_ID);
		assertEquals("should be left 50", 50, goshoAccount.getAmount(), 0.01);
	}

	@Test
	public void testTransfer() throws SQLException {
		boss = mDao.getBigBoss();
		boss.addCustomer("Gosho");
		boss.addCustomer("Martin");
		Customer gosho = boss.getCustomer(FIRST_CUSTOMER_ID);
		Customer martin = boss.getCustomer(SECOND_CUSTOMER_ID);
		boss.addPaymentAcc(gosho);
		boss.addPaymentAcc(martin);

		Account goshoAccount = boss.getAccount(FIRST_ACCOUNT_ID);
		boss.charge(goshoAccount, 100);
		goshoAccount = boss.getAccount(FIRST_ACCOUNT_ID);
		Account martinAccount = boss.getAccount(SECOND_ACCOUNT_ID);
		boss.transfer(goshoAccount, martinAccount, 150);
		goshoAccount = boss.getAccount(FIRST_ACCOUNT_ID);
		assertEquals("transfer should not pass because not enough balance", 100, goshoAccount.getAmount(), 0.01);

		boss.transfer(goshoAccount, martinAccount, 50);
		goshoAccount = boss.getAccount(FIRST_ACCOUNT_ID);
		martinAccount = boss.getAccount(SECOND_ACCOUNT_ID);
		assertEquals("Gosho and Martin should have 50", 50, goshoAccount.getAmount(), 0.01);
		assertEquals("Gosho and Martin should have 50", 50, martinAccount.getAmount(), 0.01);

	}
}
