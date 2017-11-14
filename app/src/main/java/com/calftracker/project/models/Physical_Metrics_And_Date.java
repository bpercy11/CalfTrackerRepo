package com.calftracker.project.models;

import java.util.Calendar;

public class Physical_Metrics_And_Date {
	private double weight;
	private double height;
	private Calendar dateRecorded;
	
	/**
	 * @param weight
	 * @param dateRecorded
	 */
	public Physical_Metrics_And_Date(double weight, Calendar dateRecorded) {
		super();
		this.weight = weight;
		this.height = -1;
		this.dateRecorded = dateRecorded;
	}

	public Physical_Metrics_And_Date(Calendar dateRecorded, double height) {
		super();
		this.height = height;
		this.weight = -1;
		this.dateRecorded = dateRecorded;
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
		this.dateRecorded = dateRecorded;
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
	public Calendar getDateRecorded() {
		return dateRecorded;
	}

	/**
	 * @param dateRecorded the dateRecorded to set
	 */
	public void setDateRecorded(Calendar dateRecorded) {
		this.dateRecorded = dateRecorded;
	}
}
