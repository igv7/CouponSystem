package com.igor.exceptions;

public class SelectCompanyCouponsException  extends Exception{
	
	public SelectCompanyCouponsException() {
		System.out.println("Cannot get CompanyCoupon data!");
	}

}
