package the.best.maintenancewombat.documents;

import the.best.maintenancewombat.documents.branches.Category;
import the.best.maintenancewombat.documents.branches.Location;
import the.best.maintenancewombat.documents.branches.Priority;

public class Task {
	
	String name;
	Priority prio;
	Location location;
	Category kind;
	
	public Task() {
		this.prio = Priority.LOW;
		this.kind = Category.OTHER;
		this.location = Location.SAN_ANTONIO;
	}
	
	public Task(String name) {
		this.name = name;
		this.prio = Priority.LOW;
		this.kind = Category.OTHER;
		this.location = Location.SAN_ANTONIO;
	}
	
	public Task(String name, Priority prio, Location location) {
		this.name = name;
		this.prio = prio;
		this.kind = Category.OTHER;
		this.location = location;
	}

	public Task(String name, Priority prio, Category kind, Location location) {
		this.name = name;
		this.prio = prio;
		this.kind = kind;
		this.location = location;
	}
	
	public String getName() {
		if (this.name != null) {
			return name;
		} else {
			return "unnamed";
		}
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
		return "Task [name=" + name + ", prio=" + prio + ", location=" + location + ", kind=" + kind + "]";
	}

}