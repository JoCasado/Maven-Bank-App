package com.bank.app;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import org.apache.log4j.Logger;


public class Menu {
	
	Connection cont = connect();
	UsersDAO usersdao = new UsersDAO(cont);
	
	//This is bad! Someone can steal our information
    static String url = "jdbc:postgresql://javareactdb.cepklf5wvuz3.us-east-2.rds.amazonaws.com:5432/bank?currentSchema=public";
    // jdbc:(database type used)://(endpoint):(port)/(database name (ours is blank))/(extra information(like current schema))
    static String username = "banky";
    static String password = "passbank";  
	
	 static Connection connect() {
     	
     	Connection connection; 
     	
     	try {
     		connection = DriverManager.getConnection(url, username, password); 
     	} catch (SQLException ex) {
     		
     		connection = null;
     	}
     	
     	return connection;
     	
     }
	
	static DbService ca = new DbService();
	
	public final static Logger logger = Logger.getLogger(Menu.class.getName());
	
	DbService database = new DbService();
	
	
	
	
	public static Scanner keyboard = new Scanner(System.in);
	
	Bank bank = new Bank();
	
	boolean exit;
	
	    
	    public static void main(String[] args) throws Exception{
	    	
	   	 logger.info("Hello BankingApp"); 
	    
	    	
	   /* 	Connection conn;
	    	
	    	conn = ca.ConnAll();
	    	
	        try(Connection connection = getConnection()){
	        	
	            System.out.println("Connection successful");
	            
	        	} catch (SQLException e) {
	        		// 
	        		e.printStackTrace();
	        	}
	        
	        		//
	        
	        ClientDAO clientdao = new ClientDAO(conn);
	        
	        try {
				Set<Customer> accounts = clientdao.getAllCustomers();
			} catch (SQLException e) {
				// 
				e.printStackTrace();
			} */ 
	        
	   	 	Menu menuNew = new Menu();
	        menuNew.newMenu();
	   	 
	      //  Menu menu = new Menu();
	     //  menu.runMenu();
	        
	        
	        
	    }

	    public void runMenu() throws Exception {
        	
        	printHeader();
        	while(!exit) {
        		printMenu();
        		int choice = getInput();
        		performedAction(choice);
        	}
        	
        }
	    
		private void printHeader() throws Exception {
			//
			System.out.println("+---------------------------------+");
			System.out.println("|                                 |");
			System.out.println("|                                 |");
			System.out.println("|  WELCOME TO THE Y.M.C.A. BANK   |");
			System.out.println("| (Young Money, Capital American) |");
			System.out.println("|                                 |");
			System.out.println("|                                 |");
			System.out.println("+---------------------------------+");
			
			
		}

	    private void printMenu() {
			// 
			displayHeader("Please make a Selection: ");
			
			System.out.println("| 1. Create a new Account |");
			System.out.println("| 2. Make a Deposit |");
			System.out.println("| 3. Make a Withdrawl |");
			System.out.println("| 4. List Account balances |");
			System.out.println("| 5. Merge |");
			System.out.println("| 0. Exit |");

			
			
		}

	    
	    
		private void performedAction(int choice) throws Exception{
			//
			switch(choice) {
			
			case 0:
				System.out.println("Thank you for Banking with us!");
				System.exit(0);
				break;
				
			case 1:
				//create a new account
				createAccount();
				
				break;
				
			case 2:
				//make a deposit
				makeADeposit();
				
				break;
				
			case 3:
				//make a withdrawl 
				makeAWithdrawl();
				
				break;
				
			case 4:
				//list account balances
				listBalances();
				break;
				
			case 5:
				//sign in
				Merge();
				
				break;
				
			default:
				System.out.println("Unknown Error has occured");
				
				break;
			
			}
			
		}
		
		
		
		private String askQuestion(String question, List<String> answers) {
			
			String response = "";
			
			Scanner input = new Scanner(System.in);
			
			boolean choices = ((answers == null) || answers.size() == 0) ? false : true;
			
			boolean firstRun = true;
			
			do {
					if(firstRun) {
						//System.out.println("Invalid selection. Please try again");
					}
			System.out.print(question);
			
			if(choices) {
				System.out.print("(");
				
				for(int i = 0; i < answers.size() - 1; ++i) {
					System.out.print(answers.get(i) + "/");
					
				}
				System.out.print(answers.get(answers.size()-1));
				System.out.print("): ");
			}
			response = input.next();
			firstRun = false;
			
			if(!choices) {
				
				break;
			}
			} while (!answers.contains(response));
			
			return response;
		}
		
		
		private double getDeposit(String account_type) {
			
			double initialDeposit = 0;
			Boolean valid = false;
			while(!valid) {
				
				System.out.print("Please enter initial deposit: ");
				
				try {
					
					initialDeposit = Double.parseDouble(keyboard.next());
					
				}
				catch(NumberFormatException e) {
					
					System.out.println("Deposit must be a number!");
				}
				
				if(account_type.equalsIgnoreCase("checking")) {
					if(initialDeposit < 100) {
						System.out.println("Checking requires $100 minimum to open");
					} else {
						valid = true;
					}
					
				} else if(account_type.equalsIgnoreCase("savings")) {
					if(initialDeposit < 50) {
						System.out.println("Saving requires $50 minimum to open");
					} else {
						valid = true;
					}
					
			}
			}
			return initialDeposit;
		}
		
		
		
		private void createAccount() throws InvalidAccountTypeException {
			// 
			displayHeader("Make an Account");
					
			//Get account info
			
			String account_type = askQuestion("Please choose account type: ", Arrays.asList("checking","savings"));
			
			String userName = askQuestion("Enter your user name: ", null);
			
			String passwrd = askQuestion("Enter your password: ", null);
			
			String ssn = askQuestion("Enter you SSN: ", null);
			
			double initialDeposit = getDeposit(account_type);
			
				
				//we can create account now
				Account account;
				
				if(account_type.equalsIgnoreCase("checking")) {
					System.out.println("works");
					account = new Checking(initialDeposit);
				} 
				else if(account_type.equalsIgnoreCase("savings")){
					System.out.println("works");
					account = new Savings(initialDeposit);

				}  else {
					
					throw new InvalidAccountTypeException();
				}
				
				Customer customer = new Customer(userName,passwrd,ssn, 0, account);
				
				Bank.addCustomer(customer);
				logger.info(customer);
				
				database.insertAccount(userName, passwrd, ssn, initialDeposit);
				
			}
			
		
		private double getDollarAmount(String question) {
				
				System.out.print(question);
				double amount = 0;
				try {
					
					amount = Double.parseDouble(keyboard.next());
				} catch(NumberFormatException e) {
					
					amount = 0;
				}
				
				return amount;
			}
		

		private void makeADeposit() throws SQLException {
			//
			displayHeader("Make a Deposit");

			int account = selectAccount();
		//	System.out.println("account = " +account);
			if(account >= 0) {
			
			double amount = getDollarAmount("How much would you like to deposit?: ");
			
			Bank.getCustomer(account).getAccount().deposit(amount);
			
			//database.insertMoney(amount,account);
			
			}
			
		}

		private int selectAccount() throws SQLException {
			// 
			
		/*	Set<Customer> usersSet = usersdao.getAllUsers();
            for(Customer A : usersSet) {
            	System.out.println(A);
            	
            } */
			
			ArrayList<Customer> customers = Bank.getCustomers();

			if(customers.size() <= 0) {
				
				System.out.println("No customers here");
				return -1;
			}
			
			System.out.println("Select an Account: ");
			
			
			for(int i = 0; i < customers.size(); i++) {
				
				System.out.println("\t" + (i+1) + ") " + customers.get(i).basicInfo());
			} 
			
			int account;
			System.out.print("Please enter your Selection: ");
			try {
				
				account = Integer.parseInt(keyboard.next()) - 1;
			} catch(NumberFormatException e) {
				
				account = -1;
			}
			if(account < 0 || account > customers.size()) {
				System.out.println("Invalid account selected");
				
				account = -1;
			}
				
			database.GetAllAccounts(); //
			return account;
		}
		

		private void makeAWithdrawl() throws SQLException {
			// 
			displayHeader("Make a Withdraw");

			
			int account = selectAccount();
			
			if(account >= 0) {
				
			double amount = getDollarAmount("How much would you like to withdraw?: ");
			
			Bank.getCustomer(account).getAccount().withdraw(amount);
			
			}
			
		}

		private void listBalances() throws SQLException {
			// 
			displayHeader("List Account Details");

			
			int account = selectAccount();
			
			//Connection cont = connect();
			//UsersDAO usersdao = new UsersDAO(cont);
			
            Set<Customer> usersSet = usersdao.getAllUsers();
            for(Customer A : usersSet) {
            	System.out.println(A);
            	//logger.info(A);
            }
            	
			
		/*	 try(Connection connection = connect()){
		            System.out.println("Connection successful");
		            
		            UsersDAO usersdao = new UsersDAO(connection);
		            
		            
		            Set<Customer> usersSet = usersdao.getAllUsers();
		            
		            for(Customer A : usersSet) {
		            	System.out.println(A);
		            }
		            
		        } catch (SQLException e) {
		        	// 
		        	e.printStackTrace();
		        }	*/

			
			if(account >= 0) {
			
			displayHeader("Account Details");
			System.out.println(Bank.getCustomer(account).getAccount());
			//logger.info(Bank.getCustomer(account).getAccount());
			
			
			} 
			
		}
		
		private void displayHeader(String message) {
			
			System.out.println();
			
			int width = message.length() + 6;
			StringBuilder sb = new StringBuilder();
			sb.append("+");
			
			for(int i = 0; i < width; ++i) {
				
				sb.append("-");
			}
			sb.append("+");
			System.out.println(sb.toString());
			System.out.println("|   " + message + "   |");
			System.out.println(sb.toString());
			
			

			
		}

		private void Merge() {
			// 
			
		}

		private int getInput() {
			// 
			int choice = -1;
			
			do {
				System.out.print("Enter your choice: ");
				
				try {
					choice = Integer.parseInt(keyboard.next());
				}
				catch(NumberFormatException e) {
					System.out.println("Invalid selection");
				}
				
				if(choice < 0 || choice > 5) {
					
					System.out.println("Choice not in range, try again");
					
				}
				
				
			} while(choice < 0 || choice > 5);
			
				return choice;
			
		}

		private Customer newMenu() throws Exception {
			
			System.out.println("Welcome");
			System.out.println("1.Login");
			System.out.println("2.Register");
			String options = keyboard.next();
			switch(options) {
			
			case "1":
				System.out.print("Do you want to log in?");
				String ans = keyboard.next();
				if(ans.equals("yes") ||ans.equals("y") || ans.equals("Yes")) {
					System.out.print("Enter user name: ");
					String extremeName = keyboard.next();
					System.out.println("Enter password: ");
					String takenPassword = keyboard.next();
					
					Set<Customer> account = usersdao.getAllUsers();
					for (Customer m : account) {
						if (m.getUserName().equals(extremeName) && m.getPassWord().equals(takenPassword)) {
							
							 Menu menus = new Menu();
						     menus.runMenu();
							//return m;
						}
						
							
					}
					System.out.println("Wrong Login");
					newMenu();
					return null;
					
				
				//break;
				}
			
		
			
			case "2":
				
				createAccount();
				
				newMenu();
				
				break;
			
			
			
			//Login
			
			}
			return null;	
		
			
		}
}
		

		

