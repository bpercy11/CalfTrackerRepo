package com.example.brett.calftracker;


import java.util.ArrayList;

public class Medicine {
	private String name;
	private double dosage;
	private String dosage_units;
	private int timeActive;
	//private ArrayList<MedicineFrequency> frequency;
	private String frequency;
	
	/**
	 * @param name
	 * @param dosage
	 * @param dosage_units
	 * @param timeActive
	 * @param frequency
	 */
	public Medicine(String name, double dosage, String dosage_units, int timeActive, String frequency) {
		super();
		this.name = name;
		this.dosage = dosage;
		this.dosage_units = dosage_units;
		this.timeActive = timeActive;
		this.frequency = frequency;
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
	 * @return the frequency
	 */
		public String getFrequency() {
			return frequency;
		}

		/**
	 * @param frequency the frequency to set
	 */
		public void setFrequency(String frequency) {
			this.frequency = frequency;
		}


	public Medicine createMedicine(String name, Double dosage, String dosage_units, int timeActive,
								   String frequency){
		return new Medicine(name,dosage,dosage_units,timeActive,frequency);
	}
}
