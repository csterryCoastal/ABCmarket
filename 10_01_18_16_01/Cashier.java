
public class Cashier {
	private int authorityLevel;
	private String userName;
	private String PIN;
	private String fName;
	private String lName;
	private String position;
	private String status;
	
	
	public Cashier()
	{
		userName="";
		PIN="0000";
		fName = "";
		lName = "";
		position = "cashier";
		status="Active";
		setAuthorityLevel(1);
	}
	public Cashier(String user,String pin,String first,String last,String pos,String status)
	{
		this.userName=user;
		this.PIN=pin;
		this.fName = first;
		this.lName = last;
		this.position = pos;
		this.status = status;
		this.authorityLevel = 1;
		
	}
	public String getUser()
	{
		return userName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	
	public String getfName() {
		return fName;
	}
	public void setfName(String fName) {
		this.fName = fName;
	}
	
	public String getLName() {
		return lName;
	}
	public void setLName(String lName) {
		this.lName = lName;
	}
	public int getAuthorityLevel() {
		return authorityLevel;
	}
	public void setAuthorityLevel(int authorityLevel) {
		this.authorityLevel = authorityLevel;
	}
	public String getPIN() {
		return PIN;
	}
	public void setPIN(String pIN) {
		PIN = pIN;
	}


}
