package coupons.core.system;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import coupons.core.beans.Coupon;
import coupons.core.dao.CompanyDAO;
import coupons.core.dao.CouponDAO;
import coupons.core.dao.CustomerDAO;
import coupons.core.db.dao.CompanyDBDAO;
import coupons.core.db.dao.CouponDBDAO;
import coupons.core.db.dao.CustomerDBDAO;
import coupons.core.exceptions.CouponSystemException;

public class DailyCouponExpirationTask implements Runnable {

	private CompanyDAO companyDAO = new CompanyDBDAO();
	private CustomerDAO customerDAO = new CustomerDBDAO();
	private CouponDAO couponDAO = new CouponDBDAO();

	private boolean quit;

	private Thread t;
	private String threadName;

	public DailyCouponExpirationTask(String name) {
		super();
		this.threadName = name;
	}

	@Override
	public void run() {
		
		try {
			couponDAO.removeAllExpiredCoupons();
			
		} catch (CouponSystemException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		
		
	}

	public void stopTask() {
		
		t.interrupt();

	}

	public void start() {
		System.out.println("Starting " + threadName);
		if (t == null) {
			t = new Thread(this, threadName);
			t.start();
		}
		System.out.println(threadName + " end here");
	}
	


}
