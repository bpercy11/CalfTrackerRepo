import java.util.ArrayList;

public class Vaccine
{
	private String name;
	private ArrayList<Vacc_Range> toBeAdministered;
	private double dosage;
	private String dosageUnits;
	private String methodOfAdministration;
	
	/**
	 * @param name
	 * @param toBeAdministered
	 * @param dosage
	 * @param dosageUnits
	 * @param methodOfAdministration
	 */
	public Vaccine(String name, ArrayList<Vacc_Range> toBeAdministered, double dosage, String dosageUnits,
			String methodOfAdministration) {
		super();
		this.name = name;
		this.toBeAdministered = toBeAdministered;
		this.dosage = dosage;
		this.dosageUnits = dosageUnits;
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
	 * @return the toBeAdministered
	 */
	public ArrayList<Vacc_Range> getToBeAdministered() {
		return toBeAdministered;
	}

	/**
	 * @param toBeAdministered the toBeAdministered to set
	 */
	public void setToBeAdministered(ArrayList<Vacc_Range> toBeAdministered) {
		this.toBeAdministered = toBeAdministered;
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
	 * @return the dosageUnits
	 */
	public String getDosageUnits() {
		return dosageUnits;
	}

	/**
	 * @param dosageUnits the dosageUnits to set
	 */
	public void setDosageUnits(String dosageUnits) {
		this.dosageUnits = dosageUnits;
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
