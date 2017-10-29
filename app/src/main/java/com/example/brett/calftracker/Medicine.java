
public class Medicine {
	private String name;
	private double dosage;
	private String dosage_units;
	private int timeActive;
	private String methodOfAdministration;
	
	/**
	 * @param name
	 * @param dosage
	 * @param dosage_units
	 * @param timeActive
	 * @param methodOfAdministration
	 */
	public Medicine(String name, double dosage, String dosage_units, int timeActive, String methodOfAdministration) {
		super();
		this.name = name;
		this.dosage = dosage;
		this.dosage_units = dosage_units;
		this.timeActive = timeActive;
		this.methodOfAdministration = methodOfAdministration;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the dosage
	 */
	public double getDosage() {
		return dosage;
	}

	/**
	 * @param dosage the dosage to set
	 */
	public void setDosage(double dosage) {
		this.dosage = dosage;
	}

	/**
	 * @return the dosage_units
	 */
	public String getDosage_units() {
		return dosage_units;
	}

	/**
	 * @param dosage_units the dosage_units to set
	 */
	public void setDosage_units(String dosage_units) {
		this.dosage_units = dosage_units;
	}

	/**
	 * @return the timeActive
	 */
	public int getTimeActive() {
		return timeActive;
	}

	/**
	 * @param timeActive the timeActive to set
	 */
	public void setTimeActive(int timeActive) {
		this.timeActive = timeActive;
	}

	/**
	 * @return the methodOfAdministration
	 */
	public String getMethodOfAdministration() {
		return methodOfAdministration;
	}

	/**
	 * @param methodOfAdministration the methodOfAdministration to set
	 */
	public void setMethodOfAdministration(String methodOfAdministration) {
		this.methodOfAdministration = methodOfAdministration;
	}
}
