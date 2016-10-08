package test;

import coupons.core.beans.Company;
import coupons.core.beans.Customer;
import coupons.core.exceptions.CouponSystemException;
import coupons.core.system.CouponSystem;
import facade.AdminFacade;
import facade.CompanyFacade;
import facade.CutomerFacade;

public class admin_tests {
	
	public static void main(String[] args) {
		
		CouponSystem couponSystem = null;
		AdminFacade adminFacade;
		
		try {
			couponSystem = CouponSystem.getInstance();
			adminFacade = (AdminFacade) couponSystem.login("admin", "1234", CouponSystem.ClientType.ADMIN);
			
//			adminFacade.removeCompany(adminFacade.getCompany(358907));
			Company comp1 = adminFacade.getCompany(634771);
//			can't change company name
//			comp1.setCompName("new one");
//			adminFacade.updateCompany(comp1);
			
//			System.out.println(comp1.getCompName());
//			comp1.setPassword("12345");
//			adminFacade.updateCompany(comp1);
//			
//			System.out.println(adminFacade.getCompany(929948));
			
//			System.out.println(adminFacade.getAllCompanies());
			
//			adminFacade.removeCustomer(adminFacade.getCustomer(464245));
			
			Customer cust1 = adminFacade.getCustomer(676641);
			
//			cust1.setCustName("ram");
//			

			System.out.println(adminFacade.getAllCustomers());
		} catch (CouponSystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
