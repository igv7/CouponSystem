package com.igor.beans;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Company {
	
	private long id;
	private String compName;
	private String password;
	private String email;
	
	public Company() {
		
	}
	
	public Company(String compName, String password, String email) {
		
		setCompName(compName);
		setPassword(password);
		setEmail(email);
	}
	
	public Company(long id, String compName, String password, String email) {
		
		setId(id);
		setCompName(compName);
		setPassword(password);
		setEmail(email);
	}

	public Company(Company company) {
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCompName() {
		return compName;
	}

	public void setCompName(String compName) {
		this.compName = compName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Company [id=" + id + ", compName=" + compName + ", password=" + password + ", email=" + email + "]";
	}
	
	

}
