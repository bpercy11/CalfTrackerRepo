package com.calftracker.project.models;

public class Feeding
{
	private Employee fedBy;
	private String methodOfFeeding;
	private double litersFed;
	
	/**
	 * @param fedBy
	 * @param methodOfFeeding
	 * @param litersFed
	 */
	public Feeding(Employee fedBy, String methodOfFeeding, double litersFed) {
		super();
		this.fedBy = fedBy;
		this.methodOfFeeding = methodOfFeeding;
		this.litersFed = litersFed;
	}

	/**
	 * @return the fedBy
	 */
	public Employee getFedBy() {
		return fedBy;
	}

	/**
	 * @param fedBy the fedBy to set
	 */
	public void setFedBy(Employee fedBy) {
		this.fedBy = fedBy;
	}

	/**
	 * @return the methodOfFeeding
	 */
	public String getMethodOfFeeding() {
		return methodOfFeeding;
	}

	/**
	 * @param methodOfFeeding the methodOfFeeding to set
	 */
	public void setMethodOfFeeding(String methodOfFeeding) {
		this.methodOfFeeding = methodOfFeeding;
	}

	/**
	 * @return the litersFed
	 */
	public double getLitersFed() {
		return litersFed;
	}

	/**
	 * @param litersFed the litersFed to set
	 */
	public void setLitersFed(double litersFed) {
		this.litersFed = litersFed;
	}
}
