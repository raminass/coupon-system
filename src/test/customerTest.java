package test;

import java.util.Calendar;
import java.util.Date;

import coupons.core.beans.Coupon;
import coupons.core.beans.CouponType;
import coupons.core.exceptions.CouponSystemException;
import coupons.core.system.CouponSystem;
import coupons.core.system.CouponSystem.ClientType;
import facade.CutomerFacade;
public class customerTest {
	
	public static void main(String[] args) {
		
		CouponSystem couponSystem = null;
		CutomerFacade custFacade;
		
		Calendar cal = Calendar.getInstance();
		cal.set(2016, Calendar.SEPTEMBER, 11);
		Date startDate = cal.getTime();
		cal.set(2019, Calendar.SEPTEMBER, 28);
		Date endDate = cal.getTime();


		try {
			couponSystem = CouponSystem.getInstance();
			custFacade = (CutomerFacade) couponSystem.login("A9VLNRVGL", "I11DVNL", ClientType.CUSTOMER);
			
			Coupon cop1 = new Coupon((long) 55333111, "111 coupon", startDate, endDate, 43, CouponType.TRAVELLING, "euro",
					111, null);
			
//			custFacade.purchaseCoupon(cop1);
//			System.out.println(custFacade.getAllPurchasedCoupons());
//			System.out.println(custFacade.getAllPurchasedCouponsByPrice(112.0));
			System.out.println(custFacade.getAllPurchasedCouponsByType(CouponType.TRAVELLING));
			
			
			
			
		} catch (CouponSystemException e) {	
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
