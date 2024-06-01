package the.best.maintenancewombat.documents.branches;

public enum UserType {
	MAINTENANCE, STANDARD;

	public static UserType fromString(String value) {
		if (value != null) {
			for (UserType ut : UserType.values()) {
				if (ut.name().equalsIgnoreCase(value)) {
					return ut;
				}
			}
		}
		throw new IllegalArgumentException("Invalid enum value: " + value);
	}
}
