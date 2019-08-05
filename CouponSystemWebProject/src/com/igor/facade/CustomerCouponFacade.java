package com.igor.facade;

import java.sql.SQLException;
import java.util.Set;

import com.igor.beans.CustomerCoupon;
import com.igor.dbdao.CustomerCouponDBDAO;

import com.igor.exceptions.ConnectionException;

public class CustomerCouponFacade {
	
	CustomerCouponDBDAO customerCouponDBDAO = new CustomerCouponDBDAO();
	
	public CustomerCoupon selectCustomerCoupon(long custId, long couponId) throws Exception {
		return customerCouponDBDAO.selectCustomerCoupon(custId, couponId);
		
	}
	
	public Set<CustomerCoupon> selectAllCustomerCoupons() throws Exception {
		return customerCouponDBDAO.selectAllCustomerCoupons();
	}
	
	public Set<Long> selectAllCustomersByIdCoupon(long couponId) throws ConnectionException, SQLException, Exception {
		return customerCouponDBDAO.selectAllCustomersByIdCoupon(couponId);
	}
		
}
	
