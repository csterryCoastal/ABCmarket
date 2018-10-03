//package com.cs490;

//import com.cs490.Item;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;

import java.util.Arrays;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

//Gson for converting to JSON
import com.google.gson.Gson;

//For response objects
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.MediaType;

//For exception handling
import java.sql.SQLException;
import javax.naming.NamingException;

public class EmployeeFacade {

	private static EmployeeFacade singleton;

	private EmployeeDataAccess dao;

	private EmployeeFacade() throws NamingException, SQLException {
		this.dao = EmployeeDataAccess.getInstance();
	}

	public static EmployeeFacade getInstance() throws NamingException, SQLException {

		if (singleton == null) {
			singleton = new EmployeeFacade();
		}

		return singleton;
	}
	public Cashier[] showEmployees() throws NamingException,SQLException,ClassNotFoundException
	{
		Connection con=dao.getConnection();// Using Sdao to differentiate from Store DB or Employee DB
		PreparedStatement stmt = con.prepareStatement("SELECT * FROM employees");
		ResultSet rs = stmt.executeQuery();
		Cashier[] cashiers=new Cashier[100];
		Cashier cash=new Cashier();
		int count = 0;
		while (rs.next()) {
			//int theId = rs.getInt("id");
			String user = rs.getString("userName");
			String pin = rs.getString("PIN");
			String first = rs.getString("fName");
			String last = rs.getString("lName");
			String pos = rs.getString("position");
			String status = rs.getString("status");
			int authorityLevel=rs.getInt("authorityLevel");
			//String user,String pin,String first,String last,String pos,String status
			
			if(authorityLevel==3)
			{
				//Cashier cash=new Manager(user, pin, first, last, pos, status);
			}
			else if(authorityLevel==2)
			{
				cash=new Supervisor(user, pin, first, last, pos, status);
			}
			else
			{
				cash=new Cashier(user, pin, first, last, pos, status);
			}
			
			
		
			cashiers[count] = cash;
			count++;
		}
		return cashiers;
	}
/* Hidden JUST FOR REVIEW
	public Ingredient[] getIngredients() throws NamingException, SQLException, ClassNotFoundException {
		// Get the connection fomr the IngredientDataAccess singleton object
		Connection con = dao.getConnection();

		// Execute the query
		PreparedStatement stmt = con.prepareStatement("SELECT * FROM ingredient");
		ResultSet rs = stmt.executeQuery();

		// Build the array of Ingredient Objects
		Ingredient[] ingArray = new Ingredient[100];
		int count = 0;
		while (rs.next()) {
			int theId = rs.getInt("id");
			String theName = rs.getString("name");
			String theCategory = rs.getString("category");
			Ingredient ing = new Ingredient(theId, theName, theCategory);
			ingArray[count] = ing;
			count++;
		}

		if (count > 0) {
			ingArray = Arrays.copyOf(ingArray, count);
			return ingArray;

		} else {
			return null;
		}

	}

	public Ingredient[] getIngredientByName(String theName) throws SQLException, ClassNotFoundException {
		// Get the connection fomr the IngredientDataAccess singleton object
				Connection con = dao.getConnection();

				// Execute the query
				PreparedStatement stmt = con.prepareStatement("SELECT * FROM ingredient WHERE name=?");
				stmt.setString(1, theName);
				ResultSet rs = stmt.executeQuery();

				// Build the array of Ingredient Objects
				Ingredient[] ingArray = new Ingredient[100];
				int count = 0;
				while (rs.next()) {
					int theId = rs.getInt("id");
					String theName2 = rs.getString("name");
					String theCategory = rs.getString("category");
					Ingredient ing = new Ingredient(theId, theName2, theCategory);
					ingArray[count] = ing;
					count++;
				}

				if (count > 0) {
					ingArray = Arrays.copyOf(ingArray, count);
					return ingArray;

				} else {
					return null;
				}
	}

	public Ingredient[] getIngredientById(int theId) throws SQLException, ClassNotFoundException {
		// Get the connection fomr the IngredientDataAccess singleton object
		Connection con = dao.getConnection();

		// Execute the query
		PreparedStatement stmt = con.prepareStatement("SELECT * FROM ingredient WHERE id=?");
		stmt.setInt(1, theId);
		ResultSet rs = stmt.executeQuery();

		// Build the array of Ingredient Objects
		Ingredient[] ingArray = new Ingredient[100];
		int count = 0;
		while (rs.next()) {
			int theId2 = rs.getInt("id");
			String theName = rs.getString("name");
			String theCategory = rs.getString("category");
			Ingredient ing = new Ingredient(theId2, theName, theCategory);
			ingArray[count] = ing;
			count++;
		}

		if (count > 0) {
			ingArray = Arrays.copyOf(ingArray, count);
			return ingArray;

		} else {
			return null;
		}

	}

	public Ingredient[] createIngredient(String newName, String newCategory) throws SQLException {
	
		//Get the connection fomr the IngredientDataAccess singleton object
		Connection con = dao.getConnection();
		
		//Execute the query
		PreparedStatement createStmt = con.prepareStatement("INSERT INTO Ingredient (name, category) VALUES (?, ?);");
		createStmt.setString(1, newName);
		createStmt.setString(2, newCategory);
		
		int res = createStmt.executeUpdate();
		System.out.println("Result is: " + res);
			
		//If insert was successful retrieve the new ingredient to get id
		if(res==1) {
			System.out.println("Ingredient added successfully!");
			
			PreparedStatement retrieveStmt = con.prepareStatement("SELECT * FROM Ingredient WHERE name=? AND category=?;");
			retrieveStmt.setString(1, newName);
			retrieveStmt.setString(2, newCategory);

			ResultSet rs = retrieveStmt.executeQuery();
					
			System.out.println("\nRS: " + rs);
				
			int count = 0;
			int MAX = 100;
			Ingredient[] ingArray = new Ingredient[MAX];
			
			while (rs.next()) {
				int theId = rs.getInt("id");
				String ingName = rs.getString("name");
				String ingCategory = rs.getString("category");
				Ingredient ing = new Ingredient(theId, ingName, ingCategory);
				System.out.println(ing);
				ingArray[count] = ing;
				count++;
				System.out.println(ing);
			}
				
			ingArray = Arrays.copyOf(ingArray, count);
				
			return ingArray;
				  
				
			} // END IF
			else { //The ingredient was not successfully inserted
				 return null;
				  
			} // END ELSE
						
		}*/

	/**
	 * End database modification code
	 */

}