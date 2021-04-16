package com.revature.dto;

public class PutClientDTO {

	private String firstName;
	private String lastName;
	
	public PutClientDTO() {
		super();
	}
	
	public PutClientDTO(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

}
