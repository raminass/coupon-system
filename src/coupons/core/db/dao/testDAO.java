package coupons.core.db.dao;

import java.util.Calendar;
import java.util.spi.CurrencyNameProvider;

import coupons.core.beans.Company;
import coupons.core.beans.Coupon;
import coupons.core.beans.CouponType;
import coupons.core.beans.Customer;
import coupons.core.dao.CompanyDAO;
import coupons.core.dao.CouponDAO;
import coupons.core.dao.CustomerDAO;
import coupons.core.exceptions.CouponSystemException;

public class testDAO {

	// insert coupon to the database
	public static void main(String[] args) throws CouponSystemException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {

		// create coupon
		Coupon coupon = new Coupon();
		coupon.setId(1008832);
		coupon.setTitle("test");
		CompanyDAO companyDAO = new CompanyDBDAO();
		CustomerDAO customerDAO = new CustomerDBDAO();
		CouponDAO couponDAO = new CouponDBDAO();
//		Customer customerTest = new Customer();
//		customerTest.setCustName("admin");
//		customerTest.setId(723000);
//		customerTest.setPassword("1111");
		Company company = new Company();
//		company.setCompName("comp1");
//		company.setId(3774923);
//		company.setPassword("uuu123");
//		companyDAO.createCompany(company);
		System.out.println(companyDAO.checkCompNameExist("comp1"));
//		customerDAO.createCustomer(customerTest);
//		System.out.println(customerDAO.login("rami", "98765")); 
//		System.out.println(customerDAO.getCoupons(7237572));
		System.out.println(customerDAO.checkCustomerNameExist("rami"));
//		System.out.println(customerDAO.getCustomer(7237572));
//		System.out.println(couponDAO.getCoupon(1008832));

//		// create specific dates
//		// 25/08/2008
//		// Date date = new Date(2008, 08, 22); // deprecated CTOR
//		Calendar cal = Calendar.getInstance();
//		cal.set(2008, Calendar.AUGUST, 25);
//		coupon.setStartDate(cal.getTime());
//		// 25/08/2009
//		cal.set(2019, Calendar.AUGUST, 25);
//		coupon.setEndDate(cal.getTime());
//		coupon.setType(CouponType.ELECTRICITY);
//		CouponDAO dao = new CouponDBDAO();
//		try {
//			dao.createCoupon(coupon); // insert the coupon to the database
//			dao.updateCompanyCoupon(coupon.getId(), 1230000);
//
//		} catch (CouponSystemException e) {
//			System.out.println(e.getMessage());
//			// e.printStackTrace();
//		}
	}

}
