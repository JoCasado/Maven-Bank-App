package com.bank.app;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class AccountsDAO {
	
DbService ca = new DbService();
	
	Connection conn = ca.connect();
	
	public AccountsDAO() { }
	
	public AccountsDAO(Connection conn) {
		this.conn = conn;
	}
	
	public Set<Customer> getAllCustomers() throws SQLException {
		
        Set<Customer> allCustomers = new HashSet<Customer>();
        conn = ca.connect();
        PreparedStatement statement = conn.prepareStatement("Select * from Accounts");
       
        ResultSet results = statement.executeQuery();
        
        while(results.next()){
        //Account ac = new Account();
        	allCustomers.add(new Customer(results.getString("userName"), 
        			results.getString("passwrd"),
        			results.getString("ssn"),
        			results.getInt("balance"),
        			//ac, 
        			null
        			));

        }
		return allCustomers;
	}

}
