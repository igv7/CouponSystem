package com.igor.exceptions;

public class GetAllPurchasedCouponsByTypeException extends Exception {
	
	public GetAllPurchasedCouponsByTypeException() {
		System.out.println("Could not get purchased coupons by type!");
	}

}
