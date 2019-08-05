package com.igor.dao;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Set;

import com.igor.beans.Customer;

import com.igor.enums.ClientType;
import com.igor.beans.CompanyCoupon;
import com.igor.beans.Coupon;
import com.igor.enums.CouponType;
import com.igor.beans.CustomerCoupon;
import com.igor.exceptions.AddCustomerCouponException;
import com.igor.exceptions.ConnectionException;
import com.igor.exceptions.CustomerLoginException;
import com.igor.exceptions.DeleteAllCustomersException;
import com.igor.exceptions.DeleteCustomerCouponException;
import com.igor.exceptions.DeleteCustomerException;
import com.igor.exceptions.InsertCustomerException;
import com.igor.exceptions.PurchaseCouponException;
import com.igor.exceptions.SelectAllCustomersException;
import com.igor.exceptions.SelectCustomerCouponsException;
import com.igor.exceptions.SelectCustomerException;
import com.igor.exceptions.UpdateCustomerException;

public interface CustomerDAO {
	
	public void insertCustomer(Customer customer) throws ConnectionException, InsertCustomerException, SQLException, Exception;
	public void deleteCustomer(Customer customer) throws ConnectionException, DeleteCustomerException, SQLException, Exception;
	public void updateCustomer(Customer customer) throws ConnectionException, UpdateCustomerException, 
				SQLException, Exception;
	public Customer selectCustomer(long id) throws ConnectionException, SelectCustomerException, SQLException, Exception;
	public Set<Customer>selectAllCustomers() throws ConnectionException, SelectAllCustomersException, SQLException, Exception;
	public void addCoupon(long custId, long couponId) throws ConnectionException, AddCustomerCouponException, SQLException, Exception;
	public void deleteCoupon(long custId, long couponId) throws ConnectionException, DeleteCustomerCouponException, SQLException, Exception;

	public Collection<Coupon>selectCoupons(long id) throws ConnectionException, SelectCustomerCouponsException, SQLException, Exception;
	public boolean login(String custName, String password, ClientType type) throws ConnectionException, CustomerLoginException, 
				SQLException, Exception;
	
	public void purchaseCoupon(long coupId, long customerId) throws ConnectionException, 
				PurchaseCouponException, SQLException, Exception;

	
	public void deleteAllCustomers() throws ConnectionException, DeleteAllCustomersException, SQLException, Exception;
}
