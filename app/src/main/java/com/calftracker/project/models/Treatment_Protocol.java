package com.calftracker.project.models;

import com.calftracker.project.models.Medicine;

import java.util.ArrayList;
import java.util.List;

public class Treatment_Protocol {
	private List<Medicine> medicines;
	private String notes;

	public Treatment_Protocol(){}

	/**
	 * @param medicines
	 * @param notes
	 */
	public Treatment_Protocol(List<Medicine> medicines, String notes) {
		super();
		this.medicines = medicines;
		this.notes = notes;
	}
	
	/**
	 * @return the medicines
	 */
	public List<Medicine> getMedicines() {
		return medicines;
	}

	/**
	 * @param medicines the medicines to set
	 */
	public void setMedicine(List<Medicine> medicines) {
		this.medicines = medicines;
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
	 * @param medicines the medicine used to treat the corresponding illness
	 * @param notes the notes detailing the treatment protocol for this calf
	 * @return Treatment_Protocol the new treatment protocol that was created
	 */
	public Treatment_Protocol createTreatmentProtocol(List<Medicine> medicines, String notes){
		return new Treatment_Protocol(medicines,notes);
	}

}
