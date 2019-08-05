package com.igor.exceptions;

public class AddCustomerCouponException extends Exception {
	
	public AddCustomerCouponException() {
		System.out.println("Could not append the coupon to the Customer_Coupon!");
	}

}
