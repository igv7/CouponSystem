package com.igor.beans;
import java.sql.Date;
import java.time.LocalDate;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.igor.config.SqlDateAdapter;
import com.igor.enums.CouponType;

//import com.igor.config.DateFormat;

@XmlRootElement
public class Coupon {
	
	private long id;
	private String title;
	private Date startDate;
	private Date endDate;
	private int amount;
	private CouponType type;
	private String message;
	private double price;
	private String image;
	
	
	public Coupon() {
		
	}
	
	
	public Coupon(long id, String title, Date startDate, Date endDate, int amount, CouponType type, String message,
			double price, String image) {
		
		setId(id);
		setTitle(title);
		setStartDate(startDate);
		setEndDate(endDate);
		setAmount(amount);
		setType(type);
		setMessage(message);
		setPrice(price);
		setImage(image);
	}
	
	public Coupon(String title, Date startDate, Date endDate, int amount, CouponType type, String message,
			double price, String image) {
		
//		setId(id);
		setTitle(title);
		setStartDate(startDate);
		setEndDate(endDate);
		setAmount(amount);
		setType(type);
		setMessage(message);
		setPrice(price);
		setImage(image);
	}
	
	
	
	
	
	

//	public Coupon(long id, String title, String sDate, String eDate, int amount, String type, String message,
//			double price, String image) {
//		
//		setId(id);
//		setTitle(title);
//		setStartDate(SqlDateAdapter.marshal(Date.valueOf(startDate)));
////		setEndDate(DateFormat.simpleDateFormat.format((Date)getEndDate()));
//		setStartDate(sDate);
//		setEndDate(endDate);
////		setStartDate(Date.valueOf(startDate));
////		setEndDate(Date.valueOf(endDate));
//		setAmount(amount);
//		setType(CouponType.valueOf(type));
//		setMessage(message);
//		setPrice(price);
//		setImage(image);
//	}
////	public Coupon(String title, String date1, String date2, int amount, String type, String message,
////			double price, String image) {
////		
////
////		setTitle(title);
////		setStartDate(Date.valueOf(DateFormat.date1));
////		setEndDate(endDate);
////		setAmount(amount);
////		setType(CouponType.valueOf(type));
////		setMessage(message);
////		setPrice(price);
////		setImage(image);
////	}

	
	
	
	
	
	
	
	
	public Coupon(long id, String title, Date startDate, Date endDate, CouponType type, String message,
			double price, String image) {
		
		setId(id);
		setTitle(title);
		setStartDate(startDate);
		setEndDate(endDate);
		setAmount(1);
		setType(type);
		setMessage(message);
		setPrice(price);
		setImage(image);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@XmlJavaTypeAdapter(SqlDateAdapter.class)
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	@XmlJavaTypeAdapter(SqlDateAdapter.class)
	public Date getEndDate() {
		return endDate;
	}
	
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	public CouponType getType() {
		return type;
	}

	public void setType(CouponType type) {
		this.type = type;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	
	

	@Override
	public String toString() {
		return "Coupon [id=" + id + ", title=" + title + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", amount=" + amount + ", type=" + type + ", message=" + message + ", price=" + price + ", image="
				+ image + "]";
	}
	
	
	public String toStringc() {
		return "Coupon [id=" + id + ", title=" + title + ", startDate=" + startDate + ", endDate=" + endDate
				+  ", type=" + type + ", message=" + message + ", price=" + price + ", image="
				+ image + "]";
	}
	

}
