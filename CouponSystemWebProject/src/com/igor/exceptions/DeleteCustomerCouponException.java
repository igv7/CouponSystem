package com.igor.exceptions;

public class DeleteCustomerCouponException extends Exception {
	
	public DeleteCustomerCouponException() {
		System.out.println("Could not delete the coupon from the Customer_Coupon!");
	}

}
