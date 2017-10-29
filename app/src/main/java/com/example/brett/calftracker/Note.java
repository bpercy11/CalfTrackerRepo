package com.example.brett.calftracker;

public class Note {
	private String message;
	private Easy_Date dateEntered;
	
	/**
	 * @param message
	 * @param dateEntered
	 */
	public Note(String message, Easy_Date dateEntered) {
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
	public Easy_Date getDateEntered() {
		return dateEntered;
	}

	/**
	 * @param dateEntered the dateEntered to set
	 */
	public void setDateEntered(Easy_Date dateEntered) {
		this.dateEntered = dateEntered;
	}
}
