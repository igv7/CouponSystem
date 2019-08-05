package com.igor.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.igor.beans.Coupon;
import com.igor.beans.Customer;
import com.igor.enums.CouponType;
import com.igor.exceptions.ConnectionException;
import com.igor.exceptions.GetAllPurchasedCouponsByPriceException;
import com.igor.exceptions.GetAllPurchasedCouponsByTypeException;
import com.igor.exceptions.SelectCouponsException;
import com.igor.exceptions.SelectCustomerCouponsException;
import com.igor.facade.CustomerFacade;

@Path("customer")
public class CustomerService {

	@Context
	private HttpServletRequest request;
	@Context
	private HttpServletResponse response;

	private CustomerFacade getFacade() {
		CustomerFacade customer = null;
		customer = (CustomerFacade) request.getSession(false).getAttribute("facade");
		return customer;
	}

	// PURCHASE a Coupon
	@POST
	@Path("purchaseCoupon/{coupId}")
	@Produces(MediaType.APPLICATION_JSON)
	public String purchaseCoupon(@PathParam("coupId") long coupId) throws Exception {

		CustomerFacade customer = getFacade();
		Coupon coupon = new Coupon();

		System.out.println("Coupon to purchase: id= " + coupId);

		String failMsg = "FAILED TO PURCHASE A COUPON: ID: " + coupId + " wrong amount: 0 "
				+ " or already purchased same coupon ID: " + coupId + " or the end date already passed "
				+ " - please check your data";

		try {
			coupon = customer.purchaseCoupon(coupId);
			System.out.println("SUCCESS TO PURCHASE A COUPON: ID: " + coupId);
			return new Gson().toJson(coupon) + "SUCCESS TO PURCHASE A COUPON: ID: " + coupId;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return failMsg;
	}

	// //PURCHASE a Coupon
	// @POST
	// @Path("purchaseCoupon")
	// @Consumes(MediaType.APPLICATION_JSON)
	// @Produces(MediaType.APPLICATION_JSON)
	// private String purchaseCoupon(@QueryParam("customerId") long customerId,
	// @QueryParam("coupId") long coupId) {
	//
	// CustomerFacade customer = getFacade();
	// Coupon coupon = new Coupon();
	//
	// System.out.println("Coupon to purchase: id= " + coupId + "Customer id= " +
	// customerId);
	//
	// String failMsg = "FAILED TO PURCHASE A COUPON: ID: " + coupId + " wrong
	// amount: 0 " + " or already purchased same coupon ID: "
	// + coupId + " or the end date already passed " + " - please check your data";
	//
	// try {
	// coupon = customer.purchaseCoupon(coupId, customerId);
	// System.out.println("SUCCESS TO PURCHASE A COUPON: ID: " + coupId);
	// return new Gson().toJson(coupon) + "SUCCESS TO PURCHASE A COUPON: ID: " +
	// coupId;
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	//
	// return failMsg;
	// }
	
	// GET all purchased Coupons
		@GET
		@Path("getAllPurchasedCoupons")
		@Produces(MediaType.APPLICATION_JSON)
		public String getAllPurchasedCoupons()
				throws ConnectionException, SelectCustomerCouponsException, SQLException, Exception {
			System.out.println("1111");
			CustomerFacade customer = getFacade();

			try {
				Collection<Coupon> coupons = customer.getAllPurchasedCoupons();
				if (!coupons.isEmpty()) {
					System.out.println("SUCCESS TO GET ALL PURCHASED COUPONS");
					return new Gson().toJson(coupons) + "SUCCESS TO GET ALL PURCHASED COUPONS";
				}

			} catch (SelectCouponsException e) {
				e.printStackTrace();
			}
			System.out.println(
					"CustomerService: FAILED TO GET ALL PURCHASED COUPONS: there are no purchased coupons in the DB table!");
			return null;

		}

//	// GET all purchased Coupons
//	@GET
//	@Path("getAllPurchasedCoupons")
//	@Produces(MediaType.APPLICATION_JSON)
//	public String getAllPurchasedCoupons(@QueryParam("id") long id)
//			throws ConnectionException, SelectCustomerCouponsException, SQLException, Exception {
//		System.out.println("1111");
//		CustomerFacade customer = getFacade();
//
//		try {
//			Collection<Coupon> coupons = customer.selectCoupons(id);
//			Collection<Coupon> purchasedCoupons = new ArrayList<>();
//			if (!coupons.isEmpty()) {
//				for (Coupon coupon : coupons) {
////					Coupon purchasedCoupon = coupon;
//					purchasedCoupons.addAll(coupons);
//					System.out.println(purchasedCoupons);
//				}
//			}
//			System.out.println("SUCCESS TO GET ALL PURCHASED COUPONS");
//			return new Gson().toJson(purchasedCoupons) + "SUCCESS TO GET ALL PURCHASED COUPONS";
//		} catch (SelectCouponsException e) {
//			e.printStackTrace();
//		}
//		System.out.println(
//				"CustomerService: FAILED TO GET ALL PURCHASED COUPONS: there are no purchased coupons in the DB table!");
//		return null;
//
//	}
		
		// GET all purchased Coupons by type
		@GET
		@Path("getAllPurchasedCouponsByType")
		@Produces(MediaType.APPLICATION_JSON)
		public String getAllPurchasedCouponsByType(@QueryParam("type") CouponType type)
				throws ConnectionException, SelectCustomerCouponsException, SQLException, Exception {

			CustomerFacade customer = getFacade();

			try {
				Collection<Coupon> coupons = customer.getAllPurchasedCouponsByType(type);
//				Collection<Coupon> purchasedCouponsByType = new ArrayList<>();
				if (!coupons.isEmpty()) {
//					for (Coupon coupon : coupons) {
						// Coupon purchasedCouponByType = coupon;
//						purchasedCouponsByType.addAll(customer.getAllPurchasedCouponsByType(type));
//					}
					System.out.println("SUCCESS TO GET ALL PURCHASED COUPONS BY TYPE: " + type);
					return new Gson().toJson(coupons) + "SUCCESS TO GET ALL PURCHASED COUPONS BY TYPE: "
							+ type;
				}
			} catch (SelectCouponsException e) {
				e.printStackTrace();
			} catch (GetAllPurchasedCouponsByTypeException e) {
				e.printStackTrace();
			}
			System.out.println(
					"CustomerService: FAILED TO GET ALL PURCHASED COUPONS BY TYPE: " + type + ". CHECK YOUR DATA!");

			return null;

		}

//	// GET all purchased Coupons by type
//	@GET
//	@Path("getAllPurchasedCouponsByType")
//	@Produces(MediaType.APPLICATION_JSON)
//	public String getAllPurchasedCouponsByType(@QueryParam("type") CouponType type, @QueryParam("id") long id)
//			throws ConnectionException, SelectCustomerCouponsException, SQLException, Exception {
//
//		CustomerFacade customer = getFacade();
//
//		try {
//			Collection<Coupon> coupons = customer.selectCoupons();
//			Collection<Coupon> purchasedCouponsByType = new ArrayList<>();
//			if (!coupons.isEmpty()) {
//				for (Coupon coupon : coupons) {
//					// Coupon purchasedCouponByType = coupon;
//					purchasedCouponsByType.addAll(customer.getAllPurchasedCouponsByType(type, id));
//				}
//				System.out.println("SUCCESS TO GET ALL PURCHASED COUPONS BY TYPE " + type);
//				return new Gson().toJson(purchasedCouponsByType) + "SUCCESS TO GET ALL PURCHASED COUPONS BY TYPE "
//						+ type;
//			}
//		} catch (SelectCouponsException e) {
//			e.printStackTrace();
//		} catch (GetAllPurchasedCouponsByTypeException e) {
//			e.printStackTrace();
//		}
//
//		return null;
//
//	}
		
		// GET all purchased Coupons by price
		@GET
		@Path("getAllPurchasedCouponsByPrice")
		@Produces(MediaType.APPLICATION_JSON)
		public String getAllPurchasedCouponsByPrice(@QueryParam("price") double price)
				throws ConnectionException, SelectCustomerCouponsException, SQLException, Exception {

			CustomerFacade customer = getFacade();

			try {
				Collection<Coupon> coupons = customer.getAllPurchasedCouponsByPrice(price);
//				Collection<Coupon> purchasedCouponsByPrice = new ArrayList<>();
				if (!coupons.isEmpty()) {
//					for (Coupon coupon : coupons) {
//						if (coupon.getPrice() <= price) {
							System.out.println("SUCCESS TO GET ALL PURCHASED COUPONS BY PRICE " + price);
							return new Gson().toJson(coupons) + "SUCCESS TO GET ALL PURCHASED COUPONS BY PRICE LESS THAN OR EQUALS TO "
									+ price;
//						}
//					}
//					System.out.println("SUCCESS TO GET ALL PURCHASED COUPONS BY PRICE " + price);
//					return new Gson().toJson(coupons) + "SUCCESS TO GET ALL PURCHASED COUPONS BY PRICE LESS THAN OR EQUALS TO "
//							+ price;
				}
			} catch (SelectCouponsException e) {
				e.printStackTrace();
			} catch (GetAllPurchasedCouponsByPriceException e) {
				e.printStackTrace();
			}

			return null;

		}

//	// GET all purchased Coupons by price
//	@GET
//	@Path("getAllPurchasedCouponsByPrice")
//	@Produces(MediaType.APPLICATION_JSON)
//	public String getAllPurchasedCouponsByPrice(@QueryParam("price") double price, @QueryParam("id") long id)
//			throws ConnectionException, SelectCustomerCouponsException, SQLException, Exception {
//
//		CustomerFacade customer = getFacade();
//
//		try {
//			Collection<Coupon> coupons = customer.selectCoupons();
//			Collection<Coupon> purchasedCouponsByPrice = new ArrayList<>();
//			if (!coupons.isEmpty()) {
//				for (Coupon coupon : coupons) {
//					// Coupon purchasedCouponsByPrice = coupon;
//					purchasedCouponsByPrice.addAll(customer.getAllPurchasedCouponsByPrice(price, id));
//				}
//				System.out.println("SUCCESS TO GET ALL PURCHASED COUPONS BY PRICE " + price);
//				return new Gson().toJson(purchasedCouponsByPrice) + "SUCCESS TO GET ALL PURCHASED COUPONS BY PRICE "
//						+ price;
//			}
//		} catch (SelectCouponsException e) {
//			e.printStackTrace();
//		} catch (GetAllPurchasedCouponsByPriceException e) {
//			e.printStackTrace();
//		}
//
//		return null;
//
//	}

}
