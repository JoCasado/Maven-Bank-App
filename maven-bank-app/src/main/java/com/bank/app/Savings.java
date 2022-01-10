package com.bank.app;

public class Savings extends Account{
	
private static String accountType = "Savings";
	
	
	public Savings(double initialDeposit){
		
		
		this.setBalance(initialDeposit);
		
		this.checkInterest(0);

		
	}
	
	public Savings(int accountId, double balance) {
		
		
	}
	

	@Override
	
	public String toString() {
		
		return "Account Type: " +accountType+ " Account\n"
				+ " Account Number: " + this.getAccountNumber()
				+ "\n" +" Balance: " + this.getBalance() + "\n"
				+ " Interest rate: " + this.getInterest() + "$\n";
		
	}


}
