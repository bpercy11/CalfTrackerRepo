package com.calftracker.project.models;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Vaccine_With_Date {
	private Vaccine vaccine;
	private int year;
	private int month;
	private int day;

	// empty constructor for firebase
	public Vaccine_With_Date(){}

	/**
	 * @param vaccine
	 * @param date
	 */
	public Vaccine_With_Date(Vaccine vaccine, Calendar date) {
		super();
		this.vaccine = vaccine;
		this.year = date.get(Calendar.YEAR);
		this.month = date.get(Calendar.MONTH);
		this.day = date.get(Calendar.DAY_OF_MONTH);
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
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
	public Calendar makeDate() {
		return new GregorianCalendar(year, month, day);
	}

	/**
	 * @param date the date to set
	 */
	public void putDate(Calendar date) {
		this.year = date.get(Calendar.YEAR);
		this.month = date.get(Calendar.MONTH);
		this.day = date.get(Calendar.DAY_OF_MONTH);
	}
}
