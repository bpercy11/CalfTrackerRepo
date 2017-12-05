package com.calftracker.project.models;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Note {
	private String message;

	private int year;
	private int month;
	private int day;
	
	/**
	 * @param message
	 * @param dateEntered
	 */
	public Note(String message, Calendar dateEntered) {
		super();
		this.message = message;
		this.year = dateEntered.get(Calendar.YEAR);
		this.month = dateEntered.get(Calendar.MONTH);
		this.day = dateEntered.get(Calendar.DAY_OF_MONTH);
	}

	public Note(){}

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
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the dateEntered
	 */
	public Calendar makeDateEntered() {
		return new GregorianCalendar(year, month, day);
	}

	/**
	 * @param dateEntered the dateEntered to set
	 */
	public void putDateEntered(Calendar dateEntered) {
		this.year = dateEntered.get(Calendar.YEAR);
		this.month = dateEntered.get(Calendar.MONTH);
		this.day = dateEntered.get(Calendar.DAY_OF_MONTH);
	}
}
