package com.cs490;
public class Supervisor  extends Cashier {
	
	public Supervisor() {
		super();
	}
	public Supervisor(String user,String pin,String first,String last,String pos,String status)
	{
		super(user,pin,first,last,pos,status);
		this.setAuthorityLevel(2);
		this.setPosition("Supervisor");
	}


}
