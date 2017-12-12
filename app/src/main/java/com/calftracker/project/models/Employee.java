package com.calftracker.project.models;

public class Employee
{
	private String name;
	private String id;
	private String position;

	// empty constructor for firebase
	public Employee(){}

	/**
	 * @param name
	 */
	public Employee(String name, String id, String position) {
		super();
		this.name = name;
		this.id = id;
		this.position = position;
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
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the position
	 */
	public String getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(String position) {
		this.position = position;
	}
}
