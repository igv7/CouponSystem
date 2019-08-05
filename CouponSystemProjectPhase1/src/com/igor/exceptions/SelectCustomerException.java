package com.igor.exceptions;

public class SelectCustomerException extends Exception {
	
	public SelectCustomerException() {
		System.out.println("Statement error! Unable to get Customer data");
	}

}
