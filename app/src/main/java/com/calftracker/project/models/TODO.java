package com.calftracker.project.models;
import java.util.ArrayList;

public class TODO {
	private Easy_Date dateLastUpdated;
	private ArrayList<Calf> calvesToObserve;
	private ArrayList<ArrayList<Vaccine_TODO>> vaccinesToAdminister;
	private ArrayList<Vaccine_TODO> overdueVaccinations;
	
	/**
	 * @param dateLastUpdated
	 * @param calvesToObserve
	 * @param vaccinesToAdminister
	 * @param overdueVaccinations
	 */
	public TODO(Easy_Date dateLastUpdated, ArrayList<Calf> calvesToObserve,
			ArrayList<ArrayList<Vaccine_TODO>> vaccinesToAdminister, ArrayList<Vaccine_TODO> overdueVaccinations) {
		super();
		this.dateLastUpdated = dateLastUpdated;
		this.calvesToObserve = calvesToObserve;
		this.vaccinesToAdminister = vaccinesToAdminister;
		this.overdueVaccinations = overdueVaccinations;
	}


	/**
	 * @return the dateLastUpdated
	 */
	public Easy_Date getDateLastUpdated() {
		return dateLastUpdated;
	}

	/**
	 * @param dateLastUpdated the dateLastUpdated to set
	 */
	public void setDateLastUpdated(Easy_Date dateLastUpdated) {
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
	public ArrayList<ArrayList<Vaccine_TODO>> getVaccinesToAdminister() {
		return vaccinesToAdminister;
	}

	/**
	 * @param vaccinesToAdminister the vaccinesToAdminister to set
	 */
	public void setVaccinesToAdminister(ArrayList<ArrayList<Vaccine_TODO>> vaccinesToAdminister) {
		this.vaccinesToAdminister = vaccinesToAdminister;
	}

	/**
	 * @return the overdueVaccinations
	 */
	public ArrayList<Vaccine_TODO> getOverdueVaccinations() {
		return overdueVaccinations;
	}

	/**
	 * @param overdueVaccinations the overdueVaccinations to set
	 */
	public void setOverdueVaccinations(ArrayList<Vaccine_TODO> overdueVaccinations) {
		this.overdueVaccinations = overdueVaccinations;
	}
}
