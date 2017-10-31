package com.example.brett.calftracker;

public class Illness {
	private String name;
	private Treatment_Protocol treatmentProtocol;
	
	/**
	 * @param name
	 * @param treatmentProtocol
	 */
	public Illness(String name, Treatment_Protocol treatmentProtocol) {
		super();
		this.name = name;
		this.treatmentProtocol = treatmentProtocol;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the treatmentProtocol
	 */
	public Treatment_Protocol getTreatmentProtocol() {
		return treatmentProtocol;
	}

	/**
	 * @param treatmentProtocol the treatmentProtocol to set
	 */
	public void setTreatmentProtocol(Treatment_Protocol treatmentProtocol) {
		this.treatmentProtocol = treatmentProtocol;
	}
}
