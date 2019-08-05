package com.igor.exceptions;

public class ShutdownException extends Exception {
	
	public ShutdownException() {
		System.out.println("Shutdown failed!");
	}

}
