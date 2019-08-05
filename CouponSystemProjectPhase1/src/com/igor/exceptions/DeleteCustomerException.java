package com.igor.exceptions;

public class DeleteCustomerException extends Exception {
	
	public DeleteCustomerException() {
		System.out.println("Failed to remove Customer!");
	}

}
