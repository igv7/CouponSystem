package com.igor.facade;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Set;

import com.igor.beans.Coupon;
import com.igor.dbdao.CouponDBDAO;
import com.igor.enums.CouponType;
import com.igor.exceptions.ConnectionException;
import com.igor.exceptions.DeleteAllCouponsException;
import com.igor.exceptions.DeleteCouponException;
import com.igor.exceptions.GetCouponByTypeException;
import com.igor.exceptions.InsertCouponException;
import com.igor.exceptions.SelectAllCouponsException;
import com.igor.exceptions.SelectCouponException;
import com.igor.exceptions.UpdateCouponException;

public class CouponFacade {
	
	private CouponDBDAO couponDBDAO = new CouponDBDAO();
	
	public CouponFacade() {
		
	}
	
//	public void insertCoupon(Coupon coupon) throws ConnectionException, InsertCouponException, SQLException, Exception {
//		couponDBDAO.insertCoupon(coupon);
//	}
//	
//	public void deleteCoupon(Coupon coupon) throws ConnectionException, DeleteCouponException, SQLException, Exception {
//		couponDBDAO.deleteCoupon(coupon);
//	}
//	
//	public void updateCoupon(Coupon coupon, String title, Date startDate, Date endDate, int amount, CouponType type, String message,
//			Double price, String image) throws ConnectionException, UpdateCouponException, SQLException, Exception {
//		coupon.setTitle(title);
//		coupon.setStartDate(startDate);
//		coupon.setEndDate(endDate);
//		coupon.setAmount(amount);
//		coupon.setType(type);
//		coupon.setMessage(message);
//		coupon.setPrice(price);
//		coupon.setImage(image);
//		couponDBDAO.updateCoupon(coupon, title, startDate, endDate, amount, type, message, price, image);
//	}
//	
//	public Coupon selectCoupon(long id) throws ConnectionException, SelectCouponException, SQLException, Exception {
//		return couponDBDAO.selectCoupon(id);
//	}
	
	public Set<Coupon> selectAllCoupons() throws ConnectionException, SelectAllCouponsException, SQLException, Exception {
		return couponDBDAO.selectAllCoupons();
	}
	
//	public Set<Coupon> getCouponByType(CouponType type) throws ConnectionException, GetCouponByTypeException, SQLException, Exception {
//		return couponDBDAO.getCouponByType(type);
//	}
//	
//	public void deleteAllCoupons(Coupon coupon) throws ConnectionException, DeleteAllCouponsException, SQLException, Exception {
//		couponDBDAO.deleteAllCoupons(coupon);
//	}


}
