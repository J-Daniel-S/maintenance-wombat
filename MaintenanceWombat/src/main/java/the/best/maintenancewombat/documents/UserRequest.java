package the.best.maintenancewombat.documents;

public class UserRequest {
	
	private String name, password;
	
	public UserRequest() {
		
	}
	
	public UserRequest(String name, String password) {
		this.name = name;
		this.password = password;
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

	@Override
	public String toString() {
		return "UserRequest [name=" + name + ", password=" + password + "]";
	}
	
}
