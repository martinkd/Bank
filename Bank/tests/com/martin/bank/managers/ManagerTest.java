package com.martin.bank.managers;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

import com.martin.bank.sql.DataAccess;
import com.martin.bank.sql.DataQuery;
import com.martin.bank.sql.DataUpdate;

public class ManagerTest {
	DataQuery query = new DataQuery();
	DataUpdate updateData = new DataUpdate();

	@Before
	public void clearDataBase() throws SQLException {
		DataAccess da = new DataAccess("student", "student");
		da.deleteAllTables();
	}

	@Test
	public void testAddCustomer() throws SQLException {
		updateData.addManager();
		Manager boss = query.getBigBoss();
		boss.addCustomer("gosho");
		assertTrue("first customer id should be 1", query.contains(1, "customers"));
		boss.addCustomer("misho");
		assertTrue("second customer id should be 2", query.contains(2, "customers"));
		assertFalse("third customer should not exists", query.contains(3, "customers"));
		assertEquals(2, query.getCustomers().size());
	}

	@Test
	public void testRemoveCustomer() throws SQLException {
		updateData.addManager();
		Manager boss = query.getBigBoss();
		assertFalse("should not remove customer who is not addedd", boss.removeCustomer(1));
		boss.addCustomer("Gosho");
		boss.addCustomer("peicho");
		assertTrue("should remove first customer", boss.removeCustomer(1));
		assertFalse("should not remove same customer twice", boss.removeCustomer(1));
		assertEquals(1, query.getCustomers().size());
	}

	@Test
	public void testAddAccount() throws SQLException {
		updateData.addManager();
		Manager boss = query.getBigBoss();
		assertFalse("should not add account because no customer for it", boss.addAccount(1));
		boss.addCustomer("first customer");
		assertTrue("should account to first customer", boss.addAccount(1));
		assertFalse("should not add account because no customer for it", boss.addAccount(0));
	}

	@Test
	public void testRemoveAccount() throws SQLException {
		updateData.addManager();
		Manager boss = query.getBigBoss();
		assertFalse("should not remove account if there is no such customer", boss.removeAccount(1, 1));
		boss.addCustomer("firstCustomer");
		assertFalse("should not remove account if there is no such account", boss.removeAccount(1, 1));
		boss.addAccount(1);
		assertTrue("should remove account", boss.removeAccount(1, 1));
	}
}
