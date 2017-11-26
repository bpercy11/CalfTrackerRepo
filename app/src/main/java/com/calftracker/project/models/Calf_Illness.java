package com.calftracker.project.models;

import java.util.Calendar;

public class Calf_Illness
{
	private Illness illness;
	private Calendar dateDiagnosed;
	private String outcomeNotes;
	
	/**
	 * @param illness
	 * @param dateDiagnosed
	 * @param outcomeNotes
	 */
	public Calf_Illness(Illness illness, Calendar dateDiagnosed, String outcomeNotes) {
		this.illness = illness;
		this.dateDiagnosed = dateDiagnosed;
		this.outcomeNotes = outcomeNotes;
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
	public Calendar getDateDiagnosed() {
		return dateDiagnosed;
	}

	/**
	 * @param dateDiagnosed the dateDiagnosed to set
	 */
	public void setDateDiagnosed(Calendar dateDiagnosed) {
		this.dateDiagnosed = dateDiagnosed;
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
