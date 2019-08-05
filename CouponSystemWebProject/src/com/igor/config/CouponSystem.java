package com.igor.config;

import com.igor.facade.AdminFacade;
import com.igor.dao.ClientDAO;
import com.igor.dao.CompanyDAO;
import com.igor.dao.CustomerDAO;
import com.igor.dbdao.CompanyDBDAO;
import com.igor.dbdao.CustomerDBDAO;
import com.igor.enums.ClientType;
import com.igor.facade.CompanyFacade;

import java.util.Iterator;
import java.util.Set;

import com.igor.beans.Company;
import com.igor.beans.Customer;
import com.igor.config.ConnectionPool;
import com.igor.config.Database;
import com.igor.facade.CustomerFacade;
import com.igor.exceptions.ShutdownException;
import com.igor.exceptions.StartingException;

public class CouponSystem {
	
	private static CouponSystem instance = new CouponSystem();
	
	private ConnectionPool connectionPool;
	
	private CompanyDAO companyDaoDBDAO = new CompanyDBDAO();
	private CustomerDAO customerDaoDBDAO = new CustomerDBDAO();
	
	
	Thread t1 = new Thread(new RemoveExpCoupons()); 
	
	private boolean isTrue = true;
	
	
	private CouponSystem() {
		System.out.println("WELCOME TO COUPON SYSTEM!");
		try {
			Database.buildDB();
		} catch (Exception e) {
			System.out.println("Data Base already exists");
		}
	}
	
	public static CouponSystem getInstance() throws Exception {
		return instance;
	}
	
	
	
	public ClientDAO login(String name, String password, ClientType type) throws Exception {

		try {
			switch (type) {
			case ADMINISTRATOR:
				if (name.equals("admin") && password.equals("1234")) {
					AdminFacade adminFacade = new AdminFacade();
					System.out.println("Admin logged in to system");
					return adminFacade;
				} else {
					throw new Exception("Invalid login for Admin user: " + name + password + type + ". Please try again.");
				}
			
				
			case COMPANY:
				Set<Company> companies = companyDaoDBDAO.selectAllCompanies();
				Iterator<Company> iterator = companies.iterator();
				while (iterator.hasNext()) {
					Company current = iterator.next();
					if (current.getCompName().equals(name) && current.getPassword().equals(password)) {
						CompanyFacade companyFacade = new CompanyFacade(current);
						System.out.println("Company: " + current.getCompName() + " logged in to system");
						return companyFacade;
					} else if (!iterator.hasNext()) {
						throw new Exception("Invalid login for Company user: " + name + password + type + ". Please try again.");
					}
				}
				
				
			case CUSTOMER:
				Set<Customer> customers = customerDaoDBDAO.selectAllCustomers();
				Iterator<Customer> iterator2 = customers.iterator();
				while (iterator2.hasNext()) {
					Customer current = iterator2.next();
					if (current.getCustName().equals(name) && current.getPassword().equals(password)) {
						CustomerFacade customerFacade = new CustomerFacade(current);
						System.out.println("Customer: " + current.getCustName() + " logged in to system");
						return customerFacade;
					} else if (!iterator2.hasNext()) {
						throw new Exception("Invalid login for Customer user: " + name + password + type + ". Please try again.");
					}
				}
				
				

			default:
				throw new Exception("Wrong Type. Must be " + ClientType.values());

			}

		
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Login failed " + e.getMessage());
		}
		return null;
		
	
	}
	
	
	
//	public ClientDAO login(String name, String password, ClientType type) throws Exception {
//
//		try {
//			switch (type) {
//			case ADMINISTRATOR:
//				AdminFacade adminFacade = new AdminFacade();
//				return adminFacade.login(name, password, type);
//
//				
//			case COMPANY:
//				CompanyFacade companyFacade = new CompanyFacade();
//				return companyFacade.login(name, password, type);
//
//				
//			case CUSTOMER:
//				CustomerFacade customerFacade = new CustomerFacade();
//				return customerFacade.login(name, password, type);
//
//			default:
//				throw new Exception("Wrong Type. Must be " + ClientType.values());
//
//			}
//
//		
//		} catch (Exception e) {
//			e.printStackTrace();
//			System.out.println("Login failed " + e.getMessage());
//		}
//		return null;
//		
//	
//	}
	
	
	public void starting() throws StartingException {
		try {
			t1.start();
			System.out.println("CouponSystem started");
		} catch (Exception e) {
			System.out.println("Starting failed!");
			throw new StartingException();
		}
		
	}
	
	
	public void shutdown() throws ShutdownException {
		try {
			if (isTrue) {
				connectionPool = ConnectionPool.getInstance();
				connectionPool.closeAllConnections();
				t1.interrupt();
				System.out.println("CouponSystem shutdown...");
			}
		} catch (Exception e) {
			System.out.println("Shutdown failed!");
			throw new ShutdownException();
		}
		
	}

}
