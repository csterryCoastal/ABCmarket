//package com.cs490;
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
	public static void Refund(double price)
	{
		// Get total sales and change to reflect difference in price of refund
		setTotalSales(getTotalSales()-price);
		System.out.println("Refund of "+price+" has been approved.");
		
	}
	
	public static void Scheduling()
	{
		
	}
	/**
	 * 
	 * @return double type of the total sales of the day
	 */
	public static double dailySales()
	{
		// Get daily sales from DB
		return 0;// Place holder
	}
	/**
	 * 
	 * @return boolean value from the server call indicating edit success
	 */
	public static boolean editInventory()
	{
		return false;
	}

}
