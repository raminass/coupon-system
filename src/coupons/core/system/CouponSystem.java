package coupons.core.system;

import java.sql.SQLException;

import coupons.core.dao.CompanyDAO;
import coupons.core.dao.CouponDAO;
import coupons.core.dao.CustomerDAO;
import coupons.core.db.ConnectionPool;
import coupons.core.db.dao.CompanyDBDAO;
import coupons.core.db.dao.CouponDBDAO;
import coupons.core.db.dao.CustomerDBDAO;
import coupons.core.exceptions.CouponSystemException;
import facade.AdminFacade;
import facade.CompanyFacade;
import facade.CouponClientFacade;
import facade.CutomerFacade;

public class CouponSystem {

	public enum ClientType {
		ADMIN, COMPANY, CUSTOMER
	}

	private CompanyDAO companyDAO = new CompanyDBDAO();
	private CustomerDAO customerDAO = new CustomerDBDAO();
	private CouponDAO couponDAO = new CouponDBDAO();
	public static CouponSystem instance = null;
	private DailyCouponExpirationTask dailyCouponExpirationTask = new DailyCouponExpirationTask("clearExpiredThread");

	private CouponSystem() throws CouponSystemException {
		// start connection pool
		ConnectionPool.getInstance(); // in order to see if any issues at the
										// connection pool, should starts at the
										// beginning
		this.dailyCouponExpirationTask.start(); // start coupons cleanup
	}

	public static CouponSystem getInstance() throws CouponSystemException {
		if (instance == null) {
			try {
				System.out.println("new CouponSystem instance created");
				instance = new CouponSystem();
				
			} catch (CouponSystemException e) {
				// TODO Auto-generated catch block
				throw new CouponSystemException("CouponSystem instantiate failed", e);
			}
		}
		return instance;
	}

	public CouponClientFacade login(String name, String password, ClientType clientType) throws CouponSystemException {

		switch (clientType) {
		case ADMIN:

			if (name.equals("admin") && password.equals("1234")) {
				return new AdminFacade();
			} else {
				throw new CouponSystemException("login failed");
			}

		case CUSTOMER:
			long customerID = customerDAO.login(name, password);
			if (customerID > 0) {
				return new CutomerFacade(customerID);
			} else {
				throw new CouponSystemException("incorrect login");
			}

		case COMPANY:
			long companyID = companyDAO.login(name, password);
			if (companyID > 0) {
				return new CompanyFacade(companyID);
			} else {
				throw new CouponSystemException("incorrect login");
			}

		default:
			break;
		}

		return null;
	}
}
