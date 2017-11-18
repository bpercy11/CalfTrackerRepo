package com.calftracker.project.models;
import java.util.ArrayList;
import java.util.Calendar;

public class TODO {
	private Calendar dateLastUpdated;
	private ArrayList<Calf> calvesToObserve;
	private ArrayList<ArrayList<VaccineTask>> vaccinesToAdminister;
	private ArrayList<VaccineTask> overdueVaccinations;
	
	/**
	 * @param dateLastUpdated
	 * @param calvesToObserve
	 * @param vaccinesToAdminister
	 * @param overdueVaccinations
	 */
	public TODO(Calendar dateLastUpdated, ArrayList<Calf> calvesToObserve,
				ArrayList<ArrayList<VaccineTask>> vaccinesToAdminister, ArrayList<VaccineTask> overdueVaccinations) {
		super();
		this.dateLastUpdated = dateLastUpdated;
		this.calvesToObserve = calvesToObserve;
		this.vaccinesToAdminister = vaccinesToAdminister;
		this.overdueVaccinations = overdueVaccinations;
	}


	/**
	 * @return the dateLastUpdated
	 */
	public Calendar getDateLastUpdated() {
		return dateLastUpdated;
	}

	/**
	 * @param dateLastUpdated the dateLastUpdated to set
	 */
	public void setDateLastUpdated(Calendar dateLastUpdated) {
		this.dateLastUpdated = dateLastUpdated;
	}

	/**
	 * @return the calvesToObserve
	 */
	public ArrayList<Calf> getCalvesToObserve() {
		return calvesToObserve;
	}

	/**
	 * @param calvesToObserve the calvesToObserve to set
	 */
	public void setCalvesToObserve(ArrayList<Calf> calvesToObserve) {
		this.calvesToObserve = calvesToObserve;
	}

	/**
	 * @return the vaccinesToAdminister
	 */
	public ArrayList<ArrayList<VaccineTask>> getVaccinesToAdminister() {
		return vaccinesToAdminister;
	}

	/**
	 * @param vaccinesToAdminister the vaccinesToAdminister to set
	 */
	public void setVaccinesToAdminister(ArrayList<ArrayList<VaccineTask>> vaccinesToAdminister) {
		this.vaccinesToAdminister = vaccinesToAdminister;
	}

	/**
	 * @return the overdueVaccinations
	 */
	public ArrayList<VaccineTask> getOverdueVaccinations() {
		return overdueVaccinations;
	}

	/**
	 * @param overdueVaccinations the overdueVaccinations to set
	 */
	public void setOverdueVaccinations(ArrayList<VaccineTask> overdueVaccinations) {
		this.overdueVaccinations = overdueVaccinations;
	}
}
