package com.imanmobile.sms.oneapi.model.common;

public class AccountBalance {

	/**
	 * Serial Version UID
	 */
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;

	private double balance;

	private Currency currency;

	public AccountBalance() {
		super();
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	@Override
	public String toString() {
		return "AccountBalance {balance=" + balance + ", currency=" + currency
				+ "}";
	}
}
