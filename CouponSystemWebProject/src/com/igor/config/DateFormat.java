//package com.igor.config;
//
//import java.sql.Date;
//import java.text.SimpleDateFormat;
//import java.time.LocalDate;
//
//public class DateFormat {
//	
//	public Date startDate;
//	public Date endDate;
//	
//	
//	public Date startDate(String date) {
//		startDate = simpleDateFormat.format(Date.valueOf(date));
//		return startDate;
//		
//	}
//	
//	
//	public Date endDate(String date) {
//		endDate = Date.valueOf(date);
//		return endDate;
//		
//	}
//	
//	public int x;
//
//	public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
//	String sDate = simpleDateFormat.format(Date.valueOf(LocalDate.now()));
//	String eDateMinusDays = simpleDateFormat.format(Date.valueOf(LocalDate.now().minusDays(5)));
//	String eDatePlusDays = simpleDateFormat.format(Date.valueOf(LocalDate.now().plusDays(5)));
//
//	
//	
//	
//	
//	
//	
//	
//	
//	static String stDate = "";
//	String enDate = "";
//	public static Date date1 = Date.valueOf(stDate);
//	Date date2 = Date.valueOf(enDate);
//
//}
