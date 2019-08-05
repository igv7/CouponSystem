package com.igor.exceptions;

public class PurchaseCouponException extends Exception {
	
	public PurchaseCouponException() {
		System.out.println("Cannot purchase this Coupon!");
	}

}
