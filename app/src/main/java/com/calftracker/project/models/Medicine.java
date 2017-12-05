package com.calftracker.project.models;


public class Medicine {
	private String name;
	private double dosage;
	private String dosage_units;
	private int timeActive;
	private String notes;

	
	/**
	 * @param name
	 * @param dosage
	 * @param dosage_units
	 * @param timeActive
	 * @param notes
	 */
	public Medicine(String name, double dosage, String dosage_units, int timeActive, String notes) {
		super();
		this.name = name;
		this.dosage = dosage;
		this.dosage_units = dosage_units;
		this.timeActive = timeActive;
		this.notes = notes;
	}

	public Medicine(){}

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
	 * @return the notes
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * @param notes the notes to set
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Medicine createMedicine(String name, Double dosage, String dosage_units, int timeActive,
								   String frequency){
		return new Medicine(name, dosage, dosage_units, timeActive, notes);
	}

	@Override
	public String toString() {
		return this.getName();
	}
}
