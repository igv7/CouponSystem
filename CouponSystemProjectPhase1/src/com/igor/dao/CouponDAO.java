package com.igor.dao;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import com.igor.beans.Company;
import com.igor.beans.CompanyCoupon;
import com.igor.beans.Coupon;
import com.igor.beans.CustomerCoupon;
import com.igor.enums.CouponType;
import com.igor.exceptions.ConnectionException;
import com.igor.exceptions.DeleteAllCouponsException;
import com.igor.exceptions.DeleteCouponException;
import com.igor.exceptions.GetAllPurchasedCouponsByPriceException;
import com.igor.exceptions.GetAllPurchasedCouponsByTypeException;
import com.igor.exceptions.GetAllPurchasedCouponsException;
import com.igor.exceptions.GetCouponByTypeException;
import com.igor.exceptions.InsertCouponException;
import com.igor.exceptions.RemoveExpCouponsException;
import com.igor.exceptions.SelectAllCouponsException;
import com.igor.exceptions.SelectCouponException;
import com.igor.exceptions.UpdateCouponException;

public interface CouponDAO {
	
	public void insertCoupon(Coupon coupon) throws ConnectionException, InsertCouponException, SQLException, Exception;
	public void deleteCoupon(Coupon coupon) throws ConnectionException, DeleteCouponException, SQLException, Exception;
	public void updateCoupon(Coupon coupon, String title, Date startDate, Date endDate, int amount, CouponType type, 
			String message, double price, String image) throws ConnectionException, UpdateCouponException, SQLException, Exception;
	public void updateCoupon(Coupon coupon) throws ConnectionException, UpdateCouponException, SQLException, Exception;
	public Coupon selectCoupon(long id) throws ConnectionException, SelectCouponException, SQLException, Exception;
	public Set<Coupon>selectAllCoupons() throws ConnectionException, SelectAllCouponsException, SQLException, Exception;
	public void removeExpCoupons() throws ConnectionException, RemoveExpCouponsException, SQLException, Exception;
	public Set<Coupon>getCouponByType(CouponType type) throws ConnectionException, GetCouponByTypeException, SQLException, Exception;
	public Set<Coupon> getCouponByPrice(double price) throws ConnectionException, SQLException, Exception;
	
	public void deleteAllCoupons() throws ConnectionException, DeleteAllCouponsException, SQLException, Exception;
	
	public Collection<Coupon>getAllPurchasedCoupons(long id) throws ConnectionException, GetAllPurchasedCouponsException, 
				SQLException, Exception;
	public Collection<Coupon>getAllPurchasedCouponsByType(CouponType type, long id) throws ConnectionException, 
				GetAllPurchasedCouponsByTypeException, SQLException, Exception;
	public Collection<Coupon>getAllPurchasedCouponsByPrice(double price, long id) throws ConnectionException,
				GetAllPurchasedCouponsByPriceException, SQLException, Exception;

}
