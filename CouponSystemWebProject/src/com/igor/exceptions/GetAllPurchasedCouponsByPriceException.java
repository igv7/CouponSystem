package com.igor.exceptions;

public class GetAllPurchasedCouponsByPriceException extends Exception {
	
	public GetAllPurchasedCouponsByPriceException() {
		System.out.println("Cannot get all purchased coupons by price!");
	}

}
