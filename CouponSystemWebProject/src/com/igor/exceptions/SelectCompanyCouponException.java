package com.igor.exceptions;

public class SelectCompanyCouponException extends Exception {
	
	public SelectCompanyCouponException() {
		System.out.println("Statement error! Unable to get Coupon data!");
	}

}
