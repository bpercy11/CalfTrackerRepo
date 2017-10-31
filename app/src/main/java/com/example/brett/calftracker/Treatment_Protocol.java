package com.example.brett.calftracker;

import java.util.ArrayList;

public class Treatment_Protocol {
	private ArrayList<Medicine> medicines;
	private String notes;
	
	/**
	 * @param medicines
	 * @param notes
	 */
	public Treatment_Protocol(ArrayList<Medicine> medicines, String notes) {
		super();
		this.medicines = medicines;
		this.notes = notes;
	}
	
	/**
	 * @return the medicines
	 */
	public ArrayList<Medicine> getMedicines() {
		return medicines;
	}

	/**
	 * @param medicines the medicines to set
	 */
	public void setMedicines(ArrayList<Medicine> medicines) {
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
	 * @param medicine the medicine to add
	 */
	public void addMedicine(Medicine medicine) {
		this.medicines.add(medicine);
	}
	
	/**
	 * @param medicine the medicine to delete
	 */
	public void deleteMedicine(Medicine medicine) {
		for(int i = 0; i < this.medicines.size(); i++)
		{
			if(medicine.getName().equals(this.medicines.get(i).getName()))
			{
				this.medicines.remove(i);
				return;
			}
		}
	}
}
