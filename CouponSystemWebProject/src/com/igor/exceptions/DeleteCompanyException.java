package com.igor.exceptions;

public class DeleteCompanyException extends Exception {
	
	public DeleteCompanyException() {
		System.out.println("Failed to remove Company");
	}

}
