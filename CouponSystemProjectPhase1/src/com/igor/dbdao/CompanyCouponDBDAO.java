package com.igor.dbdao;


import java.sql.Connection;
import java.sql.DriverManager;
//import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import com.igor.dao.CompanyCouponDAO;

import com.igor.config.ConnectionPool;
import com.igor.config.Database;
import com.igor.beans.CompanyCoupon;
import com.igor.beans.CustomerCoupon;
import com.igor.exceptions.ConnectionException;
import com.igor.exceptions.SelectCompanyCouponException;
import com.igor.exceptions.SelectCompanyCouponsException;



public class CompanyCouponDBDAO implements CompanyCouponDAO {

private static ConnectionPool connectionPool;




	@Override
	public Set<Long> selectCompanyCoupons() throws ConnectionException, SelectCompanyCouponsException, SQLException, Exception {
		connectionPool = ConnectionPool.getInstance();
		Connection con = connectionPool.getConnection();

		Set<Long> set = new HashSet<>();
		String sql = "select * from Company_Coupon";
		try {
			Statement statement = con.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);
		while (resultSet.next()) {
				long compId = resultSet.getLong(1);
				long couponId = resultSet.getLong(2);

			
				set.add(couponId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Cannot get CompanyCoupon data");
			throw new SelectCompanyCouponsException();
	} finally {
			con.close();
			connectionPool.returnConnection(con);
		}
		return set;
	}




	@Override
	public CompanyCoupon selectCompanyCoupon(long compId, long couponId) throws ConnectionException, SelectCompanyCouponException, 
				SQLException, Exception {
		connectionPool = ConnectionPool.getInstance();
		Connection con = connectionPool.getConnection();
		CompanyCoupon companyCoupon = new CompanyCoupon();

		try (Statement stmt = con.createStatement()) {
			String sql = "SELECT * FROM Company_Coupon WHERE comp_id = " + compId;
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			companyCoupon.setCompId(rs.getLong(1));
			companyCoupon.setCouponId(rs.getLong(2));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Statement error! Unable to get Coupon data");
			throw new SelectCompanyCouponException();
		} finally {
			con.close();
			connectionPool.returnConnection(con);
		}
		return companyCoupon;
	}
	
	
	
	@Override
	public CompanyCoupon selectCompanyCouponByCouponId(long couponId) throws Exception {
		
		connectionPool = ConnectionPool.getInstance();
		Connection con = connectionPool.getConnection();
		CompanyCoupon companyCoupon = new CompanyCoupon();

		try (Statement stmt = con.createStatement()) {
			String sql = "SELECT * FROM Company_Coupon WHERE coupon_id = " + couponId;
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			companyCoupon.setCompId(rs.getLong(1));
			companyCoupon.setCouponId(rs.getLong(2));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Statement error! Unable to get Coupon data");
			throw new SelectCompanyCouponException();
		} finally {
			con.close();
			connectionPool.returnConnection(con);
		}
		return companyCoupon;
	}




	@Override
	public Set<Long> selectCompanyCouponsByIdCompany(long id) throws ConnectionException, SQLException, Exception {
		connectionPool = ConnectionPool.getInstance();
		Connection con = connectionPool.getConnection();
		Set<Long> coupons = new HashSet<>();
		String sql = "select * from Company_Coupon where comp_id = " + id;
		try {
			Statement statement = con.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				long compId = resultSet.getLong(1);
				long couponId = resultSet.getLong(2);
				coupons.add(couponId);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Cannot get Company Coupons by Compant id");
			throw new Exception("Cannot get Company Coupons by Compant id");
		} finally {
			con.close();
			connectionPool.returnConnection(con);
		}
		return coupons;
	}


}
