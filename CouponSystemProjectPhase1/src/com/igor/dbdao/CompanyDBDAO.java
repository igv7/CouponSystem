package com.igor.dbdao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

//import org.apache.derby.tools.sysinfo;

import com.igor.beans.Company;
import com.igor.dao.CompanyDAO;

import com.igor.enums.ClientType;
import com.igor.beans.CompanyCoupon;
import com.igor.config.ConnectionPool;
import com.igor.config.Database;
import com.igor.beans.Coupon;
import com.igor.beans.Customer;
import com.igor.enums.CouponType;
import com.igor.exceptions.AddCompanyCouponExceprion;
import com.igor.exceptions.CompanyLoginException;
import com.igor.exceptions.ConnectionException;
import com.igor.exceptions.DeleteAllCompaniesException;
import com.igor.exceptions.DeleteCompanyCouponException;
import com.igor.exceptions.DeleteCompanyException;
import com.igor.exceptions.DeleteCustomerException;
import com.igor.exceptions.InsertCompanyException;
import com.igor.exceptions.SelectAllCompaniesException;
import com.igor.exceptions.SelectCompanyException;
import com.igor.exceptions.SelectCouponsException;
import com.igor.exceptions.UpdateCompanyException;

public class CompanyDBDAO implements CompanyDAO {
	private static ConnectionPool connectionPool;

	private long loginId = 0;

	public long getLoginId() {
		return loginId;
	}

	private void setLoginId(long loginId) {
		this.loginId = loginId;

	}

	private long loggedCompany = 0;

	public long getLoggedCompany() {
		return loggedCompany;
	}

	public void setLoggedCompany(long compId) {
		this.loggedCompany = compId;
	}

	@Override
	public void insertCompany(Company company)
			throws ConnectionException, InsertCompanyException, SQLException, Exception {
		
		connectionPool = ConnectionPool.getInstance();
		Connection con = connectionPool.getConnection();
		
		String sql = "INSERT INTO Company (name, password, email) VALUES (?,?,?)";
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1, company.getCompName());
			pstmt.setString(2, company.getPassword());
			pstmt.setString(3, company.getEmail());
			pstmt.executeUpdate();
			System.out.println("Company created " + company.toString());
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			System.out.println("Company creation failed");
			throw new InsertCompanyException();

		} finally {
			con.close();
			connectionPool.returnConnection(con);
		}
		
	}

	@Override
	public void deleteCompany(Company company)
			throws ConnectionException, DeleteCompanyException, SQLException, Exception {
		connectionPool = ConnectionPool.getInstance();
		Connection con = connectionPool.getConnection();

		String sql = "DELETE FROM Company_Coupon WHERE comp_id = " + company.getId();
		try {
			Statement statement = con.createStatement();
			con.setAutoCommit(false);//
			statement.executeUpdate(sql);
		} catch (SQLException e) {
			System.out.println(e);
			throw new Exception("failed to remove Company_Coupon");
		}
		sql = "DELETE FROM Company WHERE id = ?";
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			// con.setAutoCommit(false);
			pstmt.setLong(1, company.getId());
			pstmt.executeUpdate();
			con.commit();
			con.setAutoCommit(true);
			System.out.println("The Company successfully deleted " + company.toString());
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("failed to remove Company " + e.getMessage());
			throw new DeleteCompanyException();
		}
		try {
			con.rollback();
		} catch (SQLException e) {
			throw new Exception("Database error");
		} finally {
			con.close();
			connectionPool.returnConnection(con);
		}

	}

	@Override
	public void updateCompany(Company company)
			throws SQLException, ConnectionException, UpdateCompanyException, Exception {
		connectionPool = ConnectionPool.getInstance();
		Connection con = connectionPool.getConnection();

		try {
			Statement stmt = con.createStatement();
			String sql = "UPDATE Company " + " SET name = '" + company.getCompName() + "', password = '"
					+ company.getPassword() + "', email = '" + company.getEmail() + "' WHERE id = " + company.getId();
			stmt.executeUpdate(sql);
			System.out.println("Company updated " + company.toString());
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Cannot update Company " + e.getMessage());
			throw new UpdateCompanyException();
		} finally {
			con.close();
			connectionPool.returnConnection(con);
		}

	}

	@Override
	public Company selectCompany(long id) throws ConnectionException, SelectCompanyException, SQLException, Exception {
		connectionPool = ConnectionPool.getInstance();
		Connection con = connectionPool.getConnection();
		Company company = new Company();

		try (Statement stmt = con.createStatement()) {
			String sql = "SELECT * FROM Company WHERE id = " + id;
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			company.setId(rs.getLong(1));
			company.setCompName(rs.getString(2));
			company.setPassword(rs.getString(3));
			company.setEmail(rs.getString(4));
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Statement error! Unable to get Company data " + e.getMessage());
			throw new SelectCompanyException();
		} finally {
			con.close();
			connectionPool.returnConnection(con);
		}
		return company;
	}

	@Override
	public synchronized Set<Company> selectAllCompanies()
			throws SQLException, ConnectionException, SelectAllCompaniesException, Exception {
		
		connectionPool = ConnectionPool.getInstance();
		Connection con = connectionPool.getConnection();
		
		Set<Company> set = new HashSet<>();
		
		String sql = "SELECT * FROM Company";
		try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
			while (rs.next()) {
				Long id = rs.getLong(1);
				String compName = rs.getString(2);
				String password = rs.getString(3);
				String email = rs.getString(4);
				
				set.add(new Company(id, compName, password, email));
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Cannot get Company data " + e.getMessage());
			throw new SelectAllCompaniesException();
		} finally {
			con.close();
			connectionPool.returnConnection(con);
		}
		
		return set;
	}

	@Override
	public void addCoupon(Company company, Coupon coupon)
			throws ConnectionException, AddCompanyCouponExceprion, Exception {
		connectionPool = ConnectionPool.getInstance();
		Connection con = connectionPool.getConnection();

		String sql = "insert into Company_Coupon (comp_id, coupon_id) values (?, ?)";
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, company.getId());
			pstmt.setLong(2, coupon.getId());
			pstmt.executeUpdate();
			System.out.println("Coupon id " + coupon.getId() + " was appended to company id " + company.getId());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Could not append the coupon id: " + coupon.getId() + " to the company id: " + company.getId() 
			+ ". " + e.getMessage());
			throw new AddCompanyCouponExceprion();
		} finally {
			con.close();
			connectionPool.returnConnection(con);
		}

	}

	@Override
	public void deleteCoupon(long compId, long couponId)
			throws ConnectionException, DeleteCompanyCouponException, Exception {
		connectionPool = ConnectionPool.getInstance();
		Connection con = connectionPool.getConnection();

		String sql = "delete from Company_Coupon where Comp_id = " + compId + " and Coupon_id = " + couponId;
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate(sql);
			System.out.println("Coupon id " + couponId + " was deleted from company id " + compId);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Could not delete the coupon from the Company_Coupon!");
			throw new DeleteCompanyCouponException();
		} finally {
			con.close();
			connectionPool.returnConnection(con);
		}

	}

	@Override
	public Collection<Coupon> selectCoupons(long id)
			throws ConnectionException, SelectCouponsException, SQLException, Exception {
		connectionPool = ConnectionPool.getInstance();
		Connection con = connectionPool.getConnection();

		Collection<Coupon> companyCoupons = new ArrayList<>();
		Collection<Long> coupons = new ArrayList<>();
		String sql = "SELECT * FROM Company_Coupon WHERE comp_id = " + id;
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				long compId = rs.getLong(1);
				long couponId = rs.getLong(2);
				coupons.add(couponId);
			}
			for (long couponId : coupons) {
				sql = "select * from Coupon where id = " + couponId;
				Statement statement = con.createStatement();
				ResultSet resultSet = statement.executeQuery(sql);
				if (resultSet != null) {
					while (resultSet.next()) {
						long compId = resultSet.getLong(1);
						String title = resultSet.getString(2);
						Date startDate = resultSet.getDate(3);
						Date endDate = resultSet.getDate(4);
						Integer amount = resultSet.getInt(5);
						CouponType type = (CouponType.valueOf(resultSet.getString(6)));
						String message = resultSet.getString(7);
						Double price = resultSet.getDouble(8);
						String image = resultSet.getString(9);

						companyCoupons.add(
								new Coupon(compId, title, startDate, endDate, amount, type, message, price, image));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Cannot get Company_Coupon data " + e.getMessage());
			throw new SelectCouponsException();
		} finally {
			con.close();
			connectionPool.returnConnection(con);
		}
		return companyCoupons;
	}

	@Override
	public boolean login(String compName, String password, ClientType type)
			throws ConnectionException, CompanyLoginException, SQLException, Exception {
		connectionPool = ConnectionPool.getInstance();
		Connection con = connectionPool.getConnection();
		String dataBasePassword = null;
		Company company = new Company();

		String sql = "select * from Company where name = ?";
		if (type == ClientType.COMPANY) {
			try {
				PreparedStatement pstmt = con.prepareStatement(sql);
				pstmt.setString(1, compName);
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					dataBasePassword = rs.getString("password");
					if (password.equals(dataBasePassword)) {
						setLoginId(rs.getLong("id"));
						System.out.println("Login success! The company ID is: " + loginId + " and the company name is: "
								+ compName);
						setLoginId(loginId);
						setLoggedCompany(company.getId());
						return true;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Could not Login with this Company!" + compName + e.getMessage());
				throw new CompanyLoginException();
			} finally {
				con.close();
				connectionPool.returnConnection(con);
			}
		}

		return false;
	}

	@Override
	public void deleteAllCompanies() throws ConnectionException, DeleteAllCompaniesException, SQLException, Exception {
		connectionPool = ConnectionPool.getInstance();
		Connection con = connectionPool.getConnection();

		try {
			Set<Company> companies = selectAllCompanies();
			Iterator<Company> iterator = companies.iterator();
			while (iterator.hasNext()) {
				Company current = iterator.next();
				if (current.getId() > 0) {
					for (Company company : companies) {
						deleteCompany(company);
					}

				}
				
			}
			System.out.println("All Companies deleted");
		} catch (Exception e) {
			throw new DeleteAllCompaniesException();
		} finally {
			con.close();
			connectionPool.returnConnection(con);
		}
	}

}
