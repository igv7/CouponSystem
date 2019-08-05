package com.igor.exceptions;

public class RemoveExpCouponsException extends Exception {
	
	public RemoveExpCouponsException() {
		System.out.println("Cannot remove expired coupons!");
	}

}
