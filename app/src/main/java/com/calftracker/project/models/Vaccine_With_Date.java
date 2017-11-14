package com.calftracker.project.models;

public class Vaccine_With_Date {
	private Vaccine vaccine;
	private Easy_Date date;
	
	/**
	 * @param vaccine
	 * @param date
	 */
	public Vaccine_With_Date(Vaccine vaccine, Easy_Date date) {
		super();
		this.vaccine = vaccine;
		this.date = date;
	}

	/**
	 * @return the vaccine
	 */
	public Vaccine getVaccine() {
		return vaccine;
	}

	/**
	 * @param vaccine the vaccine to set
	 */
	public void setVaccine(Vaccine vaccine) {
		this.vaccine = vaccine;
	}

	/**
	 * @return the date
	 */
	public Easy_Date getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Easy_Date date) {
		this.date = date;
	}
}
