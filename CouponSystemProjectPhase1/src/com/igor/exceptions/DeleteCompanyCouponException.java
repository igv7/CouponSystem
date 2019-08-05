package com.igor.exceptions;

public class DeleteCompanyCouponException extends Exception {
	
	public DeleteCompanyCouponException() {
		System.out.println("Could not delete the coupon from the Company_Coupon!");
	}

}
