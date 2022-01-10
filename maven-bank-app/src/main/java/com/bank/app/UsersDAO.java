package com.bank.app;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class UsersDAO {
	
	 Connection conn;
	    public UsersDAO(Connection conn){
	        this.conn = conn;
	    }
	    public Set<Customer> getAllUsers() throws SQLException {
	    	
	        Set<Customer> allUsers = new HashSet<Customer>();
	        
	        PreparedStatement statement = conn.prepareStatement("Select * from Users");
	        
	        ResultSet results = statement.executeQuery();
	        
	        while(results.next()){
	            allUsers.add(new Customer(results.getString("userName"), results.getString("passwrd"), 
	            		results.getString("ssn"), results.getDouble("Balance"), null));
	        }
	        return allUsers;
	    }
	    
	    public boolean addingUsers(Customer cu) throws SQLException {
	        PreparedStatement statement = conn.prepareStatement("Insert into Users (Username, Passwrd, ssn, Balance) Values(?,?,?,?)");
	        int parameterIndex=0;
	        statement.setString(++parameterIndex, cu.getUserName());
	        statement.setString(++parameterIndex, cu.getPassWord());
	        statement.setString(++parameterIndex, cu.getSsn());
	        statement.setDouble(++parameterIndex, cu.getBalance());

	        statement.executeUpdate();

	        return true;

	    }
	}
