package the.best.maintenancewombat.documents;

import the.best.maintenancewombat.documents.branches.Location;
import the.best.maintenancewombat.documents.branches.UserType;

public class User {
	
	private String name, password;
	private UserType userType;
	private Location location;
	
	public User() {

	}
	
	public User(String name) {
		this.name = name;
		this.password = "default";
		this.userType = UserType.STANDARD;
		this.location = Location.SAN_ANTONIO;
	}


	public User(String name, String password, Location location) {
		this.name = name;
		this.password = password;
		this.userType = UserType.STANDARD;
		this.location = location;
	}
	
	public User(String name, String password, UserType userType) {
		this.name = name;
		this.password = password;
		this.userType = userType;
		this.location = this.userType == UserType.MAINTENANCE ? null : Location.SAN_ANTONIO;
	}
	
	public User(String name, String password, UserType userType, Location location) {
		super();
		this.name = name;
		this.password = password;
		this.userType = userType;
		this.location = this.userType == UserType.MAINTENANCE ? null : location;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
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
