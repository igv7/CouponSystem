package com.igor.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.igor.beans.Company;
import com.igor.beans.Coupon;
import com.igor.beans.Customer;
//import com.igor.beans.MyResponse;
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
import com.igor.exceptions.SelectCouponsException;
import com.igor.exceptions.SelectCustomerCouponsException;
import com.igor.exceptions.SelectCustomerException;
import com.igor.exceptions.UpdateCompanyException;
import com.igor.exceptions.UpdateCustomerException;
import com.igor.facade.AdminFacade;
import com.igor.facade.CustomerFacade;

@Path("admin")
public class AdminService {

	@Context
	private HttpServletRequest request;
	@Context
	private HttpServletResponse response;

	private AdminFacade getFacade() {

		AdminFacade admin = null;
		admin = (AdminFacade) request.getSession(false).getAttribute("facade");
		return admin;
	}
	
	
		
	// CREATE a new company in the db
	@POST
	@Path("createCompany")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String createCompany(Company company)
			throws SQLException, ConnectionException, SelectAllCompaniesException, Exception {

		System.out.println(company.toString());
		AdminFacade admin = getFacade();
		String failMsg = "FAILED TO ADD A NEW COMPANY: " + "There is already a company with the same name: "
				+ company.getCompName() + " - please change the company name";

		try {
			company = admin.insertCompany(company);

			System.out.println(
					"SUCCEED TO ADD A NEW COMPANY: name = " + company.getCompName() + ", id = " + company.getId());//
			return new Gson().toJson(company) + "SUCCEED TO ADD A NEW COMPANY: name = " + company.getCompName()
					+ ", id = " + company.getId();//

		} catch (InsertCompanyException e) {
			e.printStackTrace();
		}
		return failMsg;

	}

	// // CREATE a new company in the db
	// @POST
	// @Path("createCompany")
	// @Consumes(MediaType.APPLICATION_JSON)
	// @Produces(MediaType.APPLICATION_JSON)
	// public String createCompany(@QueryParam("compName") String compName,
	// @QueryParam("password") String password,
	// @QueryParam("email") String email) throws SQLException, ConnectionException,
	// SelectAllCompaniesException,
	// Exception {
	//
	// System.out.println(compName + " " + password + " " + email);
	// AdminFacade admin = getFacade();
	// String failMsg = "FAILED TO ADD A NEW COMPANY: " + "There is already a
	// company with the same name: " + compName
	// + " - please change the company name";
	//
	// Company company = new Company(compName, password, email);
	//
	// try {
	// admin.insertCompany(company);
	//// Set<Company> companies = admin.selectAllCompanies();//
	//// Iterator<Company> iterator = companies.iterator();//
	//// while (iterator.hasNext()) {//
	//// Company current = iterator.next();//
	//// if (companies.isEmpty() ||
	// !company.getCompName().equals(current.getCompName())) {//
	//// admin.insertCompany(company);//
	// System.out.println("SUCCEED TO ADD A NEW COMPANY: name = " + compName + ", id
	// = " + company.getId());//
	// return new Gson().toJson(company) + "SUCCEED TO ADD A NEW COMPANY: name = " +
	// compName + ", id = " + company.getId();//
	//// }//
	//// }//
	//
	//
	// //String result = new Gson().toJson(jsonElement)
	//// MyResponse myResponse = new MyResponse("blaaaaa", "blaaaaa");// open?
	//// return new Gson().toJson(myResponse);// open?
	// //return "SUCCEED TO ADD A NEW COMPANY: name = " + compName + ", id = " +
	// company.getId();
	//// return new Gson().toJson(company);
	//// }
	//// }
	// } catch (InsertCompanyException e) {
	// e.printStackTrace();
	// }
	// return failMsg;
	//
	// }

	// REMOVE a Company
	@DELETE
	@Path("removeCompany/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String removeCompany(@PathParam("id") long id)
			throws ConnectionException, SelectCompanyException, SQLException, Exception {

		String failMsg = "FAILED TO REMOVE A COMPANY: there is no such id! " + id
				+ " - please enter another company id";

		AdminFacade admin = getFacade();
		Company company = null;
		try {
			company = admin.selectCompany(id);
			if (company != null) {
				System.out.println("SUCCEED TO REMOVE A COMPANY: name = " + company.getCompName() + ", id = " + id);
				admin.deleteCompany(company);
				// return "SUCCEED TO REMOVE A COMPANY: name = " + company.getCompName() + ", id
				// = " + id;
				return new Gson().toJson(company) + "SUCCEED TO REMOVE A COMPANY: name = " + company.getCompName()
						+ ", id = " + id;
			}
		} catch (DeleteCompanyException e1) {
			e1.printStackTrace();
		}

		return failMsg;
	}

	// UPDATE a company
	@PUT
	@Path("updateCompany")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String updateCompany(String jsonString)
			throws ConnectionException, SelectCompanyException, SQLException, Exception {

		String failMsg = "FAILED TO UPDATE A COMPANY " + " - please enter another company id or chek your data";

		AdminFacade admin = getFacade();

		try {
			JsonParser parser = new JsonParser();
			JsonObject obj = parser.parse(jsonString).getAsJsonObject();
			long id = Long.parseLong(obj.get("id").getAsString());
			String compName = obj.get("compName").getAsString();
			String password = obj.get("password").getAsString();
			String email = obj.get("email").getAsString();
			Company company = admin.selectCompany(id);
			company = admin.updateCompany(id, compName, password, email);

			// if (company != null) {
			// company.setCompName(compName);
			// company.setPassword(password);
			// company.setEmail(email);
			// admin.updateCompany(id, compName, password, email);
			// admin.updateCompany(company, compName, password, email);
			System.out.println("SUCCEED TO UPDATE A COMPANY: " + compName + " password = " + company.getPassword()
					+ ", e-mail = " + company.getEmail() + ", id = " + id);

			return new Gson().toJson(company) + "SUCCEED TO UPDATE A COMPANY: " + compName + "password = "
					+ company.getPassword() + ",e-mail = " + company.getEmail() + ", id = " + id;

			// }
		} catch (Exception e) {
			e.printStackTrace();
		}

		return failMsg;

	}

	// // UPDATE a company
	// @PUT
	// @Path("updateCompany")
	// @Consumes(MediaType.APPLICATION_JSON)
	// @Produces(MediaType.APPLICATION_JSON)
	// public String updateCompany(@QueryParam("id") long id,
	// @QueryParam("compName") String compName,
	// @QueryParam("password") String password, @QueryParam("email") String email)
	// throws ConnectionException, SelectCompanyException, SQLException, Exception {
	//
	// String failMsg = "FAILED TO UPDATE A COMPANY: there is no such id! " + id + "
	// - please enter another company id";
	//
	// AdminFacade admin = getFacade();
	//
	// try {
	// Company company = admin.selectCompany(id);
	//
	// if (company != null) {
	// company.setCompName(compName);
	// company.setPassword(password);
	// company.setEmail(email);
	// admin.updateCompany(company, compName, password, email);
	// System.out.println("SUCCEED TO UPDATE A COMPANY: " + compName + " password =
	// " + company.getPassword()
	// + ", e-mail = " + company.getEmail() + ", id = " + id);
	//// return "SUCCEED TO UPDATE A COMPANY: " + compName + "pass = " +
	// company.getPassword() + ",e-mail = "
	//// + company.getEmail() + ", id = " + id;
	// return new Gson().toJson(company) + "SUCCEED TO UPDATE A COMPANY: " +
	// compName + "password = " + company.getPassword()
	// + ",e-mail = " + company.getEmail() + ", id = " + id;
	// }
	// } catch (UpdateCompanyException e) {
	// e.printStackTrace();
	// }
	//
	// return failMsg;
	//
	// }

	// GET a Company
	@GET
	@Path("getCompany")
	@Produces(MediaType.APPLICATION_JSON)
	public String getCompany(@QueryParam("id") long id) throws ConnectionException, SQLException, Exception {

		AdminFacade admin = getFacade();

		try {
			Company company = admin.selectCompany(id);
			if (company != null) {
				// return new Gson().toJson(new Company(company));
				System.out.println("SUCCEED TO GET A COMPANY BY ID: " + id);
				return new Gson().toJson(company) + "SUCCEED TO GET A COMPANY BY ID " + id;
			}
		} catch (SelectCompanyException e) {
			e.printStackTrace();
		}

		System.err
				.println("FAILED GET COMPANY BY ID: there is no such id!" + id + " - please enter another company id");

		return null;
	}

	// GET All Companies
	@GET
	@Path("getAllCompanies")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllCompanies() throws SQLException, ConnectionException, Exception {

		// Getting the session and the logged in facade object
		AdminFacade admin = getFacade();

		// Get the List of all the Companies from the Table in the DataBase

		try {
			Collection<Company> companies = admin.selectAllCompanies();
			Collection<Company> companiesInfo = new ArrayList<>();

			if (!companies.isEmpty()) {
				for (Company company : companies) {
					Company comapnyInfo = company;
					companiesInfo.add(comapnyInfo);
				}
				return new Gson().toJson(companiesInfo) + "SUCCEED TO GET ALL COMPANIES";
			}
			// return new Gson().toJson(companiesInfo) + "SUCCEED TO GET ALL COMPANIES";

		} catch (SelectAllCompaniesException e) {
			e.printStackTrace();
		}

		System.out.println("AdminService: FAILED GET ALL COMPANIES: there are no companies in the DB table!");
		return null;
	}

	// REMOVE All Companies
	@DELETE
	@Path("removeAllCompanies")
	@Produces(MediaType.APPLICATION_JSON)
	public String removeAllCompanies()
			throws ConnectionException, SelectAllCompaniesException, SQLException, Exception {

		String failMsg = "FAILED TO REMOVE ALL COMPANIES";

		AdminFacade admin = getFacade();
		Set<Company> companies = null;
		try {
			companies = admin.selectAllCompanies();
			if (companies != null) {
				admin.deleteAllCompanies();
				System.out.println("SUCCEED TO REMOVE ALL COMPANIES");
				// return "SUCCEED TO REMOVE ALL COMPANIES";
				return new Gson().toJson(companies) + "SUCCEED TO REMOVE ALL COMPANIES";
			}
		} catch (DeleteAllCompaniesException e1) {
			e1.printStackTrace();
		}

		return failMsg;
	}

	// CREATE a new Customer - add a customer to the Customer Table in DB
	@POST
	@Path("createCustomer")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String createCustomer(Customer customer)
			throws ConnectionException, SelectAllCustomersException, SQLException, Exception {

		System.out.println(customer.getCustName() + " " + customer.getPassword());
		AdminFacade admin = getFacade();
		String failMsg = "FAILED TO ADD A NEW CUSTOMER: " + "There is already a customer with the same name: "
				+ customer.getCustName() + " - please change the company name";

		customer = new Customer(customer.getCustName(), customer.getPassword());

		try {
			customer = admin.insertCustomer(customer);
			// Set<Customer> customers = admin.selectAllCustomers();
			// Iterator<Customer> iterator = customers.iterator();
			// while (iterator.hasNext()) {
			// Customer current = iterator.next();
			//
			// if (!customer.getCustName().equals(current.getCustName()) ||
			// customers.isEmpty()) {
			// admin.insertCustomer(customer);
			System.out.println(
					"SUCCEED TO ADD A NEW CUSTOMER: name = " + customer.getCustName() + ", id = " + customer.getId());
			return new Gson().toJson(customer) + "SUCCEED TO ADD A NEW CUSTOMER: name = " + customer.getCustName()
					+ ", id = " + customer.getId();
			// }
			// }
		} catch (InsertCustomerException e) {
			e.printStackTrace();
		}

		return failMsg;

	}

	// // CREATE a new Customer - add a customer to the Customer Table in DB
	// @POST
	// @Path("createCustomer")
	// @Consumes(MediaType.APPLICATION_JSON)
	// @Produces(MediaType.APPLICATION_JSON)
	// public String createCustomer(@QueryParam("custName") String custName,
	// @QueryParam("password") String password)
	// throws ConnectionException, SelectAllCustomersException, SQLException,
	// Exception {
	//
	// System.out.println(custName + " " + password);
	// AdminFacade admin = getFacade();
	// String failMsg = "FAILED TO ADD A NEW CUSTOMER: " + "There is already a
	// customer with the same name: "
	// + custName + " - please change the company name";
	//
	// Customer customer = new Customer(custName, password);
	//
	// try {
	// admin.insertCustomer(customer);
	//// Set<Customer> customers = admin.selectAllCustomers();
	//// Iterator<Customer> iterator = customers.iterator();
	//// while (iterator.hasNext()) {
	//// Customer current = iterator.next();
	////
	//// if (!customer.getCustName().equals(current.getCustName()) ||
	// customers.isEmpty()) {
	//// admin.insertCustomer(customer);
	// System.out.println("SUCCEED TO ADD A NEW CUSTOMER: name = " + custName + ",
	// id = " + customer.getId());
	// return new Gson().toJson(customer) + "SUCCEED TO ADD A NEW CUSTOMER: name = "
	// + custName + ", id = " + customer.getId();
	//// }
	//// }
	// } catch (InsertCustomerException e) {
	// e.printStackTrace();
	// }
	//
	// return failMsg;
	//
	// }

	// REMOVE Customer
	@DELETE
	@Path("removeCustomer/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String removeCustomer(@PathParam("id") long id)
			throws ConnectionException, SelectCustomerException, SQLException, Exception {

		String failMsg = "FAILED TO REMOVE A CUSTOMER: there is no such id! " + id
				+ " - please enter another customer id";

		AdminFacade admin = getFacade();
		Customer customer = null;
		try {
			customer = admin.selectCustomer(id);
			if (customer != null) {
				admin.deleteCustomer(customer);
				System.out.println("SUCCEED TO REMOVE A CUSTOMER: name = " + customer.getCustName() + ", id = " + id);
				return new Gson().toJson(customer) + "SUCCEED TO REMOVE A CUSTOMER: name = " + customer.getCustName()
						+ ", id = " + id;
			}
		} catch (DeleteCustomerException e) {
			e.printStackTrace();
		}

		return failMsg;
	}

	// UPDATE a Customer
	@PUT
	@Path("updateCustomer")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String updateCustomer(String jsonString)
			throws ConnectionException, SelectCustomerException, SQLException, Exception {

		String failMsg = "FAILED TO UPDATE A CUSTOMEER " + " - please enter another customer id or check your data";

		AdminFacade admin = getFacade();

		try {
			JsonParser parser = new JsonParser();
			JsonObject object = parser.parse(jsonString).getAsJsonObject();
			long id = Long.parseLong(object.get("id").getAsString());
			String custName = object.get("custName").getAsString();
			String password = object.get("password").getAsString();
			Customer customer = admin.selectCustomer(id);
			customer = admin.updateCustomer(id, custName, password);

			// if (customer != null) {
			// customer.setCustName(custName);
			// customer.setPassword(password);
			// admin.updateCustomer(customer, custName, password);
			System.out.println("SUCCEED TO UPDATE A CUSTOMEER: " + custName + ", password = " + customer.getPassword()
					+ ", id = " + id);
			return new Gson().toJson(customer) + "SUCCEED TO UPDATE A CUSTOMEER: " + custName + ", password = "
					+ customer.getPassword() + ", id = " + id;
			// }
		} catch (UpdateCustomerException e) {
			e.printStackTrace();
		}

		return failMsg;

	}

	// //UPDATE a Customer
	// @PUT
	// @Path("updateCustomer")
	// @Consumes(MediaType.APPLICATION_JSON)
	// @Produces(MediaType.APPLICATION_JSON)
	// public String updateCustomer(@QueryParam("id") long id,
	// @QueryParam("custName") String custName,
	// @QueryParam("password") String password) throws ConnectionException,
	// SelectCustomerException, SQLException,
	// Exception {
	//
	// String failMsg = "FAILED TO UPDATE A CUSTOMEER: there is no such id! " + id +
	// " - please enter another customer id";
	//
	// AdminFacade admin = getFacade();
	//
	// try {
	// Customer customer = admin.selectCustomer(id);
	//
	// if (customer != null) {
	// customer.setCustName(custName);
	// customer.setPassword(password);
	// admin.updateCustomer(customer, custName, password);
	// System.out.println("SUCCEED TO UPDATE A CUSTOMEER: " + custName + ", password
	// = " + customer.getPassword() + ", id = " + id);
	// return new Gson().toJson(customer) + "SUCCEED TO UPDATE A CUSTOMEER: " +
	// custName + ", password = " + customer.getPassword() + ", id = " + id;
	// }
	// } catch (UpdateCustomerException e) {
	// e.printStackTrace();
	// }
	//
	// return failMsg;
	//
	// }

	// GET a Customer
	@GET
	@Path("getCustomer")
	@Produces(MediaType.APPLICATION_JSON)
	public String getCustomer(@QueryParam("id") long id) throws ConnectionException, SQLException, Exception {

		AdminFacade admin = getFacade();

		try {
			Customer customer = admin.selectCustomer(id);
			if (customer != null) {
				// return new Gson().toJson(new Customer(customer));
				System.out.println("SUCCEED TO GET A CUSTOMER BY ID: " + id);
				return new Gson().toJson(customer) + "SUCCEED TO GET A CUSTOMER BY ID " + id;
			}
		} catch (SelectCustomerException e) {
			e.printStackTrace();
		}

		System.err.println(
				"FAILED GET A CUSTOMER BY ID: there is no such id!" + id + " - please enter another customer id");

		return null;
	}

	// GET All Customers
	@GET
	@Path("getAllCustomers")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllCustomers() throws SQLException, ConnectionException, Exception {

		// Getting the session and the logged in facade object
		AdminFacade admin = getFacade();

		// Get the List of all the Customers from the Table in the DataBase

		try {
			Collection<Customer> customers = admin.selectAllCustomers();
			Collection<Customer> customersInfo = new ArrayList<>();

			if (!customers.isEmpty()) {
				for (Customer customer : customers) {
					Customer customerInfo = customer;
					customersInfo.add(customerInfo);
				}
				return new Gson().toJson(customersInfo) + "SUCCEED TO GET ALL CUSTOMERS";
			}
			// return new Gson().toJson(customersInfo) + "SUCCEED TO GET ALL CUSTOMERS";

		} catch (SelectAllCustomersException e) {
			e.printStackTrace();
		}

		System.out.println("AdminService: FAILED GET ALL CUSTOMERS: there are no customers in the DB table!");
		return null;
	}

	// REMOVE All Customers
	@DELETE
	@Path("removeAllCustomers")
	@Produces(MediaType.APPLICATION_JSON)
	public String removeAllCustomers()
			throws ConnectionException, SelectAllCustomersException, SQLException, Exception {

		String failMsg = "FAILED TO REMOVE ALL CUSTOMERS";

		AdminFacade admin = getFacade();
		Set<Customer> customers = null;
		try {
			customers = admin.selectAllCustomers();
			if (customers != null) {
				admin.deleteAllCustomers();
				System.out.println("SUCCEED TO REMOVE ALL CUSTOMERS");
				// return "SUCCEED TO REMOVE ALL CUSTOMERS";
				return new Gson().toJson(customers) + "SUCCEED TO REMOVE ALL CUSTOMERS";
			}
		} catch (DeleteAllCustomersException e1) {
			e1.printStackTrace();
		}

		return failMsg;
	}

}
