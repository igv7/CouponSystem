package com.igor.exceptions;

public class DeleteCouponException extends Exception {
	
	public DeleteCouponException() {
		System.out.println("Failed to remove Coupon!");
	}

}
