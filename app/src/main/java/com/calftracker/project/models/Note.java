package com.calftracker.project.models;

import java.util.Calendar;

public class Note {
	private String message;
	private Calendar dateEntered;
	
	/**
	 * @param message
	 * @param dateEntered
	 */
	public Note(String message, Calendar dateEntered) {
		super();
		this.message = message;
		this.dateEntered = dateEntered;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the dateEntered
	 */
	public Calendar getDateEntered() {
		return dateEntered;
	}

	/**
	 * @param dateEntered the dateEntered to set
	 */
	public void setDateEntered(Calendar dateEntered) {
		this.dateEntered = dateEntered;
	}
}
