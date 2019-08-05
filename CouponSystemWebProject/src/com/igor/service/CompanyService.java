package com.igor.service;

import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.igor.beans.Company;
import com.igor.beans.Coupon;
import com.igor.enums.ClientType;
import com.igor.enums.CouponType;
import com.igor.exceptions.ConnectionException;
import com.igor.exceptions.DeleteAllCouponsException;
import com.igor.exceptions.DeleteCouponException;
import com.igor.exceptions.GetCouponByTypeException;
import com.igor.exceptions.InsertCouponException;

import com.igor.exceptions.SelectAllCouponsException;
import com.igor.exceptions.SelectCouponException;
import com.igor.exceptions.UpdateCouponException;
import com.igor.facade.CompanyFacade;



@Path("company")
public class CompanyService {
	
	@Context
	private HttpServletRequest request;
	@Context
	private HttpServletResponse response;
	
	private CompanyFacade getFacade() {
		CompanyFacade company = null;
		company = (CompanyFacade) request.getSession(false).getAttribute("facade");
		return company;
	}
	
//	//CREATE a new Coupon in DataBase
//			@POST
//			@Path("createCoupon")
//			@Consumes(MediaType.APPLICATION_JSON)
//			@Produces(MediaType.APPLICATION_JSON)
//			public String createCoupon(String jsonString) throws ConnectionException, SQLException, Exception {
//						
//				System.out.println("1");
//				CompanyFacade company = getFacade();
//				System.out.println("2");
//				try {
//					System.out.println("3");
//					Gson gson = new Gson();
//					System.out.println("4");
//					JsonParser parser = new JsonParser();
//					System.out.println("5");
//					JsonObject object = (JsonObject) parser.parse(jsonString);
//					System.out.println(String.valueOf(object));
//					System.out.println("6");
////					Coupon coupon = new Coupon();
//					Coupon coupon = gson.fromJson(object, Coupon.class);
//					System.out.println("7");
//					coupon = company.insertCoupon(coupon);
//
//					System.out.println("8");
//					return new Gson().toJson(coupon);
//
//				} catch (InsertCouponException e) {
//					e.printStackTrace();
//				}
//				System.out.println("9");
//
//				return null;
//			}
	
	
	//CREATE a new Coupon in DataBase
		@POST
		@Path("createCoupon")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public String createCoupon(Coupon coupon) throws ConnectionException, SQLException, Exception {
				
			
			CompanyFacade company = getFacade();
			String failMsg = "FAILED TO ADD A NEW COUPON: " + "There is already a coupon with the same title: " + coupon.getTitle()
			+ " - please change the coupon title";
			
			try {
				System.out.println(String.valueOf(coupon));
				coupon = company.insertCoupon(coupon);

				
				System.out.println("SUCCEED TO ADD A NEW COUPON: title = " + coupon.getTitle() + ", id = " + coupon.getId());
				return new Gson().toJson(coupon) + "SUCCEED TO ADD A NEW COUPON: title = " + coupon.getTitle() + ", id = " + coupon.getId();

			} catch (InsertCouponException e) {
				e.printStackTrace();
			}

			return failMsg;
		}
		
	
//	//CREATE a new Coupon in DataBase
//	@POST
//	@Path("createCoupon")
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)
//	public String createCoupon(@QueryParam("title") String title, @QueryParam("startDate") Date startDate, 
//			@QueryParam("endDate") Date endDate, @QueryParam("amount") int amount, @QueryParam("type") CouponType type, 
//			@QueryParam("message") String message, @QueryParam("price") double price, @QueryParam("image") String image) 
//					throws ConnectionException, SQLException, Exception {
//				
//		System.out.println("Coupon to add: " + title + ", " + startDate + ", " + endDate + ", " + amount + ", " + type 
//				+ ", " + message + ", " + price + ", " + image);
//		
//		CompanyFacade company = getFacade();
//		
//		String failMsg = "FAILED TO ADD A NEW COUPON: TITLE: " + title + "There is already a coupon with the same title: " 
//		+ title + ", or it has a wrong amount: 0" + ", or the date has expired. " + " - please check the data";
//		System.out.println(111);
//		Coupon coupon = new Coupon(title, startDate, endDate, amount, type, message, price, image);
//		System.out.println(coupon);
//		System.out.println(222);
//		try {
//			
////			Gson gson = new Gson();//
////			JsonParser parser = new JsonParser();//
////			JsonObject object = (JsonObject) parser.parse(jsonString);//
////			Coupon coupon = gson.fromJson(object, Coupon.class);//
//			
//			company.insertCoupon(coupon);
////			Set<Coupon> coupons = company.selectAllCoupons();//
////			Iterator<Coupon> iterator = coupons.iterator();//
////			while (iterator.hasNext()) {//
////				Coupon current = iterator.next();//
////				if (coupons.isEmpty() || !coupon.getTitle().equals(current.getTitle()) 
////					|| coupon.getEndDate().after(Date.valueOf(LocalDate.now())) || coupon.getAmount() > 0) {//
////					company.insertCoupon(coupon);//
//			System.out.println("SUCCEED TO ADD A NEW COUPON: title = " + title + ", startDate = " + startDate 
//					+ ", endDate = " + endDate + ", amount = " + amount + ", type =  " + type + ", message =  " + message 
//					+ ", price = " + price + ", image = " + image);
//			return new Gson().toJson(coupon) + "SUCCEED TO ADD A NEW COUPON: title = " + title + ", startDate = " + startDate 
//					+ ", endDate = " + endDate + ", amount = " + amount + ", type =  " + type + ", message =  " + message 
//					+ ", price = " + price + ", image = " + image;
////				}//
////			}//
//		} catch (InsertCouponException e) {
//			e.printStackTrace();
//		}
//		return failMsg;
//	}
	
	// REMOVE a Coupon
		@DELETE
		@Path("removeCoupon/{id}")
		@Produces(MediaType.APPLICATION_JSON)
		public String removeCoupon(@PathParam("id") long id)
				throws ConnectionException, SelectCouponException, SQLException, Exception {

			String failMsg = "FAILED TO REMOVE A COUPON: there is no such id! " + id
					+ " - please enter another coupon id";

			CompanyFacade company = getFacade();
			Coupon coupon = null;
			
			try {
				coupon = company.selectCoupon(id);
				System.out.println("COUPON TO REMOVE: " + " " + String.valueOf(coupon));
				if (coupon != null) {
					System.out.println("STATUS: 200 OK. SUCCEED TO REMOVE A COUPON: title = " + coupon.getTitle() + ", id = " + id);
					company.deleteCoupon(coupon);
//					return "SUCCEED TO REMOVE A COUPON: title = " + coupon.getTitle() + ", id = " + id);
					return new Gson().toJson(coupon) + "SUCCEED TO REMOVE A COUPON: title = " + coupon.getTitle() + ", id = " + id;
				}
				
			} catch (DeleteCouponException e1) {
				e1.printStackTrace();
			}
			return failMsg;
		}
		
		// UPDATE a coupon
		@PUT
		@Path("updateCoupon")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public String updateCoupon(Coupon coupon)
				throws ConnectionException, SelectCouponException, SQLException, Exception {
			
			String failMsg = "FAILED TO UPDATE A COUPON: TITLE: " + " - please check the data";

			CompanyFacade company = getFacade();
			Coupon coupon2 = null;

			try {
//				JsonParser parser = new JsonParser();
//				JsonObject obj = parser.parse(jsonString).getAsJsonObject();
//				long id = Long.parseLong(obj.get("id").getAsString());
//				String title = obj.get("title").getAsString();
//				String startDate = obj.get("startDate").getAsString();
//				String endDate = obj.get("endDate").getAsString();
//				int amount = Integer.parseInt(obj.get("amount").getAsString());
//				String type = obj.get("type").getAsString();
//				String message = obj.get("message").getAsString();
//				double price = Double.parseDouble(obj.get("price").getAsString());
//				String image = obj.get("image").getAsString();
				
				coupon2 = company.selectCoupon(coupon.getId());
				if (coupon2 != null) {
					coupon2 = company.updateCoupon(coupon);
				}
				
//				company.updateCoupon(id, title, startDate, endDate, amount, type, message, price, image);
////
//				if (coupon != null) {
//					coupon.setTitle(title);
//					coupon.setStartDate(startDate);
//					coupon.setEndDate(endDate);
//					coupon.setAmount(amount);
//					coupon.setType(type);
//					coupon.setMessage(message);
//					coupon.setPrice(price);
//					coupon.setImage(image);
//					company.updateCoupon(coupon, title, startDate, endDate, amount, type, message, price, image);
					System.out.println("SUCCEED TO UPDATE A COUPON: " + coupon.getTitle() + ", startDate = " + coupon.getStartDate() 
										+ ", endDate = " + coupon.getEndDate() + ", amount = " + coupon.getAmount() + ", type = " + coupon.getType() 
										+ ", message = " + coupon.getMessage() + ", price = " + coupon.getPrice() + ", image = " + coupon.getImage());
////					return "SUCCEED TO UPDATE A COUPON: " + title + ", startDate = " + startDate 
////					+ ", endDate = " + endDate + ", amount = " + amount + ", type = " + type 
////					+ ", message = " + message + ", price = " + price + ", image = " + image);
					return new Gson().toJson(coupon) + "SUCCEED TO UPDATE A COUPON: " + coupon.getTitle() + ", startDate = " + coupon.getStartDate() 
					+ ", endDate = " + coupon.getEndDate() + ", amount = " + coupon.getAmount() + ", type = " + coupon.getType() 
					+ ", message = " + coupon.getMessage() + ", price = " + coupon.getPrice() + ", image = " + coupon.getImage();
//				}
			} catch (UpdateCouponException e) {
				e.printStackTrace();
			}

			return failMsg;

		}
		
//		// UPDATE a coupon
//		@PUT
//		@Path("updateCoupon")
//		@Consumes(MediaType.APPLICATION_JSON)
//		@Produces(MediaType.APPLICATION_JSON)
//		public String updateCoupon(String jsonString)
//				throws ConnectionException, SelectCouponException, SQLException, Exception {
//			
//			String failMsg = "FAILED TO UPDATE A COUPON: TITLE: " + " - please check the data";
//
//			CompanyFacade company = getFacade();
//
//			try {
//				JsonParser parser = new JsonParser();
//				JsonObject obj = parser.parse(jsonString).getAsJsonObject();
//				long id = Long.parseLong(obj.get("id").getAsString());
//				String title = obj.get("title").getAsString();
//				String startDate = obj.get("startDate").getAsString();
//				String endDate = obj.get("endDate").getAsString();
//				int amount = Integer.parseInt(obj.get("amount").getAsString());
//				String type = obj.get("type").getAsString();
//				String message = obj.get("message").getAsString();
//				double price = Double.parseDouble(obj.get("price").getAsString());
//				String image = obj.get("image").getAsString();
//				
//				Coupon coupon = company.selectCoupon(id);
////				coupon = company.updateCoupon(coupon);
//				company.updateCoupon(id, title, startDate, endDate, amount, type, message, price, image);
//////
////				if (coupon != null) {
////					coupon.setTitle(title);
////					coupon.setStartDate(startDate);
////					coupon.setEndDate(endDate);
////					coupon.setAmount(amount);
////					coupon.setType(type);
////					coupon.setMessage(message);
////					coupon.setPrice(price);
////					coupon.setImage(image);
////					company.updateCoupon(coupon, title, startDate, endDate, amount, type, message, price, image);
//					System.out.println("SUCCEED TO UPDATE A COUPON: " + coupon.getTitle() + ", startDate = " + coupon.getStartDate() 
//										+ ", endDate = " + coupon.getEndDate() + ", amount = " + coupon.getAmount() + ", type = " + coupon.getType() 
//										+ ", message = " + coupon.getMessage() + ", price = " + coupon.getPrice() + ", image = " + coupon.getImage());
//////					return "SUCCEED TO UPDATE A COUPON: " + title + ", startDate = " + startDate 
//////					+ ", endDate = " + endDate + ", amount = " + amount + ", type = " + type 
//////					+ ", message = " + message + ", price = " + price + ", image = " + image);
//					return new Gson().toJson(coupon) + "SUCCEED TO UPDATE A COUPON: " + coupon.getTitle() + ", startDate = " + coupon.getStartDate() 
//					+ ", endDate = " + coupon.getEndDate() + ", amount = " + coupon.getAmount() + ", type = " + coupon.getType() 
//					+ ", message = " + coupon.getMessage() + ", price = " + coupon.getPrice() + ", image = " + coupon.getImage();
////				}
//			} catch (UpdateCouponException e) {
//				e.printStackTrace();
//			}
//
//			return failMsg;
//
//		}

//		// UPDATE a coupon
//		@PUT
//		@Path("updateCoupon")
//		@Consumes(MediaType.APPLICATION_JSON)
//		@Produces(MediaType.APPLICATION_JSON)
//		public String updateCoupon(@QueryParam("id") long id, @QueryParam("title") String title, @QueryParam("startDate") Date startDate, 
//				@QueryParam("endDate") Date endDate, @QueryParam("amount") int amount, @QueryParam("type") CouponType type, 
//				@QueryParam("message") String message, @QueryParam("price") double price, @QueryParam("image") String image)
//				throws ConnectionException, SelectCouponException, SQLException, Exception {
//			
//			String failMsg = "FAILED TO UPDATE A COUPON: TITLE: " + title + " - please check the data";
//
//			CompanyFacade company = getFacade();
//
//			try {
//				Coupon coupon = company.selectCoupon(id);
//
//				if (coupon != null) {
//					coupon.setTitle(title);
//					coupon.setStartDate(startDate);
//					coupon.setEndDate(endDate);
//					coupon.setAmount(amount);
//					coupon.setType(type);
//					coupon.setMessage(message);
//					coupon.setPrice(price);
//					coupon.setImage(image);
//					company.updateCoupon(coupon, title, startDate, endDate, amount, type, message, price, image);
//					System.out.println("SUCCEED TO UPDATE A COUPON: " + title + ", startDate = " + startDate 
//										+ ", endDate = " + endDate + ", amount = " + amount + ", type = " + type 
//										+ ", message = " + message + ", price = " + price + ", image = " + image);
////					return "SUCCEED TO UPDATE A COUPON: " + title + ", startDate = " + startDate 
////					+ ", endDate = " + endDate + ", amount = " + amount + ", type = " + type 
////					+ ", message = " + message + ", price = " + price + ", image = " + image);
//					return new Gson().toJson(coupon) + "SUCCEED TO UPDATE A COUPON: " + title + ", startDate = " + startDate 
//							+ ", endDate = " + endDate + ", amount = " + amount + ", type = " + type 
//							+ ", message = " + message + ", price = " + price + ", image = " + image;
//				}
//			} catch (UpdateCouponException e) {
//				e.printStackTrace();
//			}
//
//			return failMsg;
//
//		}
	
	//GET a Coupon
	@GET
	@Path("getCoupon")
	@Produces(MediaType.APPLICATION_JSON)
	public String getCoupon(@QueryParam("id") long id) throws ConnectionException, SQLException, Exception {
		
		CompanyFacade company = getFacade();
		
		try {
			Coupon coupon = company.selectCoupon(id);
			if (coupon != null) {
//				return new Gson().toJson(new Coupon(coupon));
				System.out.println("STATUS: 200 OK. SUCCEED TO GET A COUPON BY ID: " + id);
				return new Gson().toJson(coupon) + "SUCCEED TO GET A COUPON BY ID " + id;
			}
		} catch (SelectCouponException e) {
			e.printStackTrace();
		}
		
		System.err.println("FAILED GET A COUPON BY ID: there is no such id!" + id + " - please enter another coupon id");
		
		return null;
	}
	
	//GET All Coupons
		@GET
		@Path("getAllCoupons")
		@Produces(MediaType.APPLICATION_JSON)
		public String getAllCoupons() throws SQLException, ConnectionException, Exception {

			// Getting the session and the logged in facade object
			CompanyFacade company = getFacade();

			// Get the List of all the Coupons from the Table in the DataBase

			try {
				Collection<Coupon> coupons = company.selectAllCoupons();
				Collection<Coupon> couponsInfo = new ArrayList<>();

				if (!coupons.isEmpty()) {
					for (Coupon coupon : coupons) {
						Coupon couponInfo = coupon;
						couponsInfo.add(couponInfo);
					}
					System.out.println("STATUS: 200 OK. SUCCEED TO GET ALL COUPONS");
					return new Gson().toJson(couponsInfo) + "SUCCEED TO GET ALL COUPONS";
				}
//				return new Gson().toJson(couponsInfo) + "SUCCEED TO GET ALL COUPONS";

			} catch (SelectAllCouponsException e) {
				e.printStackTrace();
			}

			System.out.println("CompanyService: FAILED GET ALL COUPONS: there are no coupons in the DB table!");
			return null;
		}
		
		//GET Coupons by type
				@GET
				@Path("getCouponByType")
				@Produces(MediaType.APPLICATION_JSON)
				public String getCouponByType(@QueryParam("type") CouponType type) throws ConnectionException, SQLException, Exception {
					
					CompanyFacade company = getFacade();
					
					try {
						Collection<Coupon> coupons = company.selectAllCoupons();
						Collection<Coupon> couponsByType = new ArrayList<>();
						if (!coupons.isEmpty()) {
//							for (Coupon coupon : coupons) {
//								Coupon couponByType = coupon;
								couponsByType.addAll(company.getCouponByType(type));
//							}
							System.out.println("SUCCEED TO GET A COUPON BY TYPE: " + type);
							return new Gson().toJson(couponsByType) + "SUCCEED TO GET A COUPON BY TYPE " + type;
						}
					} catch (GetCouponByTypeException e) {
						e.printStackTrace();
					}
					System.err.println("FAILED GET A COUPON BY TYPE: " + type + " - please check your data");
					return null;
				}
		
//		//GET Coupons by type
//		@GET
//		@Path("getCouponByType")
//		@Produces(MediaType.APPLICATION_JSON)
//		public String getCouponByType(@QueryParam("type") CouponType type) throws ConnectionException, SQLException, Exception {
//			System.out.println("CompanyService 111");
//			CompanyFacade company = getFacade();
//			System.out.println("CompanyService 222");
//			try {
//				System.out.println("CompanyService 333");
//				Collection<Coupon> coupons = company.selectAllCoupons();
//				System.out.println("CompanyService 444");
//				Collection<Coupon> couponsByType = new ArrayList<>();
//				System.out.println("CompanyService 555");
//				if (!coupons.isEmpty()) {
//					System.out.println("CompanyService 666");
//					for (Coupon coupon : coupons) {
//						System.out.println("CompanyService 777");
////						Coupon couponByType = coupon;
//						couponsByType.addAll(company.getCouponByType(type));
//						System.out.println("CompanyService 888");
//					}
//					System.out.println("SUCCEED TO GET A COUPON BY TYPE: " + type);
//					return new Gson().toJson(couponsByType) + "SUCCEED TO GET A COUPON BY TYPE " + type;
//				}
//			} catch (GetCouponByTypeException e) {
//				e.printStackTrace();
//			}
//			System.err.println("FAILED GET A COUPON BY TYPE: " + type + " - please check your data");
//			return null;
//		}
				
				//REMOVE All Coupons
				@DELETE
				@Path("removeAllCoupons")
				@Produces(MediaType.APPLICATION_JSON)
				public String removeAllCoupons() throws ConnectionException, SelectAllCouponsException, SQLException, Exception {

					String failMsg = "FAILED TO REMOVE ALL COUPONS";

					CompanyFacade company = getFacade();
					Collection<Coupon> coupons = null;
					
					try {
//						coupons = company.selectAllCoupons();
//						if (coupons != null) {
							coupons = company.deleteAllCompanyCoupons();//coupons = company.deleteAllCompanyCoupons();
							System.out.println("STATUS: 200 OK. SUCCEED TO REMOVE ALL COUPONS");
//							return "SUCCEED TO REMOVE ALL COUPONS";
							return new Gson().toJson(coupons) + "SUCCEED TO REMOVE ALL COUPONS";
//						}
					} catch (DeleteAllCouponsException e1) {
						e1.printStackTrace();
					}

					return failMsg;
				}
		
//		//REMOVE All Coupons
//		@DELETE
//		@Path("removeAllCoupons")
//		@Produces(MediaType.APPLICATION_JSON)
//		public String removeAllCoupons() throws ConnectionException, SelectAllCouponsException, SQLException, Exception {
//
//			String failMsg = "FAILED TO REMOVE ALL COUPONS";
//
//			CompanyFacade company = getFacade();
//			Set<Coupon> coupons = null;
//			try {
//				coupons = company.selectAllCoupons();
//				if (coupons != null) {
//					company.deleteAllCompanyCoupons();//coupons = company.deleteAllCompanyCoupons();
//					System.out.println("STATUS: 200 OK. SUCCEED TO REMOVE ALL COUPONS");
////					return "SUCCEED TO REMOVE ALL COUPONS";
//					return new Gson().toJson(coupons) + "SUCCEED TO REMOVE ALL COUPONS";
//				}
//			} catch (DeleteAllCouponsException e1) {
//				e1.printStackTrace();
//			}
//
//			return failMsg;
//		}
		
//		//REMOVE All Coupons
//		@DELETE
//		@Path("removeAllCoupons")
//		@Produces(MediaType.APPLICATION_JSON)
//		public String removeAllCoupons() throws ConnectionException, SelectAllCouponsException, SQLException, Exception {
//
//			String failMsg = "FAILED TO REMOVE ALL COUPONS";
//
//			CompanyFacade company = getFacade();
//			Set<Coupon> coupons = null;
//			try {
//				coupons = company.selectAllCoupons();
//				if (coupons != null) {
//					company.deleteAllCoupons();
//					System.out.println("STATUS: 200 OK. SUCCEED TO REMOVE ALL COUPONS");
////					return "SUCCEED TO REMOVE ALL COUPONS";
//					return new Gson().toJson(coupons) + "SUCCEED TO REMOVE ALL COUPONS";
//				}
//			} catch (DeleteAllCouponsException e1) {
//				e1.printStackTrace();
//			}
//
//			return failMsg;
//		}

}
