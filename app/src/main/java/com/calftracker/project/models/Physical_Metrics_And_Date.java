package com.calftracker.project.models;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Physical_Metrics_And_Date {
	private double weight;
	private double height;

	private int year;
	private int month;
	private int day;
	
	/**
	 * @param weight
	 * @param dateRecorded
	 */
	public Physical_Metrics_And_Date(double weight, Calendar dateRecorded) {
		super();
		this.weight = weight;
		this.height = -1;
		this.year = dateRecorded.get(Calendar.YEAR);
		this.month = dateRecorded.get(Calendar.MONTH);
		this.day = dateRecorded.get(Calendar.DAY_OF_MONTH);
	}

	public Physical_Metrics_And_Date(Calendar dateRecorded, double height) {
		super();
		this.height = height;
		this.weight = -1;
		this.year = dateRecorded.get(Calendar.YEAR);
		this.month = dateRecorded.get(Calendar.MONTH);
		this.day = dateRecorded.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * @param weight
	 * @param height
	 * @param dateRecorded
	 */
	public Physical_Metrics_And_Date(double weight, double height, Calendar dateRecorded) {
		super();
		this.weight = weight;
		this.height = height;
		this.year = dateRecorded.get(Calendar.YEAR);
		this.month = dateRecorded.get(Calendar.MONTH);
		this.day = dateRecorded.get(Calendar.DAY_OF_MONTH);
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
	 * @return the weight
	 */
	public double getWeight() {
		return weight;
	}

	/**
	 * @param weight the weight to set
	 */
	public void setWeight(double weight) {
		this.weight = weight;
	}

	/**
	 * @return the height
	 */
	public double getHeight() {
		return height;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(double height) {
		this.height = height;
	}

	/**
	 * @return the dateRecorded
	 */
	public Calendar makeDateRecorded() {
		return new GregorianCalendar(year, month, day);
	}

	/**
	 * @param dateRecorded the dateRecorded to set
	 */
	public void putDateRecorded(Calendar dateRecorded) {
		this.year = dateRecorded.get(Calendar.YEAR);
		this.month = dateRecorded.get(Calendar.MONTH);
		this.day = dateRecorded.get(Calendar.DAY_OF_MONTH);
	}
}
