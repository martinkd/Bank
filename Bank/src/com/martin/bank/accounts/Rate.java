package com.martin.bank.accounts;

public enum Rate {
	PAYMENT_12_MONTHS(0.0001), CREDIT_12_MONTHS(0.1), SAVINGS_12_MONTHS(0.07);

	private double rate;

	private Rate(double rate) {
		this.rate = rate;
	}

	public double getRate() {
		return rate;
	}
	
}
