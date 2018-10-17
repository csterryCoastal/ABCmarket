package com.cs490;

import com.cs490.Product;

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
		PreparedStatement stmt = con.prepareStatement("SELECT * FROM employeeDB");
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

}