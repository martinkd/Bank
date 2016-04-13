package com.martin.bank.managers;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.martin.bank.accounts.Account;
import com.martin.bank.customers.Customer;


public class ManagerTest {
	Manager manager = new Manager();
	Map<Integer, Account> accounts;
	
	@Before
	public void clearCustomers() {
		Manager.customers.clear();
		Customer.accounts.clear();
	}
	
	@Test
	public void testAddCustomer() {
		assertEquals("Every new customer Id should be incremented by 1",
				1, manager.addCustomer().getId());
		assertEquals("Every new customer Id should be incremented by 1",
				2, manager.addCustomer().getId());
		assertEquals(2, Manager.customers.size());
	}
	
	@Test
	public void testRemoveCustomer() {
		assertEquals(0, Manager.customers.size());
		Customer customer = manager.addCustomer();
		assertEquals(1, Manager.customers.size());
		assertTrue("should remove with no probelm", 
				manager.removeCustomer(customer.getId()));
		assertEquals(0, Manager.customers.size());
		assertFalse("should not remove twice", 
				manager.removeCustomer(customer.getId()));
	}

	@Test
	public void testAddAccount() {
		Customer customer = manager.addCustomer();
		Account acc1 = manager.addAccount(customer.getId());
		assertEquals("every new account id should increment by 1", 
				1, acc1.getId());
		Account acc2 = manager.addAccount(customer.getId());
		assertEquals("every new account id should increment by 1", 
				2, acc2.getId());
		assertEquals(2, customer.getAccounts().size());	
	}
	
	@Test
	public void testRemoveAccount() {
		Customer customer = manager.addCustomer();
		assertFalse("should not have account with id = 0", 
				manager.removeAccount(customer.getId(), 0));
		Account account = manager.addAccount(customer.getId());
		assertTrue("should remove with no probelm", 
				manager.removeAccount(customer.getId(), account.getId()));
		assertFalse("should not remove twice same account", 
				manager.removeAccount(customer.getId(), account.getId()));
	}
}
