package com.igor.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;



public class ConnectionPool {
	
	private static ConnectionPool instance = new ConnectionPool();
	private final int MAX_CON_NUM = 10;
	
	private BlockingQueue<Connection>conQ = new LinkedBlockingQueue<>();
	
	private ConnectionPool() {
		
		try {
			Class.forName(Database.getDriverData());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		while (this.conQ.size() < MAX_CON_NUM) {
			try {
				Connection con = DriverManager.getConnection(Database.getDBUrl());
				this.conQ.offer(con);
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		
	}
	
	public static ConnectionPool getInstance() {
		if (instance == null) {
			try {
				instance = new ConnectionPool();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		return instance;
	}
	
	
	
	public synchronized Connection getConnection() throws Exception {

		try {
			while (conQ.isEmpty()) {
				wait();
				System.out.println("ALERT: connection pool is empty, please wait...");
			}
			Connection con = conQ.poll();
			con.setAutoCommit(true);
			return con;
		} catch (Exception e) {
			throw new Exception("failed to get connection.");
		}
	}
	
	public synchronized void returnConnection(Connection con) throws Exception {
		try {
			Connection con1 = DriverManager.getConnection(Database.getDBUrl());
			conQ.offer(con1);
			notifyAll();
		} catch (Exception e) {
			System.out.println("failed to return connection.");
			throw new Exception("Failed to return connection!");
		}
		
	}
	
	public void closeAllConnections() throws Exception {
		Connection con;
		while (this.conQ.peek() != null) {
			con = this.conQ.poll();
			try {
				con.close();
			} catch (Exception e) {
				throw new Exception("Unable to close all connections. ");
			}
		}
		
		instance = null;
		System.out.println("All connections have been closed in ConnectionPool");
	}
	
	public int availableConnections() {
		System.out.println("The num of available connections: " + this.conQ.size());
		return this.conQ.size();
	}
	
}
