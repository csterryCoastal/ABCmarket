package com.cs490;

//import com.cs490.Item;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.DriverManager;
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
	private static Connection Scon;
	//private StoreDataAccess Sdao;

	public StoreFacade() throws NamingException, SQLException,ClassNotFoundException{
		System.out.println("Testing Database access..STARTING");
			String connectStr="jdbc:mysql://localhost:3306/store";
			String username="root";
			String password="csci330pass";
		// Database driver
			String driver="com.mysql.jdbc.Driver";
			Class.forName(driver);
			Scon =DriverManager.getConnection(connectStr,username,password);
	}
	/*setSale
	This method sets the sale of all products (except UPC 0) on sale
	@param saleAmount- the decimal value of the sale that will be put on all items in the store
	@return saleAmount - the amount of sale percent that was the inventory was changed to or -1 if error occured
	*/
	public double setSale(Double saleAmount)throws NamingException,SQLException,ClassNotFoundException
	{

		if(saleAmount>=0)
		{
		PreparedStatement createStmt = Scon.prepareStatement("Update inventoryDB SET onSale=1,salePercent=? WHERE UPC>0");
		createStmt.setDouble(1,saleAmount);
		int a =createStmt.executeUpdate();
		}
		else if(saleAmount==0.0)// If sale amount is zero then remove all sales
		{
		PreparedStatement createStmt = Scon.prepareStatement("Update inventoryDB SET onSale=0,salePercent=0.0 WHERE UPC>0");
		//createStmt.setDouble(1,saleAmount);
		int a =createStmt.executeUpdate();
			
		}
		else
		{
			saleAmount=-1.0;
		}

		return saleAmount;
	}
	

	public static Product[] getInventory() throws NamingException,SQLException,ClassNotFoundException
	{
	
		//ResultSet rs = stmt.executeQuery("SELECT id,name, category FROM ingredient");
		//PreparedStatement stmt = Scon.prepareStatement("SELECT * FROM inventoryDB");
		PreparedStatement stmt = Scon.prepareStatement("SELECT * FROM inventoryDB");
		ResultSet rs = stmt.executeQuery();
		Product[] Inventory = new Product[10000];// Using 10k+1 to represent items 1-9999
		System.out.println("In Get IN");
		while (rs.next()) {

			int UPC = rs.getInt("UPC");
			String name = rs.getString("name");
			double price = rs.getDouble("price");
			String PU = rs.getString("priceUnit");
			double sale = rs.getDouble("salePercent");
			boolean onSale = rs.getBoolean("onSale");
			//System.out.println(name);
			//String name,int UPC,double price,String PU,double salePercent,boolean sale 
			Product holder =new Product(name,UPC,price,PU,onSale,sale);
			Inventory[UPC]= holder;// Places each item in the slot of its UPC
			
		}
		return Inventory;
	}
	
	
	public static Product getInventoryId(String id)throws NamingException,SQLException,ClassNotFoundException
	{
			//Connection Scon=Sdao.getConnection();// Using Sdao to differentiate from store DB or Employee DB
		int intUPC=0;
		Product holder=new Product();
		try
		{
			intUPC=Integer.parseInt(id);
		
		}
		catch(NumberFormatException ne)	
		{
		intUPC=0;
		System.err.println("NumberFormatException Occured");
		}
		System.out.println("Testing Database access..STARTING");
		
		//ResultSet rs = stmt.executeQuery("SELECT id,name, category FROM ingredient");
		//PreparedStatement stmt = Scon.prepareStatement("SELECT * FROM inventoryDB");
		PreparedStatement stmt = Scon.prepareStatement("SELECT * FROM inventoryDB where UPC="+intUPC);
		ResultSet rs = stmt.executeQuery();
		
		Product[] Inventory = new Product[10000];// Using 10k+1 to represent items 1-9999
		System.out.println("In Get IN");
		for(int i=0;i<10000;i++)
		{
			Inventory[i]=new Product();
		}
		while (rs.next()) {

			int UPC = rs.getInt("UPC");
			String name = rs.getString("name");
			double price = rs.getDouble("price");
			String PU = rs.getString("priceUnit");
			double sale = rs.getDouble("salePercent");
			boolean onSale = rs.getBoolean("onSale");
			//System.out.println(name);
			//String name,int UPC,double price,String PU,double salePercent,boolean sale 
			holder =new Product(name,UPC,price,PU,onSale,sale);
			Inventory[UPC]= holder;// Places each item in the slot of its UPC
			
		}
		return Inventory[intUPC];
	}
	
	
	
	
	
	
	
	
	/*
	Create inventory method creates an database of random items for testing purposes ONLY
	
	*/
	public String CreateInventoryDB() throws SQLException,ClassNotFoundException
	{	//int UPC=newItem.getUPC();
		
		
		for(int i=1;i<10000;i++)
		//if(rs.wasNull())// The item DNE
		{
			
			PreparedStatement createStmt = Scon.prepareStatement(
			"INSERT INTO inventoryDB (UPC,name, price, priceUnit, salePercent,onSale) VALUES (?, ?, ?, ?, ?,?);");// UPDATE
			createStmt.setInt(1, i);
			createStmt.setString(2, "Identity "+i+"TBD");
			createStmt.setDouble(3, Math.random()*10);
			if(Math.random()*100<50)
			{
			createStmt.setString(4, "e");
			}
			else
			{
			createStmt.setString(4, "l");
			}
			
			createStmt.setDouble(5, 0);
			createStmt.setBoolean(6, false);
			int res = createStmt.executeUpdate();
			
		}
		//else
		{
			System.out.print("Created  DB");
			
			return "Created";
			
		}
		//return true;
		
	}
	public Product changeInventory(Product newItem)  throws SQLException
	{
	int UPC=newItem.getUPC();
	String name=newItem.getName();
	double price=newItem.getPrice();
	String priceUnit=newItem.getPriceUnit();
	boolean isOnSale=newItem.getOnSale();
	double salePercent=newItem.getSalePercent();
	Product holderPro=new Product(name, UPC,price,priceUnit,isOnSale,salePercent);
	System.out.println(newItem.toString());

	System.out.println("Changing");
	
		/*
		private int UPC;
		private String name;
		private double price;
		private String priceUnit;
		private boolean onSale;
		private double salePercent;
	*/

			
	
			
		System.out.println("Overriding");
		PreparedStatement createStmt = Scon.prepareStatement("Update inventoryDB SET UPC=?,name=?,price=?,priceUnit=?,onSale=?,salePercent=? where UPC =?");
		//PreparedStatement createStmt = Scon.prepareStatement("Replace * FROM inventoryDB where UPC =?");
		//"INSERT INTO inventoryDB (UPC,name, price, priceUnit, onSale,salePercent) VALUES (?, ?, ?, ?, ?,?)");// UPDATE
		createStmt.setInt(1, UPC);
			createStmt.setString(2,name);
				System.out.println("name");
			createStmt.setDouble(3,price);
				System.out.println("price");
			createStmt.setString(4,priceUnit);
				System.out.println("PU");
			createStmt.setBoolean(5,isOnSale);
				System.out.println("sale");
			createStmt.setDouble(6,salePercent);
			createStmt.setInt(7, UPC);
		int a =createStmt.executeUpdate();
	
		
		return holderPro;
		//return true;
	}
	public Product processReturn(int UPC, double weight) throws NamingException,SQLException,ClassNotFoundException
	{
		String stringUPC=""+UPC+"";

		System.out.println("Changing");
		Product[] inventory =getInventory();
		Product returning=inventory[UPC];// Get item being returned
		Product dailySales=inventory[0];// Get current dailySales
		double newdaily=dailySales.getPrice()-returning.purchasePrice(weight);// Find new dailySales
		dailySales.setPricefromReturn(newdaily);;// Set new daily sales as current sales
		System.out.println("Overriding");
		PreparedStatement createStmt = Scon.prepareStatement("Update inventoryDB SET price=? where UPC =?");
			createStmt.setDouble(1,dailySales.getPrice());
			createStmt.setInt(2, 0);
		int a =createStmt.executeUpdate();
	
		
		return dailySales;
		//return true;
	}
	


}