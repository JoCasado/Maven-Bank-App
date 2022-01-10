package com.bank.app;


public class Customer {
	
	private String userName;
	private String passWord;
	private String ssn;
	private double balance;
	private Account account;
	

	public Customer(String userName, String passWord, String ssn, double balance, Account account) {
		
		this.userName = userName;
		this.passWord = passWord;
		this.ssn = ssn;
		this.balance=balance;
		this.account = account;
		
	}
		// 
		

	public Customer(String userName2, String passwrd, String ssn2, Account account2) {
		
		
		
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getPassWord() {
		return passWord;
	}


	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}


	public String getSsn() {
		return ssn;
	}


	public void setSsn(String ssn) {
		this.ssn = ssn;
	}


	public double getBalance() {
		return balance;
	}


	public void setBalance(double balance) {
		this.balance = balance;
	}


	public void setAccount(Account account) {
		this.account = account;
	}


	@Override
	
	public String toString() {
		
		return "\nCustomer Information\n" + 
				" User name: " +userName+ "\n" +
				" Password: " +passWord+ "\n" +
				" SSN: " +ssn+ "\n" +
				account;
	}
	
	public String basicInfo() {
		
		return 	" Account Number: " +account.getAccountNumber() +
				" - " + "User name: " +userName+ " " +
				" Password: " +passWord;
	}
	
	public Account getAccount() {
		
		return account;
	}

}
