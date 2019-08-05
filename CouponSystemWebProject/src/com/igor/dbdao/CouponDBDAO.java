package com.igor.dbdao;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

//import org.apache.derby.tools.sysinfo;

import com.igor.dao.CouponDAO;
import com.igor.enums.CouponType;
import com.igor.config.ConnectionPool;
import com.igor.config.Database;
import com.igor.beans.Coupon;
import com.igor.beans.Customer;
import com.igor.beans.CustomerCoupon;
import com.igor.exceptions.ConnectionException;
import com.igor.exceptions.DeleteAllCouponsException;
import com.igor.exceptions.DeleteCouponException;
import com.igor.exceptions.GetAllPurchasedCouponsByPriceException;
import com.igor.exceptions.GetAllPurchasedCouponsByTypeException;
import com.igor.exceptions.GetAllPurchasedCouponsException;
import com.igor.exceptions.GetCouponByTypeException;
import com.igor.exceptions.InsertCouponException;
import com.igor.exceptions.RemoveExpCouponsException;
import com.igor.exceptions.SelectAllCouponsException;
import com.igor.exceptions.SelectCouponException;
import com.igor.exceptions.UpdateCouponException;
import com.sun.org.apache.bcel.internal.util.SecuritySupport;

public class CouponDBDAO implements CouponDAO {

	private static ConnectionPool connectionPool;


	@Override
	public void insertCoupon(Coupon coupon) throws ConnectionException, InsertCouponException, SQLException, Exception {
		connectionPool = ConnectionPool.getInstance();
		Connection con = connectionPool.getConnection();

		String sql = "INSERT INTO Coupon (title, startDate, endDate, amount, type, message, price, image) VALUES (?,?,?,?,?,?,?,?)";
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1, coupon.getTitle());
			pstmt.setString(2, coupon.getStartDate().toString());////setDate // without .toString()
			pstmt.setString(3, coupon.getEndDate().toString());////setDate // without .toString()
			pstmt.setInt(4, coupon.getAmount());
			pstmt.setString(5, coupon.getType().toString());
			pstmt.setString(6, coupon.getMessage());
			pstmt.setDouble(7, coupon.getPrice());
			pstmt.setString(8, coupon.getImage());
			pstmt.executeUpdate();
			System.out.println("The Coupon successfully added " + coupon.toString());
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Coupon creation failed! " + e.getMessage());		
			throw new InsertCouponException();
		}finally {
			con.close();
			connectionPool.returnConnection(con);
		}
		
	}

	@Override
	public void deleteCoupon(Coupon coupon) throws ConnectionException, DeleteCouponException, SQLException, Exception {
		connectionPool = ConnectionPool.getInstance();
		Connection con = connectionPool.getConnection();

		String sql = "DELETE FROM Customer_Coupon WHERE Coupon_id = " + coupon.getId();
		try {
			Statement statement = con.createStatement();
			con.setAutoCommit(false);
			statement.executeUpdate(sql);
		} catch (SQLException e) {
			System.out.println(e);
			throw new Exception("failed to remove Customer_Coupon");
		}
		sql = "DELETE FROM Company_Coupon WHERE Coupon_id =" + coupon.getId();
		try {
			Statement statement = con.createStatement();
			statement.executeUpdate(sql);
		} catch (SQLException e) {
			System.out.println(e);
			throw new Exception("failed to remove Company_Coupon");
		}
		sql = "DELETE FROM Coupon WHERE id = ?";
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, coupon.getId());
			pstmt.executeUpdate();
			con.commit();
			con.setAutoCommit(true);
			System.out.println("The Coupon successfully deleted " + coupon.toString());
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("failed to remove Coupon " + e.getMessage());
			throw new DeleteCouponException();
		}
		try {
			con.rollback();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Database error " + e.getMessage());
			throw new Exception("Database error");
		}
		finally {
			con.close();
			connectionPool.returnConnection(con);
		}
		
	}

	@Override
	public void updateCoupon(Coupon coupon, String title, Date startDate, Date endDate, int amount, CouponType type,
			String message, double price, String image) throws ConnectionException, UpdateCouponException, SQLException, Exception {
		connectionPool = ConnectionPool.getInstance();
		Connection con = connectionPool.getConnection();

		try {
			Statement stmt = con.createStatement();
			String sql = "UPDATE Coupon SET title = '" + coupon.getTitle() + "', startDate = '" + coupon.getStartDate() 
			+ "', endDate = '" + coupon.getEndDate() + "', amount = " + coupon.getAmount() + ", type = '" + coupon.getType() 
			+ "', message = '" + coupon.getMessage() + "', price = " + coupon.getPrice() + ", image = '" + coupon.getImage() 
			+ "' WHERE id = " + coupon.getId();
			stmt.executeUpdate(sql);
			System.out.println("The Coupon successfully updated " + coupon.toString());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Cannot update Coupon! " + e.getMessage());
			throw new UpdateCouponException();
		} finally {
			con.close();
			connectionPool.returnConnection(con);
		}
		
	}
	
	@Override
	public void updateCoupon(Coupon coupon) throws ConnectionException, UpdateCouponException, SQLException, Exception {
		connectionPool = ConnectionPool.getInstance();
		Connection con = connectionPool.getConnection();

		try {
			Statement stmt = con.createStatement();
			String sql = "UPDATE Coupon SET title = '" + coupon.getTitle() + "', startDate = '" + coupon.getStartDate() 
			+ "', endDate = '" + coupon.getEndDate() + "', amount = " + coupon.getAmount() + ", type = '" + coupon.getType() 
			+ "', message = '" + coupon.getMessage() + "', price = " + coupon.getPrice() + ", image = '" + coupon.getImage() 
			+ "' WHERE id = " + coupon.getId();
			stmt.executeUpdate(sql);
			System.out.println("The Coupon successfully updated " + coupon.toString());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Cannot update Coupon! " + e.getMessage());
			throw new UpdateCouponException();
		} finally {
			con.close();
			connectionPool.returnConnection(con);
		}
		
	}

	@Override
	public Coupon selectCoupon(long id) throws ConnectionException, SelectCouponException, SQLException, Exception {
		connectionPool = ConnectionPool.getInstance();
		Connection con = connectionPool.getConnection();
		Coupon coupon = new Coupon();

		try (Statement stmt = con.createStatement()) {
		String sql = "SELECT * FROM Coupon WHERE id = " + id;
		ResultSet rs = stmt.executeQuery(sql);
		rs.next();
		coupon.setId(rs.getLong(1));
		coupon.setTitle(rs.getString(2));
		coupon.setStartDate(rs.getDate(3));
		coupon.setEndDate(rs.getDate(4));
		coupon.setAmount(rs.getInt(5));
		coupon.setType(CouponType.valueOf(rs.getString(6)));
		coupon.setMessage(rs.getString(7));
		coupon.setPrice(rs.getDouble(8));
		coupon.setImage(rs.getString(9));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Statement error! Unable to get Coupon data " + e.getMessage());
			throw new SelectCouponException();
		} finally {
			con.close();
			connectionPool.returnConnection(con);
		}
		return coupon;
	}

	@Override
	public synchronized Set<Coupon> selectAllCoupons() throws ConnectionException, SelectAllCouponsException, SQLException, Exception {
		connectionPool = ConnectionPool.getInstance();
		Connection con = connectionPool.getConnection();

		Set<Coupon>set = new HashSet<>();
		String sql = "SELECT * FROM Coupon";
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Long id = rs.getLong(1);
				String title = rs.getString(2);
				Date startDate = rs.getDate(3);
				Date endDate = rs.getDate(4);
				Integer amount = rs.getInt(5);
				CouponType type = (CouponType.valueOf(rs.getString(6)));
				String message = rs.getString(7);
				Double price = rs.getDouble(8);
				String image = rs.getString(9);
				
				set.add(new Coupon(id, title, startDate, endDate, amount, type, message, price, image));
			}
		} catch (SQLException e) {
			System.out.println("Cannot get Coupon data " + e.getMessage());
			throw new SelectAllCouponsException();
		} finally {
			con.close();
			connectionPool.returnConnection(con);
		}
		return set;
	}
	
	
	@Override
	public void removeExpCoupons() throws ConnectionException, RemoveExpCouponsException, SQLException, Exception {
		connectionPool = ConnectionPool.getInstance();
		Connection con = connectionPool.getConnection();

		Collection<Coupon> removeList = null;
		CouponDBDAO couponDBDAO = new CouponDBDAO();
		try {
			removeList = (Set<Coupon>) couponDBDAO.selectAllCoupons();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error on remove expired coupons");
		}
		Iterator<Coupon> iterator = removeList.iterator();
		if (iterator != null) {
			while (iterator.hasNext()) {
				Coupon coupon = iterator.next();
				if (coupon.getEndDate().before(Date.valueOf(LocalDate.now()))) {
					
					try {
						couponDBDAO.deleteCoupon(coupon);
						System.out.println("Expired coupons removed");
					} catch (Exception e) {
						e.printStackTrace();
						System.out.println("Cannot remove expired coupons. " + e.getMessage());
						throw new RemoveExpCouponsException();
					} finally {
						con.close();
						connectionPool.returnConnection(con);
					}
					
				}
			}
		}
		
		
	}
	
	@Override
	public Set<Coupon> getCouponByType(CouponType type) throws ConnectionException, GetCouponByTypeException, SQLException, Exception {
		connectionPool = ConnectionPool.getInstance();
		Connection con = connectionPool.getConnection();

		Set<Coupon> coupons = new HashSet<>();
		String sql = "select * from Coupon where type = ?";
		try {
			PreparedStatement preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, type.toString());
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet != null) {
				while (resultSet.next()) {
					Coupon coupon = selectCoupon(resultSet.getLong("id"));
					if (coupon.getId() != 0) {
						coupons.add(this.selectCoupon(coupon.getId()));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Could not get coupons by type! " + e.getMessage());
			throw new GetCouponByTypeException();
		} finally {
			con.close();
			connectionPool.returnConnection(con);
		}
		return coupons;
	}
	
	@Override
	public Set<Coupon> getCouponByPrice(double price) throws ConnectionException, SQLException, Exception {
		connectionPool = ConnectionPool.getInstance();
		Connection con = connectionPool.getConnection();
		
		Set<Coupon> coupons = new HashSet<>();
		String sql = "select * from Coupon where price <= ?";
		try {
			PreparedStatement preparedStatement = con.prepareStatement(sql);
			preparedStatement.setDouble(1, price);//setString(1, price.toString());
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet != null) {
				while (resultSet.next()) {
					Coupon coupon = selectCoupon(resultSet.getLong("id"));//getDouble("price")
					if (coupon.getId() != 0) {
						coupons.add(this.selectCoupon(coupon.getId()));//getPrice
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Could not get coupons by price! " + e.getMessage());
			throw new Exception("Could not get coupons by price! ");
		} finally {
			con.close();
			connectionPool.returnConnection(con);
		}
		return coupons;
	}

	@Override
	public void deleteAllCoupons() throws ConnectionException, DeleteAllCouponsException, SQLException, Exception {
		connectionPool = ConnectionPool.getInstance();
		Connection con = connectionPool.getConnection();

		try {
			Set<Coupon> coupons = selectAllCoupons();
			Iterator<Coupon> iterator = coupons.iterator();
			while (iterator.hasNext()) {
				Coupon current = iterator.next();
				if (current.getId() > 0) {
					for(Coupon coupon : coupons) {
						deleteCoupon(coupon);
					}
				}
			}
			System.out.println("All Coupons deleted");
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("Cannot delete Coupon data. " + e.getMessage());
			throw new DeleteAllCouponsException();
		}finally {
			con.close();
			connectionPool.returnConnection(con);
		}
		
		
	}

	@Override
	public Collection<Coupon> getAllPurchasedCoupons(long id) throws ConnectionException, GetAllPurchasedCouponsException, 
				SQLException, Exception {
		connectionPool = ConnectionPool.getInstance();
		Connection con = connectionPool.getConnection();

		Collection<Coupon> coupons = new ArrayList<>();
		Collection<Long> couponId = new ArrayList<>();
		try {
			String sql = "select * from Customer_Coupon where cust_id = " + id;
			Statement statement = con.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				long idCust = resultSet.getLong("cust_id");
				long idCoup = resultSet.getLong("coupon_id");
				couponId.add(idCoup);
			}
			for (long idCoup : couponId) {
				sql = "select * from Coupon where id = " + idCoup;
				Statement statement2 = con.createStatement();
				ResultSet resultSet2 = statement2.executeQuery(sql);
				while (resultSet2.next()) {
					long idC = resultSet2.getLong("id");
					String title = resultSet2.getString("title");
					Date startDate = resultSet2.getDate("startDate");
					Date endDate = resultSet2.getDate("endDate");
//					int amount = resultSet2.getInt("amount");
					CouponType type = (CouponType.valueOf(resultSet2.getString("type")));
					String message = resultSet2.getString("message");
					Double price = resultSet2.getDouble("price");
					String image = resultSet2.getString("image");

						coupons.add(new Coupon(idC, title, startDate, endDate, type, message, price, image));

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error selectAllCustomerCoupons; " + e.getMessage());
			throw new GetAllPurchasedCouponsException();
		} finally {
			con.close();
			connectionPool.returnConnection(con);
		}
		return coupons;
	}
	
	@Override
	public Collection<Coupon> getAllPurchasedCouponsByType(CouponType type, long id) throws ConnectionException, 
				GetAllPurchasedCouponsByTypeException, SQLException, Exception {
		connectionPool = ConnectionPool.getInstance();
		Connection con = connectionPool.getConnection();

		Collection<Coupon> purchasedByType = new ArrayList<>();
		Collection<Long> cutumerCoupons = new ArrayList<>();
		try {
			String sql ="select * from Customer_Coupon where cust_id = " + id;
			Statement statement = con.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				long custId = resultSet.getLong("cust_id");
				long coupId = resultSet.getLong("coupon_id");
				cutumerCoupons.add(coupId);
			}
			for (long coupId : cutumerCoupons)
			    sql = "select * from Coupon where type = '" + type.toString() + "' and id = " + coupId;
				Statement statement2 = con.createStatement();
				ResultSet resultSet2 = statement2.executeQuery(sql);
				if (resultSet2 != null) {
					while (resultSet2.next()) {
						long idC = resultSet2.getLong("id");
						String title = resultSet2.getString("title");
						Date startDate = resultSet2.getDate("startDate");
						Date endDate = resultSet2.getDate("endDate");
//						int amount = resultSet2.getInt("amount");
						CouponType type1 = (CouponType.valueOf(resultSet2.getString("type")));
						String message = resultSet2.getString("message");
						Double price = resultSet2.getDouble("price");
						String image = resultSet2.getString("image");

							purchasedByType.add(new Coupon(idC, title, startDate, endDate, type1, message, price, image));

				    }
				}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Could not get purchased coupons by type. " + e.getMessage());
			throw new GetAllPurchasedCouponsByTypeException();
		} finally {
			con.close();
			connectionPool.returnConnection(con);
		}
		return purchasedByType;
	}

	
	@Override
	public Collection<Coupon> getAllPurchasedCouponsByPrice(double price, long id) throws ConnectionException, 
				GetAllPurchasedCouponsByPriceException, SQLException, Exception {
		connectionPool = ConnectionPool.getInstance();
		Connection con = connectionPool.getConnection();

		Collection<Coupon> purchasedByPrice = new ArrayList<>();
		Collection<Long> customerCoupons = new ArrayList<>();
		try {
			String sql = "select * from Customer_Coupon where cust_id = " + id;
			Statement statement = con.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				long custId = resultSet.getLong("cust_id");
				long coupId = resultSet.getLong("coupon_id");
				customerCoupons.add(coupId);
			}
			for (long coupId : customerCoupons) {
				sql = "select * from Coupon where price <= " + price + "and id = " + coupId;
				Statement statement2 = con.createStatement();
				ResultSet resultSet2 = statement2.executeQuery(sql);
				if (resultSet2 != null) {
					while (resultSet2.next()) {
						long idC = resultSet2.getLong("id");
						String title = resultSet2.getString("title");
						Date startDate = resultSet2.getDate("startDate");
						Date endDate = resultSet2.getDate("endDate");
//						int amount = resultSet2.getInt("amount");
						CouponType type = (CouponType.valueOf(resultSet2.getString("type")));
						String message = resultSet2.getString("message");
						Double price1 = resultSet2.getDouble("price");
						String image = resultSet2.getString("image");
						
						purchasedByPrice.add(new Coupon(idC, title, startDate, endDate, type, message, price1, image));
//						System.out.println(purchasedByPrice);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Cannot get all purchased coupons by price. " + e.getMessage());
			throw new GetAllPurchasedCouponsByPriceException();
		} finally {
			con.close();
			connectionPool.returnConnection(con);
		}
		
		return purchasedByPrice;
	}

	
		
}
