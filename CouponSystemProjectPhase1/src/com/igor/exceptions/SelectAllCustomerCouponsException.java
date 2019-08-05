package com.igor.exceptions;

public class SelectAllCustomerCouponsException extends Exception {
	
	public SelectAllCustomerCouponsException() {
		System.out.println("Cannot get CustomerCoupon data!");
	}

}
