package com.example.brett.calftracker;

import java.util.ArrayList;

public class Treatment_Protocol {
	private Medicine medicine;
	private String notes;
	
	/**
	 * @param medicine
	 * @param notes
	 */
	public Treatment_Protocol(Medicine medicine, String notes) {
		super();
		this.medicine = medicine;
		this.notes = notes;
	}
	
	/**
	 * @return the medicine
	 */
	public Medicine getMedicine() {
		return medicine;
	}

	/**
	 * @param medicine the medicine to set
	 */
	public void setMedicine(Medicine medicine) {
		this.medicine = medicine;
	}

	/**
	 * @return the notes
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * @param notes the notes to set
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}

	/**
	 * creates and returns a new treatment protocol for an illness
	 *
	 * @param medicine the medicine used to treat the corresponding illness
	 * @param notes the notes detailing the treatment protocol for this calf
	 * @return Treatment_Protocol the new treatment protocol that was created
	 */
	public Treatment_Protocol createTreatmentProtocol(Medicine medicine, String notes){
		return new Treatment_Protocol(medicine,notes);
	}

}
