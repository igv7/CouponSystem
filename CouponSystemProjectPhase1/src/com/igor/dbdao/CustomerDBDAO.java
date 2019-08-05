package com.igor.dbdao;
import java.net.ConnectException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.igor.dao.CustomerDAO;

import com.igor.enums.ClientType;
import com.igor.beans.Company;
import com.igor.dbdao.CompanyDBDAO;
import com.igor.beans.CompanyCoupon;
import com.igor.config.ConnectionPool;
import com.igor.config.Database;
import com.igor.beans.Coupon;
import com.igor.beans.Customer;
import com.igor.dbdao.CouponDBDAO;
import com.igor.enums.CouponType;
import com.igor.beans.CustomerCoupon;
import com.igor.dbdao.CustomerCouponDBDAO;
import com.igor.exceptions.AddCustomerCouponException;
import com.igor.exceptions.ConnectionException;
import com.igor.exceptions.CustomerLoginException;
import com.igor.exceptions.DeleteAllCompaniesException;
import com.igor.exceptions.DeleteAllCustomersException;
import com.igor.exceptions.DeleteCustomerCouponException;
import com.igor.exceptions.DeleteCustomerException;
import com.igor.exceptions.InsertCustomerException;
import com.igor.exceptions.PurchaseCouponException;
import com.igor.exceptions.SelectAllCustomersException;
import com.igor.exceptions.SelectCustomerCouponsException;
import com.igor.exceptions.SelectCustomerException;
import com.igor.exceptions.UpdateCustomerException;


public class CustomerDBDAO implements CustomerDAO {
	
	private static ConnectionPool connectionPool;
	
	CouponDBDAO couponDBDAO = new CouponDBDAO();
	CustomerCouponDBDAO customerCouponDBDAO = new CustomerCouponDBDAO();
	CustomerCoupon customerCoupon = new CustomerCoupon();
	

    private long loginId = 0;
	
	public long getLoginId() {
		return loginId;
	}

	private void setLoginId(long loginId) {
		this.loginId = loginId;
		
	}
	
	
	
    private long loggedCustomer = 0;
	
	public void setLoggedCustomer(long custId) {
		this.loggedCustomer = custId;
	}

	public long getLoggedCustomer() {
		return loggedCustomer;
	}

	@Override
	public void insertCustomer(Customer customer) throws ConnectionException, InsertCustomerException, SQLException, Exception {
		connectionPool = ConnectionPool.getInstance();
		Connection con = connectionPool.getConnection();

		String sql = "INSERT INTO Customer (name, password) VALUES (?,?)";
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1, customer.getCustName());
			pstmt.setString(2, customer.getPassword());
			pstmt.executeUpdate();
			System.out.println("The Customer successfully added " + customer.toString());
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Customer creation failed! " + e.getMessage());
			throw new InsertCustomerException();
		}finally {
			con.close();
			connectionPool.returnConnection(con);
		}
		
		
	}

	@Override
	public void deleteCustomer(Customer customer) throws ConnectionException, DeleteCustomerException, SQLException, Exception {
		connectionPool = ConnectionPool.getInstance();
		Connection con = connectionPool.getConnection();

		String sql = "DELETE FROM Customer_Coupon WHERE cust_id = " + customer.getId();
		try {
			Statement statement = con.createStatement();
			con.setAutoCommit(false);//
			statement.executeUpdate(sql);
		} catch (SQLException e) {
			System.out.println(e);
			throw new Exception("failed to remove Customer_Coupon");
		}
		sql = "DELETE FROM Customer WHERE id = ?";
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
//			con.setAutoCommit(false);
			pstmt.setLong(1, customer.getId());
			pstmt.executeUpdate();
			con.commit();
			con.setAutoCommit(true);
			System.out.println("The Customer successfully deleted " + customer.toString());
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("failed to remove Customer " + e.getMessage());
			throw new DeleteCustomerException();
		}
		try {
			con.rollback();
		} catch (SQLException e) {
			throw new Exception("Database error");
		}
		finally {
			con.close();
			connectionPool.returnConnection(con);
		}
		
		
	}

	@Override
	public void updateCustomer(Customer customer) throws ConnectionException, UpdateCustomerException, 
				SQLException, Exception {
		connectionPool = ConnectionPool.getInstance();
		Connection con = connectionPool.getConnection();

		try {
			Statement stmt = con.createStatement();
			String sql ="UPDATE Customer SET name = '" + customer.getCustName() + "', password = '" + customer.getPassword() 
			+ "' WHERE id = " + customer.getId();
			stmt.executeUpdate(sql);
			System.out.println("Customer updated " + customer.toString());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Failed to update Customer! " + e.getMessage());
			throw new UpdateCustomerException();
		} finally {
			con.close();
			connectionPool.returnConnection(con);
		}
		
		
	}

	@Override
	public Customer selectCustomer(long id) throws ConnectionException, SelectCustomerException, SQLException, Exception {
		connectionPool = ConnectionPool.getInstance();
		Connection con = connectionPool.getConnection();
		Customer customer = new Customer();

		try (Statement stmt = con.createStatement()) {
			String sql = "SELECT * FROM Customer WHERE id = " + id;
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			customer.setId(rs.getLong(1));
			customer.setCustName(rs.getString(2));
			customer.setPassword(rs.getString(3));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Statement error! Unable to get Customer data " + e.getMessage());
			throw new SelectCustomerException();
		} finally {
			con.close();
			connectionPool.returnConnection(con);
		}
		return customer;
	}

	@Override
	public synchronized Set<Customer> selectAllCustomers() throws ConnectionException, SelectAllCustomersException, 
				SQLException, Exception {
		connectionPool = ConnectionPool.getInstance();
		Connection con = connectionPool.getConnection();

		Set<Customer>set = new HashSet<>();
		String sql = "SELECT * FROM Customer";
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Long id = rs.getLong(1);
				String custName = rs.getString(2);
				String password = rs.getString(3);
				
				set.add(new Customer(id, custName, password));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Cannot get AllCustomers data " + e.getMessage());
			throw new SelectAllCustomersException();
		} finally {
			con.close();
			connectionPool.returnConnection(con);
		}
		return set;
	}
	
	@Override
	public void addCoupon(long custId, long couponId) throws ConnectionException, AddCustomerCouponException, SQLException, Exception {
		connectionPool = ConnectionPool.getInstance();
		Connection con = connectionPool.getConnection();

		String sql = "insert into Customer_Coupon (cust_id, coupon_id) values (?, ?)";
		try  {
			PreparedStatement pstmt = con.prepareStatement(sql); 
			pstmt.setLong(1, custId);
			pstmt.setLong(2, couponId);
			pstmt.executeUpdate();
			System.out.println("Coupon id " + couponId + " was appended to customer id " + custId);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Could not append the coupon to the customer " + e.getMessage());
			throw new AddCustomerCouponException();
		} finally {
			con.close();
			connectionPool.returnConnection(con);
		}
		
	}
	
	@Override
	public void deleteCoupon(long custId, long couponId) throws ConnectionException, DeleteCustomerCouponException, SQLException, Exception {
		connectionPool = ConnectionPool.getInstance();
		Connection con = connectionPool.getConnection();

		String sql = "delete from Customer_Coupon where Cust_id = "+ custId + " and Coupon_id = "+ couponId; 
		try  {
			Statement stmt = con.createStatement(); 
			stmt.executeUpdate(sql);
			System.out.println("Coupon id " + couponId + " was deleted from customer id " + custId);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Could not delete the coupon from the Customer_Coupon. " + e.getMessage());
			throw new DeleteCustomerCouponException();
		} finally {
			con.close();
			connectionPool.returnConnection(con);
		}
		
	}
	
	@Override
	public Collection<Coupon> selectCoupons(long id) throws ConnectionException, SelectCustomerCouponsException, SQLException, Exception {
		connectionPool = ConnectionPool.getInstance();
		Connection con = connectionPool.getConnection();

		Collection<Coupon> customerCoupons = new ArrayList<>();
		Collection<Long> coupons = new ArrayList<>();
//		System.out.println("id="+id);
		String sql = "SELECT * FROM Customer_Coupon WHERE cust_id = " + id;
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				long custId = rs.getLong(1);
				long couponId = rs.getLong(2);
				coupons.add(couponId);
			}
			for (long couponId : coupons) {
				sql = "select * from Coupon where id = " + couponId;
				Statement statement = con.createStatement();
				ResultSet resultSet = statement.executeQuery(sql);
				if (resultSet != null) {
					while (resultSet.next()) {
						long idC = resultSet.getLong(1);
						String title = resultSet.getString(2);
						Date startDate = resultSet.getDate(3);
						Date endDate = resultSet.getDate(4);
						Integer amount = resultSet.getInt(5);
						CouponType type = (CouponType.valueOf(resultSet.getString(6)));
						String message = resultSet.getString(7);
						Double price = resultSet.getDouble(8);
						String image = resultSet.getString(9);
						
						customerCoupons.add(new Coupon(idC, title, startDate, endDate, amount, type, message, price, image));
					}
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("Cannot get Customer_Coupon data " + e.getMessage()); 
			throw new SelectCustomerCouponsException();
		}finally {
			con.close();
			connectionPool.returnConnection(con);
		}
		return customerCoupons;
	}

	@Override
	public boolean login(String custName, String password, ClientType type) throws ConnectionException, CustomerLoginException, 
				SQLException, Exception {
		connectionPool = ConnectionPool.getInstance();
		Connection con = connectionPool.getConnection();
		String dataBasePassword = null;
		Customer customer = new Customer();

		String sql = "select * from Customer where name = ?";
		if (type == ClientType.CUSTOMER) {
			try {
				PreparedStatement pstmt = con.prepareStatement(sql);
				pstmt.setString(1, custName);
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					dataBasePassword = rs.getString("password");
					if (password.equals(dataBasePassword)) {
						setLoginId(rs.getLong("id"));
						System.out.println("Login success! The customer ID is: " + loginId + " the customer name is: " + custName);
						setLoginId(loginId);
						setLoggedCustomer(customer.getId());
						return true;
					}
				}
			} catch (Exception e) {
				System.out.println("Could not Login with this Customer! " + custName);
				throw new CustomerLoginException();
			} finally {
				con.close();
				connectionPool.returnConnection(con);
			}
		}
		
		return false;
	}
	
	
//	@Override
//	public void purchaseCoupon(Coupon coupon, Customer customer) throws ConnectionException, 
//				PurchaseCouponException, SQLException, Exception {
//		connectionPool = ConnectionPool.getInstance();
//		Connection con = connectionPool.getConnection();
//		CustomerDBDAO customerDBDAO = new CustomerDBDAO();

//		Coupon thisCoupon = new Coupon();
//		Collection<CustomerCoupon> coupons = new ArrayList<>();
//		String sql = "select * from Customer_Coupon WHERE cust_id = ? and coupon_id = ?";
//			PreparedStatement ps = con.prepareStatement(sql);
//			ps.setLong(1, customer.getId());
//			ps.setLong(2, coupon.getId());
//			ResultSet rs = ps.executeQuery();
//			System.out.println(String.valueOf(customer.getId()));
//			System.out.println(String.valueOf(coupon.getId()));
//			System.out.println(String.valueOf(this.customerCoupon.getCustId()));
//			System.out.println(String.valueOf(customer.getId()));
//			System.out.println(String.valueOf(this.customerCoupon.getCouponId()));
//			System.out.println(String.valueOf(thisCoupon.getId()));
//			if (rs != null && this.customerCoupon.getCustId() == customer.getId() && this.customerCoupon.getCouponId() == thisCoupon.getId()) {
//				System.out.println("This coupon already exists. Cannot buy it twice.");
//				throw new PurchaseCouponException();
//			} else if (coupon.getEndDate().after(Date.valueOf(LocalDate.now()))) {
//			try {
//				thisCoupon = couponDBDAO.selectCoupon(coupon.getId());
//				Collection<Coupon> customerCoupons = new ArrayList<>();
//				customerCoupons = couponDBDAO.selectAllCoupons();//(getLoginId());
//				con.setAutoCommit(false);
//				if (thisCoupon.getAmount() > 0) {
//					thisCoupon.setAmount(thisCoupon.getAmount() - 1);
//					System.out.println(111);
//					couponDBDAO.updateCoupon(thisCoupon);
//					System.out.println(222);
//					customerDBDAO.addCoupon(customer, thisCoupon);
//					System.out.println(333);
//					System.out.println("Coupon  name: " + thisCoupon.getTitle() + " id: " + thisCoupon.getId() + " was parchused.");
//			        System.out.println("Expires at: " + thisCoupon.getEndDate().toString());
//				}
//				con.commit();
//				con.setAutoCommit(true);
//			} catch (Exception e) {
//				e.printStackTrace();
////				throw new Exception("Could not buy the coupon, please check your information");
//				con.rollback();
//				throw new PurchaseCouponException();
//			} finally {
//				con.close();
//				connectionPool.returnConnection(con);
//			}
//		}else {
//			System.out.println("Could not buy coupons, it is removed , or Invalid for parchuse");
//		} 
//		
//	}
	
	@Override
	public void purchaseCoupon(long coupId, long customerId) throws ConnectionException, 
				PurchaseCouponException, SQLException, Exception {
		connectionPool = ConnectionPool.getInstance();
		Connection con = connectionPool.getConnection();
		CustomerDBDAO customerDBDAO = new CustomerDBDAO();

		Coupon thisCoupon = couponDBDAO.selectCoupon(coupId);
		Collection<CustomerCoupon> coupons = new ArrayList<>();
		String sql = "select * from Customer_Coupon WHERE cust_id = ? and coupon_id = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setLong(1, customerId);
			ps.setLong(2, coupId);
			ResultSet rs = ps.executeQuery();
			if (rs != null && this.customerCoupon.getCustId() == customerId && this.customerCoupon.getCouponId() == coupId) {
				System.out.println("This coupon already exists. Cannot buy it twice.");
				throw new PurchaseCouponException();
			} else if (thisCoupon.getEndDate().after(Date.valueOf(LocalDate.now()))) {
			try {
				thisCoupon = couponDBDAO.selectCoupon(coupId);
				Collection<Coupon> customerCoupons = new ArrayList<>();
				customerCoupons = couponDBDAO.selectAllCoupons();//(getLoginId());
				con.setAutoCommit(false);
				if (thisCoupon.getAmount() > 0) {
					thisCoupon.setAmount(thisCoupon.getAmount() - 1);
					couponDBDAO.updateCoupon(thisCoupon);
					customerDBDAO.addCoupon(customerId, thisCoupon.getId());
					System.out.println("Coupon  name: " + thisCoupon.getTitle() + " id: " + thisCoupon.getId() + " was parchused.");
			        System.out.println("Expires at: " + thisCoupon.getEndDate().toString());
				}
				con.commit();
				con.setAutoCommit(true);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Could not buy the coupon, please check your information. " + e.getMessage());
				con.rollback();
				throw new PurchaseCouponException();
			} finally {
				con.close();
				connectionPool.returnConnection(con);
			}
		}else {
			System.out.println("Could not buy coupons, it is removed , or Invalid for parchuse");
		} 
		
	}
	
	
	

	@Override
	public void deleteAllCustomers() throws ConnectionException, DeleteAllCustomersException, SQLException, Exception {
		connectionPool = ConnectionPool.getInstance();
		Connection con = connectionPool.getConnection();

		try {
			Set<Customer> customers = selectAllCustomers();
			Iterator<Customer> iterator = customers.iterator();
			while (iterator.hasNext()) {
				Customer current = iterator.next();
				if (current.getId() > 0) {
					for (Customer customer : customers) {
						deleteCustomer(customer);
					}

				}
				
			}
			System.out.println("All Customers deleted");
		} catch (Exception e) {
			throw new DeleteAllCustomersException();
		} finally {
			con.close();
			connectionPool.returnConnection(con);
		}
	}

	

}
