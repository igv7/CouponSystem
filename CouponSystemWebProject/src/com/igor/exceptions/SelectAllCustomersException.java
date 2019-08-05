package com.igor.exceptions;

public class SelectAllCustomersException extends Exception {
	
	public SelectAllCustomersException() {
		System.out.println("Cannot get AllCustomers data!");
	}

}
