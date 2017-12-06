package com.calftracker.project.models;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Calf_Illness
{
	private Illness illness;
	private String outcomeNotes;

	private int year;
	private int month;
	private int day;
	
	/**
	 * @param illness
	 * @param dateDiagnosed
	 * @param outcomeNotes
	 */
	public Calf_Illness(Illness illness, Calendar dateDiagnosed, String outcomeNotes) {
		this.illness = illness;
		this.outcomeNotes = outcomeNotes;
		this.year = dateDiagnosed.get(Calendar.YEAR);
		this.month = dateDiagnosed.get(Calendar.MONTH);
		this.day = dateDiagnosed.get(Calendar.DAY_OF_MONTH);
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
	 * @return the illness
	 */
	public Illness getIllness() {
		return illness;
	}

	/**
	 * @param illness the illness to set
	 */
	public void setIllness(Illness illness) {
		this.illness = illness;
	}

	/**
	 * @return the dateDiagnosed
	 */
	public Calendar makeDateDiagnosed() {
		return new GregorianCalendar(year, month, day);
	}

	/**
	 * @param dateDiagnosed the dateDiagnosed to set
	 */
	public void putDateDiagnosed(Calendar dateDiagnosed) {
		this.year = dateDiagnosed.get(Calendar.YEAR);
		this.month = dateDiagnosed.get(Calendar.MONTH);
		this.day = dateDiagnosed.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * @return the outcomeNotes
	 */
	public String getOutcomeNotes() {
		return outcomeNotes;
	}

	/**
	 * @param outcomeNotes the outcomeNotes to set
	 */
	public void setOutcomeNotes(String outcomeNotes) {
		this.outcomeNotes = outcomeNotes;
	}
}
