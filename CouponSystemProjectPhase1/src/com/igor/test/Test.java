package com.igor.test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.time.LocalDate;

import com.igor.facade.AdminFacade;
//import client.ClientSwitchCase;
import com.igor.enums.ClientType;

//import org.apache.derby.client.am.DateTime;

import com.igor.beans.Company;
import com.igor.dbdao.CompanyDBDAO;
//import company.CompanyCouponDBDAO;
//import company.CompanyCouponFacade;
import com.igor.facade.CompanyFacade;
import com.igor.beans.CompanyCoupon;
import com.igor.dbdao.CompanyCouponDBDAO;
import com.igor.config.ConnectionPool;
import com.igor.config.Database;
import com.igor.beans.Coupon;
import com.igor.dbdao.CouponDBDAO;
import com.igor.facade.CouponFacade;
import com.igor.config.CouponSystem;
import com.igor.enums.CouponType;
import com.igor.beans.Customer;
import com.igor.dbdao.CustomerDBDAO;
import com.igor.facade.CustomerFacade;
import com.igor.dbdao.CustomerCouponDBDAO;


public class Test {
	
	public static void main(String[] args) throws Exception {
		
		Class.forName("org.apache.derby.jdbc.ClientDriver");
//		Class.forName(Database.getDriverData());
		
//		Connection con = DriverManager.getConnection(Database.getDBUrl());
		
		
//		ConnectionPool connectionPool;
//		connectionPool = ConnectionPool.getInstance();
//		Connection con = connectionPool.getConnection();
		
		
//		try {
//			Connection con = ConnectionPool.getInstance().getConnection();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
		try {
			ConnectionPool.getInstance().getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		Database.dropDB();
//		Database.buildDB();
		

		
//		CouponSystem couponSystem = null;
		
		try {
			CouponSystem.getInstance().starting();//
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		try {
			ConnectionPool.getInstance().availableConnections();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
//		CouponSystem.getInstance();
		
		
		
		//--------------------------------------------------Company-----------------------------------------------------------
//		Database.createTableCompany(con);
//		Database.dropTableCompany(con);
		
		//--------------------------------------------------Customer----------------------------------------------------------
//		Database.createTableCustomer(con);
//		Database.dropTableCustomer(con);
		
		//--------------------------------------------------Coupon------------------------------------------------------------
//		Database.createTableCoupon(con);
//		Database.dropTableCoupon(con);
		
	    //--------------------------------------------------Company_Coupon----------------------------------------------------
//	    Database.cteateTableCompanyCoupon(con);
//	    Database.dropTableCompanyCoupon(con);
	    
	    //--------------------------------------------------Custpmer_Coupon---------------------------------------------------
//	    Database.createTableCustomerCoupon(con);
//	    Database.dropTableCustomerCoupon(con);
		
		
		
		
		
	    
	   
		Company company1 = new Company(1, "adidas", "123", "adidas@gmail.com");
		Company company2 = new Company(2, "TEVA", "456", "teva@gmail.com");
		Company company3 = new Company(3, "Coca cola", "789", "cocacola@gmail.com");
		
		
		
		
		Customer customer1 = new Customer(1, "Kobi", "kobi123");
		Customer customer2 = new Customer(2, "Igor", "igor456");
		Customer customer3 = new Customer(3, "Moshe", "moshe789");
		Customer customer4 = new Customer(4, "Bibi", "bibi101");
		
		
		
		
		Coupon coupon1 = new Coupon(1, "SPORT Coupon", Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now().plusDays(5)),  
				5, CouponType.SPORTS, "VIP ONLY", 50.90, "img");
		Coupon coupon2 = new Coupon(2, "HEALTH Coupon", Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now().plusDays(5)),  
				5, CouponType.HEALTH, "VIP ONLY", 70.90, "img");
		Coupon coupon3 = new Coupon(3, "DRINKS Coupon", Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now().plusDays(5)),  
				5, CouponType.DRINKS, "VIP ONLY", 60.90, "img");
		Coupon coupon4 = new Coupon(4, "ELECTRICITY Coupon", Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now().plusDays(5)), 
				5, CouponType.ELECTRICITY, "VIP ONLY", 80.50, "img");
		Coupon coupon5 = new Coupon(5, "SPORT5 Coupon", Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now().plusDays(5)),  
				5, CouponType.SPORTS, "VIP ONLY", 100.90, "img");
		
		
		
		
		
		//--------------------------------------------------AdminFacade-----------------------------------------------------
//		CompanyDBDAO companyDBDAO = new CompanyDBDAO();
//		AdminFacade adminFacade = (AdminFacade) new ClientSwitchCase().login("admin", "1234", ClientType.ADMINISTRATOR);
		
		AdminFacade adminFacade = (AdminFacade) CouponSystem.getInstance().login("admin", "1234", ClientType.ADMINISTRATOR);
		adminFacade.insertCompany(company1);
		adminFacade.insertCompany(company2);
		adminFacade.insertCompany(company3);
//		adminFacade.deleteCompany(company1);
//		adminFacade.deleteCompany(company2);
//		adminFacade.deleteCompany(company3);
//		adminFacade.updateCompany(1, "adidas clothes", "1233", "adidas@gmail.com");
//		adminFacade.updateCompany(2, "TEVA Health", "4566", "teva@gmail.com");
//		adminFacade.updateCompany(3, "CocaCola", "7899", "cocacola@gmail.com");
//		   System.out.println(adminFacade.selectCompany(1));
//		adminFacade.selectCompany(1);
//		   System.out.println(adminFacade.selectAllCompanies());
//		adminFacade.selectAllCompanies();
//		   adminFacade.deleteAllCompanies();//??????
//		
		adminFacade.insertCustomer(customer1);
		adminFacade.insertCustomer(customer2);
		adminFacade.insertCustomer(customer3);
//		adminFacade.insertCustomer(customer4);
//		adminFacade.deleteCustomer(customer1);
//		adminFacade.deleteCustomer(customer2);
//		adminFacade.deleteCustomer(customer3);
//		adminFacade.deleteCustomer(customer4);
//		adminFacade.updateCustomer(customer1, "Kobi Shasha", "kobi1234");
//		adminFacade.updateCustomer(customer2, "Igor Gamazov", "igor4567");
//		adminFacade.updateCustomer(customer3, "Moshe Moshe", "moshe7890");
//		adminFacade.updateCustomer(customer4, "Bibi Bibi", "bibi1010");
//		   System.out.println(adminFacade.selectCustomer(1));
//		adminFacade.selectCustomer(1);
//		   System.out.println(adminFacade.selectAllCustomers());
//		adminFacade.selectAllCustomers();
//		   adminFacade.deleteAllCustomers();//??????
		
				
				
				
				
				
		//--------------------------------------------------CompanyFacade-----------------------------------------------------
//		CompanyFacade companyFacade = new CompanyFacade();
//		CompanyFacade companyFacade = (CompanyFacade) new ClientSwitchCase().login("adidas", "123", ClientType.COMPANY);
//				new ClientSwitchCase().login("TEVA", "456", ClientType.COMPANY);
//				new ClientSwitchCase().login("Coca cola", "789", ClientType.COMPANY);
		
		CompanyFacade companyFacade = (CompanyFacade) CouponSystem.getInstance().login(company1.getCompName(), company1.getPassword(), ClientType.COMPANY);
//		CompanyFacade companyFacade2 = (CompanyFacade) CouponSystem.getInstance().login(company2.getCompName(), company2.getPassword(), ClientType.COMPANY);		
//		CompanyFacade companyFacade3 = (CompanyFacade) CouponSystem.getInstance().login(company3.getCompName(), company3.getPassword(), ClientType.COMPANY);
		companyFacade.insertCoupon(coupon1);
		companyFacade.insertCoupon(coupon2);
		companyFacade.insertCoupon(coupon3);
//		companyFacade.insertCoupon(coupon4);
//		companyFacade.insertCoupon(coupon5);
//		companyFacade2.insertCoupon(coupon4);////
//		companyFacade.deleteCoupon(coupon1);
//		companyFacade.deleteCoupon(coupon2);
//		companyFacade.deleteCoupon(coupon3);
//		companyFacade.deleteCoupon(coupon4);
//		companyFacade.deleteCoupon(coupon5);
//		companyFacade.updateCoupon(coupon1, "Sport Coupon", Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now().minusDays(5)), 
//				5, CouponType.SPORTS, "For each person", 70.90, "img");
//		companyFacade.updateCoupon(coupon2, "Health Coupon", Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now().minusDays(5)), 
//		        5, CouponType.HEALTH, "For each person", 90.90, "img");
//      companyFacade.updateCoupon(coupon3, "Drinks Coupon", Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now().plusDays(5)), 
//		        5, CouponType.DRINKS, "For each person", 80.90, "img");
//      companyFacade.updateCoupon(coupon4, "Electricity Coupon", Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now().plusDays(5)), 
//              5, CouponType.ELECTRICITY, "For each person", 90.50, "img");
//		companyFacade.updateCoupon(coupon5, "Sport5 Coupon", Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now().plusDays(5)), 
//				5, CouponType.SPORTS, "For each person", 120.90, "img");
		
//		   System.out.println(companyFacade.selectCoupon(4));//from Coupon table
//		companyFacade.selectCoupon(2);
//		   System.out.println(companyFacade.selectCoupons(1));//from Company_Coupon table
//		   System.out.println(companyFacade.selectAllCoupons());//from Coupon table
//		companyFacade.selectAllCoupons();
//		   System.out.println(companyFacade.getCouponByType(CouponType.SPORTS));//from Coupon table
//		companyFacade.getCouponByType(CouponType.SPORTS);
//		   companyFacade.addCoupon(1, 4);
//		   companyFacade.deleteCoupon(1, 1);
//		   Coupon coupon = null;
//		   companyFacade.deleteAllCoupons(coupon);
//		   System.out.println(companyFacade.selectCompany(company1));	
				
				
				
				
				
		//--------------------------------------------------CustomerFacade----------------------------------------------------
//		CustomerFacade customerFacade = (CustomerFacade) new ClientSwitchCase().login("Igor", "igor456", ClientType.CUSTOMER);
//				new ClientSwitchCase().login("Moshe", "moshe789", ClientType.CUSTOMER);
//				new ClientSwitchCase().login("Kobi", "kobi123", ClientType.CUSTOMER);
		
		CustomerFacade customerFacade = (CustomerFacade) CouponSystem.getInstance().login(customer1.getCustName(), customer1.getPassword(), ClientType.CUSTOMER);
//		CustomerFacade customerFacade2 = (CustomerFacade) CouponSystem.getInstance().login(customer2.getCustName(), customer2.getPassword(), ClientType.CUSTOMER);
//		CustomerFacade customerFacade3 = (CustomerFacade) CouponSystem.getInstance().login(customer3.getCustName(), customer3.getPassword(), ClientType.CUSTOMER);
		customerFacade.purchaseCoupon(1);//1,1//coupon1
		customerFacade.purchaseCoupon(2);//2,1//coupon2
//		customerFacade.purchaseCoupon(3);//3,1//coupon3
//		   System.out.println(customerFacade.getAllPurchasedCoupons());//1
//		customerFacade.getAllPurchasedCoupons();//1
//		   System.out.println(customerFacade.getAllPurchasedCouponsByType(CouponType.SPORTS));//,1
//		customerFacade.getAllPurchasedCouponsByType(CouponType.SPORTS);//,1
//		   System.out.println(customerFacade.getAllPurchasedCouponsByPrice(90.0, 1));
//		customerFacade.getAllPurchasedCouponsByPrice(90.0);//,1
		
		
		
		
		
		
			
		
//		CouponSystem.getInstance().shutdown();
		
		
	}



}
