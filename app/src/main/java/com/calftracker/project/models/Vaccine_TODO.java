package com.calftracker.project.models;

import java.util.Calendar;

public class Vaccine_TODO
{
	private Calf calf;
	private Vaccine vaccine;
	private Calendar dueDate;
	private boolean start;

	/**
	 * @param calf
	 * @param vaccine
	 */
	public Vaccine_TODO(Calf calf, Vaccine vaccine, Calendar dueDate, boolean start) {
		super();
		this.calf = calf;
		this.vaccine = vaccine;
		this.dueDate = dueDate;
		this.start = start;
	}

	/**
	 * @return the calf
	 */
	public Calf getCalf() {
		return calf;
	}

	/**
	 * @param calf the calf to set
	 */
	public void setCalf(Calf calf) {
		this.calf = calf;
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
}
