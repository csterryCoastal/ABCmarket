package com.cs490;

//import com.cs490.Item;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MultivaluedMap;

//Necessary for connecting and retrieving from the database
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

//Gson for converting to JSON
import com.google.gson.Gson;

import javax.ws.rs.core.Response;
import javax.*;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.MediaType;

import javax.naming.NamingException;
import java.sql.SQLException;
@Path("ws2")
public class StoreServices {


		@Path("/inventoryCreate")
		@GET
		@Produces("text/plain")
		public static void CREATE() throws NamingException, SQLException, ClassNotFoundException{
			
			System.out.println("Accessing database...");

			StoreFacade sFacade = new StoreFacade();
			System.out.println("B4 Get Inventory");
			sFacade.CreateInventoryDB();
			
			
			/**
			 * End database retrieval code
			 */
			
		}
		
		@Path("/inventory")
		@GET
		@Produces("text/plain")
		public static String getInventory() throws NamingException, SQLException, ClassNotFoundException{
			
			System.out.println("Accessing database...");

			StoreFacade sFacade = new StoreFacade();
			System.out.println("B4 Get Inventory");
			Product[] resultArray = sFacade.getInventory();
			System.out.println("B4 Null");
			//sFacade.addInventory(resultArray[0]);
			System.out.println(resultArray);
			if(resultArray != null) {
				System.out.println("IN NOT NULL");
				Gson theGsonObj = new Gson();
				String result = theGsonObj.toJson(resultArray);
				
				ResponseBuilder rb = Response.ok(result, MediaType.TEXT_PLAIN);
				rb.status(200);
				System.out.println("Finished reading from database.");
				return result;//rb.build();
				
			} else {
				return "Fail";//Response.status(700).build();
			}
			
			/**
			 * End database retrieval code
			 */
			
		}
		/**
		Get Inventory by Id method finds one inventory item and returns it
		@param id - the UPC of the desired item
		@return result- a JSON object with the result of the server list
		**/
		//@Path("/inventory/id={id}name={name}")
		@Path("/inventory/{id}")
		@GET
		@Produces("text/plain")
		public static String getInventoryId(@PathParam("id") String theId) throws NamingException, SQLException, ClassNotFoundException{
			
			System.out.println("Accessing database... getINVENTORY");
			StoreFacade sFacade = new StoreFacade();
			System.out.println("B4 Get Inventory");
			Product resultArray = sFacade.getInventoryId(theId);
			
			if(resultArray != null) {
				System.out.println("IN NOT NULL");
				Gson theGsonObj = new Gson();
				String result = theGsonObj.toJson(resultArray);
				System.out.println(result);
				ResponseBuilder rb = Response.ok(result, MediaType.TEXT_PLAIN);
				rb.status(200);
				System.out.println("Finished reading from database.");
				return result;//rb.build();
				
			} else {
				return "Fail";//Response.status(700).build();
			}
			
			/**
			 * End database retrieval code
			 */
			
		}
		
		
		/////////////////////////
		/**
		Set sale method sets ALL the inventory to have a sale
		@param sale - sale percent to be set on all object (but the UPC zero object of sales)
		@return result- a JSON object with the result of the sale creation
		**/
		@Path("/inventory/SetSale/{sale}")
		@GET
		@Produces("text/plain")
		public static String setSales(@PathParam("sale") String theSaleParam) throws NamingException, SQLException, ClassNotFoundException{
			double theSale;
			System.out.println("Accessing database...");
			StoreFacade sFacade = new StoreFacade();
			System.out.println("B4 Setting Sales");
			try
			{
			 theSale =Double.parseDouble(theSaleParam);
		
			}
			catch(NumberFormatException ne)	
			{
			theSale=0;
			System.err.println("NumberFormatException Occured");
			}

			double done = sFacade.setSale(theSale/100);
			System.out.println("Sale= "+theSale/100);
			if(done==0)
				return "Sales Removed";
			else if (done>0)
				return "Sale of "+done+"added to all items";				
			 else {
				return "Fail";//Response.status(700).build();
			}
			
			/**
			 * End database retrieval code
			 */
			
		}
		
		
		/////////////////////
		/* Edit inventory method takes the input from the HTML page to update a server Item at UPC designated
		
		*/
		
		@Path("/inventory")
		@POST
		@Produces("text/plain")
		@Consumes("application/x-www-form-urlencoded")
		public String editInventory(MultivaluedMap<String,String> formFields) throws SQLException, NamingException,ClassNotFoundException {
		//private int UPC;
		//private String name;
		//private double price;
		//private String priceUnit;
		//private boolean onSale;
		//private double salePercent;
		
		int UPC=Integer.parseInt(formFields.getFirst("UPC"));
		String name=formFields.getFirst("name");
		double price=Double.parseDouble(formFields.getFirst("price"));
		String priceUnit=formFields.getFirst("priceUnit");
		boolean onSale=Boolean.parseBoolean(formFields.getFirst("onSale"));
		double salePercent=Double.parseDouble(formFields.getFirst("salePercent"));
		Product holderProduct=new Product(name,UPC,price,priceUnit,onSale,salePercent);
		System.out.println(holderProduct.toString());
		
		
		
			System.out.println("Accessing database...");
			StoreFacade sFacade = new StoreFacade();
			System.out.println("B4 Change Inventory");
			
			Product resultArray = sFacade.changeInventory(holderProduct);
			System.out.println("B4 Null");
			if(resultArray != null) {
				System.out.println("IN NOT NULL");
				Gson theGsonObj = new Gson();
				String result = theGsonObj.toJson(resultArray);
				
				ResponseBuilder rb = Response.ok(result, MediaType.TEXT_PLAIN);
				rb.status(200);
				System.out.println("Finished reading from database.");
				return result;//rb.build();
				
			} else {
				return "Fail";//Response.status(700).build();
			}
			
			/**
			 * End database retrieval code
			 */
			
		}

		/* This method will become the return function for customer service clients
		
		
		*/
		@Path("/inventory/return")
		@POST
		@Produces("text/plain")
		@Consumes("application/x-www-form-urlencoded")
		public String returnCash(MultivaluedMap<String,String> formFields) throws SQLException, NamingException,ClassNotFoundException {
		//private int UPC;
		//private String name;
		//private double price;
		//private String priceUnit;
		//private boolean onSale;
		//private double salePercent;
		
			int UPC=Integer.parseInt(formFields.getFirst("UPC"));
			double weight=Double.parseDouble(formFields.getFirst("weight"));
		
			System.out.println("Accessing database...");
			StoreFacade sFacade = new StoreFacade();
			System.out.println("B4 return");
			
			Product resultArray = sFacade.processReturn(UPC,weight);
			System.out.println("B4 Null");
			if(resultArray != null) {
				System.out.println("IN NOT NULL");
				Gson theGsonObj = new Gson();
				String result = theGsonObj.toJson(resultArray);
				
				ResponseBuilder rb = Response.ok(result, MediaType.TEXT_PLAIN);
				rb.status(200);
				System.out.println("Finished reading from database.");
				return result;//rb.build();
				
			} else {
				return "Fail";//Response.status(700).build();
			}
			
			/**
			 * End database retrieval code
			 */
			
		}
		
		
		
		
		  //John 's code
		
		@Path("/inventory/add")
		@POST
		@Produces("text/plain")
		@Consumes("application/x-www-form-urlencoded")
		public static String addItem(MultivaluedMap<String,String> formFields) throws NamingException, SQLException, ClassNotFoundException{
			//int UPC=Integer.parseInt(formFields.getFirst("UPC"));
			//double quantity=Double.parseDouble(formFields.getFirst("quantity"));
			String UPC = formFields.getFirst("UPC");
			String quantity= formFields.getFirst("quantity");
			System.out.println("Accessing database... addItem");
			StoreFacade sFacade = new StoreFacade();
			System.out.println("Add Shopping Cart");
			sFacade.addCartItem(UPC,quantity);
			System.out.println("After ADD");
			Product[] resultArray= sFacade.shoppingCart();
			if(resultArray != null) {
				System.out.println("IN NOT NULL");
				Gson theGsonObj = new Gson();
				String result = theGsonObj.toJson(resultArray);
				System.out.println(result);
				ResponseBuilder rb = Response.ok(result, MediaType.TEXT_PLAIN);
				rb.status(200);
				System.out.println("Finished reading from database.");
				return result;//rb.build();
				
			} else {
				System.out.println("addFailed");
				return "Fail";//Response.status(700).build();
			}
			
			/**
			 * End database retrieval code
			 */
			
		}
		
		@Path("/inventory/delete/{id}")
		@GET
		@Produces("text/plain")
		public static String deleteItem(@PathParam("id") String theId) throws NamingException, SQLException, ClassNotFoundException{
			
			System.out.println("Accessing database... deletItem");
			StoreFacade sFacade = new StoreFacade();
			System.out.println("Delete Shopping Cart");
			sFacade.deleteCartItem(theId);
			Product[] resultArray= sFacade.shoppingCart();
			if(resultArray != null) {
				System.out.println("IN NOT NULL");
				Gson theGsonObj = new Gson();
				String result = theGsonObj.toJson(resultArray);
				System.out.println(result);
				ResponseBuilder rb = Response.ok(result, MediaType.TEXT_PLAIN);
				rb.status(200);
				System.out.println("Finished reading from database.");
				return result;//rb.build();
				
			} else {
				System.out.println("deleteFailed");
				return "Fail";//Response.status(700).build();
			}
			
			/**
			 * End database retrieval code
			 */
			
		}
		
		@Path("/inventory/checkout")
		@POST
		@Produces("text/plain")
		@Consumes("application/x-www-form-urlencoded")
		public static String checkout(MultivaluedMap<String,String> formFields) throws NamingException, SQLException, ClassNotFoundException{
			double price =Double.parseDouble(formFields.getFirst("price"));
			System.out.println("Checkout");
			StoreFacade sFacade = new StoreFacade();
			String result = sFacade.checkout(price);
			
			if(result!= null) {
			return result;//rb.build();
				
			} else {
				return "Fail";//Response.status(700).build();
			}
			
			/**
			 * End database retrieval code
			*/
			
		}

			
			/**
			 * End database retrieval code
			*/
			
	}


