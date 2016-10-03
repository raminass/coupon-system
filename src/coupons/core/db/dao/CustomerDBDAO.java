package coupons.core.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.derby.tools.sysinfo;

import coupons.core.beans.Coupon;
import coupons.core.beans.Customer;
import coupons.core.dao.CustomerDAO;
import coupons.core.db.ConnectionPool;
import coupons.core.exceptions.CouponSystemException;

public class CustomerDBDAO implements CustomerDAO {

	@Override
	public void createCustomer(Customer customer) throws CouponSystemException {
		Connection con = null;
		// check if the company doesn't exist
		if (getCustomer(customer.getId()).getId() != 0L) {

			throw new CouponSystemException("creation failed, customer id:" + customer.getId() + "already exists");
		}

		else {
			try {
				// get connection from pool
				con = ConnectionPool.getInstance().getConnection();
				String sql = "insert into customer values(?,?,?)";
				PreparedStatement pstmt = con.prepareStatement(sql);
				pstmt.setLong(1, customer.getId());
				pstmt.setString(2, customer.getCustName());
				pstmt.setString(3, customer.getPassword());
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
	public void removeCustomer(Customer customer) throws CouponSystemException {
		Connection con = null;

		if (getCustomer(customer.getId()) == null) {

			throw new CouponSystemException("remove failed, Customer id:" + customer.getId() + "doesn't exist");

		}

		else {

			try {
				// get connection from pool
				con = ConnectionPool.getInstance().getConnection();
				String sql = "delete from customer where id = ?";
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

	}

	@Override
	public void updateCustomer(Customer customer) throws CouponSystemException {
		Connection con = null;

		if (getCustomer(customer.getId()) == null) {

			throw new CouponSystemException("update failed, Customer id:" + customer.getId() + "doesn't exists");

		} else {

			try {
				// get connection from pool
				con = ConnectionPool.getInstance().getConnection();
				String sql = "update customer set cust_name = ?, password = ? where id = ? ";
				PreparedStatement pstmt = con.prepareStatement(sql);
				pstmt.setString(1, customer.getCustName());
				pstmt.setString(2, customer.getPassword());
				pstmt.setLong(3, customer.getId());
				pstmt.executeUpdate();

			} catch (SQLException e) {
				throw new CouponSystemException("update failed", e);

			}

			finally { // return connection to pool
				if (con != null) {
					ConnectionPool.getInstance().returnConnection(con);
				}
			}

		}

	}

	@Override
	public Customer getCustomer(long id) throws CouponSystemException {
		Connection con = null;
		Customer customer = new Customer();
		try {
			// get connection from pool
			con = ConnectionPool.getInstance().getConnection();
			String sql = "select * from customer where id = ?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, id);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {

				customer.setId(id);
				customer.setCustName(rs.getString(2));
				customer.setPassword(rs.getString(3));
				return customer;
			}

			else {

				System.out.println("customer doesn't exist");
				customer.setId(0L);
				return customer;

			}

		} catch (SQLException e) {
			throw new CouponSystemException("read failed", e);

		}

		finally { // return connection to pool
			if (con != null) {
				ConnectionPool.getInstance().returnConnection(con);
			}
		}
	}

	@Override
	public Collection<Customer> getAllCustomers() throws CouponSystemException {
		Connection con = null;
		Collection<Customer> customers = new ArrayList<>();
		try {
			// get connection from pool
			con = ConnectionPool.getInstance().getConnection();
			String sql = "select * from customer";
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Customer customer = new Customer();
				customer.setId(rs.getLong(1));
				customer.setCustName(rs.getString(2));
				customer.setPassword(rs.getString(3));
				customers.add(customer);
			}
			return customers;
		}

		catch (SQLException e) {
			throw new CouponSystemException("get All customers failed", e);
		}

		finally { // return connection to pool
			if (con != null) {
				ConnectionPool.getInstance().returnConnection(con);
			}
		}
	}

	@Override
	public Collection<Coupon> getCoupons(long id) throws CouponSystemException {
		Connection con = null;
		Collection<Coupon> coupons = new ArrayList<Coupon>();
		try {
			// get connection from pool
			con = ConnectionPool.getInstance().getConnection();
			String sql = "select a.* from coupon a,customer_coupon b where a.id=b.coupon_id and b.cust_id = ?";
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
		}

		finally { // return connection to pool
			if (con != null) {
				ConnectionPool.getInstance().returnConnection(con);
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
			String sql = "select id from customer where cust_name = ? and password = ?";
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
	public boolean checkCustomerNameExist(String custName) throws CouponSystemException {
		Connection con = null;

		try {
			// get connection from pool
			con = ConnectionPool.getInstance().getConnection();
			String sql = "select * from customer where cust_name = ?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, custName);
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

}
