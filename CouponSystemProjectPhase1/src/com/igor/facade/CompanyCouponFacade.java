package com.igor.facade;


import java.util.Set;

import com.igor.beans.CompanyCoupon;
import com.igor.dbdao.CompanyCouponDBDAO;


public class CompanyCouponFacade {
	
	CompanyCouponDBDAO companyCouponDBDAO = new CompanyCouponDBDAO();

	public CompanyCoupon selectCompanyCoupon(long custId, long couponId) throws Exception {
		return companyCouponDBDAO.selectCompanyCoupon(custId, couponId);
	}
	public Set<Long> selectCompanyCoupons() throws Exception {
		return companyCouponDBDAO.selectCompanyCoupons();
	}
	

}
