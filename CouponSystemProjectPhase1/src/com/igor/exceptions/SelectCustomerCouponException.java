package com.igor.exceptions;

public class SelectCustomerCouponException extends Exception {
	
	public SelectCustomerCouponException() {
		System.out.println("Statement error! Unable to get Coupon data!");
	}

}
