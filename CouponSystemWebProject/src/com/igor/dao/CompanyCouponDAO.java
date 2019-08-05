package com.igor.dao;


import java.sql.SQLException;
import java.util.Set;

import com.igor.beans.CompanyCoupon;

import com.igor.exceptions.ConnectionException;
import com.igor.exceptions.SelectCompanyCouponException;
import com.igor.exceptions.SelectCompanyCouponsException;



public interface CompanyCouponDAO {

	public CompanyCoupon selectCompanyCoupon(long compId, long couponId) throws ConnectionException, SelectCompanyCouponException, 
			SQLException, Exception;
	public CompanyCoupon selectCompanyCouponByCouponId(long couponId) throws Exception;
	public Set<Long> selectCompanyCoupons() throws ConnectionException, SelectCompanyCouponsException, SQLException, Exception;
	public Set<Long> selectCompanyCouponsByIdCompany(long id) throws ConnectionException, SQLException, Exception;
	
}
