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

public class StoreFacade {

	private static StoreFacade singleton;

	private StoreDataAccess Sdao;

	private StoreFacade() throws NamingException, SQLException {
		this.Sdao = StoreDataAccess.getInstance();
	}

	public static StoreFacade getInstance() throws NamingException, SQLException {

		if (singleton == null) {
			singleton = new StoreFacade();
		}

		return singleton;
	}
	public Product[] getInventory() throws NamingException,SQLException,ClassNotFoundException
	{
		Connection Scon=Sdao.getConnection();// Using Sdao to differentiate from store DB or Employee DB
		PreparedStatement stmt = Scon.prepareStatement("SELECT * FROM store");
		ResultSet rs = stmt.executeQuery();
		Product[] Inventory = new Product[10000];// Using 10k+1 to represent items 1-9999
		
		while (rs.next()) {

			int UPC = rs.getInt("UPC");
			String name = rs.getString("name");
			double price = rs.getDouble("price");
			String PU = rs.getString("priceUnit");
			double sale = rs.getDouble("salePercent");
			boolean onSale = rs.getBoolean("onSale");
			
			//String name,int UPC,double price,String PU,double salePercent,boolean sale 
			Product holder =new Product(name,UPC,price,PU,sale,onSale);
			Inventory[UPC+1]= holder;// Places each item in the slot of its UPC
			
		}
		return Inventory;
	}
	public boolean addInventory(Product newItem) throws SQLException
	{	int UPC=newItem.getUPC();
		Connection Scon=Sdao.getConnection();// Using Sdao to differentiate from store DB or Employee DB
		PreparedStatement stmt = Scon.prepareStatement("SELECT * FROM store where UPC =?");
		stmt.setInt(1, UPC);
		ResultSet rs = stmt.executeQuery();
		
		if(rs.wasNull())
		{
			PreparedStatement createStmt = Scon.prepareStatement("INSERT INTO Inventory (UPC,name, price, PU, salePercent,onSale) VALUES (?, ?, ?, ?, ?,?);");
			createStmt.setInt(1, newItem.getUPC());
			createStmt.setString(2, newItem.getName());
			createStmt.setDouble(3, newItem.getPrice());
			createStmt.setString(4, newItem.getPriceUnit());
			createStmt.setDouble(5, newItem.getSalePercent());
			createStmt.setBoolean(6, newItem.getOnSale());
			int res = createStmt.executeUpdate();
			return true;
		}
		else
		{
			return false;
			
		}
		
		
	}
	public boolean changeInventory()
	{
		return true;
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