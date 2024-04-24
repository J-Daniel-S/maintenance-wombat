package the.best.maintenancewombat.documents.branches;

public enum Category {
	ELECTRICAL, PLUMBING, IT, PAINT, CLEANUP, OTHER;
	
	public static Category fromString(String value) {
		if (value != null) {
			for (Category cat : Category.values()) {
				if (cat.name().equalsIgnoreCase(value)) {
					return cat;
				}
			}
		}
		throw new IllegalArgumentException("Invalid enum value: " + value);
	}
	
}