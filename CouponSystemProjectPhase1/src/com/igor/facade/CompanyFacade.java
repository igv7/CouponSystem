package com.igor.facade;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.xml.ws.FaultAction;

import com.igor.dao.ClientDAO;
import com.igor.enums.ClientType;
import com.igor.beans.Company;
import com.igor.beans.CompanyCoupon;
import com.igor.dbdao.CompanyCouponDBDAO;
import com.igor.dbdao.CompanyDBDAO;
import com.igor.beans.Coupon;
import com.igor.dbdao.CouponDBDAO;
import com.igor.enums.CouponType;
import com.igor.exceptions.AddCompanyCouponExceprion;
import com.igor.exceptions.CompanyLoginException;
import com.igor.exceptions.ConnectionException;
import com.igor.exceptions.DeleteAllCompaniesException;
import com.igor.exceptions.DeleteAllCouponsException;
import com.igor.exceptions.DeleteCompanyCouponException;
import com.igor.exceptions.DeleteCompanyException;
import com.igor.exceptions.DeleteCouponException;
import com.igor.exceptions.GetCouponByTypeException;
import com.igor.exceptions.InsertCompanyException;
import com.igor.exceptions.InsertCouponException;
import com.igor.exceptions.SelectAllCompaniesException;
import com.igor.exceptions.SelectAllCouponsException;
import com.igor.exceptions.SelectCompanyException;
import com.igor.exceptions.SelectCouponException;
import com.igor.exceptions.SelectCouponsException;
import com.igor.exceptions.UpdateCompanyException;
import com.igor.exceptions.UpdateCouponException;


public class CompanyFacade implements ClientDAO{
	
	private Company company;
	private CompanyDBDAO companyDBDAO = new CompanyDBDAO();
	private CouponDBDAO couponDBDAO = new CouponDBDAO();
	private CompanyCouponDBDAO companyCouponDBDAO = new CompanyCouponDBDAO();
	
	
	private long loggedCompany = 0;
	
	public CompanyFacade() {
		
	}
	
	
	public CompanyFacade(Company company) {
		this.company= company;
	}
	
	
	public long getLoggedCompany() {
		return loggedCompany;
	}

	public void setLoggedCompany(long loggedCompany) {
		this.loggedCompany = loggedCompany;
	}
	
	
	
	@Override
	public ClientDAO login(String name, String password, ClientType type) throws ConnectionException, CompanyLoginException, SQLException, 
					Exception {
		if (companyDBDAO.login(name, password, type)) {
			CompanyFacade companyFacade = new CompanyFacade(this.company);//company
			companyFacade.setLoggedCompany(companyDBDAO.getLoginId());
			System.out.println("Logged in as " + name);
			return companyFacade;
		} else
			System.out.println("Could not login with this Company " + name + ". Try again or check you credentials");
			throw new CompanyLoginException();
	}
	
	
	
//	public void insertCompany(Company company) throws ConnectionException, InsertCompanyException, SQLException, Exception {
//		companyDBDAO.insertCompany(company);
//	}
//	
//	public void deleteCompany(Company company) throws ConnectionException, DeleteCompanyException, SQLException, Exception {
//		companyDBDAO.deleteCompany(company);
//	}
//	
//	public void updateCompany(Company company, String compName, String password, String email) throws SQLException, ConnectionException, 
//				UpdateCompanyException, Exception {
//		company.setCompName(compName);
//		company.setPassword(password);
//		company.setEmail(email);
//		companyDBDAO.updateCompany(company, compName, password, email);
//	}
//	
//	public Company selectCompany(long id) throws ConnectionException, SelectCompanyException, SQLException, Exception {
//		return companyDBDAO.selectCompany(id);
//	}
//	
//	public Set<Company> selectAllCompanies(Company company) throws SQLException, ConnectionException, SelectAllCompaniesException, 
//				Exception {
//		return companyDBDAO.selectAllCompanies();
//	}
//	
//	public void deleteAllCompanies() throws ConnectionException, DeleteAllCompaniesException, SQLException, Exception {
//	companyDBDAO.deleteAllCompanies();
//}
	
	public void addCoupon(Company company, Coupon coupon) throws ConnectionException, AddCompanyCouponExceprion, Exception {
		companyDBDAO.addCoupon(company, coupon);
	}
	
	public void deleteCoupon(long compId, long couponId) throws ConnectionException, DeleteCompanyCouponException, Exception {
		companyDBDAO.deleteCoupon(compId, couponId);
	}
	
	public Collection<Coupon> selectCoupons(long id) throws ConnectionException, SelectCouponsException, SQLException, Exception {
		return companyDBDAO.selectCoupons(id);
	}

	
	

	
	
	
	
	public Coupon insertCoupon(Coupon coupon) throws ConnectionException, InsertCouponException, SQLException, Exception {
		
		try {
			if (coupon.getAmount() < 1) {
				throw new Exception("Company: " + this.company.getCompName() + " failed to add coupon - wrong amount: 0, couponId: " 
			+ coupon.getId());
			}
			
			if (coupon.getEndDate().before(Date.valueOf(LocalDate.now()))) {
				throw new Exception("Company: " + this.company.getCompName() + "  failed to add coupon -  the date has expired " 
			+ coupon.getEndDate());
			}
			
			Set<Coupon> coupons = couponDBDAO.selectAllCoupons();
			Iterator<Coupon> iterator = coupons.iterator();
			while (iterator.hasNext()) {
				Coupon current = iterator.next();
				if (coupon.getTitle().equals(current.getTitle())) {
					throw new Exception("Company: " + this.company.getCompName() + "  failed to add coupon - this coupon already exists " 
				+ coupon.getTitle());
				} 
				
			}
			
			if (!iterator.hasNext()) {
				couponDBDAO.insertCoupon(coupon);
				companyDBDAO.addCoupon(this.company, coupon);
			}

		
		} catch (InsertCouponException e) {
			e.getMessage();
		} catch (Exception e) {
			throw new Exception("Company " + this.company.getCompName() + " Failed to insert Coupon " + coupon.getTitle());
		}
		return coupon;
		
	}
	
	
//	public Coupon insertCoupon(Coupon coupon) throws ConnectionException, InsertCouponException, SQLException, Exception {
//		
//		try {
//			Set<Company> companies = companyDBDAO.selectAllCompanies();
//			Iterator<Company> iterator2 = companies.iterator();
//			while (iterator2.hasNext()) {
//				Company current2 = iterator2.next();
//			
//			if (coupon.getAmount() < 1) {
//				throw new Exception("Company: " + current2.getCompName() + " failed to add coupon - wrong amount: 0, couponId: " 
//			+ coupon.getId());
//			}
//			if (coupon.getEndDate().before(Date.valueOf(LocalDate.now()))) {
//				throw new Exception("Company: " + current2.getCompName() + "  failed to add coupon -  the date has expired " 
//			+ coupon.getEndDate());
//			}
//			Set<Coupon> coupons = couponDBDAO.selectAllCoupons();
//			Iterator<Coupon> iterator = coupons.iterator();
//			while (iterator.hasNext()) {
//				Coupon current = iterator.next();
//				if (coupon.getTitle().equals(current.getTitle())) {
//					throw new Exception("Company: " + current2.getCompName() + "  failed to add coupon - this coupon already exists " 
//				+ coupon.getTitle());
//				}
//			}
//			if (!iterator2.hasNext()) {
//				couponDBDAO.insertCoupon(coupon);
//				companyDBDAO.addCoupon(current2, coupon);//+this.company
//			}
//			}
//		
//		} catch (InsertCouponException e) {
//			e.getMessage();
//		} catch (Exception e) {
//			throw new Exception("Failed to insert Coupon " + coupon.getId());
//		}
//		return coupon;
//		
//	}
	
	public void deleteCoupon(Coupon coupon) throws ConnectionException, DeleteCouponException, SQLException, Exception {
		try {
			Set<Coupon> coupons = couponDBDAO.selectAllCoupons();
			Iterator<Coupon> iterator = coupons.iterator();
			boolean b = false;
			while (iterator.hasNext()) {
				Coupon current = iterator.next();
				if (current.getId() == coupon.getId()) {
					b = true;
				}
			}
			if (b == false && !iterator.hasNext()) {
				throw new Exception("Coupon does not exist in system");
			}
			Coupon removeCoup = couponDBDAO.selectCoupon(coupon.getId());
//			CompanyCoupon removeCoup = companyCouponDBDAO.selectCompanyCouponByCouponId(coupon.getId());
				if (coupon.getId() != 0) {
					couponDBDAO.deleteCoupon(removeCoup);
				}
//			couponDBDAO.deleteCoupon(coupon);
		} catch (DeleteCouponException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			throw new Exception("Failed to delete Coupon");
		}
		
	}
	
//	public void deleteCoupon(Coupon coupon) throws ConnectionException, DeleteCouponException, SQLException, Exception {
//		try {
//			Set<Coupon> coupons = couponDBDAO.selectAllCoupons();
//			Iterator<Coupon> iterator = coupons.iterator();
//			boolean b = false;
//			while (iterator.hasNext()) {
//				Coupon current = iterator.next();
//				if (current.getId() == coupon.getId()) {
//					b = true;
//				}
//			}
//			if (b == false && !iterator.hasNext()) {
//				throw new Exception("Coupon does not exist in system");
//			}
//			Collection<Coupon> removeCoup = companyDBDAO.selectCoupons(coupon.getId());
//			for (Coupon c : removeCoup) {
//				couponDBDAO.deleteCoupon(c);
//			}
////			couponDBDAO.deleteCoupon(coupon);
//		} catch (DeleteCouponException e) {
//			System.out.println(e.getMessage());
//		} catch (Exception e) {
//			throw new Exception("Failed to delete Coupon");
//		}
//		
//	}
	
	public Collection<Coupon> deleteAllCompanyCoupons() throws ConnectionException, DeleteCouponException, SQLException, Exception {//void
		Collection<Coupon> removeCoup = companyDBDAO.selectCoupons(this.company.getId());
	try {
//		Set<Coupon> coupons = couponDBDAO.selectAllCoupons();
//		Iterator<Coupon> iterator = coupons.iterator();
//		boolean b = false;
//		while (iterator.hasNext()) {
//			Coupon current = iterator.next();
//			if (current.getId() == coupon.getId()) {
//				b = true;
//			}
//		}
//		if (b == false && !iterator.hasNext()) {
//			throw new Exception("Coupon does not exist in system");
//		}
//		Collection<Coupon> removeCoup = companyDBDAO.selectCoupons(this.company.getId());
		for (Coupon c : removeCoup) {
			couponDBDAO.deleteCoupon(c);
		}
//		couponDBDAO.deleteCoupon(coupon);
	} catch (DeleteCouponException e) {
		System.out.println(e.getMessage());
	} catch (Exception e) {
		throw new Exception("Failed to delete Coupon");
	}
	return removeCoup;
}
	
	public Coupon updateCoupon(long id, String title, Date startDate, Date endDate, int amount, CouponType type, String message,
			Double price, String image) throws ConnectionException, UpdateCouponException, SQLException, Exception {
		Coupon coupon = new Coupon();
		try {
			Collection<Coupon> couponsUpdate = couponDBDAO.selectAllCoupons();
			Iterator<Coupon> iterator = couponsUpdate.iterator();
			boolean b = false;
			while (iterator.hasNext()) {
				Coupon current = iterator.next();
				if (current.getId() == id) {
					b = true;
				}
			}
			if (b == false && !iterator.hasNext()) {
				throw new Exception("Coupon does not exist in system");
			}
			Company company = companyDBDAO.selectCompany(id);
			Collection<Coupon> couponsToUpdate = companyDBDAO.selectCoupons(company.getId());
			Iterator<Coupon> iterator2 = couponsToUpdate.iterator();
			while (iterator2.hasNext()) {
				coupon = iterator2.next();
				coupon.setTitle(title);
				coupon.setStartDate(startDate);
				coupon.setEndDate(endDate);
				coupon.setAmount(amount);
				coupon.setType(type);
				coupon.setMessage(message);
				coupon.setPrice(price);
				coupon.setImage(image);
				couponDBDAO.updateCoupon(coupon, title, startDate, endDate, amount, type, message, price, image);
			}
		} catch (UpdateCouponException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			throw new Exception("Cannot update Coupon");
		}
		return coupon;
	}
	
	public void updateCoupon(Coupon coupon, String title, Date startDate, Date endDate, int amount, CouponType type, String message,
			Double price, String image) throws ConnectionException, UpdateCouponException, SQLException, Exception {
		try {
			Collection<Coupon> couponsUpdate = couponDBDAO.selectAllCoupons();
			Iterator<Coupon> iterator = couponsUpdate.iterator();
			boolean b = false;
			while (iterator.hasNext()) {
				Coupon current = iterator.next();
				if (current.getId() == coupon.getId()) {
					b = true;
				}
			}
			if (b == false && !iterator.hasNext()) {
				throw new Exception("Coupon does not exist in system");
			}
			Company company = companyDBDAO.selectCompany(coupon.getId());
			Collection<Coupon> couponsToUpdate = companyDBDAO.selectCoupons(company.getId());
			Iterator<Coupon> iterator2 = couponsToUpdate.iterator();
			while (iterator2.hasNext()) {
				Coupon coupon2 = iterator2.next();
				coupon2.setTitle(title);
				coupon2.setStartDate(startDate);
				coupon2.setEndDate(endDate);
				coupon2.setAmount(amount);
				coupon2.setType(type);
				coupon2.setMessage(message);
				coupon2.setPrice(price);
				coupon2.setImage(image);
				couponDBDAO.updateCoupon(coupon2, title, startDate, endDate, amount, type, message, price, image);
			}
		} catch (UpdateCouponException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			throw new Exception("Cannot update Coupon");
		}
		
	}
	
	public Coupon updateCoupon(Coupon coupon) throws ConnectionException, UpdateCouponException, SQLException, Exception {
		couponDBDAO.updateCoupon(coupon);
		return coupon;
	}
	
	public Coupon selectCoupon(long id) throws ConnectionException, SelectCouponException, SQLException, Exception {
		
		try {
			Set<Coupon> coupons = couponDBDAO.selectAllCoupons();
			Iterator<Coupon> iterator = coupons.iterator();
			boolean b = false;
			while (iterator.hasNext()) {
				Coupon current = iterator.next();
				if (current.getId() == id) {
					b = true;
				}
			}
			if (b == false && !iterator.hasNext()) {
				throw new Exception("Coupon does not exist in system");
			}
			Set<Long> companyCoupons = companyCouponDBDAO.selectCompanyCouponsByIdCompany(this.company.getId());
			Iterator<Long> iterator2 = companyCoupons.iterator();
			while (iterator2.hasNext()) {
				long current = iterator2.next();
				if (current == id) {
					System.out.println(couponDBDAO.selectCoupon(id));
					return couponDBDAO.selectCoupon(id);
				}
			}
		} catch (SelectCouponException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			throw new Exception("Cannot select Coupon");
		}
		return null;
	}
	
	public Set<Coupon> selectAllCoupons() throws ConnectionException, SelectAllCouponsException, SQLException, Exception {
		try {
			Set<Long> coupons = companyCouponDBDAO.selectCompanyCoupons();
			Set<Coupon> selectCoupons = new HashSet<>();
			for (Long cId : coupons) {
				selectCoupons.add(couponDBDAO.selectCoupon(cId));
			}
			Collection<Coupon> selectAllCoup = selectCoupons;
			for (Coupon coupon : selectAllCoup) {
				System.out.println(coupon);
			}
			return selectCoupons;
//			return couponDBDAO.selectAllCoupons();
		} catch (SelectAllCouponsException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			throw new Exception("Cannot select all Coupons");
		}
		return null;
	}
	
	public Collection<Coupon> getCouponByType(CouponType type) throws ConnectionException, GetCouponByTypeException, SQLException, Exception {
		
		try {
			Collection<Coupon> coupons = companyDBDAO.selectCoupons(this.company.getId());
			Set<Coupon> selectCoupons = new HashSet<>();
//			for (Coupon coupon : coupons) {//check delete
				selectCoupons.addAll(couponDBDAO.getCouponByType(type));
//			}//check delete
			if (selectCoupons.isEmpty()) {
				throw new Exception("Failed to get coupons by type");
			}
			
			Collection<Coupon> selectAllCoup = selectCoupons;
//			for (Coupon coupon : selectAllCoup) {//check delete
//				System.out.println(coupon);//check delete
//			}//check delete
			System.out.println(selectAllCoup+"\n");//?check add
			return selectAllCoup;
		} catch (GetCouponByTypeException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			throw new Exception("Cannot get all Coupons by type");
		}
		return null;
	}
	
//	public Collection<Coupon> getCouponByType(CouponType type) throws ConnectionException, GetCouponByTypeException, SQLException, Exception {
//		System.out.println("CompanyFacade 111");
//		try {
//			System.out.println("CompanyFacade 222");
//			Collection<Coupon> coupons = companyDBDAO.selectCoupons(this.company.getId());
//			System.out.println("CompanyFacade 333");
//			Set<Coupon> selectCoupons = new HashSet<>();
//			System.out.println("CompanyFacade 444");
//			for (Coupon coupon : coupons) {//check delete
//				System.out.println("CompanyFacade 555");
//				selectCoupons.addAll(couponDBDAO.getCouponByType(type));
//				System.out.println("CompanyFacade 666");
//			}//check delete
//			System.out.println("CompanyFacade 777");
//			if (selectCoupons.isEmpty()) {
//				System.out.println("CompanyFacade 888");
//				throw new Exception("Failed to get coupons by type");
//			}
//			System.out.println("CompanyFacade 999");
//			Collection<Coupon> selectAllCoup = selectCoupons;
//			System.out.println("CompanyFacade 101010");
//			for (Coupon coupon : selectAllCoup) {//check delete
//				System.out.println("CompanyFacade 111111");//check delete
//				System.out.println(coupon);//check delete
//			}//check delete
//			System.out.println(selectAllCoup);//?check add
//			System.out.println("CompanyFacade 121212");
//			return selectCoupons;
//		} catch (GetCouponByTypeException e) {
//			System.out.println(e.getMessage());
//		} catch (Exception e) {
//			throw new Exception("Cannot get all Coupons by type");
//		}
//		return null;
//	}
	
//	public Set<Coupon> getCouponByType(CouponType type) throws ConnectionException, GetCouponByTypeException, SQLException, Exception {
//		try {
//			Set<Long> coupons = companyCouponDBDAO.selectCompanyCouponsByIdCompany(this.company.getId());
//			Set<Coupon> selectCoupons = new HashSet<>();
//			for (Long cId : coupons) {
//				selectCoupons.addAll(couponDBDAO.getCouponByType(type));
//			}
//			if (selectCoupons.isEmpty()) {
//				throw new Exception("Failed to get coupons by type");
//			}
//			Collection<Coupon> selectAllCoup = selectCoupons;
//			for (Coupon coupon : selectAllCoup) {
//				System.out.println(coupon);
//			}
//			return selectCoupons;
//		} catch (GetCouponByTypeException e) {
//			System.out.println(e.getMessage());
//		} catch (Exception e) {
//			throw new Exception("Cannot get all Coupons by type");
//		}
//		return null;
//	}
	
//	public Set<Coupon> getCouponByType(CouponType type) throws ConnectionException, GetCouponByTypeException, SQLException, Exception {
//		try {
//			Set<Long> coupons = companyCouponDBDAO.selectCompanyCoupons();//selectCompanyCouponsByIdCompany(long id)
//			Set<Coupon> selectCoupons = new HashSet<>();
//			for (Long cId : coupons) {
//				selectCoupons.addAll(couponDBDAO.getCouponByType(type));
//			}
//			if (selectCoupons.isEmpty()) {
//				throw new Exception("Failed to get coupons by type");
//			}
//			Collection<Coupon> selectAllCoup = selectCoupons;
//			for (Coupon coupon : selectAllCoup) {
//				System.out.println(coupon);
//			}
//			return selectCoupons;
//		} catch (GetCouponByTypeException e) {
//			System.out.println(e.getMessage());
//		} catch (Exception e) {
//			throw new Exception("Cannot get all Coupons by type");
//		}
//		return null;
//	}
	
	
	
	
	
	public void deleteAllCoupons() throws ConnectionException, DeleteAllCouponsException, SQLException, Exception {
		try {
			Set<Coupon> coupons = couponDBDAO.selectAllCoupons();
			if (coupons.isEmpty()) {
				System.out.println("Cannot get Coupons data, the Coupon table is empty.");
				throw new Exception("Cannot get Coupons data, the Coupon table is empty.");
			}
			if (!coupons.isEmpty()) {
				couponDBDAO.deleteAllCoupons();
			}
//			couponDBDAO.deleteAllCoupons();//?
		} catch (DeleteAllCouponsException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			throw new Exception("Cannot delete All Coupons");
		}
		
	}


//	public Coupon updateCoupon(long id, String title, String startDate, String endDate, int amount, String type,
//			String message, double price, String image) {
//		Coupon coupon = new Coupon();
//		try {
//			Collection<Coupon> couponsUpdate = couponDBDAO.selectAllCoupons();
//			Iterator<Coupon> iterator = couponsUpdate.iterator();
//			boolean b = false;
//			while (iterator.hasNext()) {
//				Coupon current = iterator.next();
//				if (current.getId() == id) {
//					b = true;
//				}
//			}
//			if (b == false && !iterator.hasNext()) {
//				throw new Exception("Coupon does not exist in system");
//			}
//			Company company = companyDBDAO.selectCompany(id);
//			Collection<Coupon> couponsToUpdate = companyDBDAO.selectCoupons(company.getId());
//			Iterator<Coupon> iterator2 = couponsToUpdate.iterator();
//			while (iterator2.hasNext()) {
//				coupon = iterator2.next();
//				coupon.setTitle(title);
//				coupon.setStartDate(startDate.toString());
//				coupon.setEndDate(endDate);
//				coupon.setAmount(amount);
//				coupon.setType(type);
//				coupon.setMessage(message);
//				coupon.setPrice(price);
//				coupon.setImage(image);
//				couponDBDAO.updateCoupon(coupon);
//			}
//		} catch (UpdateCouponException e) {
//			System.out.println(e.getMessage());
//		} catch (Exception e) {
//			throw new Exception("Cannot update Coupon");
//		}
//		return coupon;
//		
//	}


}
