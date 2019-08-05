package com.igor.exceptions;

public class DeleteAllCompaniesException extends Exception {
	
	public DeleteAllCompaniesException() {
		System.out.println("Failed to delete all Companies!");
	}

}
