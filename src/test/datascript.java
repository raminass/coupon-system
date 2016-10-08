package test;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import coupons.core.beans.Company;
import coupons.core.beans.Coupon;
import coupons.core.beans.CouponType;
import coupons.core.beans.Customer;
import coupons.core.exceptions.CouponSystemException;
import coupons.core.system.CouponSystem;
import facade.AdminFacade;
import facade.CompanyFacade;
import facade.CutomerFacade;

// creating customers and companies data

public class datascript {
	
	public static void main(String[] args) {
		
		CouponSystem couponSystem = null;
		CutomerFacade customerFacade;
		CompanyFacade companyFacade;
		AdminFacade adminFacade;
//		Calendar cal = Calendar.getInstance();
//		cal.set(2016, Calendar.AUGUST, 25);
//		Date startDate = cal.getTime();
//		cal.set(2019, Calendar.AUGUST, 25);
//		Date endDate = cal.getTime();
//
//		Coupon cop = new Coupon((long) 55342634, "first coupon", startDate, endDate, 4, CouponType.SPORT, "gym coupons",
//				223.4, null);
		Company company = new Company();
		Customer customer = new Customer();
		randomizer randNames = new randomizer();
		try {
			
			
			couponSystem = CouponSystem.getInstance();
			adminFacade = (AdminFacade) couponSystem.login("admin", "1234", CouponSystem.ClientType.ADMIN);
			
			for (int i = 0; i < 100; i++) {
//				company.setId(randNames.randID());
//				company.setCompName(randNames.randomIdentifier());
//				company.setPassword(randNames.randomIdentifier());
//				company.setEmail(randNames.randomIdentifier() + "@" + randNames.randomIdentifier() + ".com");
//				adminFacade.createCompany(company);
				customer.setId(randNames.randID());
				customer.setCustName(randNames.randomIdentifier());
				customer.setPassword(randNames.randomIdentifier());
				adminFacade.createCustomer(customer);
			}
			
			// companyFacade = (CompanyFacade) couponSystem.login("comp1",
			// "uuu123", CouponSystem.ClientType.COMPANY);
			// companyFacade.createCoupon(cop);
//			customerFacade = (CutomerFacade) couponSystem.login("admin", "1111", CouponSystem.ClientType.CUSTOMER);
//			customerFacade.purchaseCoupon(cop);
//			System.out.println(customerFacade.getAllPurchasedCoupons());
		} catch (CouponSystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

}
