package com.calftracker.project.models;

import com.calftracker.project.models.Medicine;

import java.util.ArrayList;
import java.util.List;

public class Treatment_Protocol {
	private List<Medicine> medicines;
	private List<String> notes;
	
	/**
	 * @param medicines
	 * @param notes
	 */
	public Treatment_Protocol(List<Medicine> medicines, List<String> notes) {
		super();
		this.medicines = new ArrayList<Medicine>();
		this.notes = new ArrayList<String>();
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
	public List<String> getNotes() {
		return notes;
	}

	/**
	 * @param notes the notes to set
	 */
	public void setNotes(ArrayList<String> notes) {
		this.notes = notes;
	}

	/**
	 * creates and returns a new treatment protocol for an illness
	 *
	 * @param medicines the medicine used to treat the corresponding illness
	 * @param notes the notes detailing the treatment protocol for this calf
	 * @return Treatment_Protocol the new treatment protocol that was created
	 */
	public Treatment_Protocol createTreatmentProtocol(List<Medicine> medicines, List<String> notes){
		return new Treatment_Protocol(medicines,notes);
	}

}
