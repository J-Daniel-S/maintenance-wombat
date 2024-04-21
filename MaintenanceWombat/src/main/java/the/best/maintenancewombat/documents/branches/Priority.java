package the.best.maintenancewombat.documents.branches;

public enum Priority {
	HIGH, MEDIUM, LOW;
	
	public static Priority fromString(String value) {
		if (value != null) {
			for (Priority prio : Priority.values()) {
				if (prio.name().equalsIgnoreCase(value)) {
					return prio;
				}
			}
		}
		throw new IllegalArgumentException("Invalid enum value: " + value);
	}
}