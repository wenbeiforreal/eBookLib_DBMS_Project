package eBook.model;

public class Users {
	protected String loginName; 
	protected String loginPass; 
	protected String firstName; 
	protected String lastName; 
	protected String email;
	
	//Default constructor for first time initialization 
	public Users(String loginName, String loginPass, String firstName, String lastName, String email) {
		this.loginName = loginName;
		this.loginPass = loginPass;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}
	
	//Constructor with reference to only primary key
	public Users(String loginName) {
		this.loginName = loginName; 
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getLoginPass() {
		return loginPass;
	}

	public void setLoginPass(String loginPass) {
		this.loginPass = loginPass;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
	
}
