package the.best.maintenancewombat.documents;

import the.best.maintenancewombat.documents.branches.RequestType;

public class MaintenanceRequest {
	
	Task task;
	RequestType type;
	User maintenanceUser, requestingUser;
	
	public MaintenanceRequest() {
		
	}
	
	public MaintenanceRequest(Task task, RequestType type) {
		this.task = task;
		this.type = type;
	}
	
	public MaintenanceRequest(Task task, RequestType type, User maintUser) {
		this.task = task;
		this.type = type;
		this.maintenanceUser = maintUser;
	}
	
	public MaintenanceRequest(User reqUser, Task task, RequestType type) {
		this.task = task;
		this.type = type;
		this.requestingUser = reqUser;
	}
	
	public User getMaintenanceUser() {
		return maintenanceUser;
	}

	public void setMaintenanceUser(User maintenanceUser) {
		this.maintenanceUser = maintenanceUser;
	}

	public User getRequestingUser() {
		return requestingUser;
	}

	public void setRequestingUser(User requestingUser) {
		this.requestingUser = requestingUser;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public RequestType getType() {
		return type;
	}

	public void setType(RequestType type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "MaintenanceRequest [task=" + task + ", type=" + type + ", maintenanceUser=" + maintenanceUser
				+ ", requestingUser=" + requestingUser + "]";
	}
	
	

}


