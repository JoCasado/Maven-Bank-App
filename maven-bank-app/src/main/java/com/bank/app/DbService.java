package com.bank.app;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;

import org.apache.log4j.Logger;

public class DbService {
	
	    //Creating the connection and testing that we can connect
	       
	        //This is bad! Someone can steal our information
	        static String url = "jdbc:postgresql://javareactdb.cepklf5wvuz3.us-east-2.rds.amazonaws.com:5432/bank?currentSchema=public";
	        // jdbc:(database type used)://(endpoint):(port)/(database name (ours is blank))/(extra information(like current schema))
	        static String username = "banky";
	        static String password = "passbank";  

	         

	     /*   FileInputStream propinput = new FileInputStream("D:\\REVATURE\\TRAININGNOTES\\application.properties.txt");
	        Properties props =new Properties();
	        props.load(propinput);
	        //String url = "jdbc:postgresql://javareactdb.cepklf5wvuz3.us-east-2.rds.amazonaws.com:5432/bank?currentSchema=public";
	        String url = (String) props.get("url");
	        String username = (String) props.get("username");
	        String password = (String) props.get("password"); 
	        //String username = "username";
		    //String password = "password";  */
	        
	       static Connection connect() {
	        	
	        	Connection connection;
	        	
	        	try {
	        		connection = DriverManager.getConnection(url, username, password); 
	        	} catch (SQLException ex) {
	        		
	        		connection = null;
	        	}
	        	
	        	return connection;
	        	
	        }
	        
	        //CREATE
	       
	       public void insertAccount (String userName, String passwrd, String ssn, double balance) {
	    	   
	    	   Connection connection = connect();
	        	try {
	        		connection.setAutoCommit(true);
	        		//Added user
	        		String addUserSql = "insert into Users(Username, Passwrd, ssn, Balance) values(?,?,?,?)";
	    	   
	    	   PreparedStatement addUser = connection.prepareStatement(addUserSql, Statement.RETURN_GENERATED_KEYS);
       			addUser.setString(1, userName);
       			addUser.setString(2, passwrd);
       			addUser.setString(3, ssn);
       			addUser.setDouble(4, balance);
       			addUser.executeUpdate();
	    	   
	       } catch (Exception e) {
	    	   e.printStackTrace();
	       }
	        	
	       }
	       
	       public void insertMoney (double balance, String passwrd) {
	    	   
	    	   Connection connection = connect();
	        	try {
	        		connection.setAutoCommit(true);
	        		//Added user
	        		String updateUserSql = "UPDATE Users SET Balance = balance+" +balance+ "WHERE passwrd = " +passwrd;
	    	   
	    	   PreparedStatement updateUser = connection.prepareStatement(updateUserSql);
       			updateUser.executeUpdate();
	    	   
	       } catch (Exception e) {
	    	   e.printStackTrace();
	       }
	        	
	       }
	       
	        
	        int AddAccount(String userName, String passwrd, String ssn, AccountType account_type, Double balance) {
	        	
	        	int userId = -1;
	        	int accountId = -1;
	        	Connection connection = connect();
	        	try {
	        		connection.setAutoCommit(false);
	        		//Added user
	        /*		String addUserSql = "insert into Users(Username, Passwrd, ssn) values(?,?,?)";
	        		
	        		PreparedStatement addUser = connection.prepareStatement(addUserSql, Statement.RETURN_GENERATED_KEYS);
	        		addUser.setString(1, userName);
	        		addUser.setString(2, passwrd);
	        		addUser.setString(3, ssn);
	        		addUser.executeUpdate();
	        		
	        		ResultSet addUserResults = addUser.getGeneratedKeys();
	        		
	        		if(addUserResults.next()) {
	        			userId = addUserResults.getInt(1);
	        		}  */
	        		//Add account
	        		String addAccountSql = "insert into Accounts(a_type, Balance) values (?,?)";
	        		
	        		PreparedStatement addAccount = connection.prepareStatement(addAccountSql, Statement.RETURN_GENERATED_KEYS);
	        		addAccount.setString(1, account_type.name());
	        		addAccount.setDouble(2, balance);
	        		addAccount.executeUpdate();
	        		
	        		ResultSet addAccountResults = addAccount.getGeneratedKeys();
	        		
	        		if(addAccountResults.next()) {
	        			accountId = addAccountResults.getInt(1);
	        		}
	        		//Link the user to the account
	        		if(userId > 0 && accountId > 0) {
	        			
	        			String linkAccountSql = "insert into mappings(UserId, AccountId) values (?,?)";
	        			PreparedStatement linkAccount = connection.prepareStatement(linkAccountSql);
	        			linkAccount.setInt(1, userId);
	        			linkAccount.setInt(2, accountId);
	        			linkAccount.executeUpdate();
	        			connection.commit();
	        		} 
	        		else {
	        			connection.rollback();
	        		}
	        		connection.close();
	        		
	        	} catch (SQLException ex) {
	        		
	        		System.err.println("An error has occurred: " + ex.getMessage());
	        	}
	        	return accountId;
	        	
	        }
	        
	        //READ
	        
	        Customer GetAccount(int accountId) {
	        	
	        	Customer customer = null;
	        	Connection connection = connect();
	        	try {
	        		
	        		String findUserSql = "select Username, Passwrd, ssn, a_type, Balance "
	        				+ "from Users a join mappings b on a.ID = b.UserId "
	        				+ "join Accounts c on c.ID = b.AccountId "
	        				+ "where c.ID = ?";
	        		
	        		PreparedStatement findUser = connection.prepareStatement(findUserSql);
	        		findUser.setInt(1, accountId);
	        		
	        		ResultSet findUserResults = findUser.executeQuery();
	        		
	        		if(findUserResults.next()) {
	        			
	        			String userName = findUserResults.getString("Username");
	        			String passwrd = findUserResults.getString("Passwrd");
	        			String ssn = findUserResults.getString("ssn");
	        			AccountType account_type = AccountType.valueOf(findUserResults.getString("a_type"));
	        			double balance = findUserResults.getDouble("Balance");
	        			Account account;
	        			if(account_type == AccountType.Checking) {
	        				account = new Checking(accountId, balance);
	        				
	        			} else if(account_type == AccountType.Savings) {
	        				account = new Savings(accountId, balance);

	        				
	        			} else {
	        				
	        				throw new Exception("Unkwon Account type");
	        			}
	        			
	        			customer = new Customer(userName, passwrd, ssn, balance, account);
	        			
	        		} 
	        		else {
	        			System.err.println("No user account was found for ID " +accountId);
	        		}
	        	}  catch (Exception e) {
	        		
	        		System.err.println(e.getMessage());

	        	}
	        	return customer;
	        }
	        
	        //UPDATE
	        
	        boolean UpdateAccount(int accountId, double balance) {
	        	
	        	boolean success = false;
	        	Connection connection = connect();
	        	try {
	        		
	        		String updateSql = "UPDATE Accounts SET Balance = ? WHERE ID = ?";
	        		PreparedStatement updateBalance = connection.prepareStatement(updateSql);
	        		updateBalance.setDouble(1, balance);
	        		updateBalance.setInt(2, accountId);
	        		updateBalance.executeUpdate();
	        		success = true;
	        	} catch (SQLException ex) {
	        		
	        		System.err.println(ex.getMessage());
	        	}
	        	return success;
	        }
	        
	        //DELETE
	        
	        boolean DeleteAccount(int accountId) {
	        	
	        	boolean success = false;
	        	Connection connection = connect();
	        	try {
	        		
	        		String deleteSql = "DELETE Users,Accounts "
	        				+ "from Users a join mappings b on a.ID = b.UserId "
	        				+ "join Accounts c on c.ID = b.AccountId "
	        				+ "where c.ID = ?";
	        		PreparedStatement deleteAccount = connection.prepareStatement(deleteSql);
	        		deleteAccount.setInt(1, accountId);
	        		deleteAccount.executeUpdate();
	        		success = true;
	        	} catch (SQLException ex) {
	        		
	        		System.err.println(ex.getMessage());
	        	}
	        	return success;
	        }
	        
	        
	        
	        //Get all Accounts
	        
	        ArrayList<Customer> GetAllAccounts(){
	        	
	        	ArrayList<Customer> customers = new ArrayList<>();
	        	Connection connection = connect();
	        	
	        	try {
	        	String findUserSql = "select AccountId, Username, Passwrd, ssn, a_type, Balance "
        				+ "from Users a join mappings b on a.ID = b.UserId "
        				+ "join Accounts c on c.ID = b.AccountId ";
	        	
	        	PreparedStatement findAllUsers = connection.prepareStatement(findUserSql);
        		
        		ResultSet findUserResults = findAllUsers.executeQuery();
        		
        		while(findUserResults.next()) {
        			
        			String userName = findUserResults.getString("Username");
        			String passwrd = findUserResults.getString("Passwrd");
        			String ssn = findUserResults.getString("ssn");
        			AccountType account_type = AccountType.valueOf(findUserResults.getString("a_type"));
        			double balance = findUserResults.getDouble("Balance");
        			int accountId = findUserResults.getInt("AccountId");

        			
        			Account account;
        			if(account_type == AccountType.Checking) {
        				account = new Checking(accountId, balance);
        				
        			} else if(account_type == AccountType.Savings) {
        				account = new Savings(accountId, balance);

        				
        			} else {
        				
        				throw new Exception("Unkwon Account type");
        			}
        			
        			customers.add(new Customer(userName, passwrd, ssn, account));
        			
	        }
	        	}
	        	catch(Exception e) {
	        		System.err.println(e.getMessage());
	        	}
        		return customers;
	        }
	        
	    public static void main(String[] args) throws IOException{
	    	
	        try(Connection connection = connect()){
	            System.out.println("Connection successful");
	            
	            UsersDAO usersdao = new UsersDAO(connection);
	            
	            
	            Set<Customer> usersSet = usersdao.getAllUsers();
	            
	            for(Customer A : usersSet) {
	            	System.out.println(A);
	            }
	            
	        } catch (SQLException e) {
	        	// 
	        	e.printStackTrace();
	        }
	    }
	    
			
}



	        
	
