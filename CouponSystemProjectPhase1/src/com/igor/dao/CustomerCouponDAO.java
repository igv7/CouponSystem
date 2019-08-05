package com.igor.dao;

import java.sql.SQLException;
import java.util.Set;

import com.igor.beans.CustomerCoupon;

import com.igor.exceptions.ConnectionException;
import com.igor.exceptions.SelectAllCustomerCouponsException;
import com.igor.exceptions.SelectCustomerCouponException;


public interface CustomerCouponDAO {
	
	public CustomerCoupon selectCustomerCoupon(long custId, long couponId) throws ConnectionException, SelectCustomerCouponException, 
				SQLException, Exception;
	public Set<CustomerCoupon>selectAllCustomerCoupons() throws ConnectionException, SelectAllCustomerCouponsException, 
				SQLException, Exception;
	
	public Set<Long>selectAllCustomersByIdCoupon(long couponId) throws ConnectionException, SQLException, Exception; 
	
	public Set<Long> selectAllCustomerCoupons(long id) throws ConnectionException, SQLException, Exception;

}
