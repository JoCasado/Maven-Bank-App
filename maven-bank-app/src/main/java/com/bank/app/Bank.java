package com.bank.app;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;


public class Bank implements Serializable{
	
static ArrayList<Customer> customers = new ArrayList<Customer>();
	
	Scanner keyscan = new Scanner(System.in);

	public static void addCustomer(Customer customer) {
	
		customers.add(customer);
		
	}

	public static Customer getCustomer(int account) {
		// 
		return customers.get(account);
	}
	
	public static ArrayList<Customer> getCustomers(){
		
		return customers;
	}

}
