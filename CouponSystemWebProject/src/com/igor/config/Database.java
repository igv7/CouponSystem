package com.igor.config;
//import java.awt.image.VolatileImage;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
	
	public static String getDriverData() {
		return "org.apache.derby.jdbc.ClientDriver";
	}

	public static String getDBUrl() {
		return "jdbc:derby://localhost:3301/JBDB;create=true";
	}
	
	
	private static ConnectionPool connectionPool;

	
	public static void createTableCompany() throws Exception {
		connectionPool = ConnectionPool.getInstance();
		Connection con = connectionPool.getConnection();
		try {
			Statement stmt = con.createStatement();
			String sql;

			sql = "create table Company ("
					+ "id bigint not null primary key generated always as identity(start with 1, increment by 1), "
					+ "name varchar(50) not null, " + "password varchar(50) not null, " + "email varchar(50) not null)";
			stmt.executeUpdate(sql);
			System.out.println("success: Company table has been created " + sql);
		} catch (SQLException e) {
			throw new Exception("Unable to create Company table");
		} finally {
			con.close();
			connectionPool.returnConnection(con);
		}
		
		
	}
	
	public static void createTableCustomer() throws Exception {
		connectionPool = ConnectionPool.getInstance();
		Connection con = connectionPool.getConnection();
		try {
			Statement stmt = con.createStatement();
			String sql;

			sql = "create table Customer ("
					+ "id bigint not null primary key generated always as identity(start with 1, increment by 1), "
					+ "name varchar(50) not null, " + "password varchar(50) not null)";
			stmt.executeUpdate(sql);
			System.out.println("success: Customer table has been created " + sql);
		} catch (SQLException e) {
			throw new Exception("Unable to create Customer table");
		} finally {
			con.close();
			connectionPool.returnConnection(con);
		}
		
		
	}
	
	public static void createTableCoupon() throws Exception {
		connectionPool = ConnectionPool.getInstance();
		Connection con = connectionPool.getConnection();
		try {
			Statement stmt = con.createStatement();
			String sql;

			sql = "create table Coupon ("
					+ "id bigint not null primary key generated always as identity(start with 1, increment by 1), "
					+ "title varchar(50) not null, " + "startdate date not null, " + "enddate date not null, " 
					+ "amount int not null, " + "type varchar(50) not null, " + "message varchar(50) not null, " 
					+ "price double not null, " + "image char(250) not null)";
			stmt.executeUpdate(sql);
			System.out.println("success: Coupon table has been created " + sql);
		} catch (SQLException e) {
			throw new Exception("Unable to create Coupon table");
		} finally {
			con.close();
			connectionPool.returnConnection(con);
		}
		
		
	}
	
	public static void cteateTableCompanyCoupon() throws Exception {
		connectionPool = ConnectionPool.getInstance();
		Connection con = connectionPool.getConnection();
		try {
			Statement stmt = con.createStatement();
			String sql;
			
			sql = "create table Company_Coupon ("
					+ "comp_id bigint not null, " + "foreign key (comp_id) references Company (id), " 
					+ "coupon_id bigint not null, " + "foreign key (coupon_id) references Coupon (id), " 
					+ "primary key (comp_id, coupon_id))"; 
			stmt.executeUpdate(sql);
			System.out.println("success: Company_Coupon table has been created " + sql);
		} catch (SQLException e) {
			throw new Exception("Unable to create Company_Coupon table");
		} finally {
			con.close();
			connectionPool.returnConnection(con);
		}
		
	}
	
	public static void createTableCustomerCoupon() throws Exception {
		connectionPool = ConnectionPool.getInstance();
		Connection con = connectionPool.getConnection();
		try {
			Statement stmt = con.createStatement();
			String sql;
			
			sql = "create table Customer_Coupon ("
					+ "cust_id bigint not null, " + "foreign key (cust_id) references Customer (id), " 
					+ "coupon_id bigint not null, " + "foreign key (coupon_id) references Coupon (id), " 
					+ "primary key (cust_id, coupon_id))";
			stmt.executeUpdate(sql);
			System.out.println("success: Customer_Coupon table has been created " + sql);
		} catch (SQLException e) {
			throw new Exception("Unable to create Customer_Coupon table");
		} finally {
			con.close();
			connectionPool.returnConnection(con);
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	public static void dropTableCompany() throws Exception {
		connectionPool = ConnectionPool.getInstance();
		Connection con = connectionPool.getConnection();
		try {
			Statement stmt = con.createStatement();
			String sql;
			
			sql = "DROP table Company";
			stmt.executeUpdate(sql);
			System.out.println("success: Company Table dropped successfully " + sql);
		} catch (SQLException e) {
			throw new Exception("Unable to drop Company Table");
		} finally {
			con.close();
			connectionPool.returnConnection(con);
		}
		
	}
	
	public static void dropTableCustomer() throws Exception {
		connectionPool = ConnectionPool.getInstance();
		Connection con = connectionPool.getConnection();
		try {
			Statement stmt = con.createStatement();
			String sql;
			
			sql = "DROP table Customer";
			stmt.executeUpdate(sql);
			System.out.println("success: Customer Table dropped successfully " + sql);
		} catch (SQLException e) {
			throw new Exception("Unable to drop Customer Table");
		} finally {
			con.close();
			connectionPool.returnConnection(con);
		}
		
	}
	
	public static void dropTableCoupon() throws Exception {
		connectionPool = ConnectionPool.getInstance();
		Connection con = connectionPool.getConnection();
		try {
			Statement stmt = con.createStatement();
			String sql;
			
			sql = "DROP table Coupon";
			stmt.executeUpdate(sql);
			System.out.println("success: Coupon Table dropped successfully " + sql);
		} catch (SQLException e) {
			throw new Exception("Unable to drop Coupon Table");
		} finally {
			con.close();
			connectionPool.returnConnection(con);
		}
		
	}
	
	public static void dropTableCompanyCoupon() throws Exception {
		connectionPool = ConnectionPool.getInstance();
		Connection con = connectionPool.getConnection();
		try {
			Statement stmt = con.createStatement();
			String sql;
			
			sql = "DROP table Company_Coupon";
			stmt.executeUpdate(sql);
			System.out.println("success: Company_Coupon Table dropped successfully " + sql);
		} catch (SQLException e) {
			throw new Exception("Unable to drop Company_Coupon Table");
		} finally {
			con.close();
			connectionPool.returnConnection(con);
		}
		
	}
	
	public static void dropTableCustomerCoupon() throws Exception {
		connectionPool = ConnectionPool.getInstance();
		Connection con = connectionPool.getConnection();
		try {
			Statement stmt = con.createStatement();
			String sql;
			
			sql = "DROP table Customer_Coupon";
			stmt.executeUpdate(sql);
			System.out.println("success: Customer_Coupon Table dropped successfully " + sql);
		} catch (SQLException e) {
			throw new Exception("Unable to drop Customer_Coupon Table");
		} finally {
			con.close();
			connectionPool.returnConnection(con);
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	public static void buildDB() throws Exception {
		try {
			Database.createTableCompany();
			Database.createTableCustomer();
			Database.createTableCoupon();
			Database.cteateTableCompanyCoupon();
			Database.createTableCustomerCoupon();
		} catch (Exception e) {
			throw new Exception("Unable to build all tables of DB");
		}
		
	}
	
	public static void dropDB() throws Exception {
		try {
			Database.dropTableCompanyCoupon();
			Database.dropTableCustomerCoupon();
			Database.dropTableCompany();
			Database.dropTableCustomer();
			Database.dropTableCoupon();
		} catch (Exception e) {
			throw new Exception("Unable to drop all tables of DB");
		}
		
	}

}
