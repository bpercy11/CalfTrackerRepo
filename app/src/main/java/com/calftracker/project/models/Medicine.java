package com.calftracker.project.models;


public class Medicine {
	private String name;
	private double dosage;
	private String dosage_units;
	private int timeActive;

	
	/**
	 * @param name
	 * @param dosage
	 * @param dosage_units
	 * @param timeActive
	 */
	public Medicine(String name, double dosage, String dosage_units, int timeActive) {
		super();
		this.name = name;
		this.dosage = dosage;
		this.dosage_units = dosage_units;
		this.timeActive = timeActive;
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

	public Medicine createMedicine(String name, Double dosage, String dosage_units, int timeActive,
								   String frequency){
		return new Medicine(name,dosage,dosage_units,timeActive);
	}
}