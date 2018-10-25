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
		
		@Path("/inventory/{id}")
		@GET
		@Produces("text/plain")
		public static String getInventoryId(@PathParam("id") String theId) throws NamingException, SQLException, ClassNotFoundException{
			
			System.out.println("Accessing database...");
			StoreFacade sFacade = new StoreFacade();
			System.out.println("B4 Get Inventory");
			Product resultArray = sFacade.getInventoryId(theId);
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

		
/*
		@Path("/employees")
		@GET
		@Produces("text/plain")
		public static Response getEmployees() throws NamingException, SQLException, ClassNotFoundException{
			
			System.out.println("Accessing database...");

			//EmployeeFacade eFacade = EmployeeFacade.getInstance();
			//EmployeeFacade eFacade = new EmployeeFacade();
			Cashier[] resultArray = eFacade.showEmployees();
			
			if(resultArray != null) {
				Gson theGsonObj = new Gson();
				String result = theGsonObj.toJson(resultArray);
				
				ResponseBuilder rb = Response.ok(result, MediaType.TEXT_PLAIN);
				rb.status(200);
				System.out.println("Finished reading from database.");
				return rb.build();
				
			} else {
				return Response.status(700).build();
			}
			
			/**
			 * End database retrieval code
			 */
			
		//}
}

