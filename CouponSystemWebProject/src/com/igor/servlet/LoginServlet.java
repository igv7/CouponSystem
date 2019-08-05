package com.igor.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.igor.config.CouponSystem;
import com.igor.config.Database;
import com.igor.dao.ClientDAO;
import com.igor.enums.ClientType;
import com.igor.facade.AdminFacade;
import com.igor.facade.CompanyFacade;
import com.igor.facade.CustomerFacade;
import com.sun.org.apache.bcel.internal.generic.RETURN;

//import org.apache.jasper.tagplugins.jstl.core.Out;
public class LoginServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private CouponSystem couponSystem;
	
	public LoginServlet() {
		super();
	}
	
	// init function - start the Coupon System
	public void init() throws ServletException {
		try {
			couponSystem = CouponSystem.getInstance();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Failed to connect to db, Failed to load system");
			System.exit(1);
		}
		System.out.println("Loaded...");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
//		response.setContentType("text/html;charset=UTF-8");//
//        PrintWriter out = response.getWriter();//
//        Database.getDriverData();//
//        Database.getDBUrl();//
		
        
     // check whether there is a open session
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();// killing the session if exist
		}
		
		session = request.getSession(true);// create a new session for a new client
		System.out.println(session.getId() +" * "+ session.getMaxInactiveInterval());
		
		// getting the data from the Login HTML form
		String userName = request.getParameter("name");
		String password = request.getParameter("password");
		String clientType = request.getParameter("type");
		
		
//		session.setAttribute("name", userName);//
//		session.setAttribute("password", password);//
//		session.setAttribute("type", clientType);//
		
//		ClientType type = ClientType.valueOf(clientType);// convert String to ENUM
		ClientType type = ClientType.valueOf(clientType.toUpperCase());// convert String to ENUM
		
		try {
			ClientDAO clientFacade = couponSystem.login(userName, password, type);
			
			System.out.println("loginServlet: request = " + request);
			System.out.println("loginServlet: response = " + response);
			
			if (clientFacade != null) {
				// updating the session with the login facade
				session.setAttribute("facade", clientFacade);
				// dispatcher to the right Page according to the Client Type
				switch (type) {
				case ADMINISTRATOR:
					AdminFacade adminFacade = (AdminFacade) couponSystem.login(userName, password, type);
					session.setAttribute("admin", adminFacade);
					request.getRequestDispatcher("/admin.html").forward(request, response);
					break;
				case COMPANY:
					// updating the session with the logged in company
					CompanyFacade companyFacade = (CompanyFacade) couponSystem.login(userName, password, type);
					session.setAttribute("company", companyFacade);
					request.getRequestDispatcher("/company.html").forward(request, response);
					break;
				case CUSTOMER:
					// updating the session with the logged in customer
					CustomerFacade customerFacade = (CustomerFacade) couponSystem.login(userName, password, type);
					session.setAttribute("customer", customerFacade);
					request.getRequestDispatcher("/customer.html").forward(request, response);
					break;

				default:
					break;
				}
			} else {
				// return to the Login HTML form if the user name or password are incorrect
				response.getWriter().print("The UserName or the Password are incorrect! please try again");
				response.sendRedirect("/login.html");
			}
//			String json = new Gson().toJson(clientFacade);
//		    response.setContentType("application/json");
//		    response.setCharacterEncoding("UTF-8");
//		    response.getWriter().write(json);
		    
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Wrong Password or UserName!");
		}
		
//		out.close();//
//		System.out.println(userName + " " + password + " " + clientType);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	public void destroy() {
		System.out.println("bye.......");
	}

}
