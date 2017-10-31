package com.example.brett.calftracker;

public class Illness {
	private String name;
	private Treatment_Protocol treatmentProtocol;

	public Illness() {} // default no-arg constructor

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

	/**
	 * create a new Illness with the specified name and treatment protocol
	 *
	 * @param name name of the illness to be created
	 * @param treatmentProtocol treatment protocol for this illness
	 * @return Illness
	 */
	public Illness createIllness(String name, Treatment_Protocol treatmentProtocol){
		return new Illness(name,treatmentProtocol);
	}

}
