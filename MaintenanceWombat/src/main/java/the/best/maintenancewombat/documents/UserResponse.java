package the.best.maintenancewombat.documents;

public class UserResponse {

	String message;
	User user;
	
	public UserResponse() {
		message = "204";
		user = null;
	}
	
	public UserResponse(User user) {
		this.user = user;
		this.message = "200";
	}
	
	public UserResponse(String message) {
		this.user = null;
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "UserResponse [message=" + message + ", user=" + user + "]";
	}

}