package com.igor.exceptions;

public class SelectCompanyException extends Exception {
	
	public SelectCompanyException() {
		System.out.println("Statement error! Unable to get Company data!");
	}

}
