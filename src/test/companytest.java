package test;

import java.util.Calendar;
import java.util.Date;

import coupons.core.beans.Company;
import coupons.core.beans.Coupon;
import coupons.core.beans.CouponType;
import coupons.core.exceptions.CouponSystemException;
import coupons.core.system.CouponSystem;
import coupons.core.system.CouponSystem.ClientType;
import facade.AdminFacade;
import facade.CompanyFacade;

public class companytest {

	public static void main(String[] args) {

		CouponSystem couponSystem = null;
		CompanyFacade compFacade;
		
		Calendar cal = Calendar.getInstance();
		cal.set(2016, Calendar.SEPTEMBER, 11);
		Date startDate = cal.getTime();
		cal.set(2019, Calendar.SEPTEMBER, 28);
		Date endDate = cal.getTime();


		try {
			couponSystem = CouponSystem.getInstance();
			compFacade = (CompanyFacade) couponSystem.login("014H4RW0", "QPWIJC4MS", ClientType.COMPANY);
			
			Coupon cop1 = new Coupon((long) 5546843, "111 coupon", startDate, endDate, 43, CouponType.TRAVELLING, "euro",
					111, null);
//			compFacade.createCoupon(cop1);
//			System.out.println(compFacade.getCoupon(55333222));
//			System.out.println(compFacade.getAllCoupon());
//			compFacade.updateCoupon(cop1);
//			System.out.println(compFacade.getCouponByType(CouponType.HEALTH));
//			System.out.println(compFacade.getCouponUpToPrice(520.0));
			System.out.println(compFacade.getCouponUpToDate(endDate));
			
			
			
			
		} catch (CouponSystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
