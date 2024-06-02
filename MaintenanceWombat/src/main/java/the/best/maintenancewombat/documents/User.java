package the.best.maintenancewombat.documents;

import the.best.maintenancewombat.documents.branches.UserType;

public class User {
	
	private String name, password;
	private UserType userType;
	
	public User() {

	}
	
	public User(String name) {
		this.name = name;
		this.password = "default";
		this.userType = UserType.STANDARD;
	}
	
	public User(String name, String password) {
		this.name = name;
		this.password = password;
		this.userType = UserType.STANDARD;
	}
	
	public User(String name, String password, UserType userType) {
		this.name = name;
		this.password = password;
		this.userType = userType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}
	
	@Override
	public String toString() {
		return "User [name=" + name + ", password=" + password + "]";
	}
	
}
