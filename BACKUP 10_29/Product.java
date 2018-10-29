package com.cs490;
import java.util.Scanner;

public class Product {
private int UPC;
private String name;
private double price;
private String priceUnit;
private boolean onSale;
private double salePercent;

public Product()
{
	name="";
	UPC=-1;
	price=0;
	onSale=false;
	priceUnit="e";// Price unit will either be E or L denoting Each or by pound(L)
}
public Product(String name,int UPC,double price,String PU,boolean sale,double salePercent )
{
	this.name=name;
	this.UPC=UPC;
	this.price=price;
	this.priceUnit=PU;
	this.salePercent=salePercent;
	this.onSale=sale;
	
	//TODO try catch is valid
}

// Make getters and setters

public void setName(String newName)
{
	if(!newName.isEmpty())
	name=newName;
	else
		System.out.println("Error: Name cannot be null.");
}
public String getName()
{
	return name;
}

public void setUPC(int newUPC)
{

	UPC=newUPC;
}

public int getUPC()
{
	return UPC;
	
}

public void setPriceUnit(String newPU)
{
	if(!newPU.isEmpty())// Check to see if pu is empty or not
		{
		priceUnit=newPU;
		}
	
}
public boolean getOnSale()
{
	return onSale;
}
public String getPriceUnit()
{
	return priceUnit;
}
public double getPrice()
{
	return price;
}
public void setPrice(double newPrice)
{
	if(newPrice>0)
		this.price=newPrice;
	else
		System.out.println("Error: New Price was not valid");
}
public double getSalePercent()
{
	return salePercent;
}
public void setSale(double newSale)
{
	if(newSale>0)
	{
		this.salePercent=newSale;
		this.onSale=true;
	}
	else
	{
		this.salePercent=0;
		this.onSale=false;
	}
	
}
/*  THIS METHOD HAS BEEN REPLACED BY THE CURRENT setSale to be server compatible
public void setSale()
{	String holder="";
	boolean isDone=false;
	Scanner in = new Scanner(System.in);// Place holder for DB script
	System.out.println("Would you like to put "+name+" on sale? (Y/N)");
	holder=in.nextLine();
	while(!isDone)
	{
		if(holder.toLowerCase().equals("y"))
		{
		System.out.print("Please enter the sale percent as a whole number: 0-100");
		holder=""+in.nextInt();
		System.out.println("Confirm a new sale value of "+holder+"% off? (Y/N)");
			if(holder.toLowerCase().equals("y"))
			{
			isDone=true;
			salePercent=Integer.parseInt(holder)/100;// Shift the decimal
			onSale=true;
			}
		}
		else if(holder.toLowerCase().equals("n"))
		{
			System.out.println("All sales removed for: "+ name);
			onSale=false;
			salePercent=0.0;// Reset to market price
			isDone=true;
		}
	}
	in.close();// Close scanner that was created// To be removed
	
	
}
*/
public double purchasePrice(double quantity)
{
	if(!onSale)
	{
		if(priceUnit.toLowerCase().equals('e'))// If price unit is per item
		return Math.ceil(quantity)*price;// If quantity is decimal then round up for per each price
		else
		return quantity*price;
	}
	else// if product is on sale
	{
		if(priceUnit.toLowerCase().equals('e'))// If price unit is per item round up. Sale is calculated
			return Math.ceil(quantity)*price*(1-salePercent);// If quantity is decimal then round up for per each price
			else
			return quantity*price*(1-salePercent);
		
	}
	
}


public String toString()
{
	String result="UPC: "+UPC+",Product Name: "+name+", price: $"+price+",Unit: "+priceUnit+", On Sale?: "+onSale+",Sale percent: "+salePercent+"%";
	return result;
	
	

}





}
