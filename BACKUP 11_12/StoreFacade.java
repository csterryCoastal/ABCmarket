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
import java.util.Arrays;
import java.util.ArrayList;

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
		//John added this arraylist
    private static ArrayList<Product> shoppingCart;
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
		System.out.println("Getting InventoryID");
		
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
	/*
 public Product addCartItem(String id,String quantity) throws ClassNotFoundException, NamingException, SQLException {
		
		  Product pro=getInventoryId(id);
		  System.out.println("Add Cart Item");
		  double q = Double.parseDouble(quantity);
		  pro.setQuantity(q);		
		  shoppingCart.add(pro);
		  System.out.println("Added to cart");
		
		return pro;
	}
	
	

	public Product deleteCartItem(String id) throws SQLException, ClassNotFoundException, NamingException {
		int intUPC=0;
		try
		{
			intUPC=Integer.parseInt(id);
		
		}
		catch(NumberFormatException ne)	
		{
		intUPC=0;
		System.err.println("NumberFormatException Occured");
		}
		
		Product pro = new Product();
		pro = getInventoryId(id);
		   for(int i=0;i<shoppingCart.size();i++) {
			   if(shoppingCart.get(i).getUPC() == intUPC) {
				   shoppingCart.remove(i);
			   }
		  }
		 return pro;
		
	}/*
	public Product[] shoppingCart()
	{
		Product[] prodArray=new Product[100];
		for(int i=0;i<shoppingCart.size();i++)
		{
			prodArray[i]=shoppingCart.get(i);
		}
		prodArray=Arrays.copyOf(prodArray,shoppingCart.size());
		return prodArray;
		
	}
	
	
	public String checkout(double total) throws ClassNotFoundException, NamingException, SQLException {
		    
		    String result ="Thank you for shopping in ABC Convenient Market!";
		    Product current=getInventoryId("0");
		    double currentsales=current.getPrice();
		    current.setPricefromReturn(currentsales+total);
		    changeInventory(current);
		    shoppingCart.clear();
		    return result;
	}
	*/
		public String checkout(double total) throws ClassNotFoundException, NamingException, SQLException {
		    
		    String result ="Thank you for shopping in ABC Convenient Market!";
		    Product current=getInventoryId("0");
		    double currentsales=current.getPrice();
		    current.setPricefromReturn(currentsales+total);
		    changeInventory(current);
			PreparedStatement createStmt = Scon.prepareStatement("DELETE FROM shoppingcartdb");
		   // createStmt.setInt(1, intUPC);
		    int a =createStmt.executeUpdate();
		    return result;
	}
	
	public Product addCartItem(String id,String Quantity) throws ClassNotFoundException, NamingException, SQLException {
		  
		  Product pro=getInventoryId(id);
		  double q= Double.parseDouble(Quantity);
		  pro.setQuantity(q);// Prints setQuantity \n Done set price
		  System.out.println("Add Cart Item");
		  int UPC=pro.getUPC();
	      String name=pro.getName();
	      double price=pro.getPrice();
	      String priceUnit=pro.getPriceUnit();
	      boolean isOnSale=pro.getOnSale();
	      double salePercent=pro.getSalePercent();
		  double quantity = pro.getItemQuantity();
		  double purchasePrice = pro.purchasePrice(q);
		  
	      Product holderPro=new Product(name, UPC,price,priceUnit,isOnSale,salePercent,quantity,purchasePrice);
	      System.out.println(pro.toString());
		  System.out.println("quantity: " + holderPro.getItemQuantity()+" purchase price:" + holderPro.getPurchasePrice());
          
		  PreparedStatement createStmt = Scon.prepareStatement(
			"INSERT INTO shoppingcartdb (UPC,name, price, priceUnit, onSale, salePercent,quantity,purchasePrice) VALUES (?, ?, ?, ?, ?,?,?,?)");// UPDATE
			createStmt.setInt(1, UPC);
			createStmt.setString(2, name);
			createStmt.setDouble(3, price);
			createStmt.setString(4, priceUnit);
			createStmt.setBoolean(5, isOnSale);
			createStmt.setDouble(6, salePercent);
			createStmt.setDouble(7, quantity);
			createStmt.setDouble(8, purchasePrice);
			int res = createStmt.executeUpdate();
			//createStmt.executeQuery();
		     //pro.setQuantity(q);		
		     //shoppingCart.add(pro);
		     //System.out.println("Added to shoppingcartdb");
			System.out.println("ADDED to DB");
		return holderPro;
	}
	
	

	public Product[] deleteCartItem(String id) throws SQLException, ClassNotFoundException, NamingException {
		int intUPC=0;
		try
		{
			intUPC=Integer.parseInt(id);
		
		}
		catch(NumberFormatException ne)	
		{
		intUPC=0;
		System.err.println("NumberFormatException Occured");
		}
		System.out.println("Deleting ");
		PreparedStatement createStmt = Scon.prepareStatement("DELETE FROM shoppingcartdb WHERE UPC = ?");
		createStmt.setInt(1, intUPC);
		int a =createStmt.executeUpdate();
		System.out.println("Deleted from shoppingcartdbart");
		
		 return shoppingCart();
		
	}
	
	public static Product[] shoppingCart() throws NamingException,SQLException,ClassNotFoundException
	{
		int itemcount=0;
	
		//ResultSet rs = stmt.executeQuery("SELECT id,name, category FROM ingredient");
		//PreparedStatement stmt = Scon.prepareStatement("SELECT * FROM inventoryDB");
		PreparedStatement stmt = Scon.prepareStatement("SELECT * FROM shoppingcartdb");
		ResultSet rs = stmt.executeQuery();
		Product[] shoppingcart = new Product[10000];// Using 10k+1 to represent items 1-9999
		System.out.println("In Get IN");
		while (rs.next()) {

			int UPC = rs.getInt("UPC");
			String name = rs.getString("name");
			double price = rs.getDouble("price");
			String PU = rs.getString("priceUnit");
			double sale = rs.getDouble("salePercent");
			boolean onSale = rs.getBoolean("onSale");
			double quantity = rs.getDouble("quantity");
			double purchasePrice = rs.getDouble("purchasePrice");
			//System.out.println(name);
			//String name,int UPC,double price,String PU,double salePercent,boolean sale 
			Product holder =new Product(name,UPC,price,PU,onSale,sale,quantity,purchasePrice);
			shoppingcart[itemcount]= holder;// Places each item in the slot of its UPC
			itemcount++;
		}

		
		shoppingcart=Arrays.copyOf(shoppingcart,itemcount);
		return shoppingcart;
	}
/*
	public Product[] shoppingCart()
	{
		Product[] prodArray=new Product[100];
		for(int i=0;i<shoppingCart.size();i++)
		{
			prodArray[i]=shoppingCart.get(i);
		}
		prodArray=Arrays.copyOf(prodArray,shoppingCart.size());
		return prodArray;
		
	}
	*/
	


}