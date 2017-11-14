package com.calftracker.project.models;

public class Vacc_Range
{
	private int[] span;

	/**
	 * @param span
	 */
	public Vacc_Range(int[] span) {
		super();
		this.span = span;
	}

	/**
	 * @return the span
	 */
	public int[] getSpan() {
		return span;
	}

	/**
	 * @param span the span to set
	 */
	public void setSpan(int[] span) {
		this.span = span;
	}
}
