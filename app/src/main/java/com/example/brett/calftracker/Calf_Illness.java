package com.example.brett.calftracker;
import com.example.brett.calftracker.Illness;

public class Calf_Illness
{
	private Illness illness;
	private Easy_Date dateDiagnosed;
	private String outcomeNotes;
	
	/**
	 * @param illness
	 * @param dateDiagnosed
	 * @param outcomeNotes
	 */
	public Calf_Illness(Illness illness, Easy_Date dateDiagnosed, String outcomeNotes) {
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
	public Easy_Date getDateDiagnosed() {
		return dateDiagnosed;
	}

	/**
	 * @param dateDiagnosed the dateDiagnosed to set
	 */
	public void setDateDiagnosed(Easy_Date dateDiagnosed) {
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
