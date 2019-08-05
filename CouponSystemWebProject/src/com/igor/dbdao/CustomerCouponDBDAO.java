package com.igor.dbdao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.igor.beans.CustomerCoupon;
import com.igor.dao.CustomerCouponDAO;

import com.igor.config.ConnectionPool;
import com.igor.config.Database;
import com.igor.beans.Coupon;
import com.igor.dbdao.CouponDBDAO;
import com.igor.enums.CouponType;
import com.igor.exceptions.ConnectionException;
import com.igor.exceptions.SelectAllCustomerCouponsException;
import com.igor.exceptions.SelectCustomerCouponException;

public class CustomerCouponDBDAO implements CustomerCouponDAO {
	CouponDBDAO couponDBDAO = new CouponDBDAO();

	private static ConnectionPool connectionPool;

	@Override
	public CustomerCoupon selectCustomerCoupon(long custId, long couponId) throws ConnectionException, SelectCustomerCouponException, 
				SQLException, Exception {
		connectionPool = ConnectionPool.getInstance();
		Connection con = connectionPool.getConnection();
		CustomerCoupon customerCoupon = new CustomerCoupon();

		try (Statement stmt = con.createStatement()) {
			String sql = "SELECT * FROM Customer_Coupon WHERE cust_id = " + custId;
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			customerCoupon.setCustId(rs.getLong(1));
			customerCoupon.setCouponId(rs.getLong(2));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Statement error! Unable to get Coupon data. " + e.getMessage());
			throw new SelectCustomerCouponException();
		} finally {
			con.close();
			connectionPool.returnConnection(con);
		}
		return customerCoupon;
	}

	@Override
	public Set<CustomerCoupon> selectAllCustomerCoupons() throws ConnectionException, SelectAllCustomerCouponsException, 
				SQLException, Exception {
		connectionPool = ConnectionPool.getInstance();
		Connection con = connectionPool.getConnection();

		Set<CustomerCoupon> set = new HashSet<>();
		String sql = "select * from Customer_Coupon";
		try {
			Statement statement = con.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			if (resultSet != null) {
				while (resultSet.next()) {
					Long custId = resultSet.getLong(1);
					Long couponId = resultSet.getLong(2);
					
					Coupon coupon = couponDBDAO.selectCoupon(resultSet.getLong(1));
					if (coupon.getId() != 0 && coupon.getEndDate().after(Date.valueOf(LocalDate.now()))) {
						set.add(new CustomerCoupon(custId, couponId));
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Cannot get CustomerCoupon data. " + e.getMessage());
			throw new SelectAllCustomerCouponsException();
		} finally {
			con.close();
			connectionPool.returnConnection(con);
		}
		return set;
	}

	@Override
	public Set<Long> selectAllCustomersByIdCoupon(long couponId) throws ConnectionException, SQLException, Exception {
		connectionPool = ConnectionPool.getInstance();
		Connection con = connectionPool.getConnection();
		Set<Long> set = new HashSet<>();
		try {
			String sql = "select * from Customer_Coupon where coupon_id = " + couponId;
			Statement statement = con.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				long custId = resultSet.getLong(1);
//				long cId = resultSet.getInt(2);
				set.add(custId);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("unable to get Customer_Coupon data. couponId: " + couponId + e.getMessage());
			throw new Exception("unable to get Customer_Coupon data. couponId: " + couponId);
		}
		finally {
			con.close();
			connectionPool.returnConnection(con);
		}
		return set;
		
	}

	@Override
	public Set<Long> selectAllCustomerCoupons(long id) throws ConnectionException, SQLException, Exception {
		connectionPool = ConnectionPool.getInstance();
		Connection con = connectionPool.getConnection();
		Set<Long> set = new HashSet<>();
		try {
			String sql = "select * from Customer_Coupon where cust_id = " + id;
			Statement statement = con.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				long custId = resultSet.getLong(1);
//				long cId = resultSet.getInt(2);
				set.add(custId);
			}
		} catch (SQLException e) {
			throw new Exception("unable to get Customer_Coupon data. customer Id: " + id);
		}
		finally {
			con.close();
			connectionPool.returnConnection(con);
		}
		return set;
	}


}
