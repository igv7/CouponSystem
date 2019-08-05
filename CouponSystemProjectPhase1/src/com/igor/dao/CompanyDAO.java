package com.igor.dao;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Set;

import com.igor.beans.Company;

import com.igor.enums.ClientType;
import com.igor.beans.CompanyCoupon;
import com.igor.beans.Coupon;
import com.igor.exceptions.AddCompanyCouponExceprion;
import com.igor.exceptions.CompanyLoginException;
import com.igor.exceptions.ConnectionException;
import com.igor.exceptions.DeleteAllCompaniesException;
import com.igor.exceptions.DeleteCompanyCouponException;
import com.igor.exceptions.DeleteCompanyException;
import com.igor.exceptions.InsertCompanyException;
import com.igor.exceptions.SelectAllCompaniesException;
import com.igor.exceptions.SelectCompanyException;
import com.igor.exceptions.SelectCouponsException;
import com.igor.exceptions.UpdateCompanyException;

public interface CompanyDAO {
	
	public void insertCompany(Company company) throws ConnectionException, InsertCompanyException, SQLException, Exception;
	public void deleteCompany(Company company) throws ConnectionException, DeleteCompanyException, SQLException, Exception;
	public void updateCompany(Company company) throws SQLException, ConnectionException, 
				UpdateCompanyException, Exception;
	public Company selectCompany(long id) throws ConnectionException, SelectCompanyException, SQLException, Exception;
	public Set<Company>selectAllCompanies() throws SQLException, ConnectionException, SelectAllCompaniesException, Exception;
	public void addCoupon(Company company, Coupon coupon) throws ConnectionException, AddCompanyCouponExceprion, Exception;
	public void deleteCoupon(long compId, long couponId) throws ConnectionException, DeleteCompanyCouponException, Exception;
	public Collection<Coupon>selectCoupons(long id) throws ConnectionException, SelectCouponsException, SQLException, Exception;
	public boolean login(String compName, String password, ClientType type) throws ConnectionException, CompanyLoginException, 
				SQLException, Exception;
	
	public void deleteAllCompanies() throws ConnectionException, DeleteAllCompaniesException, SQLException, Exception;
}
