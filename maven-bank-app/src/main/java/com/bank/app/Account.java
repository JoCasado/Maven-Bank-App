package com.bank.app;

public class Account {
	
	protected double balance = 0;
	protected double interest = 0.02;
	protected int accountNumber;
	protected static int numberOfAccounts = 1000;
	
	public Account(){
		
		accountNumber = numberOfAccounts++;
		
	}
	
	
	public double getBalance() {
		return balance;
	}
	
	public void setBalance(double balance) {
		this.balance = balance;
	}
	
	public double getInterest() {
		return interest = 100;
	}
	
	public void setInterest(double interest) {
		this.interest = interest;
	}
	
	public int getAccountNumber() {
		return accountNumber;
	}
	
	
	public void withdraw(double amount) {
		
		if(amount + 5 > balance) {
			
			System.out.println("You have insufficient funds");
			return;
		}
		balance -= amount +5;
		checkInterest(0);

		System.out.println("You have withdrawn $ " + amount+ " dollars plus incurred a fee of $5");
		System.out.println("You now have a Balance of $ " +balance);
		
	}
	
	public void deposit(double amount) {
		
		if(amount <= 0) {
			
			System.out.println("You cannot deposit negative money");
			return;
			
		}
		
		checkInterest(amount);
		
		amount = amount + amount * interest;
		balance += amount;
		System.out.print("You have deposited $ " + amount+ " dollars with an interest rate of " +(interest * 100)+ "%");
		System.out.print("You now have a Balance of $ " +balance);
		
	}
	
	public void checkInterest(double amount) {
		
		if(balance + amount > 10000) {
			
			interest = 0.05;
		}
		else {
			interest = 0.02;
		}
		
	}

}
