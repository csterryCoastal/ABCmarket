//package com.cs490;

import javax.naming.InitialContext;
import javax.naming.Context;

import javax.sql.DataSource;
import java.sql.Connection;

import java.sql.SQLException;
import javax.naming.NamingException;

public class EmployeeDataAccess {
	
	private static EmployeeDataAccess singleton;
	
	private DataSource dataSource;
	
	private EmployeeDataAccess () throws NamingException, SQLException {
		Context initContext = new InitialContext();
		Context envContext = (Context) initContext.lookup("java:/comp/env");
		this.dataSource = (DataSource) envContext.lookup("jdbc/EmployeeDB");
		//Connetction con = dataSource.getConnection();
	}
	
	public static EmployeeDataAccess getInstance() throws NamingException, SQLException {
		
		if(singleton == null) {
			singleton = new EmployeeDataAccess();
		}
		
		return singleton;
		
	}
	
	public Connection getConnection() throws SQLException {
		
		return dataSource.getConnection();
		
	}

}
