package kp.company.domain;

/**
 * The employee job title.
 * 
 */
public enum Title {
	ANALYST("Analyst"), DEVELOPER("Developer"), MANAGER("Manager");

	private String name;

	/**
	 * Constructor.
	 * 
	 */
	Title(String name) {
		this.name = name;
	}

	/**
	 * Gets name.
	 * 
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}
}