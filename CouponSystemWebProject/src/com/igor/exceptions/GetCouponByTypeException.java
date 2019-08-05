package com.igor.exceptions;

public class GetCouponByTypeException extends Exception {
	
	public GetCouponByTypeException() {
		System.out.println("Could not get coupons by type!");
	}

}
