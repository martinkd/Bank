package com.martin.bank.accounts;

public class Account {
	private int id;
	String type;
	private double amount;
	private double rate;
	private double interest;
	private int customerId;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public double getInterest() {
		return interest;
	}
	public void setInterest(double interest) {
		this.interest = interest;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	
	@Override
	public String toString() {
		return String.format("Account: %d%n"
				+ "type: %s%n"
				+ "amount: %.2f%n"
				+ "interest: %.2f%n", getId(), getType(), getAmount(), getInterest() );
	}
}