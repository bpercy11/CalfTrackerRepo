package com.example.brett.calftracker;

public class Physical_Metrics_And_Date {
	private double weight;
	private double height;
	private Easy_Date dateRecorded;
	
	/**
	 * @param weight
	 * @param dateRecorded
	 */
	public Physical_Metrics_And_Date(double weight, Easy_Date dateRecorded) {
		super();
		this.weight = weight;
		this.dateRecorded = dateRecorded;
	}

	/**
	 * @param weight
	 * @param height
	 * @param dateRecorded
	 */
	public Physical_Metrics_And_Date(double weight, double height, Easy_Date dateRecorded) {
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
	public Easy_Date getDateRecorded() {
		return dateRecorded;
	}

	/**
	 * @param dateRecorded the dateRecorded to set
	 */
	public void setDateRecorded(Easy_Date dateRecorded) {
		this.dateRecorded = dateRecorded;
	}
}
