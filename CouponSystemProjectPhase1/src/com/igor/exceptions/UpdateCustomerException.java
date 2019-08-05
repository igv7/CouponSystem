package com.igor.exceptions;

public class UpdateCustomerException extends Exception {
	
	public UpdateCustomerException() {
		System.out.println("Failed to update Customer!");
	}

}
