package com.martin.bank.accounts;

public class Payment extends Account {
	
	public Payment() {
		super.setType(Type.PAYMENT);
		super.setRate(Rate.PAYMENT_12_MONTHS.getRate());
	}
}
