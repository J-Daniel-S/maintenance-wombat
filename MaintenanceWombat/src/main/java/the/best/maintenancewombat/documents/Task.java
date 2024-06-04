package the.best.maintenancewombat.documents;

import the.best.maintenancewombat.documents.branches.Category;
import the.best.maintenancewombat.documents.branches.Location;
import the.best.maintenancewombat.documents.branches.Priority;
import the.best.maintenancewombat.services.MaintenanceService;

public class Task {
	
	long id;
	String name;
	Priority prio;
	Location location;
	Category kind;
	
	public Task() {
		this.id = MaintenanceService.generateId();
		
	}
	
	public Task(String name, Priority prio, Location location) {
		this.name = name;
		this.prio = prio;
		this.location = location;
		this.id = MaintenanceService.generateId();
	}

	public Task(String name, Priority prio, Category kind, Location location) {
		this.name = name;
		this.prio = prio;
		this.kind = kind;
		this.location = location;
		this.id = MaintenanceService.generateId();
	}
	
	public Task(String name) {
		this.name = name;
		this.id = MaintenanceService.generateId();
	}
	
	public String getName() {
		if (this.name != null) {
			return name;
		} else {
			return "unnamed";
		}
	}
	
	public long getId() {
		return id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Priority getPrio() {
		return prio;
	}

	public void setPrio(Priority prio) {
		this.prio = prio;
	}

	public Category getKind() {
		return kind;
	}

	public void setKind(Category kind) {
		this.kind = kind;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
	
	public boolean compareLocation(String location) {
		return this.location.toString().trim().equalsIgnoreCase(location.trim());
	}

	@Override
	public String toString() {
		return "Task [id=" + id + ", name=" + name + ", prio=" + prio + ", location=" + location + ", kind=" + kind
				+ "]";
	}

}