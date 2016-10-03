package test;

import java.util.Calendar;
import java.util.Date;

import coupons.core.beans.Coupon;
import coupons.core.beans.CouponType;
import coupons.core.beans.Customer;
import coupons.core.exceptions.CouponSystemException;
import coupons.core.system.CouponSystem;
import coupons.core.system.DailyCouponExpirationTask;
import facade.CompanyFacade;
import facade.CutomerFacade;

public class test1 {
	public static void main(String[] args) {
		
		CouponSystem couponSystem = null;
		CutomerFacade customerFacade  ;
		CompanyFacade companyFacade ;
		Calendar cal = Calendar.getInstance();
		cal.set(2016, Calendar.AUGUST, 25);
		Date startDate = cal.getTime();
		cal.set(2019, Calendar.AUGUST, 25);
		Date endDate = cal.getTime();
		
		Coupon cop = new Coupon((long)55342634,"first coupon", startDate, endDate, 4, CouponType.SPORT, "gym coupons", 223.4, null);
		
		try {
			couponSystem = CouponSystem.getInstance();
//			companyFacade = (CompanyFacade) couponSystem.login("comp1", "uuu123", CouponSystem.ClientType.COMPANY);
//			companyFacade.createCoupon(cop);
			customerFacade = (CutomerFacade) couponSystem.login("admin", "1111", CouponSystem.ClientType.CUSTOMER);
			customerFacade.purchaseCoupon(cop);
			System.out.println(customerFacade.getAllPurchasedCoupons());
		} catch (CouponSystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

	}
}
