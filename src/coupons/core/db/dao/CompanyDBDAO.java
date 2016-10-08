package coupons.core.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import coupons.core.beans.Company;
import coupons.core.beans.Coupon;
import coupons.core.beans.CouponType;
import coupons.core.dao.CompanyDAO;
import coupons.core.db.ConnectionPool;
import coupons.core.exceptions.CouponSystemException;

public class CompanyDBDAO implements CompanyDAO {

	public CompanyDBDAO() {
		super();
	}

	@Override
	public void createCompany(Company company) throws CouponSystemException {
		Connection con = null;

		// check if the company doesn't exist
		if (getCompany(company.getId()).getId() != 0L) {

			throw new CouponSystemException("creation failed, Company id:" + company.getId() + "already exists");
		} else {

			try {
				// get connection from pool
				con = ConnectionPool.getInstance().getConnection();
				String sql = "insert into company values(?,?,?,?)";
				PreparedStatement pstmt = con.prepareStatement(sql);
				pstmt.setLong(1, company.getId());
				pstmt.setString(2, company.getCompName());
				pstmt.setString(3, company.getPassword());
				pstmt.setString(4, company.getEmail());
				pstmt.executeUpdate();
				System.out.println("new company created successfully: " + company);

			} catch (SQLException e) {
				throw new CouponSystemException("create failed", e);

			} finally { // return connection to pool
				if (con != null) {
					ConnectionPool.getInstance().returnConnection(con);
				}
			}

		}

	}

	@Override
	public void removeCompany(Company company) throws CouponSystemException {
		Connection con = null;
		// check if the company doesn't exist
		if (getCompany(company.getId()) == null) {

			throw new CouponSystemException("remove failed, Company id:" + company.getId() + "doesn't exist");

		} else {

			try {
				// get connection from pool
				con = ConnectionPool.getInstance().getConnection();
				String sql = "delete from company where id = ?";
				PreparedStatement pstmt = con.prepareStatement(sql);
				pstmt.setLong(1, company.getId());
				pstmt.executeUpdate();

			} catch (SQLException e) {
				throw new CouponSystemException("delete failed", e);

			} finally { // return connection to pool
				if (con != null) {
					ConnectionPool.getInstance().returnConnection(con);
				}
			}

		}

	}

	@Override
	public void updateCompany(Company company) throws CouponSystemException {
		Connection con = null;

		if (getCompany(company.getId()) == null) {

			throw new CouponSystemException("update failed, Company id:" + company.getId() + "doesn't exists");

		} else {

			try {
				// get connection from pool
				con = ConnectionPool.getInstance().getConnection();
				String sql = "update company set comp_name = ?, password = ?, email = ? where id = ? ";
				PreparedStatement pstmt = con.prepareStatement(sql);
				pstmt.setString(1, company.getCompName());
				pstmt.setString(2, company.getPassword());
				pstmt.setString(3, company.getEmail());
				pstmt.setLong(4, company.getId());
				pstmt.executeUpdate();

			} catch (SQLException e) {
				throw new CouponSystemException("update failed", e);

			} finally { // return connection to pool
				if (con != null) {
					ConnectionPool.getInstance().returnConnection(con);
				}
			}
		}

	}

	@Override
	public Company getCompany(long id) throws CouponSystemException {
		Connection con = null;
		Company company = new Company();

		try {
			// get connection from pool
			con = ConnectionPool.getInstance().getConnection();
			String sql = "select * from company where id = ?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				company.setId(id);
				company.setCompName(rs.getString(2));
				company.setPassword(rs.getString(3));
				company.setEmail(rs.getString(4));

				return company;
			} else {

				company.setId(0L);
				return company;
			}

		} catch (SQLException e) {
			throw new CouponSystemException("read failed", e);

		} finally { // return connection to pool
			if (con != null) {
				ConnectionPool.getInstance().returnConnection(con);
			}
		}

	}

	@Override
	public Collection<Company> getAllCompanies() throws CouponSystemException {
		Connection con = null;
		Collection<Company> companies = new ArrayList<Company>();
		try {
			// get connection from pool
			con = ConnectionPool.getInstance().getConnection();
			String sql = "select * from company";
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Company company = new Company();
				company.setId(rs.getLong(1));
				company.setCompName(rs.getString(2));
				company.setPassword(rs.getString(3));
				company.setEmail(rs.getString(4));
				companies.add(company);
			}
			return companies;
		}

		catch (SQLException e) {
			throw new CouponSystemException("get All companies failed", e);
		} finally { // return connection to pool
			if (con != null) {
				ConnectionPool.getInstance().returnConnection(con);
			}
		}
	}

	@Override
	public Collection<Coupon> getCoupons(long id) throws CouponSystemException {
		Connection con = null;
		Collection<Coupon> coupons = new ArrayList<Coupon>();

		if (getCompany(id) == null) {
			throw new CouponSystemException("can't getCoupons for company id:" + id + "reason: company doesn't exist");

		} else {
			try {
				// get connection from pool
				con = ConnectionPool.getInstance().getConnection();
				String sql = "select a.* from coupon a,company_coupon b where a.id=b.coupon_id and b.comp_id = ?";
				PreparedStatement pstmt = con.prepareStatement(sql);
				pstmt.setLong(1, id);
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					Coupon coupon = new Coupon();
					coupon.setId(rs.getLong(1));
					coupon.setTitle(rs.getString(2));
					coupon.setStartDate(rs.getDate(3));
					coupon.setEndDate(rs.getDate(4));
					coupon.setAmount(rs.getInt(5));
					coupon.setType(rs.getInt(6));
					coupon.setMessage(rs.getString(7));
					coupon.setPrice(rs.getDouble(8));
					coupon.setImage(rs.getString(9));
					coupons.add(coupon);
				}
				return coupons;
			}

			catch (SQLException e) {
				throw new CouponSystemException("get all coupons failed", e);
			} finally { // return connection to pool
				if (con != null) {
					ConnectionPool.getInstance().returnConnection(con);
				}
			}
		}

	}

	@Override
	public long login(String userName, String userPass) throws CouponSystemException {
		Connection con = null;
		long userId = 0L;

		try {
			// get connection from pool
			con = ConnectionPool.getInstance().getConnection();
			String sql = "select id from company where comp_name = ? and password = ?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, userName);
			pstmt.setString(2, userPass);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				userId = rs.getLong(1);

			}

		} catch (SQLException e) {
			throw new CouponSystemException("login failed", e);

		}

		finally { // return connection to pool
			if (con != null) {
				ConnectionPool.getInstance().returnConnection(con);
			}
		}
		return userId;
	}

	@Override
	public boolean checkCompNameExist(String name) throws CouponSystemException {

		Connection con = null;

		try {
			// get connection from pool
			con = ConnectionPool.getInstance().getConnection();
			String sql = "select * from company where comp_name = ?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {

				return true;
			} else {
				return false;
			}

		} catch (SQLException e) {
			throw new CouponSystemException("read failed", e);

		} finally { // return connection to pool
			if (con != null) {
				ConnectionPool.getInstance().returnConnection(con);
			}
		}
	}

	@Override
	public Collection<Coupon> getCouponsByType(Long id, CouponType type) throws CouponSystemException {
		Connection con = null;
		Collection<Coupon> coupons = new ArrayList<Coupon>();

		try {
			// get connection from pool
			con = ConnectionPool.getInstance().getConnection();
			String sql = "select a.* from coupon a,company_coupon b where a.id=b.coupon_id and b.comp_id = ? and a.type = ?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, id);
			pstmt.setInt(2, type.ordinal());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Coupon coupon = new Coupon();
				coupon.setId(rs.getLong(1));
				coupon.setTitle(rs.getString(2));
				coupon.setStartDate(rs.getDate(3));
				coupon.setEndDate(rs.getDate(4));
				coupon.setAmount(rs.getInt(5));
				coupon.setType(rs.getInt(6));
				coupon.setMessage(rs.getString(7));
				coupon.setPrice(rs.getDouble(8));
				coupon.setImage(rs.getString(9));
				coupons.add(coupon);
			}
			return coupons;
		}

		catch (SQLException e) {
			throw new CouponSystemException("get all coupons failed", e);
		} finally { // return connection to pool
			if (con != null) {
				ConnectionPool.getInstance().returnConnection(con);
			}
		}

	}

	@Override
	public Collection<Coupon> getCouponsByPrice(Long id, double price) throws CouponSystemException {

		Connection con = null;
		Collection<Coupon> coupons = new ArrayList<Coupon>();

		try {
			// get connection from pool
			con = ConnectionPool.getInstance().getConnection();
			String sql = "select a.* from coupon a,company_coupon b where a.id=b.coupon_id and b.comp_id = ? and a.price <= ?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, id);
			pstmt.setDouble(2, price);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Coupon coupon = new Coupon();
				coupon.setId(rs.getLong(1));
				coupon.setTitle(rs.getString(2));
				coupon.setStartDate(rs.getDate(3));
				coupon.setEndDate(rs.getDate(4));
				coupon.setAmount(rs.getInt(5));
				coupon.setType(rs.getInt(6));
				coupon.setMessage(rs.getString(7));
				coupon.setPrice(rs.getDouble(8));
				coupon.setImage(rs.getString(9));
				coupons.add(coupon);
			}
			return coupons;
		}

		catch (SQLException e) {
			throw new CouponSystemException("get all coupons failed", e);
		} finally { // return connection to pool
			if (con != null) {
				ConnectionPool.getInstance().returnConnection(con);
			}
		}

	}

	@Override
	public Collection<Coupon> getCouponsUpToDate(Long id, Date endDate) throws CouponSystemException {
		
		Connection con = null;
		Collection<Coupon> coupons = new ArrayList<Coupon>();

		try {
			// get connection from pool
			con = ConnectionPool.getInstance().getConnection();
			String sql = "select a.* from coupon a,company_coupon b where a.id=b.coupon_id and b.comp_id = ? and a.end_date <= ?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, id);
			java.util.Date endDateBean = endDate;
			java.sql.Date endDateSQL = new java.sql.Date(endDateBean.getTime());
			pstmt.setDate(2, endDateSQL);
			
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Coupon coupon = new Coupon();
				coupon.setId(rs.getLong(1));
				coupon.setTitle(rs.getString(2));
				coupon.setStartDate(rs.getDate(3));
				coupon.setEndDate(rs.getDate(4));
				coupon.setAmount(rs.getInt(5));
				coupon.setType(rs.getInt(6));
				coupon.setMessage(rs.getString(7));
				coupon.setPrice(rs.getDouble(8));
				coupon.setImage(rs.getString(9));
				coupons.add(coupon);
			}
			return coupons;
		}

		catch (SQLException e) {
			throw new CouponSystemException("get all coupons failed", e);
		} finally { // return connection to pool
			if (con != null) {
				ConnectionPool.getInstance().returnConnection(con);
			}
		}

	}

}
