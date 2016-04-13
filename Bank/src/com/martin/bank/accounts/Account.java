package com.martin.bank.accounts;

public abstract class Account {
	static int id = 0;
	String type;
	double amount;
	double rate;
	double interset;
	
	public Account () {
		id +=1;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		Account.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	public double getInterset() {
		return interset;
	}
	public void setInterset(double interset) {
		this.interset = interset;
	}
	
}