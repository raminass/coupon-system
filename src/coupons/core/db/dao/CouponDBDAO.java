package coupons.core.db.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import coupons.core.beans.Company;
import coupons.core.beans.Coupon;
import coupons.core.beans.CouponType;
import coupons.core.beans.Customer;
import coupons.core.dao.CouponDAO;
import coupons.core.db.ConnectionPool;
import coupons.core.exceptions.CouponSystemException;

public class CouponDBDAO implements CouponDAO {

	public CouponDBDAO() {
		super();
	}

	@Override
	public void createCoupon(Coupon coupon) throws CouponSystemException {
		Connection con = null;

		// check if the coupon doesn't exist
		if (getCoupon(coupon.getId()) != null) {

			throw new CouponSystemException("creation failed, coupon id:" + coupon.getId() + "already exists");
		}

		else {

			try {
				// get connection from pool
				con = ConnectionPool.getInstance().getConnection();
				String sql = "insert into coupon values(?,?,?,?,?,?,?,?,?)";
				PreparedStatement pstmt = con.prepareStatement(sql);
				pstmt.setLong(1, coupon.getId());
				pstmt.setString(2, coupon.getTitle());

				// convert the java.util.Date to java.sql.Date
				java.util.Date startDateBean = coupon.getStartDate();
				java.sql.Date startDateSQL = new java.sql.Date(startDateBean.getTime());
				pstmt.setDate(3, startDateSQL);

				// convert the java.util.Date to java.sql.Date
				java.util.Date endDateBean = coupon.getEndDate();
				java.sql.Date endDateSQL = new java.sql.Date(endDateBean.getTime());
				pstmt.setDate(4, endDateSQL);

				pstmt.setInt(5, coupon.getAmount());
				pstmt.setInt(6, coupon.getType().ordinal());
				pstmt.setString(7, coupon.getMessage());
				pstmt.setDouble(8, coupon.getPrice());
				pstmt.setString(9, coupon.getImage());

				pstmt.executeUpdate();

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
	public void removeCoupon(Coupon coupon) throws CouponSystemException {
		Connection con = null;

		if (getCoupon(coupon.getId()) == null) {

			throw new CouponSystemException("remove failed, coupon id:" + coupon.getId() + "doesn't exist");

		} else {
			try {
				// get connection from pool
				con = ConnectionPool.getInstance().getConnection();
				String sql = "delete from coupon where id = ?";
				PreparedStatement pstmt = con.prepareStatement(sql);
				pstmt.setLong(1, coupon.getId());
				pstmt.executeUpdate();

				// delete from company_coupon
				sql = "delete from company_coupon where coupon_id = ?";
				pstmt = con.prepareStatement(sql);
				pstmt.setLong(1, coupon.getId());
				pstmt.executeUpdate();

				// delete from customer_coupon
				sql = "delete from customer_coupon where coupon_id = ?";
				pstmt = con.prepareStatement(sql);
				pstmt.setLong(1, coupon.getId());
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
	public void updateCoupon(Coupon coupon) throws CouponSystemException {
		Connection con = null;

		if (getCoupon(coupon.getId()) == null) {

			throw new CouponSystemException("update failed, coupon id:" + coupon.getId() + "doesn't exists");

		} else {

			try {
				// get connection from pool
				con = ConnectionPool.getInstance().getConnection();
				String sql = "update coupon set title = ?, start_date = ?, end_date = ?,amount = ?,type = ?, message = ?,price =?,image = ? where id = ?";
				PreparedStatement pstmt = con.prepareStatement(sql);
				pstmt.setString(1, coupon.getTitle());

				// convert the java.util.Date to java.sql.Date
				java.util.Date startDateBean = coupon.getStartDate();
				java.sql.Date startDateSQL = new java.sql.Date(startDateBean.getTime());
				pstmt.setDate(2, startDateSQL);

				// convert the java.util.Date to java.sql.Date
				java.util.Date endDateBean = coupon.getEndDate();
				java.sql.Date endDateSQL = new java.sql.Date(endDateBean.getTime());
				pstmt.setDate(3, endDateSQL);

				pstmt.setInt(4, coupon.getAmount());
				pstmt.setInt(5, coupon.getType().ordinal());
				pstmt.setString(6, coupon.getMessage());
				pstmt.setDouble(7, coupon.getPrice());
				pstmt.setString(8, coupon.getImage());
				pstmt.setLong(9, coupon.getId());
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
	public Coupon getCoupon(long id) throws CouponSystemException {
		Connection con = null;
		Coupon coupon = new Coupon();
		try {
			// get connection from pool
			con = ConnectionPool.getInstance().getConnection();
			String sql = "select * from coupon where id = ?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				coupon.setId(id);
				coupon.setTitle(rs.getString(2));
				coupon.setStartDate((java.util.Date) rs.getDate(3));
				coupon.setEndDate((java.util.Date) rs.getDate(4));
				coupon.setAmount(rs.getInt(5));
				coupon.setType(rs.getInt(6));
				coupon.setMessage(rs.getString(7));
				coupon.setPrice(rs.getDouble(8));
				coupon.setImage(rs.getString(9));
				return coupon;
			} else {
				return null;

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
	public Collection<Coupon> getAllCoupons() throws CouponSystemException {
		Connection con = null;
		Collection<Coupon> coupons = new ArrayList<Coupon>();
		try {
			// get connection from pool
			con = ConnectionPool.getInstance().getConnection();
			String sql = "select * from coupon";
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Coupon coupon = new Coupon();
				coupon.setId(rs.getLong(1));
				coupon.setTitle(rs.getString(2));
				coupon.setStartDate((java.util.Date) rs.getDate(3));
				coupon.setEndDate((java.util.Date) rs.getDate(4));
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
			throw new CouponSystemException("get All coupons failed", e);
		} finally { // return connection to pool
			if (con != null) {
				ConnectionPool.getInstance().returnConnection(con);

			}
		}
	}

	@Override
	public Collection<Coupon> getAllCouponsByType(CouponType type) throws CouponSystemException {
		Connection con = null;
		Collection<Coupon> coupons = new ArrayList<Coupon>();
		try {
			// get connection from pool
			con = ConnectionPool.getInstance().getConnection();
			String sql = "select a.* from coupon where type = ?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, type.ordinal());
			;
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
	public Boolean checkCouponNameExist(String name) throws CouponSystemException {

		Connection con = null;

		try {
			// get connection from pool
			con = ConnectionPool.getInstance().getConnection();
			String sql = "select * from coupon where title = ?";
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
	public void deleteAllCompnayCoupons(Company company) throws CouponSystemException {

		Connection con = null;
		Collection<Long> coupons = new ArrayList<Long>();

		try {

			// get connection from pool
			con = ConnectionPool.getInstance().getConnection();

			// get all the coupons for the deleted company and store it at
			// coupons collection
			String sql = "select coupon_id from company_coupon where comp_id = ?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, company.getId());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				coupons.add(rs.getLong(1));

			}

			Iterator<Long> itr = coupons.iterator();
			while (itr.hasNext()) {
				Long couponId = (Long) itr.next();

				// delete all coupons from coupns
				sql = "delete from coupon where id = ?";
				pstmt = con.prepareStatement(sql);
				pstmt.setLong(1, couponId);
				pstmt.executeUpdate();

				// delete all coupons from customer_coupon
				sql = "delete from customer_coupon where coupon_id = ?";
				pstmt = con.prepareStatement(sql);
				pstmt.setLong(1, couponId);
				pstmt.executeUpdate();

			}

			// delete all coupons from company_coupon
			sql = "delete from company_coupon where comp_id = ?";
			pstmt = con.prepareStatement(sql);
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

	@Override
	public void deleteAllCustomerCoupons(Customer customer) throws CouponSystemException {

		Connection con = null;

		try {
			// get connection from pool
			con = ConnectionPool.getInstance().getConnection();
			String sql = "delete from customer_coupon where cust_id = ?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, customer.getId());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			throw new CouponSystemException("delete failed", e);

		}

		finally { // return connection to pool
			if (con != null) {
				ConnectionPool.getInstance().returnConnection(con);
			}
		}

	}

	@Override
	public void updateCompanyCoupon(long couponId, long companyId) throws CouponSystemException {

		Connection con = null;

		try {
			// get connection from pool
			con = ConnectionPool.getInstance().getConnection();
			String sql = "insert into company_coupon values(?,?)";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, companyId);
			pstmt.setLong(2, couponId);

			pstmt.executeUpdate();

		} catch (SQLException e) {
			throw new CouponSystemException("create failed", e);

		} finally { // return connection to pool
			if (con != null) {
				ConnectionPool.getInstance().returnConnection(con);
			}
		}

	}

	@Override
	public long getCustomerCoupon(long couponId) throws CouponSystemException {

		Connection con = null;

		try {
			// get connection from pool
			con = ConnectionPool.getInstance().getConnection();
			String sql = "select cust_id from customer_coupon where coupon_id = ?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, couponId);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				return rs.getLong(1);
			} else {
				return 0L;
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
	public long getCompanyCoupon(long couponId) throws CouponSystemException {
		Connection con = null;

		try {
			// get connection from pool
			con = ConnectionPool.getInstance().getConnection();
			String sql = "select comp_id from company_coupon where coupon_id = ?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, couponId);
			ResultSet rs = pstmt.executeQuery();

			return rs.getLong(1);

		} catch (SQLException e) {
			throw new CouponSystemException("read failed", e);

		} finally { // return connection to pool
			if (con != null) {
				ConnectionPool.getInstance().returnConnection(con);
			}
		}
	}

	@Override
	public void updateCustomerCoupon(long customerId, long couponId) throws CouponSystemException {
		Connection con = null;

		try {
			// get connection from pool
			con = ConnectionPool.getInstance().getConnection();
			String sql = "insert into customer_coupon values(?,?)";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, customerId);
			pstmt.setLong(2, couponId);

			pstmt.executeUpdate();

		} catch (SQLException e) {
			throw new CouponSystemException("update customer_coupon table failed", e);

		} finally { // return connection to pool
			if (con != null) {
				ConnectionPool.getInstance().returnConnection(con);
			}
		}

	}

	@Override
	public void removeAllExpiredCoupons() throws CouponSystemException {

		Connection con = null;

		try {
			// get connection from pool
			con = ConnectionPool.getInstance().getConnection();

			// delete from company_coupon
			String sql = "delete from company_coupon where coupon_id in (select id from coupon where end_date < CURRENT_DATE)";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt = con.prepareStatement(sql);
			pstmt.executeUpdate();

			// delete from customer_coupon
			sql = "delete from customer_coupon where coupon_id in (select id from coupon where end_date < CURRENT_DATE)";
			pstmt = con.prepareStatement(sql);
			pstmt.executeUpdate();
			
			//delete expired coupons from coupon table
			sql = "delete from coupon where end_date < CURRENT_DATE";
			pstmt = con.prepareStatement(sql);
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
