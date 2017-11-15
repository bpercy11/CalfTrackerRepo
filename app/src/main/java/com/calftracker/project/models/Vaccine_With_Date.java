package com.calftracker.project.models;

import java.util.Calendar;

public class Vaccine_With_Date {
	private Vaccine vaccine;
	private Calendar date;
	
	/**
	 * @param vaccine
	 * @param date
	 */
	public Vaccine_With_Date(Vaccine vaccine, Calendar date) {
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
	public Calendar getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Calendar date) {
		this.date = date;
	}
}
