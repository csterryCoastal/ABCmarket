//package com.cs490;

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


	
		
		@Path("/inventory")
		@GET
		@Produces("text/plain")
		public static Response getInventory() throws NamingException, SQLException, ClassNotFoundException{
			
			System.out.println("Accessing database...");

			StoreFacade sFacade = StoreFacade.getInstance();
			
			Item[] resultArray = iFacade.getInventory();
			
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
			
		}
		
		@Path("/employees")
		@GET
		@Produces("text/plain")
		public static Response getInventory() throws NamingException, SQLException, ClassNotFoundException{
			
			System.out.println("Accessing database...");

			EmployeeFacade eFacade = EmployeeFacade.getInstance();
			
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
			
		}
}
}
