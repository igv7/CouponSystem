package com.igor.exceptions;

public class CustomerLoginException extends Exception {
	
	public CustomerLoginException() {
		System.out.println("Could not Login with this Customer!");
	}

}
