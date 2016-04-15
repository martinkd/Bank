package com.martin.bank.accounts;

public class Payment extends Account {
	private final String type = "PAYMENT";
	
	public Payment() {
		super.type = this.type;
	}
}
