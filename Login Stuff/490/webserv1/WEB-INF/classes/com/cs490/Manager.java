package com.cs490;
public class Manager  extends Cashier {

	public Manager() {
		super();
	}
	public Manager(String user,String pin,String first,String last,String pos,String status)
	{
		super(user,pin,first,last,pos,status);
		this.setAuthorityLevel(3);
		this.setPosition("Manager");
	}


}