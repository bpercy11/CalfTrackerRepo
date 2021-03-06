package com.calftracker.project.models;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Calf {
	private String photo;
	private String farmId;
	private int internalId;
	private String gender;
	private ArrayList<String> calfAllergies;
	private Sire sire;
	private String dam;
	private boolean active;
	private ArrayList<Vaccine_With_Date> administeredVaccines;
	private ArrayList<Vaccine> neededVaccines;
	private ArrayList<Calf_Illness> illnessHistory;
	private ArrayList<Physical_Metrics_And_Date> physicalHistory;
	private Feeding[] feedingHistory;
	private ArrayList<Note> notes;
	private boolean needToObserveForIllness;

	private int calfyear;
	private int calfmonth;
	private int calfday;
	
	/**
	 * @param farmId
	 * @param internalId
	 * @param gender
	 * @param dateOfBirth
	 * @param neededVaccines
	 */
	public Calf(String photo, String farmId, int internalId, String gender, Calendar dateOfBirth,
			ArrayList<Vaccine> neededVaccines) {
		super();
		// FROM CONSTRUCTOR ARGUMENTS
		this.photo = photo;
		this.farmId = farmId;
		this.internalId = internalId;
		this.gender = gender;
		this.calfyear = dateOfBirth.get(Calendar.YEAR);
		this.calfmonth = dateOfBirth.get(Calendar.MONTH);
		this.calfday = dateOfBirth.get(Calendar.DAY_OF_MONTH);
		this.neededVaccines = neededVaccines;
		
		// SET UP REST OF FIELDS FOR LATER USE
		this.active = true;
		this.calfAllergies = new ArrayList<String>();
		this.administeredVaccines = new ArrayList<Vaccine_With_Date>();
		this.illnessHistory = new ArrayList<Calf_Illness>();
		this.physicalHistory = new ArrayList<Physical_Metrics_And_Date>();
		this.feedingHistory = new Feeding[2];
		this.notes = new ArrayList<Note>();

		this.needToObserveForIllness = false;
	}

	//LAZY WITHOUT INTERNAL AND USING CALENDAR
	public Calf(String photo, String farmId, String gender, Calendar dateOfBirth) {
		super();
		// FROM CONSTRUCTOR ARGUMENTS
        this.photo = photo;
		this.farmId = farmId;
		this.gender = gender;
		this.calfyear = dateOfBirth.get(Calendar.YEAR);
		this.calfmonth = dateOfBirth.get(Calendar.MONTH);
		this.calfday = dateOfBirth.get(Calendar.DAY_OF_MONTH);
		this.neededVaccines = new ArrayList<Vaccine>();

		// SET UP REST OF FIELDS FOR LATER USE
		this.active = true;
		this.calfAllergies = new ArrayList<String>();
		this.administeredVaccines = new ArrayList<Vaccine_With_Date>();
		this.illnessHistory = new ArrayList<Calf_Illness>();
		this.physicalHistory = new ArrayList<Physical_Metrics_And_Date>();
		this.feedingHistory = new Feeding[2];
		this.notes = new ArrayList<Note>();

		this.needToObserveForIllness = false;
	}

	public int getCalfyear() {
		return calfyear;
	}

	public void setCalfyear(int calfyear) {
		this.calfyear = calfyear;
	}

	public int getCalfmonth() {
		return calfmonth;
	}

	public void setCalfmonth(int calfmonth) {
		this.calfmonth = calfmonth;
	}

	public int getCalfday() {
		return calfday;
	}

	public void setCalfday(int calfday) {
		this.calfday = calfday;
	}

	/**
	 * @return the photo
	 */
	public String getPhoto() { return photo; }

	/**
	 * @param photo the photo to set
	 */
	public void setPhoto(String photo) {
		this.photo = photo;
	}

	/**
	 * @return the farmId
	 */
	public String getFarmId() {
		return farmId;
	}

	/**
	 * @param farmId the farmId to set
	 */
	public void setFarmId(String farmId) {
		this.farmId = farmId;
	}

	/**
	 * @return the internalId
	 */
	public int getInternalId() {
		return internalId;
	}

	/**
	 * @param internalId the internalId to set
	 */
	public void setInternalId(int internalId) {
		this.internalId = internalId;
	}

	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * @param gender the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * @return the dateOfBirth
	 */
	public Calendar makeCalendarDOB() {
		return new GregorianCalendar(this.calfyear, this.calfmonth, this.calfday);
	}

	/**
	 * @param dateOfBirth the dateOfBirth to set
	 */
	public void placeCalendarDOB(Calendar dateOfBirth) {
		this.calfyear = dateOfBirth.get(Calendar.YEAR);
		this.calfmonth = dateOfBirth.get(Calendar.MONTH);
		this.calfday = dateOfBirth.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * @return the calfAllergies
	 */
	public ArrayList<String> getCalfAllergies() {
		return calfAllergies;
	}

	/**
	 * @param calfAllergies the calfAllergies to set
	 */
	public void setCalfAllergies(ArrayList<String> calfAllergies) {
		this.calfAllergies = calfAllergies;
	}

	/**
	 * @return the sire
	 */
	public Sire getSire() {
		return sire;
	}

	/**
	 * @param sire the sire to set
	 */
	public void setSire(Sire sire) {
		this.sire = sire;
	}

	/**
	 * @return the dam
	 */
	public String getDam() {
		return dam;
	}

	/**
	 * @param dam the dam to set
	 */
	public void setDam(String dam) {
		this.dam = dam;
	}

	/**
	 * @return the active
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

	/**
	 * @return the administeredVaccines
	 */
	public ArrayList<Vaccine_With_Date> getAdministeredVaccines() {
		return administeredVaccines;
	}

	/**
	 * @param administeredVaccines the administeredVaccines to set
	 */
	public void setAdministeredVaccines(ArrayList<Vaccine_With_Date> administeredVaccines) {
		this.administeredVaccines = administeredVaccines;
	}

	/**
	 * @return the neededVaccines
	 */
	public ArrayList<Vaccine> getNeededVaccines() {
		return neededVaccines;
	}

	/**
	 * @param neededVaccines the neededVaccines to set
	 */
	public void setNeededVaccines(ArrayList<Vaccine> neededVaccines) {
		this.neededVaccines = neededVaccines;
	}

	/**
	 * @return the illnessHistory
	 */
	public ArrayList<Calf_Illness> getIllnessHistory() {
		return illnessHistory;
	}

	/**
	 * @param illnessHistory the illnessHistory to set
	 */
	public void setIllnessHistory(ArrayList<Calf_Illness> illnessHistory) {
		this.illnessHistory = illnessHistory;
	}

	/**
	 * @return the physicalHistory
	 */
	public ArrayList<Physical_Metrics_And_Date> getPhysicalHistory() {
		return physicalHistory;
	}

	/**
	 * @param physicalHistory the physicalHistory to set
	 */
	public void setPhysicalHistory(ArrayList<Physical_Metrics_And_Date> physicalHistory) {
		this.physicalHistory = physicalHistory;
	}

	/**
	 * @return the feedingHistory
	 */
	public Feeding[] getFeedingHistory() {
		return feedingHistory;
	}

	/**
	 * @param feedingHistory the feedingHistory to set
	 */
	public void setFeedingHistory(Feeding[] feedingHistory) {
		this.feedingHistory = feedingHistory;
	}

	/**
	 * @return the notes
	 */
	public ArrayList<Note> getNotes() {
		return notes;
	}

	/**
	 * @param note the note to add
	 */
	public void addNote(Note note) {
		this.notes.add(note);
	}

	public Note getNoteNdx(int i) { return this.notes.get(i); }

	public int getNotesSize() { return this.notes.size(); }

	/**
	 * @param notes the notes to set
	 */
	public void setNotes(ArrayList<Note> notes) {
		this.notes = notes;
	}
	
	/**
	 * @return the needToObserveForIllness
	 */
	public boolean isNeedToObserveForIllness() {
		return needToObserveForIllness;
	}

	/**
	 * @param needToObserveForIllness the needToObserveForIllness to set
	 */
	public void setNeedToObserveForIllness(boolean needToObserveForIllness) {
		this.needToObserveForIllness = needToObserveForIllness;
	}

	/**
	 * This function deletes the vaccine given by the function argument from 
	 * the neededVaccines field and adds a Vaccine_With_Date to the administeredVaccines
	 * field. Vaccines that need to be administered multiple times over the calf's life
	 * need to entered twice in the neededVaccines ArrayList because this function
	 * just searches for the first entry in neededVaccines that matches the vaccine
	 * function argument and removes it, with no knowledge of if that vaccine
	 * needs to be administered 2+ times.
	 * 
	 * @param vaccine
	 * @param dateAdministered
	 */
	public void addAdministeredVaccine(Vaccine vaccine, Calendar dateAdministered)
	{
		if(this.neededVaccines.contains(vaccine))
			for(int i = 0; i < this.neededVaccines.size(); i++)
				if(this.neededVaccines.get(i).equals(vaccine))
				{
					this.neededVaccines.remove(i);
					break;
				}
					
		Vaccine_With_Date administeredVaccine = new Vaccine_With_Date(vaccine, dateAdministered);
		this.administeredVaccines.add(administeredVaccine);
	}
}
