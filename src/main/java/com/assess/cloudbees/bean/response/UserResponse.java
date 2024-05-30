package com.assess.cloudbees.bean.response;

public class UserResponse {
	private String firstName;
	private String lastName;
	private String mail;
	public UserResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	public UserResponse(String firstName, String lastName, String mail) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.mail = mail;
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
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	
}

