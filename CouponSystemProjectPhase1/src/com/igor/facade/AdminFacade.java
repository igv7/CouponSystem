package com.igor.facade;


import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

//import org.apache.derby.tools.sysinfo;

import com.igor.dao.ClientDAO;
import com.igor.enums.ClientType;
import com.igor.beans.Company;
import com.igor.dbdao.CompanyDBDAO;
import com.igor.dbdao.CompanyCouponDBDAO;
import com.igor.beans.Coupon;
import com.igor.dbdao.CouponDBDAO;
import com.igor.beans.Customer;
import com.igor.dbdao.CustomerDBDAO;
import com.igor.exceptions.ConnectionException;
import com.igor.exceptions.DeleteAllCompaniesException;
import com.igor.exceptions.DeleteAllCustomersException;
import com.igor.exceptions.DeleteCompanyException;
import com.igor.exceptions.DeleteCustomerException;
import com.igor.exceptions.InsertCompanyException;
import com.igor.exceptions.InsertCustomerException;
import com.igor.exceptions.SelectAllCompaniesException;
import com.igor.exceptions.SelectAllCustomersException;
import com.igor.exceptions.SelectCompanyException;
import com.igor.exceptions.SelectCustomerException;
import com.igor.exceptions.UpdateCompanyException;
import com.igor.exceptions.UpdateCustomerException;

//import sun.util.logging.resources.logging;

public class AdminFacade implements ClientDAO {
	
	CompanyDBDAO companyDBDAO = new CompanyDBDAO();
	CustomerDBDAO customerDBDAO = new CustomerDBDAO();
	CompanyCouponDBDAO companyCouponDBDAO = new CompanyCouponDBDAO();
	CouponDBDAO couponDBDAO = new CouponDBDAO();
	

	@Override
	public ClientDAO login(String name, String password, ClientType type) throws Exception {
		if (name.equalsIgnoreCase("admin") && password.equals("1234") && type.equals(ClientType.ADMINISTRATOR)) {
			System.out.println("Admin logged in to system");
			return new AdminFacade();
		} else
			throw new Exception("Login FAILED for admin");
	}
	
	public Company insertCompany(Company company) throws ConnectionException, InsertCompanyException, SQLException, 
				Exception {
		
		try {
			Set<Company> companies = companyDBDAO.selectAllCompanies();
			Iterator<Company> iterator = companies.iterator();
			while (iterator.hasNext()) {
				Company current = iterator.next();
				if (company.getCompName().equals(current.getCompName())) {
		 			System.out.println("Cannot create Company " + company.getCompName() + " this Company already exists.");
		 			throw new InsertCompanyException();
		 		} 
				
			}
	 		if (!iterator.hasNext()) {
	 			companyDBDAO.insertCompany(company);
	 			System.out.println("New Company added by Admin. Company name: " + company.getCompName());
			} 
	 		
		} catch (InsertCompanyException e) {
			e.getMessage();
			e.printStackTrace();
		}
		return company;
		
	}
	
	public void deleteCompany(Company company) throws ConnectionException, DeleteCompanyException, SQLException, Exception {
		try {
			Set<Company> companies = companyDBDAO.selectAllCompanies();
			Iterator<Company> iterator = companies.iterator();
			boolean b = false;
			while (iterator.hasNext()) {
				Company current = iterator.next();
				if (current.getId() == company.getId()) {
					b = true;
				}
			}
			if (b == false && !iterator.hasNext()) {
				System.out.println("Cannot remove this Company! This Company does not exist in system");
				throw new Exception("Cannot remove this Company! This Company does not exist in system");
			}

			Collection<Coupon> removeCoup = companyDBDAO.selectCoupons(company.getId());
			for (Coupon c : removeCoup) {
				couponDBDAO.deleteCoupon(c);
			}
			companyDBDAO.deleteCompany(company);
		} catch (DeleteCompanyException e) {
			e.getMessage();
			e.printStackTrace();
		}
		
	}
	
	public Company updateCompany(long id, String compName, String password, String email) throws SQLException, ConnectionException, 
				UpdateCompanyException, Exception {
		Company company = companyDBDAO.selectCompany(id);
		try {
			Set<Company> companies = companyDBDAO.selectAllCompanies();
			Iterator<Company> iterator = companies.iterator();
			boolean b = false;
			while (iterator.hasNext()) {
				Company current = iterator.next();
				if (current.getId() == id) {
					b = true;
				}
			}
			if (!iterator.hasNext() && b == false) {
				System.out.println("This Company does not exist in system");
				throw new Exception("This Company does not exist in system");
			}
			company.setCompName(compName);
			company.setPassword(password);
			company.setEmail(email);
			companyDBDAO.updateCompany(company);
		} catch (UpdateCompanyException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		} catch (Exception e) {
			throw new Exception("Admin failed to update company. company id: " + company.getId());
		}
		return company;
		
	}
	
	public Company selectCompany(long id) throws ConnectionException, SelectCompanyException, SQLException, Exception {
		try {
			Set<Company> companies = companyDBDAO.selectAllCompanies();
			Iterator<Company> iterator = companies.iterator();
			boolean b = false;
			while (iterator.hasNext()) {
				Company current = iterator.next();
				if (current.getId() == id) {
					b = true;
				}
			}
			if (!iterator.hasNext() && b == false) {
				System.out.println("This Company does not exist in system");
				throw new Exception("This Company does not exist in system");
			}
			System.out.println(companyDBDAO.selectCompany(id));
			return companyDBDAO.selectCompany(id);
		} catch (SelectCompanyException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			throw new Exception("Admin failed to select a company. company id: " + id);
		}
		return null;
	}
	
	public Set<Company> selectAllCompanies() throws SQLException, ConnectionException, SelectAllCompaniesException, 
				Exception {
		
		try {
			Set<Company> companies = companyDBDAO.selectAllCompanies();
			if (companies.isEmpty()) {
				System.out.println("Admin failed to select all companies. There is no company in the company table yet.");
//				throw new Exception("Admin failed to select all companies. No cmpany data");
			}
	
			for (Company company : companies) {
				System.out.println(company);
			}
			
//			return companyDBDAO.selectAllCompanies();
			return companies;
		} catch (SelectAllCompaniesException e) {
			System.out.println(e.getMessage());
		
		} catch (Exception e) {
			throw new Exception("Admin failed to select all companies");
		}
		
		return null;
	}
	
	public void deleteAllCompanies() throws ConnectionException, DeleteAllCompaniesException, SQLException, Exception {
		try {
			Set<Company> companies = companyDBDAO.selectAllCompanies();
			if (companies.isEmpty()) {
				System.out.println("Cannot get Company data, the Company table is empty.");
				throw new Exception("Cannot get Company data, the Company table is empty.");
			}
	
			if (!companies.isEmpty()) {
				companyDBDAO.deleteAllCompanies();
			}
				
			
			companyDBDAO.deleteAllCompanies();//?
		} catch (DeleteAllCompaniesException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			throw new Exception("Cannot delete All Companies");
		}
		
		
	}
	
	
	
	
	
	
	
	public Customer insertCustomer(Customer customer) throws ConnectionException, InsertCustomerException, SQLException, Exception {
		try {
		Set<Customer> customers = customerDBDAO.selectAllCustomers();
		Iterator<Customer> iterator = customers.iterator();
		
			while (iterator.hasNext()) {
				Customer current = iterator.next();
				if (customer.getCustName().equals(current.getCustName())) {
					System.out.println("Cannot create Customer " + customer.getCustName() + " this Customer already exists.");
					throw new InsertCustomerException();
				}
			}
			if (!iterator.hasNext()) {
				customerDBDAO.insertCustomer(customer);
				System.out.println("New Customer added by Admin. Customer name: " + customer.getCustName());
			}
		} catch (InsertCustomerException e) {
			e.getMessage();
		}
		return customer;
		
	}
	
	public void deleteCustomer(Customer customer) throws ConnectionException, DeleteCustomerException, SQLException, Exception {
		try {
			Set<Customer> customers = customerDBDAO.selectAllCustomers();
			Iterator<Customer> iterator = customers.iterator();
			boolean b = false;
			while (iterator.hasNext()) {
				Customer current = iterator.next();
				if (current.getId() == customer.getId()) {
					b = true;
				}
			}
			if (b == false && !iterator.hasNext()) {
				System.out.println("This Customer does not exist in system. Cannot delete this Customer");
				throw new Exception("This Customer does not exist in system. Cannot delete this Customer");
			}
			Collection<Coupon> removeCustCoup = customerDBDAO.selectCoupons(customer.getId());
			for (Coupon coupon : removeCustCoup) {
				customerDBDAO.deleteCustomer(customer);
			}
			customerDBDAO.deleteCustomer(customer);
		} catch (DeleteCustomerException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			throw new Exception("Admin failed to remove Customer. Customer id: " + customer.getId() + ". This Customer does not exist");
		}
		
	}
	
	public Customer updateCustomer(long id, String custName, String password) throws ConnectionException, UpdateCustomerException, 
				SQLException, Exception {
		Customer customer = customerDBDAO.selectCustomer(id);
		try {
			Set<Customer> customers = customerDBDAO.selectAllCustomers();
			Iterator<Customer> iterator = customers.iterator();
			boolean b = false;
			while (iterator.hasNext()) {
				Customer current = iterator.next();
				if (current.getId() == id) {
					b = true;
				}
			}
			if (!iterator.hasNext() && b == false) {
				System.out.println("This Customer does not exist in system");
				throw new Exception("This Customer does not exist in system");
			}
			customer.setCustName(custName);
			customer.setPassword(password);
			customerDBDAO.updateCustomer(customer);
		} catch (UpdateCustomerException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		} catch (Exception e) {
			throw new Exception("Admin failed to update customer. Customer id: " + customer.getId());
		}
		return customer;
	}
	
	public Customer selectCustomer(long id) throws ConnectionException, SelectCustomerException, SQLException, Exception {
		try {
			Set<Customer> customers = customerDBDAO.selectAllCustomers();
			Iterator<Customer> iterator = customers.iterator();
			boolean b = false;
			while (iterator.hasNext()) {
				Customer current = iterator.next();
				if (current.getId() == id) {
					b = true;
				}
			}
			if (!iterator.hasNext() && b == false) {
				System.out.println("This Customer does not exist in system");
				throw new Exception("This Customer does not exist in system");
			}
			System.out.println(customerDBDAO.selectCustomer(id));
			return customerDBDAO.selectCustomer(id);
		} catch (SelectCustomerException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			throw new Exception("Admin failed to select a customer. Customer id: " + id);
		}
		return null;
	}
	
	public Set<Customer> selectAllCustomers() throws ConnectionException, SelectAllCustomersException, SQLException, Exception {
		try {
			Set<Customer> customers = customerDBDAO.selectAllCustomers();
			if (customers.isEmpty()) {
				System.out.println("Admin failed to select all customers. There is no customer in the customer table yet.");
//				throw new Exception("Admin failed to select all customers. There is no customer in the customer table yet.");//
			}
			for (Customer customer : customers) {
				System.out.println(customer);
			}
			return customers;
//			return customerDBDAO.selectAllCustomers();
		} catch (SelectAllCustomersException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			throw new Exception("Admin failed to select all customers");
		}
		return null;
	}
	
	public void deleteAllCustomers() throws ConnectionException, DeleteAllCustomersException, SQLException, Exception {
		try {
			Set<Customer> customers = customerDBDAO.selectAllCustomers();
			if (customers.isEmpty()) {
				System.out.println("Cannot get Customer data, the Customer table is empty.");
				throw new Exception("Cannot get Customer data, the Customer table is empty.");
			}
	
			if (!customers.isEmpty()) {
				customerDBDAO.deleteAllCustomers();
			}
				
			
			customerDBDAO.deleteAllCustomers();//?
		} catch (DeleteAllCustomersException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			throw new Exception("Cannot delete All Customers");
		}
		
	}

}
