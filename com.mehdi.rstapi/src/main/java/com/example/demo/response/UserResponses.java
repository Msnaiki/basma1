package com.example.demo.response;

import java.io.Serializable;

public class UserResponses implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7469909926627412736L;
	private String id;
	private String firstName;
	private String lastname;
	private String email;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
}
