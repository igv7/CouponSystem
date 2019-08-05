package com.igor.config;

import com.igor.dbdao.CouponDBDAO;

public class RemoveExpCoupons implements Runnable {
	
	CouponDBDAO couponDBDAO = new CouponDBDAO();
	
	private boolean isTrue = true;
	
	
	
	public RemoveExpCoupons() {
		
	}

	@Override
	public void run() {
		while (isTrue) {
			try {
				couponDBDAO.removeExpCoupons();
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Task Error !");
			}
		}
		
		
	}

}
