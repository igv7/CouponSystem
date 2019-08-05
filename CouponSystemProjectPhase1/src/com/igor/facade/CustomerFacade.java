package com.igor.facade;

import java.awt.List;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.lang.model.type.PrimitiveType;

import com.igor.dbdao.CustomerDBDAO;

import com.igor.dao.ClientDAO;
import com.igor.enums.ClientType;
import com.igor.beans.Coupon;
import com.igor.beans.Customer;
import com.igor.dbdao.CouponDBDAO;
import com.igor.enums.CouponType;
import com.igor.beans.CustomerCoupon;
import com.igor.dbdao.CustomerCouponDBDAO;
import com.igor.exceptions.AddCustomerCouponException;
import com.igor.exceptions.ConnectionException;
import com.igor.exceptions.CustomerLoginException;
import com.igor.exceptions.DeleteAllCustomersException;
import com.igor.exceptions.DeleteCustomerCouponException;
import com.igor.exceptions.DeleteCustomerException;
import com.igor.exceptions.GetAllPurchasedCouponsByPriceException;
import com.igor.exceptions.GetAllPurchasedCouponsByTypeException;
import com.igor.exceptions.GetAllPurchasedCouponsException;
import com.igor.exceptions.InsertCustomerException;
import com.igor.exceptions.PurchaseCouponException;
import com.igor.exceptions.SelectAllCustomersException;
import com.igor.exceptions.SelectCustomerCouponsException;
import com.igor.exceptions.SelectCustomerException;
import com.igor.exceptions.UpdateCustomerException;

public class CustomerFacade implements ClientDAO {

	private Customer customer;
	private CustomerDBDAO customerDBDAO = new CustomerDBDAO();
	private CouponDBDAO couponDBDAO = new CouponDBDAO();
	private CustomerCouponDBDAO customerCouponDBDAO = new CustomerCouponDBDAO();
	private long loggedCustomer = 0;

	public CustomerFacade() {

	}

	public CustomerFacade(Customer customer) {
		this.customer = customer;
	}

	public long getLoggedCustomer() {
		return loggedCustomer;
	}

	public void setLoggedCustomer(long loggedCustomer) {
		this.loggedCustomer = loggedCustomer;
	}

	// public void insertCustomer(Customer customer) throws ConnectionException,
	// InsertCustomerException, SQLException, Exception {
	// customerDBDAO.insertCustomer(customer);
	// }
	//
	// public void deleteCustomer(Customer customer) throws ConnectionException,
	// DeleteCustomerException, SQLException, Exception {
	// customerDBDAO.deleteCustomer(customer);
	// }
	//
	// public void updateCustomer(Customer customer, String custName, String
	// password) throws ConnectionException, UpdateCustomerException,
	// SQLException, Exception {
	// customer.setCustName(custName);
	// customer.setPassword(password);
	// customerDBDAO.updateCustomer(customer, custName, password);
	// }
	//
	// public Customer selectCustomer(long id) throws ConnectionException,
	// SelectCustomerException, SQLException, Exception {
	// return customerDBDAO.selectCustomer(id);
	// }
	//
	// public Set<Customer> selectAllCustomers() throws ConnectionException,
	// SelectAllCustomersException, SQLException, Exception {
	// return customerDBDAO.selectAllCustomers();
	// }

	public void addCoupon(long custId, long couponId)
			throws ConnectionException, AddCustomerCouponException, SQLException, Exception {
		customerDBDAO.addCoupon(custId, couponId);
	}

	public void deleteCoupon(long custId, long couponId)
			throws ConnectionException, DeleteCustomerCouponException, SQLException, Exception {
		customerDBDAO.deleteCoupon(custId, couponId);
	}

	public Collection<Coupon> selectCoupons()
			throws ConnectionException, SelectCustomerCouponsException, SQLException, Exception {
		return customerDBDAO.selectCoupons(this.customer.getId());
	}

	@Override
	public ClientDAO login(String name, String password, ClientType type)
			throws ConnectionException, CustomerLoginException, SQLException, Exception {
		if (customerDBDAO.login(name, password, type)) {
			CustomerFacade customerFacade = new CustomerFacade(this.customer);
			customerFacade.setLoggedCustomer(customerDBDAO.getLoginId());
			System.out.println("Logged in as " + name);
			return customerFacade;
		} else
			System.out.println("Could not login with this Customer " + name + ". Try again or check you credentials");
		throw new CustomerLoginException();

	}

	public Coupon purchaseCoupon(long id)// void
			throws ConnectionException, PurchaseCouponException, SQLException, Exception {
		Coupon coupon = couponDBDAO.selectCoupon(id);
		try {
			Set<Coupon> coupons = couponDBDAO.selectAllCoupons();
			// System.out.println(coupons);//
			Iterator<Coupon> iterator = coupons.iterator();
			while (iterator.hasNext()) {
				Coupon current = iterator.next();
				if (current.getAmount() < 1) {
					System.out.println("failed to purchase coupon - wrong amount: 0, Coupon id: " + current.getId());
					throw new PurchaseCouponException();
				}
				if (current.getEndDate().before(Date.valueOf(LocalDate.now()))) {
					System.out.println("failed to purchase coupon - the end date already passed.");
					throw new PurchaseCouponException();
				}
				Set<Long> customers = customerCouponDBDAO.selectAllCustomersByIdCoupon(current.getId());
				Iterator<Long> iterator2 = customers.iterator();
				while (iterator2.hasNext()) {
					long current2 = iterator2.next();
					if (this.customer.getId() == current2 && id == current.getId()) {
						throw new Exception("Customer unable to purchase - already purchased same coupon. Coupon id: "
								+ current.getId() + ", Customer id: " + this.customer.getId());
					}
				}
			}
//			if (!iterator.hasNext()) {
				customerDBDAO.purchaseCoupon(id, this.customer.getId());
//			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new PurchaseCouponException();
		}
		return coupon;
	}

//	public void purchaseCoupon(long coupId, long customerId)
//			throws ConnectionException, PurchaseCouponException, SQLException, Exception {
//		try {
//			Set<Coupon> coupons = couponDBDAO.selectAllCoupons();
//			// System.out.println(coupons);//
//			Iterator<Coupon> iterator = coupons.iterator();
//			while (iterator.hasNext()) {
//				Coupon current = iterator.next();
//				if (current.getAmount() < 1) {
//					System.out.println("failed to purchase coupon - wrong amount: 0, Coupon id: " + current.getId());
//					throw new PurchaseCouponException();
//				}
//				if (current.getEndDate().before(Date.valueOf(LocalDate.now()))) {
//					System.out.println("failed to purchase coupon - the end date already passed.");
//					throw new PurchaseCouponException();
//				}
//				Set<Long> customers = customerCouponDBDAO.selectAllCustomersByIdCoupon(current.getId());
//				Iterator<Long> iterator2 = customers.iterator();
//				while (iterator2.hasNext()) {
//					long current2 = iterator2.next();
//					if (customerId == current2 && coupId == current.getId()) {
//						throw new Exception("Customer unable to purchase - already purchased same coupon. Coupon id: "
//								+ current.getId() + ", Customer id: " + customerId);
//					}
//				}
//			}
//			if (!iterator.hasNext()) {
//				customerDBDAO.purchaseCoupon(coupId, customerId);
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new PurchaseCouponException();
//		}
//
//	}

	public Collection<Coupon> getAllPurchasedCoupons()
			throws ConnectionException, GetAllPurchasedCouponsException, SQLException, Exception {
		try {
			Set<Long> coupons = customerCouponDBDAO.selectAllCustomerCoupons(this.customer.getId());
			Set<Coupon> purchasedCoupons = new HashSet<>();
			for (Long cId : coupons) {
				purchasedCoupons.addAll(couponDBDAO.getAllPurchasedCoupons(this.customer.getId()));
			}
			if (purchasedCoupons.isEmpty()) {
				throw new Exception("Coupons do not exist in system");
			}
			Collection<Coupon> getAllPurchasedCoup = purchasedCoupons;
			for (Coupon coupon : getAllPurchasedCoup) {
				System.out.println(coupon);
			}
			return purchasedCoupons;
		} catch (GetAllPurchasedCouponsException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Failed to get all purcased coupons for Customer id: " + this.customer.getId() + ". Coupons do not exist");
			throw new Exception("Failed to get all purcased coupons for Customer id: " + this.customer.getId() + ". Coupons do not exist");
		}
		// return couponDBDAO.getAllPurchasedCoupons(id);
		return null;
	}
	
	public Collection<Coupon> getAllPurchasedCouponsByType(CouponType type)
			throws ConnectionException, GetAllPurchasedCouponsByTypeException, SQLException, Exception {
		
		try {
			Collection<Coupon> coupons = couponDBDAO.getAllPurchasedCoupons(this.customer.getId());
//			System.out.println(coupons);
			Collection<Coupon> purchasedCoupons = new ArrayList<>();
			for (Coupon coupon : coupons) {
				if (coupon.getType().equals(type)) {
					purchasedCoupons.addAll(couponDBDAO.getCouponByType(coupon.getType()));
					System.out.println(purchasedCoupons);
				}
				
			}
			
			if (purchasedCoupons.isEmpty()) {
				throw new Exception("Coupons do not exist in system");
			}
			
			Collection<Coupon> getAllPurchasedCoupByType = purchasedCoupons;
			for (Coupon coupon : getAllPurchasedCoupByType) {
				System.out.println(coupon);
			}
			return purchasedCoupons;
		} catch (GetAllPurchasedCouponsByTypeException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println(
					"Failed to get all purcased coupons by type for Customer id: " + this.customer.getId() + ". Coupons do not exist");
			throw new Exception(
					"Failed to get all purcased coupons by type for Customer id: " + this.customer.getId() + ". Coupons do not exist");
		}
		
		return null;
	}

//	public Collection<Coupon> getAllPurchasedCouponsByType(CouponType type)
//			throws ConnectionException, GetAllPurchasedCouponsByTypeException, SQLException, Exception {
//		try {
//			Set<Long> coupons = customerCouponDBDAO.selectAllCustomerCoupons(this.customer.getId());
//			Set<Coupon> purchasedCoupons = new HashSet<>();
//			for (Long cId : coupons) {
//				purchasedCoupons.addAll(couponDBDAO.getAllPurchasedCouponsByType(type, cId)); // id //this.customer.getId()
//			}
//			if (purchasedCoupons.isEmpty()) {
//				throw new Exception("Coupons do not exist in system");
//			}
//			Collection<Coupon> getAllPurchasedCoup = purchasedCoupons;
//			for (Coupon coupon : getAllPurchasedCoup) {
//				System.out.println(coupon);
//			}
//			return purchasedCoupons;//getAllPurchasedCoup
//		} catch (GetAllPurchasedCouponsByTypeException e) {
//			System.out.println(e.getMessage());
//			e.printStackTrace();
//		} catch (Exception e) {
//			System.out.println(
//					"Failed to get all purcased coupons by type for Customer id: " + this.customer.getId() + ". Coupons do not exist");
//			throw new Exception(
//					"Failed to get all purcased coupons by type for Customer id: " + this.customer.getId() + ". Coupons do not exist");
//		}
//		// return couponDBDAO.getAllPurchasedCouponsByType(type, id);
//		return null;
//	}
	
	public Collection<Coupon> getAllPurchasedCouponsByPrice(double price)
			throws ConnectionException, GetAllPurchasedCouponsByPriceException, SQLException, Exception {
		try {
			Collection<Coupon> coupons = couponDBDAO.getAllPurchasedCoupons(this.customer.getId());
			Collection<Coupon> purchasedCoupons = new ArrayList<>();
			for (Coupon coupon : coupons) {
				if (coupon.getPrice() <= price ) {//|| coupon.getPrice() == price
					purchasedCoupons.add(couponDBDAO.selectCoupon(coupon.getId()));//couponDBDAO.getCouponByPrice(coupon.getPrice()
//					System.out.println(purchasedCoupons);
				}
				
			}
			if (purchasedCoupons.isEmpty()) {
				throw new Exception("Coupons do not exist in system");
			}
			Collection<Coupon> getAllPurchasedCoupByPrice = purchasedCoupons;
			for (Coupon coupon : getAllPurchasedCoupByPrice) {
				System.out.println(coupon);
			}
			return purchasedCoupons;
		} catch (GetAllPurchasedCouponsByPriceException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println(
					"Failed to get all purcased coupons by price for Customer id: " + this.customer.getId() + ". Coupons do not exist");
			throw new Exception(
					"Failed to get all purcased coupons by price for Customer id: " + this.customer.getId() + ". Coupons do not exist");
		}
		// return couponDBDAO.getAllPurchasedCouponsByPrice(price, id);
		return null;
	}

//	public Collection<Coupon> getAllPurchasedCouponsByPrice(double price, long id)
//			throws ConnectionException, GetAllPurchasedCouponsByPriceException, SQLException, Exception {
//		try {
//			Set<Long> coupons = customerCouponDBDAO.selectAllCustomerCoupons(id);
//			Set<Coupon> purchasedCoupons = new HashSet<>();
//			for (Long cId : coupons) {
//				purchasedCoupons.addAll(couponDBDAO.getAllPurchasedCouponsByPrice(price, cId)); // id
//			}
//			if (purchasedCoupons.isEmpty()) {
//				throw new Exception("Coupons do not exist in system");
//			}
//			Collection<Coupon> getAllPurchasedCoup = purchasedCoupons;
//			for (Coupon coupon : getAllPurchasedCoup) {
//				System.out.println(coupon);
//			}
//			return purchasedCoupons;
//		} catch (GetAllPurchasedCouponsByPriceException e) {
//			System.out.println(e.getMessage());
//			e.printStackTrace();
//		} catch (Exception e) {
//			System.out.println(
//					"Failed to get all purcased coupons by price for Customer id: " + id + ". Coupons do not exist");
//			throw new Exception(
//					"Failed to get all purcased coupons by price for Customer id: " + id + ". Coupons do not exist");
//		}
//		// return couponDBDAO.getAllPurchasedCouponsByPrice(price, id);
//		return null;
//	}

	public void deleteAllCustomers() throws ConnectionException, DeleteAllCustomersException, SQLException, Exception {
		customerDBDAO.deleteAllCustomers();
	}

}
