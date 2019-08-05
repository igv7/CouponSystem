package com.igor.exceptions;

public class CompanyLoginException extends Exception {
	
	public CompanyLoginException() {
		System.out.println("Could not Login with this Company!");
	}

}
