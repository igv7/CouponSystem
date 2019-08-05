package com.igor.beans;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Customer {
	
	private long id;
	private String custName;
	private String password;
	
	public Customer() {
		
	}
	
    public Customer(String custName, String password) {
		
		setCustName(custName);
		setPassword(password);
	}
	
	public Customer(long id, String custName, String password) {
		
		setId(id);
		setCustName(custName);
		setPassword(password);
	}

	public Customer(Customer customer) {
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", custName=" + custName + ", password=" + password + "]";
	}
	
	

}
